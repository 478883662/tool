package com.zhangb.family.doctor.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.zhangb.family.common.dto.FamilyFileDto;
import com.zhangb.family.common.entity.FamilyFile;
import com.zhangb.family.common.service.FamilyFileService;
import com.zhangb.family.common.util.ExportWordUtil;
import com.zhangb.family.common.util.PrintUtil;
import com.zhangb.family.doctor.bo.ReimbPrintBo;
import com.zhangb.family.doctor.bo.ReimbUnPrintRecordBo;
import com.zhangb.family.doctor.common.constants.DoctorSingleInstance;
import com.zhangb.family.doctor.common.constants.ReimbConstants;
import com.zhangb.family.doctor.entity.ReimbPrintInfo;
import com.zhangb.family.doctor.service.IDoctorPrintService;
import com.zhangb.family.doctor.service.IReimbService;
import com.zhangb.family.doctor.util.PrintBizUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;

@Service
public class DoctorPrintServiceImpl implements IDoctorPrintService {

    @Autowired
    private IReimbService reimbService;
    @Autowired
    private FamilyFileService familyFileService;
    // 打印机名称
    @Value("${print.name}")
    private String printName;

    @Override
    public void printOne(ReimbUnPrintRecordBo reimbDealRecord) throws Exception {
        if (reimbDealRecord == null) {
            return;
        }
        ReimbPrintBo reimbPrintBo = reimbService.getPrintInfo(reimbDealRecord.getBizId());

        reimbPrintBo.setFamilyLocation(reimbDealRecord.getFamilyLocation());
        //套入模版生成临时word文档   文件名为报销日期+姓名+bizid
        String docFileName = DateUtil.formatDate(reimbDealRecord.getCreatedDate()) + reimbDealRecord.getName() + reimbDealRecord.getBizId();
        //最终待打印的word文档文件路径
        String srcFilePath = ReimbConstants.UN_PRINT_PATH + docFileName + ".doc";
        //1，生成原始word文档
        ExportWordUtil.exportWord(reimbPrintBo, srcFilePath);


        //2，生成有处方图的word文档
        //将图片插入到word文档中
        //处方图文件全路径
        String chuFangImgFileName = ReimbConstants.CHUFANG_PIC_PATH + reimbDealRecord.getIllNessName() + "." + ReimbConstants.PIC_TYPE_PNG;
        //从数据库里取出最新的处方图，然后下载至chuFangImgFileName路径中
        downloadImg(chuFangImgFileName, reimbDealRecord.getIdCard());
        //插入处方图后的新文件名
        String newFileName = docFileName + "_1.doc";
        if (FileUtil.exist(chuFangImgFileName)) {
            PrintUtil.replaceImg(srcFilePath, chuFangImgFileName, ReimbConstants.CHUFANG_IMG_IN_WORD_STR, 480, 260, ReimbConstants.UN_PRINT_PATH + newFileName);
            FileUtil.del(srcFilePath);
            //源文件变成有处方图的文件
            srcFilePath = ReimbConstants.UN_PRINT_PATH + newFileName;
        }

        //3，生成有个人医疗账户图的word文档
        //获取医疗账户图文件名，png或者jpg格式
        String ylCardImgFileName = PrintBizUtil.getYlCardName(reimbDealRecord.getYlCard(), reimbDealRecord.getName()) + "." + ReimbConstants.PIC_TYPE_PNG;
        //从数据库里取出最新的个人医疗账户图，然后下载至ylCardImgFileName路径中
        downloadImg(ylCardImgFileName, reimbDealRecord.getIdCard());
        if (!FileUtil.exist(ylCardImgFileName)) {
            ylCardImgFileName = PrintBizUtil.getYlCardName(reimbDealRecord.getYlCard(), reimbDealRecord.getName()) + "." + ReimbConstants.PIC_TYPE_JPG;
        }
        if (FileUtil.exist(ylCardImgFileName)) {
            newFileName = docFileName + "_2.doc";
            PrintUtil.replaceImg(srcFilePath, ylCardImgFileName, ReimbConstants.YLCARD_IMG_IN_WORD_STR, 480, 170, ReimbConstants.UN_PRINT_PATH + newFileName);
            FileUtil.del(srcFilePath);
            //源文件变成有医疗信息图的文件
            srcFilePath = ReimbConstants.UN_PRINT_PATH + newFileName;
        }
        //打印文件
        printOneFile(srcFilePath, printName);

        //更新状态为已打印
        ReimbPrintInfo reimbPrintInfo = new ReimbPrintInfo();
        reimbPrintInfo.setBizId(reimbDealRecord.getBizId());
        reimbPrintInfo.setPrintState("1002");
        reimbService.savePrintInfo(reimbPrintInfo);
    }

    /**
     * 调用打印机打印单个文件
     *
     * @param srcFilePath
     * @param printName
     */
    private void printOneFile(String srcFilePath, String printName) {
//        调用打印机打印word文档
        PrintUtil.printWord(srcFilePath, printName);
//        删除临时文件
        FileUtil.del(srcFilePath);
    }

    /**
     * 下载图片到本地路径
     *
     * @param filePath
     * @param pkOid
     */
    private void downloadImg(String filePath, String pkOid) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            if (DoctorSingleInstance.INSTANCE.isNewChufang(filePath)) {
                //今日同步过，不用再同步，直接取本地的即可
                return;
            }
            //取出图片
            FamilyFileDto familyFileDto = new FamilyFileDto();
            familyFileDto.setPkOid(pkOid);
            FamilyFile familyFile = familyFileService.getFamilyFile(familyFileDto);
            if (familyFile == null || familyFile.getFile() == null) {
                return;
            }
            byte[] bytes = familyFile.getFile();
            //下载图片到本地文件夹
            bufferedOutputStream = FileUtil.getOutputStream(filePath);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
