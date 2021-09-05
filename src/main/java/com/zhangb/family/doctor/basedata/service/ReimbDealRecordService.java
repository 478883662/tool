package com.zhangb.family.doctor.basedata.service;

import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;

public interface ReimbDealRecordService {
    /**
     * 获取报销记录里相同病例最新的一次报销记录
     * @param illNessNo
     * @return
     */
    ReimbDealRecordPO getNewDealRecord(String illNessNo);
}
