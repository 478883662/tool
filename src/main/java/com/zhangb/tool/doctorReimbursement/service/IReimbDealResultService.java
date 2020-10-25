package com.zhangb.tool.doctorReimbursement.service;

import com.zhangb.tool.doctorReimbursement.entity.ReimbDealResult;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbDealResultService {
    /**
     * 保存操作记录
     * @param reimbDealResult
     * @return
     */
    Integer saveDealResult(ReimbDealResult reimbDealResult) throws SQLException, IllegalAccessException;
}
