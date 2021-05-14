package com.zhangb.tool.doctorReimbursement.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.tool.common.util.ExportWordUtil;
import com.zhangb.tool.common.util.PrintUtil;
import com.zhangb.tool.doctorReimbursement.bo.ReimbPrintBo;
import com.zhangb.tool.doctorReimbursement.bo.ReimbUnPrintRecordBo;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbConstants;
import com.zhangb.tool.doctorReimbursement.entity.ReimbPrintInfo;
import com.zhangb.tool.doctorReimbursement.service.*;
import com.zhangb.tool.doctorReimbursement.util.PrintBizUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ReimbUnPrintRecordBo> getAllPrint(@RequestParam(value = "state", required = false) String state) throws Exception {
        List<ReimbUnPrintRecordBo> reimbPrintInfoList = reimbService.getAllUnPrintInfo(state);
        reimbPrintInfoList.forEach(e->{
            String fileName = PrintBizUtil.getYlCardName(e.getYlCard(),e.getName())+"."+ReimbConstants.PIC_TYPE_PNG;
            if (FileUtil.exist(fileName)){
                e.setFilePath(e.getYlCard()+e.getName()+"."+ReimbConstants.PIC_TYPE_PNG);
            }else{
                e.setFilePath(e.getYlCard()+e.getName()+"."+ReimbConstants.PIC_TYPE_JPG);
            }
        });
        return reimbPrintInfoList;
    }

    @RequestMapping("/printAll")
    @ResponseBody
    public String printAll(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "state", required = false) String state) throws Exception {
        if (StrUtil.isNotBlank(name)){
            printName = name;
        }
        //TODO 打印所有未打印的记录
        List<ReimbUnPrintRecordBo> reimbPrintInfoList = reimbService.getAllUnPrintInfo(state);
        for (ReimbUnPrintRecordBo reimbDealRecord:reimbPrintInfoList){
            try{
                printOne(reimbDealRecord);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return "生成word文档完成";
    }


    @RequestMapping("/printOne")
    @ResponseBody
    private void printOne(ReimbUnPrintRecordBo reimbDealRecord) throws Exception {
        if (reimbDealRecord == null){
            return;
        }
        ReimbPrintBo reimbPrintBo =reimbService.getPrintInfo(reimbDealRecord.getBizId());

        reimbPrintBo.setFamilyLocation(reimbDealRecord.getFamilyLocation());
        //套入模版生成临时word文档   文件名为报销日期+姓名+bizid
        String docFileName = DateUtil.formatDate(reimbDealRecord.getCreatedDate())+reimbDealRecord.getName()+reimbDealRecord.getBizId();
        //原始word文档文件路径
        String srcFilePath =ReimbConstants.UN_PRINT_PATH + docFileName +".doc";
        //生成原始word文档
        ExportWordUtil.exportWord(reimbPrintBo,srcFilePath);

        //获取医疗账户图文件名，png或者jpg格式
        String ylCardImgFileName = PrintBizUtil.getYlCardName(reimbDealRecord.getYlCard(),reimbDealRecord.getName())+"."+ReimbConstants.PIC_TYPE_PNG;
        if (!FileUtil.exist(ylCardImgFileName)){
            ylCardImgFileName = PrintBizUtil.getYlCardName(reimbDealRecord.getYlCard(),reimbDealRecord.getName())+"."+ReimbConstants.PIC_TYPE_JPG;
        }

        //将图片插入到word文档中
        //处方图文件全路径
        String chuFangImgFileName = ReimbConstants.CHUFANG_PIC_PATH+reimbDealRecord.getIllNessName()+"."+ReimbConstants.PIC_TYPE_PNG;
        //插入处方图后的新文件名
        String newFileName=docFileName+"_1.doc";
        if(FileUtil.exist(chuFangImgFileName)){
            PrintUtil.replaceImg(srcFilePath,chuFangImgFileName, ReimbConstants.CHUFANG_IMG_IN_WORD_STR,480,260,ReimbConstants.UN_PRINT_PATH+newFileName);
            FileUtil.del(srcFilePath);
            //源文件变成有处方图的文件
            srcFilePath = ReimbConstants.UN_PRINT_PATH+newFileName;
        }
        if(FileUtil.exist(ylCardImgFileName)){
            newFileName = docFileName+"_2.doc";
            PrintUtil.replaceImg(srcFilePath,ylCardImgFileName,ReimbConstants.YLCARD_IMG_IN_WORD_STR,480,170,ReimbConstants.UN_PRINT_PATH+newFileName);
            FileUtil.del(srcFilePath);
            //源文件变成有医疗信息图的文件
        }
        //更新状态为已打印
        ReimbPrintInfo reimbPrintInfo = new ReimbPrintInfo();
        reimbPrintInfo.setBizId(reimbDealRecord.getBizId());
        reimbPrintInfo.setPrintState("1002");
        reimbService.savePrintInfo(reimbPrintInfo);
    }


    @RequestMapping("/getAllWord")
    @ResponseBody
    public List<String> getAllWord() throws Exception {
        String fFilePath = ReimbConstants.UN_PRINT_PATH;
        List<String> fileList = FileUtil.listFileNames(fFilePath);
        fileList = fileList.stream().map(e->{
           return fFilePath+e;
        }).collect(Collectors.toList());
        //列出文件夹下的所有文件
        return fileList;
    }

    /**
     * 打印单个文件
     * @param filePath
     * @return
     * @throws Exception
     */
    @RequestMapping("/printOneFile")
    @ResponseBody
    public String printOneFile(@RequestParam(value = "filePath", required = true) String filePath) throws Exception {
        printOneFile(filePath,printName);
        return "打印成功";
    }

    /**
     * 打印所有文件
     * @return
     * @throws Exception
     */
    @RequestMapping("/printAllFile")
    @ResponseBody
    public String printAllFile() throws Exception {
        //列出文件夹下的所有文件
        List<String> fileList = FileUtil.listFileNames(ReimbConstants.UN_PRINT_PATH);
        for (String fileName : fileList){
            try{
                printOneFile(ReimbConstants.UN_PRINT_PATH+fileName,printName);
            }catch (Exception e){

            }
        }
        return null;
    }

    /**
     * 打印单个文件
     * @param srcFilePath
     * @param printName
     */
    private void printOneFile(String srcFilePath,String printName){
//        调用打印机打印word文档
        PrintUtil.printWord(srcFilePath,printName);
//        删除临时文件
        FileUtil.del(srcFilePath);
    }

}
