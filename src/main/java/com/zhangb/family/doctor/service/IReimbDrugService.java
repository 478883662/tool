package com.zhangb.family.doctor.service;


import com.zhangb.family.doctor.entity.ReimbDrug;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbDrugService {
    /**
     * 保存药品
     * @param reimbDrug
     * @return
     */
    Integer saveDrug(ReimbDrug reimbDrug) throws Exception;
}
