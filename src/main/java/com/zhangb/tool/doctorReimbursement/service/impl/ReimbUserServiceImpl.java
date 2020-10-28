package com.zhangb.tool.doctorReimbursement.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import com.zhangb.tool.common.dao.BaseDao;
import com.zhangb.tool.doctorReimbursement.bo.ReimbUserBo;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.tool.doctorReimbursement.common.constants.RemoteConstants;
import com.zhangb.tool.doctorReimbursement.entity.ReimbUserInfo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbYlCard;
import com.zhangb.tool.doctorReimbursement.remote.service.ICxnhRemoteService;
import com.zhangb.tool.doctorReimbursement.service.IReimbRecordService;
import com.zhangb.tool.doctorReimbursement.service.IReimbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z9104 on 2020/9/24.
 */
@Service("reimbUserServiceImpl")
public class ReimbUserServiceImpl implements IReimbUserService {

    @Autowired
    private ICxnhRemoteService remoteService;


    @Override
    public String syncUserByName(String name) throws Exception {
        //远程调用长信农合获取用户信息
        String result = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_GET_USER_BY_NAME_STRATEGY,name);
        if(StrUtil.hasBlank(result)){
            return "获取长信农合用户信息为空";
        }
        List<ReimbUserInfo> resultList = parseResult(result);
        for(ReimbUserInfo reimbUserInfo:resultList){
            //先查询数量
            ReimbUserInfo where = new ReimbUserInfo();
            where.setIdCard(reimbUserInfo.getIdCard());
            int count = BaseDao.count(where);
            //数据库里存在就只更新值
            if(count>0){
                BaseDao.update(reimbUserInfo,where);
            }else{//不存在就插入一条新记录
                BaseDao.insert(reimbUserInfo);
            }
        }
        return result;
    }


    @Override
    public String syncUserByYlCard(String ylCard) throws Exception {
        //远程调用长信农合获取用户信息
        String result = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_GET_USER_BY_YLCARD_STRATEGY,ylCard);
        System.out.println("同步医疗账号【"+ylCard+"】:"+result);
        if(StrUtil.hasBlank(result)){
            return "获取长信农合用户信息为空";
        }
        List<ReimbUserInfo> resultList = parseResult(result);
        for(ReimbUserInfo reimbUserInfo:resultList){
            try{
                //先查询数量
                ReimbUserInfo where = new ReimbUserInfo();
                where.setIdCard(reimbUserInfo.getIdCard());
                int count = BaseDao.count(where);
                //数据库里存在就只更新值
                if(count>0){
                    BaseDao.update(reimbUserInfo,where);
                }else{//不存在就插入一条新记录
                    BaseDao.insert(reimbUserInfo);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "同步用户信息完成";
    }

    @Override
    public List<ReimbUserInfo> getAllUserInfo(ReimbUserInfo reimbUserInfo) throws SQLException, IllegalAccessException {
        return BaseDao.select(reimbUserInfo,ReimbUserInfo.class);
    }

    @Override
    public List<ReimbYlCard> getAllYlCard() throws SQLException, IllegalAccessException {
        return BaseDao.select(new ReimbYlCard(),ReimbYlCard.class);
    }

    @Override
    public void addYlCard(ReimbYlCard reimbYlCard) throws SQLException, IllegalAccessException {
        ReimbYlCard where = new ReimbYlCard();
        where.setYlCard(reimbYlCard.getYlCard());
        int count = BaseDao.count(where);
        if(count>0){
            BaseDao.update(reimbYlCard,where);
        }else{
            BaseDao.insert(reimbYlCard);
        }
    }

    @Override
    public List<ReimbUserBo> getUserBoList() throws SQLException {
        String today = DateUtil.today();
        int year = DateUtil.thisYear();
        String sql ="select\n" +
                "        t1.YL_CARD,\n" +
                "        t1.`NAME`,\n" +
                "        t1.MASTER_NAME,\n" +
                "        IFNULL((select\n" +
                "            t2.sync_Date \n" +
                "        from\n" +
                "            tool_sync_record_t t2  \n" +
                "        where\n" +
                "            t2.id = t1.SELF_NO \n" +
                "            and t2.sync_type='REIMB_RECORD'  \n" +
                "            and t2.sync_Date=? \n" +
                "        order by\n" +
                "            t2.create_date desc limit 0,\n" +
                "            1),\n" +
                "        '') sync_Date,\n" +
                "        IFNULL((select\n" +
                "            t3.REIMB_DATE \n" +
                "        from\n" +
                "            tool_deal_record_t t3 \n" +
                "        where\n" +
                "            t1.YL_CARD =t3.YL_CARD \n" +
                "            and t1.`NAME` = t3.`NAME`  \n" +
                "        order by\n" +
                "            t3.REIMB_DATE desc limit 0,\n" +
                "            1),\n" +
                "        '') REIMB_DATE,\n" +
                "        IFNULL(t4.deal_result,\n" +
                "        '') deal_result,\n" +
                "      (select sum(MONEY) from tool_deal_record_t where self_no = t1.SELF_NO and YL_CARD=t1.YL_CARD and REIMB_YEAR = ? AND REIMB_TYPE='1101') remib_total\n" +
                "     from\n" +
                "        tool_patient_t t1 \n" +
                "    left join\n" +
                "        tool_deal_result_t t4 \n" +
                "            on (\n" +
                "                t1.yl_card = t4.yl_card \n" +
                "                and t1.name = t4.name\n" +
                "            ) \n" +
                "    order by\n" +
                "        t1.yl_card,t1.`NAME`";
        return Db.use().query(  sql,
                ReimbUserBo.class,today,year);
    }


    private List<ReimbUserInfo> parseResult(String result){
        List<ReimbUserInfo> reimbUserInfoList = new ArrayList<>();
        //0@!@$0121011214 4306210121011214 欧凤华 1214 04 4 章婧 2 2020/5/25 0:00:00 430621202005250042 0 723053649 城关镇-&gt;荣站居委会-&gt;1组 1 1 93630709 2020 430621012101 0 0 0 0 0 0 20200525-20201231
        //每行一个用户信息
        String[] rowList = StrUtil.split(result,"\r\n");
        for (String row:rowList){
            ReimbUserInfo reimbUserInfo = new ReimbUserInfo();
            String[] resultList = StrUtil.split(row,"\t");
            reimbUserInfo.setCardNo(resultList[0]);
            reimbUserInfo.setYlCard(resultList[1]);
            reimbUserInfo.setMasterName(resultList[2]);
            reimbUserInfo.setSufId(resultList[3]);
            reimbUserInfo.setSeq(resultList[4]);
            reimbUserInfo.setReleationNum(resultList[5]);
            reimbUserInfo.setName(resultList[6]);
            reimbUserInfo.setSex(resultList[7]);
            reimbUserInfo.setBirthday(resultList[8]);
            reimbUserInfo.setIdCard(resultList[9]);
            reimbUserInfo.setAge(resultList[10]);
            reimbUserInfo.setSelfNo(resultList[11]);
            reimbUserInfo.setPreId(resultList[17]);
            reimbUserInfoList.add(reimbUserInfo);
        }
        return reimbUserInfoList;
    }
}
