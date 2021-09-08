package com.zhangb.family.doctor.basedata.service;

import com.zhangb.family.common.exception.BizException;

public interface IReimbSyncService {

    /**
     * 同步病例
     * @throws Exception
     */
    void syncIllness() throws Exception;

    /**
     * 通过个人编码同步补偿记录到本地库
     * @param selfNo
     * @return
     */
    void syncRemoteRecord(String selfNo) throws Exception;

    /**
     * 通过bizId同步补偿记录到本地库
     * @param selfNo
     * @return
     */
    void syncRemoteRecord(String selfNo, String bizId) throws Exception;

    /**
     * 同步病例与用药的关系
     * @return
     */
    String syncRemoteIllnessDrug(String illNessNo) throws BizException;
}
