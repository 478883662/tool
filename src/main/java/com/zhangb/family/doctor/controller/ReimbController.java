package com.zhangb.family.doctor.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ExportWordUtil;
import com.zhangb.family.common.util.PrintUtil;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.bo.ReimbPrintBo;
import com.zhangb.family.doctor.bo.ReimbUnPrintRecordBo;
import com.zhangb.family.doctor.bo.ReimbUserBo;
import com.zhangb.family.doctor.bo.ReimbYearTotalBo;
import com.zhangb.family.doctor.common.constants.ReimbConstants;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.entity.ReimbPrintInfo;
import com.zhangb.family.doctor.service.IReimbRecordService;
import com.zhangb.family.doctor.service.IReimbService;
import com.zhangb.family.doctor.util.PrintBizUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/reimb")
public class ReimbController {

    @Autowired
    private IReimbRecordService recordService;
    @Autowired
    private IReimbService reimbService;
    // 打印机名称
    @Value("${print.name}")
    private String printName;


    /**
     * 查询年度累计报销金额
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReimbTotal")
    @ResponseBody
    public ViewData getReimbTotal() throws Exception {

        List<ReimbYearTotalBo> resultList = ListUtil.list(false);
        for (int i = 2020; i <= DateUtil.thisYear(); i++) {
            ReimbYearTotalBo reimbYearTotalBo = new ReimbYearTotalBo();
            reimbYearTotalBo.setYear(i);
            reimbYearTotalBo.setTotal(recordService.getYlReimbTotal(RemoteConstants.YL_LOCATION_NO, i));
            resultList.add(reimbYearTotalBo);
        }
        return ViewDataUtil.success(resultList);
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
    public ViewData reimbursement(@RequestParam(value = "ylCard", required = true) String ylCard,
                                  @RequestParam(value = "name", required = false) String name) throws Exception {
        return ViewDataUtil.success(reimbService.reimbForYlCardAndName(ylCard, name));
    }

    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/reimbursement?ylCard=4306210121011214&name=欧凤华
     *
     * @param list 医疗账户_报销人
     * @return
     * @throws Exception
     */
    @RequestMapping("/batchReimbursement")
    @ResponseBody
    public ViewData batchReimbursement(@RequestBody List<ReimbUserBo> list) throws Exception {
        if (CollectionUtil.isEmpty(list)) {
            return ViewDataUtil.success(CollectionUtil.newArrayList("请选择要报销的人！"));
        }
        List<String> resultMap = CollectionUtil.newArrayList();
        for (ReimbUserBo reimbUserBo : list) {
            resultMap.add(reimbService.reimbForYlCardAndName(reimbUserBo.getYlCard(), reimbUserBo.getName()));
        }
        return ViewDataUtil.success(resultMap);
    }


    private void printOne(ReimbUnPrintRecordBo reimbDealRecord) throws Exception {
        if (reimbDealRecord == null) {
            return;
        }
        ReimbPrintBo reimbPrintBo = reimbService.getPrintInfo(reimbDealRecord.getBizId());

        reimbPrintBo.setFamilyLocation(reimbDealRecord.getFamilyLocation());
        //套入模版生成临时word文档   文件名为报销日期+姓名+bizid
        String docFileName = DateUtil.formatDate(reimbDealRecord.getCreatedDate()) + reimbDealRecord.getName() + reimbDealRecord.getBizId();
        //原始word文档文件路径
        String srcFilePath = ReimbConstants.UN_PRINT_PATH + docFileName + ".doc";
        //生成原始word文档
        ExportWordUtil.exportWord(reimbPrintBo, srcFilePath);

        //获取医疗账户图文件名，png或者jpg格式
        String ylCardImgFileName = PrintBizUtil.getYlCardName(reimbDealRecord.getYlCard(), reimbDealRecord.getName()) + "." + ReimbConstants.PIC_TYPE_PNG;
        if (!FileUtil.exist(ylCardImgFileName)) {
            ylCardImgFileName = PrintBizUtil.getYlCardName(reimbDealRecord.getYlCard(), reimbDealRecord.getName()) + "." + ReimbConstants.PIC_TYPE_JPG;
        }

        //将图片插入到word文档中
        //处方图文件全路径
        String chuFangImgFileName = ReimbConstants.CHUFANG_PIC_PATH + reimbDealRecord.getIllNessName() + "." + ReimbConstants.PIC_TYPE_PNG;
        //插入处方图后的新文件名
        String newFileName = docFileName + "_1.doc";
        if (FileUtil.exist(chuFangImgFileName)) {
            PrintUtil.replaceImg(srcFilePath, chuFangImgFileName, ReimbConstants.CHUFANG_IMG_IN_WORD_STR, 480, 260, ReimbConstants.UN_PRINT_PATH + newFileName);
            FileUtil.del(srcFilePath);
            //源文件变成有处方图的文件
            srcFilePath = ReimbConstants.UN_PRINT_PATH + newFileName;
        }
        if (FileUtil.exist(ylCardImgFileName)) {
            newFileName = docFileName + "_2.doc";
            PrintUtil.replaceImg(srcFilePath, ylCardImgFileName, ReimbConstants.YLCARD_IMG_IN_WORD_STR, 480, 170, ReimbConstants.UN_PRINT_PATH + newFileName);
            FileUtil.del(srcFilePath);
            //源文件变成有医疗信息图的文件
        }
        //更新状态为已打印
        ReimbPrintInfo reimbPrintInfo = new ReimbPrintInfo();
        reimbPrintInfo.setBizId(reimbDealRecord.getBizId());
        reimbPrintInfo.setPrintState("1002");
        reimbService.savePrintInfo(reimbPrintInfo);
    }


    /**
     * 打印单个文件
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
}
