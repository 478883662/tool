package com.zhangb.family.doctor.service;

import com.zhangb.family.doctor.bo.ReimbUnPrintRecordBo;

public interface IDoctorPrintService {
    /**
     * 打印单个病例
     * @param reimbDealRecord
     * @throws Exception
     */
    void printOne(ReimbUnPrintRecordBo reimbDealRecord) throws Exception;

}
