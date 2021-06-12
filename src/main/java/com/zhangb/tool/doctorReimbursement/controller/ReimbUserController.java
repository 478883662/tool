package com.zhangb.tool.doctorReimbursement.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.tool.doctorReimbursement.bo.ReimbIllnessBo;
import com.zhangb.tool.doctorReimbursement.bo.ReimbUserBo;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbConstants;
import com.zhangb.tool.doctorReimbursement.common.constants.RemoteConstants;
import com.zhangb.tool.doctorReimbursement.entity.ReimbDealResult;
import com.zhangb.tool.doctorReimbursement.entity.ReimbPrintInfo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbUserInfo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbYlCard;
import com.zhangb.tool.doctorReimbursement.service.*;
import com.zhangb.tool.doctorReimbursement.util.PrintBizUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reimbUser")
public class ReimbUserController {

    @Autowired
    private IReimbUserService userService;
    @Autowired
    private IReimbRecordService recordService;
    @Autowired
    private IReimbSyncService syncService;
    @Autowired
    private IReimbService reimbService;
    @Autowired
    private IReimbDealResultService dealResultService;
    // 打印机名称
    @Value("${print.name}")
    private String printName;

    @RequestMapping("/getAllYlCard")
    @ResponseBody
    public List<ReimbYlCard> getAllYlCard() throws Exception {
        List<ReimbYlCard> ylCardList = userService.getAllYlCard();
        return CollectionUtil.reverse(ylCardList);
    }


    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<ReimbUserBo> getAllUser() throws Exception {
        List<ReimbUserBo> userList=userService.getUserBoList();
        userList.forEach(e->{
            String fileName = PrintBizUtil.getYlCardName(e.getYlCard(),e.getName())+"."+ReimbConstants.PIC_TYPE_PNG;
            if (FileUtil.exist(fileName)){
                e.setFilePath(e.getYlCard()+e.getName()+"."+ReimbConstants.PIC_TYPE_PNG);
            }else{
                e.setFilePath(e.getYlCard()+e.getName()+"."+ReimbConstants.PIC_TYPE_JPG);
            }

        });
        return userList;
    }

    @RequestMapping("/getReimbTotal")
    @ResponseBody
    public Map<String,BigDecimal> getReimbTotal() throws Exception {
        Map<String,BigDecimal> resultMap = CollectionUtil.newHashMap();
        BigDecimal ylTotal_2020 = recordService.getYlReimbTotal(RemoteConstants.YL_LOCATION_NO,2020);
        BigDecimal ylTotal_2021 = recordService.getYlReimbTotal(RemoteConstants.YL_LOCATION_NO,2021);
        resultMap.put("ylTotal_2020",ylTotal_2020);
        resultMap.put("ylTotal_2021",ylTotal_2021);
        return resultMap;
    }

    /**
     * 全量同步所有信息，一天只允许同步一次
     * @throws Exception
     */
    @RequestMapping("/syncAll")
    @ResponseBody
    public String syncAll() throws Exception {
        return syncService.syncAll();
    }

    /**
     * 手工添加医疗账户
     * http://localhost:8085/reimbUser/addYlCard?ylCard=4306210121011214&masterName=欧凤华
     *
     * @param ylCard
     * @return
     * @throws Exception
     */
    @RequestMapping("/addYlCard")
    @ResponseBody
    public String addYlCard(@RequestParam(value = "ylCard", required = true) String ylCard) throws Exception {
        if (StrUtil.hasBlank(ylCard)) {
            return "FAIL:医疗账号不能为空";
        }
        ReimbYlCard reimbYlCard = new ReimbYlCard();
        reimbYlCard.setYlCard(ylCard);
        String result = syncService.syncUserInfo(ylCard);
        syncService.syncRecordInfo(ylCard,null);
        if(StrUtil.startWith(result,"FAIL")){
            return result;
        }
        //只要不是FAIL开头就是正常业务返回
        reimbYlCard.setMasterName(result);
        userService.addYlCard(reimbYlCard);
        return "保存医疗账户成功";
    }



    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/reimbursement?ylCard=4306210121011214&name=欧凤华
     *
     * @param ylCard 医疗账户
     * @param name   报销人
     * @return
     * @throws Exception
     */
    @RequestMapping("/reimbursement")
    @ResponseBody
    public String reimbursement(@RequestParam(value = "ylCard", required = true) String ylCard,
                                @RequestParam(value = "name", required = false) String name) throws Exception {
        return reimbOneUser(ylCard, name);
    }

    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/upload.jsp?ylCard=4306210121011214&name=欧凤华
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws Exception {
        String ylCard = request.getParameter("ylCard");
        String name = request.getParameter("name");
        String fileName=ylCard+name;
        if (file.isEmpty()) {
            throw new Exception("上传文件为空");
        }
        String srcFileName = file.getOriginalFilename();
        String fileType = srcFileName.substring(srcFileName.lastIndexOf(".")+1);
        if (!StrUtil.equals(fileType,ReimbConstants.PIC_TYPE_PNG)
                && !StrUtil.equals(fileType,ReimbConstants.PIC_TYPE_JPG)){
            throw new Exception("文件类型只能是png、jpg");
        }
        String filePath = ReimbConstants.PIC_FILE_PATH; // 上传后的路径
        File dest = new File(filePath + fileName+"."+fileType);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传成功";
    }
    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/reimbursement?ylCard=4306210121011214&name=欧凤华
     *
     * @param paramMap 医疗账户_报销人
     * @return
     * @throws Exception
     */
    @RequestMapping("/batchReimbursement")
    @ResponseBody
    public List<String> batchReimbursement(@RequestBody Map<String,String> paramMap) throws Exception {
//        reimbOneUser(ylCard, name);
        String users = paramMap.get("users");
        if(StrUtil.hasBlank(users)){
            return CollectionUtil.newArrayList("请选择要报销的人！");
        }
        List<String> resultMap = CollectionUtil.newArrayList();
        String[] paramStrs = StrUtil.split(users,",");
        for(String paramStr :paramStrs){
            String[] params = StrUtil.split(paramStr,"_");
            resultMap.add(reimbOneUser(params[0], params[1]));
        }
        return resultMap;
    }

    /**
     * 报销单人
     * @param ylCard
     * @param name
     * @return
     * @throws Exception
     */
    private String reimbOneUser(String ylCard,String name) throws Exception {
        try{
            //一次只能有一个用户进行报销。
            if (StrUtil.hasBlank(ylCard)) {
                return "入参医疗账号不能为空";
            }
            //报销前先同步
            syncService.syncUserInfo(ylCard);
            syncService.syncRecordInfo(ylCard,name);
            //校验医疗机构今年报销总额是否已超
//            BigDecimal ylTotal = recordService.getYlReimbTotal(RemoteConstants.YL_LOCATION_NO);
//            if (ReimbConstants.YL_TOTAL_YEAR.compareTo(ylTotal) < 0) {
//                return "泥家湖卫生室报销总额已达上线无法继续报销，当前报销总额为：" + ylTotal;
//            }

            Map<String, String> resultMap = CollectionUtil.newHashMap();
            //获取医疗账户下要报销的人
            ReimbUserInfo param = new ReimbUserInfo();
            param.setYlCard(ylCard);
            if (!StrUtil.hasBlank(name)){
                //入参中有名字，就会按名字精确查询
                param.setName(name);
            }
            List<ReimbUserInfo> userList = userService.getAllUserInfo(param);
            for (ReimbUserInfo user : userList) {
                ReimbDealResult reimbDealResult = new ReimbDealResult();
                reimbDealResult.setYlCard(ylCard);
                reimbDealResult.setName(user.getName());
                reimbDealResult.setDealTime(DateUtil.now());
                try{
                    //获取这次报销的病例
                    ReimbIllnessBo reimbIllnessBo = reimbService.getNextIllness(user);
                    if(CollectionUtil.isEmpty(reimbIllnessBo.getReimbDrugBoList())){
                        throw new Exception(reimbIllnessBo.getIllnessName()+":没有找到用药处方");
                    }
                    //报销处理
                    String result = reimbService.reimb(reimbIllnessBo);
                    resultMap.put(user.getName(),result+"\r\n");
                    //报销结束了就同步一次远端报销记录到本地库
                    String bizId = recordService.syncRecord(user.getSelfNo());
                    if (bizId==null){
                        System.out.println("本地仍没有报销记录");
                        continue;
                    }
                    ReimbPrintInfo reimbPrintInfo = new ReimbPrintInfo();
                    reimbPrintInfo.setBizId(bizId);
                    reimbPrintInfo.setCreatedDate(new Date());
                    reimbPrintInfo.setPrintState("1001");
                    reimbService.savePrintInfo(reimbPrintInfo);
                    reimbDealResult.setDealResult("报销成功");
                }catch (Exception e){
                    e.printStackTrace();
                    resultMap.put(user.getName(),e.getMessage()+"\r\n");
                    reimbDealResult.setDealResult(e.getMessage());
                } finally {
                    dealResultService.saveDealResult(reimbDealResult);
                }

            }
            return resultMap.toString();
        }catch (Exception e){
            return  e.getMessage();
        }
    }
}
