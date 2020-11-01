package com.zhangb.tool.doctorReimbursement.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.tool.common.util.ExportWordUtil;
import com.zhangb.tool.common.util.PrintUtil;
import com.zhangb.tool.doctorReimbursement.bo.ReimbPrintBo;
import com.zhangb.tool.doctorReimbursement.bo.ReimbUnPrintRecordBo;
import com.zhangb.tool.doctorReimbursement.bo.ReimbUserBo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbPrintInfo;
import com.zhangb.tool.doctorReimbursement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/print")
public class ReimbPrintController {

    @Autowired
    private IReimbUserService userService;
    @Autowired
    private IReimbService reimbService;
    // 打印机名称
    @Value("${print.name}")
    private String printName;

    @RequestMapping("/getAllPrint")
    @ResponseBody
    public List<ReimbUnPrintRecordBo> getAllPrint() throws Exception {
        List<ReimbUnPrintRecordBo> reimbPrintInfoList = reimbService.getAllUnPrintInfo();
        return reimbPrintInfoList;
    }

    @RequestMapping("/printAll")
    @ResponseBody
    public void print(@RequestParam(value = "name", required = false) String name) throws Exception {
        if (StrUtil.isNotBlank(name)){
            printName = name;
        }
        System.out.println("开始打印，打印机名："+printName);
        //TODO 打印所有未打印的记录
        List<ReimbUnPrintRecordBo> reimbPrintInfoList = reimbService.getAllUnPrintInfo();
        for (ReimbUnPrintRecordBo reimbDealRecord:reimbPrintInfoList){
            ReimbPrintBo reimbPrintBo =reimbService.getPrintInfo(reimbDealRecord.getBizId());
            reimbPrintBo.setFamilyLocation(reimbDealRecord.getFamilyLocation());
            //套入模版生成临时word文档
            String filePath ="D:\\temp\\reimb_"+reimbDealRecord.getBizId()+".doc";
            ExportWordUtil.exportWord(reimbPrintBo,filePath);
            //调用打印机打印word文档
            PrintUtil.printWord(filePath,printName);
            //删除临时文件
            FileUtil.del(filePath);
            //更新状态为已打印
            ReimbPrintInfo reimbPrintInfo = new ReimbPrintInfo();
            reimbPrintInfo.setBizId(reimbDealRecord.getBizId());
            reimbPrintInfo.setPrintState("1002");
            reimbService.savePrintInfo(reimbPrintInfo);
        }
        return;
    }

}
