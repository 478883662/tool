package com.zhangb.family.doctor.operate.service;


import com.zhangb.family.doctor.basedata.entity.ReimbDealResultPO;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbDealResultService {
    /**
     * 保存操作记录
     * @param reimbDealResultPO
     * @return
     */
    Integer saveDealResult(ReimbDealResultPO reimbDealResultPO) throws Exception;
}
