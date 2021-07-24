package com.zhangb.family.doctor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.common.util.ChineseNumberUtil;
import com.zhangb.family.doctor.bo.*;
import com.zhangb.family.doctor.common.constants.ReimbConstants;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.common.enums.DoctorPrintStateEnum;
import com.zhangb.family.doctor.common.enums.DoctorReimbResultEnum;
import com.zhangb.family.doctor.entity.*;
import com.zhangb.family.doctor.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbServiceImpl implements IReimbService {

    @Autowired
    private IReimbDealResultService dealResultService;
    @Autowired
    private IReimbUserService reimbUserService;
    @Autowired
    private IReimbSyncService reimbSyncService;
    @Autowired
    private ICxnhRemoteService remoteService;
    @Autowired
    private IReimbRecordService recordService;
    @Autowired
    private IReimbIllnessService illnessService;
    @Autowired
    private IReimbIllnessDrugService illnessDrugService;
    @Autowired
    private IReimbDrugService reimbDrugService;
    @Autowired
    private IDoctorPrintService doctorPrintService;

    @Override
    public String getBizId() throws Exception {
        return remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_GET_BIZID_STRATEGY);
    }

    @Override
    public String bindBizId(String bizId, String selfNo,
                            String name, String ylCard, String illnessNo,
                            String inDate, String outDate) throws Exception {
        return remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_BIND_BIZID_STRATEGY,
                bizId, selfNo, name, ylCard, illnessNo,
                inDate, outDate);
    }

    @Override
    public String saveSelectDrug(String bizId, String drugNo, String drugName, String oneType,
                                 String drugSet, String inDate, String price,
                                 String num, String money) throws Exception {
        String resultStr = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_SELECT_DRUG_STRATEGY,
                bizId, drugNo, drugName, oneType, drugSet, inDate, price, num, money);
        return resultStr;
    }

    @Override
    public ReimbIllnessBo getNextIllness(ReimbUserInfo user) throws Exception {

        //校验用户报销额度是否已超
        BigDecimal userTotal = recordService.getUserReimbTotal(user.getSelfNo());
        //等于400了也不能再报销了
        if (ReimbConstants.USER_TOTAL_YEAR.compareTo(userTotal) <= 0) {
            throw new BizException( "报销额已达上线，报销总额为：" + userTotal);
        }

        ReimbIllnessBo reimbIllnessBo = new ReimbIllnessBo();
        reimbIllnessBo.setName(user.getName());
        reimbIllnessBo.setSelfNo(user.getSelfNo());
        reimbIllnessBo.setYlCard(user.getYlCard());
        reimbIllnessBo.setReleationNum(user.getReleationNum());
        reimbIllnessBo.setCardNo(user.getCardNo());
        reimbIllnessBo.setMasterName(user.getMasterName());
        reimbIllnessBo.setBirthday(user.getBirthday());
        reimbIllnessBo.setIdCard(user.getIdCard());
        reimbIllnessBo.setAge(user.getAge());
        //查询今年来用户在泥家湖的最近的一次报销记录
        ReimbDealRecord niJiaHuRecord = recordService.getLastRecord(user.getSelfNo(), RemoteConstants.YL_LOCATION_NO);
        //查询今年来最近的一次报销记录
        ReimbDealRecord record = recordService.getLastRecord(user.getSelfNo());

        Date outDate = null;
        //1、今年来在泥家湖卫生室没有报销过
        if (niJiaHuRecord == null) {
            //随机获取一种要报销的病例.及出院时间
            ReimbIllness reimbIllness = illnessService.getRandomIllness(user,null);
            reimbIllnessBo.setIllnessName(reimbIllness.getIllnessName());
            reimbIllnessBo.setIllnessNo(reimbIllness.getIllnessNo());
            //第一次报销病例必须要在上半年
            int day = (int) DateUtil.between(DateUtil.beginOfYear(new Date()).toJdkDate(),new Date(), DateUnit.DAY);
            reimbIllnessBo.setOutDate(getRandomDate(1,day>180?180:day));
            if (record != null){
                //报销过，就取最后一次的出院时间
                outDate = record.getOutDate();
                reimbIllnessBo.setOutDate(outDate);
            }
            //从本地库里获取病例的用药列表
            reimbIllnessBo.setReimbDrugBoList(illnessDrugService.getIllnessDrugList(reimbIllness.getIllnessNo()));

        } else { //2、今年来在泥家湖卫生室报销过
            //1、如果是今天就不能在报销了
            if (StrUtil.equals(DateUtil.today(), DateUtil.formatDate(record.getReimbDate()))) {
                throw new Exception(String.format("今日已报销过\r\n", user.getName()));
            }
            String illnessNo = record.getIllNessNo();
            String illnessName = record.getIllNessName();
            Date rOutDate = record.getOutDate();
            //判断最近一次病例报销记录是不是泥家湖卫生室的，若不是，那么接下来的病就得取最近一次泥家湖报销的病。
            if (!StrUtil.equals(RemoteConstants.YL_LOCATION_NO, record.getYlLocationNo())) {
                //若不是泥家湖的报销记录，就取泥家湖最近的报销记录
                illnessNo = niJiaHuRecord.getIllNessNo();
                illnessName = niJiaHuRecord.getIllNessName();
            }
            //查询此病在今年已报销的次数
            int illnessCount = recordService.getIllnessCount(user.getSelfNo(), illnessNo);
            //今年来泥家湖报销此病的次数超过6次，则需要换病
            if (illnessCount >= ReimbConstants.DEFUALT_ILLNESS_COUNT) {
                //需要换一种病，判断当前时间与上次的得病时间是否超过1个月，未超过1个月也暂时不能报销
                long dayBetween = DateUtil.between(rOutDate, new Date(), DateUnit.DAY);
                //随机生成一种新病
                ReimbIllness reimbIllness = illnessService.getRandomIllness(user,illnessNo);
                reimbIllnessBo.setIllnessName(reimbIllness.getIllnessName());
                reimbIllnessBo.setIllnessNo(reimbIllness.getIllnessNo());
                //取第一种病之后30天到今天之间的随机一天
                int randomDay = NumberUtil.generateRandomNumber(ReimbConstants.DEFUALT_ILLNESS_BETWEEN,Integer.valueOf(dayBetween+""),1)[0];;
                //出院时间为上次报销的出院时间+30+n
                reimbIllnessBo.setOutDate(DateUtil.offsetDay(rOutDate,randomDay));
                //从本地库里获取病例的用药列表
                reimbIllnessBo.setReimbDrugBoList(illnessDrugService.getIllnessDrugList(reimbIllness.getIllnessNo()));
            }else{
                //查询远程病例及用药信息
                reimbIllnessBo.setReimbDrugBoList(illnessDrugService.getIllnessDrugList(illnessNo));
                reimbIllnessBo.setIllnessNo(illnessNo);
                reimbIllnessBo.setIllnessName(illnessName);
                reimbIllnessBo.setOutDate(rOutDate);
            }

        }
        return reimbIllnessBo;
    }

    /**
     * 获取一个随机的 1-9月之间的日期
     * @return
     */
    private Date getRandomDate(int begin,int end) {
        int randomDay = NumberUtil.generateRandomNumber(begin,end,1)[0];
        return DateUtil.offsetDay(DateUtil.beginOfYear(new Date()),randomDay);
    }

    @Override
    public DoctorReimbResultEnum reimb(ReimbIllnessBo reimbIllnessBo) throws Exception {
        List<ReimbDrugBo> reimbDrugBoList = reimbIllnessBo.getReimbDrugBoList();
        //获取远端的业务id
        String bizId = getBizId();
        //从本地数据库里获取病例信息
        ReimbIllness reimbIllness = illnessService.getIllness(reimbIllnessBo.getIllnessNo());
        if (reimbIllness == null) {
            throw new Exception(String.format("找不到病例：%s\r\n",  reimbIllnessBo.getIllnessName()));
        }
        //获取今年第一天的日期
        Date fristDayOfYear = DateUtil.beginOfYear(new Date());

        if(DateUtil.compare(reimbIllnessBo.getOutDate(),fristDayOfYear)<0){
            reimbIllnessBo.setOutDate(fristDayOfYear);
        }
        //计算本次的入院时间、出院时间
        Date inDate = DateUtil.offsetDay(reimbIllnessBo.getOutDate(), NumberUtil.parseInt(reimbIllness.getRestDay()));
        Date outDate = DateUtil.offsetDay(inDate, NumberUtil.parseInt(reimbIllness.getHospitalDay()));
        System.out.println(reimbIllnessBo.getName() + " 入--出 院时间为:" + inDate + "---" + outDate);
        //绑定业务id与用户的关系
        bindBizId(bizId, reimbIllnessBo.getSelfNo(), reimbIllnessBo.getName(),
                reimbIllnessBo.getYlCard(), reimbIllnessBo.getIllnessNo(),
                DateUtil.formatDate(inDate), DateUtil.formatDate(outDate));

        //保存选择的药品
        for (ReimbDrugBo reimbDrugBo : reimbDrugBoList) {
            //计算金额 = 单价 x  数量
            String money = NumberUtil.mul(new BigDecimal(reimbDrugBo.getPrice()), NumberUtil.parseInt(reimbDrugBo.getDrugNum())).toString();
            //1 药品编号  2药品名称  3药品细分类  4药品序号  5入院时间 6单价  7数量  8金额
            String saveResult = saveSelectDrug(bizId, reimbDrugBo.getDrugNo(),
                    reimbDrugBo.getDrugName(),
                    reimbDrugBo.getOneType(),
                    reimbDrugBo.getDrugSeq(),
                    DateUtil.formatDate(inDate),
                    reimbDrugBo.getPrice(),
                    reimbDrugBo.getDrugNum(),
                    money);
            System.out.println("保存选择药品结果：" + saveResult);
        }
        //保存试算
        //1 业务id     2 个人编码  3姓名   4医疗账号   5病例编码   6入院日期   7出院日期  8与户主关系
        String result1 = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE_STRATEGY,
                bizId,reimbIllnessBo.getSelfNo(),reimbIllnessBo.getName(),
                reimbIllnessBo.getYlCard(),reimbIllnessBo.getIllnessNo(),
                DateUtil.formatDate(inDate),DateUtil.formatDate(outDate),
                reimbIllnessBo.getReleationNum()
                );

        //1 cardNo    2医疗账号  3户主姓名 4姓名 5生日  6身份证
        // 7年龄   8个人编码  9报销年度
        // 10病例编码  11入院日期 12bizId  13病例名称
        String result2 = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE1_STRATEGY,
                reimbIllnessBo.getCardNo(),reimbIllnessBo.getYlCard(),
                reimbIllnessBo.getMasterName(),reimbIllnessBo.getName(),reimbIllnessBo.getBirthday(),
                reimbIllnessBo.getIdCard(),reimbIllnessBo.getAge(),reimbIllnessBo.getSelfNo(),
                String.valueOf(DateUtil.thisYear()),reimbIllnessBo.getIllnessNo(),
                DateUtil.formatDate(inDate),bizId,reimbIllnessBo.getIllnessName());

        String result3 = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE2_STRATEGY,
                bizId);

        String result4 = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE3_STRATEGY,
                bizId);
        if (StrUtil.isBlank(result4)){
            throw new Exception("试算失败，须登陆客户端操作");
        }
        BigDecimal tryResult = parseResultTrySave(result4);
        if(tryResult.compareTo(BigDecimal.ZERO)>0){
            //正式补偿
            String result = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_STRATEGY,
                    bizId);
            if(StrUtil.equals("0@!@!0@!0@!0@!0@!0@!0@!0@!0@!0@$@$",result)){
                return DoctorReimbResultEnum.SUCCESS;
            }
            throw new Exception("正式补偿失败:"+result);
        }else if(tryResult.compareTo(BigDecimal.ZERO) == 0){
            throw new Exception("今日可报销额度为0");
        }else{
            throw new Exception("试算失败:"+result4);
        }

    }

    @Override
    public ReimbPrintBo getPrintInfo(String bizId) throws Exception {
        String result = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_PRINT_STRATEGY,
                bizId);
        ReimbPrintBo ReimbPrintBo = parsePrintResult(result);
        return ReimbPrintBo;
    }

    @Override
    public void savePrintInfo(ReimbPrintInfo reimbPrintInfo) throws Exception {
        ReimbPrintInfo where  = new ReimbPrintInfo();
        where.setBizId(reimbPrintInfo.getBizId());
        if (BaseDao.count(where)>0){
            BaseDao.updateForValue(reimbPrintInfo,where);
        }else{
            BaseDao.insert(reimbPrintInfo);
        }
    }

    @Override
    public List<ReimbUnPrintRecordBo> getAllUnPrintInfo(String state) throws SQLException {
        List<ReimbUnPrintRecordBo> reimbPrintInfoList = Db.use().query("select t1.BIZ_ID," +
                        "t1.`NAME`,t2.created_date,t1.ILLNESS_NAME,t1.MONEY ," +
                        "t3.family_location,t1.yl_card,t1.yl_location\n" +
                        "from tool_reimb_print_t t2 left join tool_deal_record_t t1 on (t1.BIZ_ID = t2.biz_id)" +
                        "left join tool_patient_t t3 on (  t1.SELF_NO = t3.SELF_NO) WHERE\n" +
                        "    t2.print_state=? and t1.MONEY>0\n" +
                        "ORDER BY t1.`NAME`,t2.created_date  ",
                ReimbUnPrintRecordBo.class,state);
        return reimbPrintInfoList;
    }

    @Override
    public ReimbUnPrintRecordBo getUnPrintInfo(String bizId,String state) throws SQLException {
        List<ReimbUnPrintRecordBo> reimbPrintInfoList = Db.use().query("select t1.BIZ_ID," +
                        "t1.`NAME`,t2.created_date,t1.ILLNESS_NAME,t1.MONEY ," +
                        "t3.family_location,t1.yl_card,t1.yl_location\n" +
                        "from tool_reimb_print_t t2 left join tool_deal_record_t t1 on (t1.BIZ_ID = t2.biz_id)" +
                        "left join tool_patient_t t3 on (  t1.SELF_NO = t3.SELF_NO) WHERE\n" +
                        "    t2.print_state=? and t1.MONEY>0 \n" +
                        "and t1.biz_id = ? ",
                ReimbUnPrintRecordBo.class,state,bizId);
        if (CollectionUtil.isEmpty(reimbPrintInfoList)){
            return null;
        }
        return reimbPrintInfoList.get(0);
    }

    @Override
    public List<DoctorReimbResultBo> reimbForYlCardAndName(String ylCard, String name) {
        List<DoctorReimbResultBo> resultList = new ArrayList<>();
        try{
            //一次只能有一个用户进行报销。
            if (StrUtil.hasBlank(ylCard)) {
                resultList.add(new DoctorReimbResultBo(ylCard,name,DoctorReimbResultEnum.EMPTY_YL_CARD));
                return resultList;
            }
            //报销前先同步医疗账号下的所有人
            reimbSyncService.syncAllByYlCard(ylCard);

            //获取医疗账户下要报销的人
            List<ReimbUserInfo> userList = reimbUserService.getAllUserInfo(new ReimbUserInfo(ylCard,name));
            for (ReimbUserInfo user : userList) {
                //记录每次报销的结果到结果集然后返回给前端
                resultList.add(reimbForUser(user));
            }
        }catch (Exception e){
            resultList.add(new DoctorReimbResultBo(ylCard,name,DoctorReimbResultEnum.REIMB_ERROR));
        }
        return resultList;
    }

    /**
     * 报销单个人
     * @param user
     * @return
     * @throws Exception
     */
    private DoctorReimbResultBo reimbForUser(ReimbUserInfo user) throws Exception {
        DoctorReimbResultBo doctorReimbResultBo = new DoctorReimbResultBo(user.getYlCard(),user.getName());

        ReimbDealResult reimbDealResult = new ReimbDealResult();
        reimbDealResult.setYlCard(user.getYlCard());
        reimbDealResult.setName(user.getName());
        reimbDealResult.setDealTime(DateUtil.now());
        DoctorReimbResultEnum result = null;
        try{
            //获取这次报销的病例
            ReimbIllnessBo reimbIllnessBo = getNextIllness(user);
            if(CollectionUtil.isEmpty(reimbIllnessBo.getReimbDrugBoList())){
                return doctorReimbResultBo.setCodeMsg(DoctorReimbResultEnum.NO_DRUG);
            }
            //报销处理
            result = reimb(reimbIllnessBo);
            reimbDealResult.setDealResult(result.getMsg());
            reimbDealResult.setDealCode(result.getCode());
            if (!result.getCode().equals(DoctorReimbResultEnum.SUCCESS.getCode())){
                //报销失败处理
                return doctorReimbResultBo.setCodeMsg(result.getCode(),result.getMsg());
            }
            //可能报销成功：报销结束了就同步一次远端报销记录到本地库
            String bizId = recordService.syncRecord(user.getSelfNo());
            if (bizId==null){
                return doctorReimbResultBo.setCodeMsg(DoctorReimbResultEnum.REIMB_FAIL);
            }


            //保存待打印记录
            ReimbPrintInfo reimbPrintInfo = new ReimbPrintInfo();
            reimbPrintInfo.setBizId(bizId);
            reimbPrintInfo.setCreatedDate(new Date());
            reimbPrintInfo.setPrintState(DoctorPrintStateEnum.UN_PRINT.getCode());
            savePrintInfo(reimbPrintInfo);

            //查询待打印记录
            ReimbUnPrintRecordBo reimbUnPrintRecordBo = getUnPrintInfo(bizId,DoctorPrintStateEnum.UN_PRINT.getCode());
            doctorPrintService.printOne(reimbUnPrintRecordBo);
        }catch (Exception e){
            e.printStackTrace();
            if (result!=null){
                reimbDealResult.setDealResult(result.getMsg());
                reimbDealResult.setDealCode(result.getCode());
            }else {
                reimbDealResult.setDealResult(e.getMessage());
                reimbDealResult.setDealCode("9999");
            }
        }finally {
            dealResultService.saveDealResult(reimbDealResult);
        }
        return doctorReimbResultBo.setCodeMsg(reimbDealResult.getDealCode(),reimbDealResult.getDealResult());
    }


    /**
     * 解析打印信息到封装类中
     * @param result
     * @return
     */
    private ReimbPrintBo parsePrintResult(String result) {
        if (StrUtil.isBlank(result)){
            return null;
        }
        ReimbPrintBo reimbPrintBo = new ReimbPrintBo();
        String[] cols = StrUtil.split(result,"\t");
        reimbPrintBo.setName(cols[2]);
        reimbPrintBo.setSex(cols[3]);
        reimbPrintBo.setAge(cols[0]);
        reimbPrintBo.setIdCard(cols[5]);
        reimbPrintBo.setYlCard(cols[6]);
        //待定--为空
        reimbPrintBo.setZhuYuanHao("");
        reimbPrintBo.setReimbType(cols[46]);
        //家庭住址 取本地库
        reimbPrintBo.setFamilyLocation("");
        reimbPrintBo.setInDate(DateUtil.formatDate(DateUtil.parseDate(cols[11])));
        reimbPrintBo.setOutDate(DateUtil.formatDate(DateUtil.parseDate(cols[10])));
        reimbPrintBo.setZhuYuanDay(cols[1]);
        //出院诊断
        reimbPrintBo.setIllnessName(StrUtil.sub(cols[12],0,12));

        reimbPrintBo.setChuangWeiYlFee(NumberUtil.round(cols[15],2).toString());
        reimbPrintBo.setHuLiYlFee(NumberUtil.round(cols[16],2).toString());
        reimbPrintBo.setXiYaoYlFee(NumberUtil.round(cols[17],2).toString());
        reimbPrintBo.setZhongYaoYlFee(NumberUtil.round(cols[18],2).toString());
        reimbPrintBo.setHuaYanYlFee(NumberUtil.round(cols[19],2).toString());
        reimbPrintBo.setZhenLiaoYlFee(NumberUtil.round(cols[20],2).toString());
        reimbPrintBo.setShouShuYlFee(NumberUtil.round(cols[21],2).toString());
        reimbPrintBo.setJianChaYlFee(NumberUtil.round(cols[22],2).toString());
        reimbPrintBo.setOthterYlFee(NumberUtil.round(cols[23],2).toString());
        //计算医疗总计费用
        BigDecimal totalYlFee = BigDecimal.ZERO.add(NumberUtil.round(cols[15],2))
                                                .add(NumberUtil.round(cols[16],2))
                                                .add(NumberUtil.round(cols[17],2))
                                                .add(NumberUtil.round(cols[18],2))
                                                .add(NumberUtil.round(cols[19],2))
                                                .add(NumberUtil.round(cols[20],2))
                                                .add(NumberUtil.round(cols[21],2))
                                                .add(NumberUtil.round(cols[22],2))
                                                .add(NumberUtil.round(cols[23],2)) ;
        reimbPrintBo.setTotalYlFee(totalYlFee.toString());


        reimbPrintBo.setChuangWeiCanFee(NumberUtil.round(cols[24],2).toString());
        reimbPrintBo.setHuLiCanFee(NumberUtil.round(cols[25],2).toString());
        reimbPrintBo.setXiYaoCanFee(NumberUtil.round(cols[26],2).toString());
        reimbPrintBo.setZhongYaoCanFee(NumberUtil.round(cols[27],2).toString());
        reimbPrintBo.setHuaYanCanFee(NumberUtil.round(cols[28],2).toString());
        reimbPrintBo.setZhenLiaoCanFee(NumberUtil.round(cols[29],2).toString());
        reimbPrintBo.setShouShuCanFee(NumberUtil.round(cols[30],2).toString());
        reimbPrintBo.setJianChaCanFee(NumberUtil.round(cols[31],2).toString());
        reimbPrintBo.setOtherCanFee(NumberUtil.round(cols[32],2).toString());

        //计算可报销总计
        BigDecimal totalCanFee = BigDecimal.ZERO.add(NumberUtil.round(cols[24],2))
                .add(NumberUtil.round(cols[25],2))
                .add(NumberUtil.round(cols[26],2))
                .add(NumberUtil.round(cols[27],2))
                .add(NumberUtil.round(cols[28],2))
                .add(NumberUtil.round(cols[29],2))
                .add(NumberUtil.round(cols[30],2))
                .add(NumberUtil.round(cols[31],2))
                .add(NumberUtil.round(cols[32],2)) ;
        reimbPrintBo.setTotalCanFee(totalCanFee.toString());
        reimbPrintBo.setHeSuanLocation(cols[51]);
        reimbPrintBo.setHeSuanPerson(cols[33]);
        reimbPrintBo.setHeSuanMoney(NumberUtil.round(cols[40],2).toString());
        reimbPrintBo.setZengJianReimbMoney(NumberUtil.round(cols[47],2).toString());
        reimbPrintBo.setActualReimbMoney(NumberUtil.round(cols[40],2).toString());
        reimbPrintBo.setActualReimbMoneyCn(ChineseNumberUtil.getChineseNumber(cols[40]));
        reimbPrintBo.setIsTopTotal(cols[53]);
        reimbPrintBo.setHeSuanDate(DateUtil.formatDate(DateUtil.parseDate(cols[50])));
        reimbPrintBo.setReimbNo(cols[41]);
        reimbPrintBo.setJgLevel(cols[42]);
        reimbPrintBo.setYlJg(cols[14]);
        reimbPrintBo.setHuAttr(cols[59]);
        return reimbPrintBo;
    }

    /**
     * 解析试算结果
     * @param result4
     * @return
     */
    private BigDecimal parseResultTrySave(String result4) {
        try {
            //031	门诊统筹帐户	32007126	1	35
            String[] cols = StrUtil.split(result4,"\t");
            return new BigDecimal (cols[4].trim());
        }catch (Exception e){
            System.out.println(result4);
            e.printStackTrace();
            return new BigDecimal(-1);
        }
    }
}
