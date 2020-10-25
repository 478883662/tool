package com.zhangb.tool.doctorReimbursement.service;

import com.zhangb.tool.doctorReimbursement.entity.ReimbSyncInfo;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbSyncService {

    /**
     * 获取同步的次数
     * @param ylCard
     * @param today
     * @param type
     * @return
     */
    int getSyncCount(String ylCard, String today, String type) throws SQLException, IllegalAccessException;

    /**
     * 插入一条同步日志
     * @param reimbSyncInfo
     * @throws SQLException
     * @throws IllegalAccessException
     */
    void insertSyncRecord(ReimbSyncInfo reimbSyncInfo) throws SQLException, IllegalAccessException;

    /**
     * 同步用户信息
     * @param ylCard
     */
    void syncUserInfo(String ylCard) throws Exception;

    /**
     * 同步报销记录信息
     * @param ylCard
     * @param name
     */
    void syncRecordInfo(String ylCard, String name) throws Exception;
}
