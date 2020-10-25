package com.zhangb.tool.doctorReimbursement.service;

import com.zhangb.tool.doctorReimbursement.entity.ReimbDrug;
import com.zhangb.tool.doctorReimbursement.entity.ReimbIllnessDrug;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbDrugService {
    /**
     * 保存药品
     * @param reimbDrug
     * @return
     */
    Integer saveDrug(ReimbDrug reimbDrug) throws SQLException, IllegalAccessException;
}
