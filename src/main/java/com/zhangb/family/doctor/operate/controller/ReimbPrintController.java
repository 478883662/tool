package com.zhangb.family.doctor.operate.controller;

import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.operate.bo.ReimbUnPrintRecordBo;
import com.zhangb.family.doctor.operate.service.IDoctorPrintService;
import com.zhangb.family.doctor.operate.service.IReimbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor/print")
public class ReimbPrintController {

    @Autowired
    private IDoctorPrintService doctorPrintService;
    @Autowired
    private IReimbService reimbService;

    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/reimbursement?ylCard=4306210121011214&name=欧凤华
     *
     * @param printOid 打印记录oid
     * @return
     * @throws Exception
     */
    @RequestMapping("/printOne")
    @ResponseBody
    public ViewData printOne(@RequestParam("printOid") String printOid) throws Exception {
        ReimbUnPrintRecordBo reimbUnPrintRecordBo = reimbService.getPrintInfoByOid(printOid);
        doctorPrintService.printOne(reimbUnPrintRecordBo);
        return ViewDataUtil.success(true);
    }


}
