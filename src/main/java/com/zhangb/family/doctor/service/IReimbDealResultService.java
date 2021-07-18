package com.zhangb.family.doctor.service;


import com.zhangb.family.doctor.entity.ReimbDealResult;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbDealResultService {
    /**
     * 保存操作记录
     * @param reimbDealResult
     * @return
     */
    Integer saveDealResult(ReimbDealResult reimbDealResult) throws Exception;
}
