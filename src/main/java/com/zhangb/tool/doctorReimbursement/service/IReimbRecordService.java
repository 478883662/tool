package com.zhangb.tool.doctorReimbursement.service;

import com.zhangb.tool.doctorReimbursement.entity.ReimbDealRecord;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
public interface IReimbRecordService {

    /**
     * 通过个人编码同步补偿记录到本地库
     * @param selfNo
     * @return
     */
    String syncRecord(String selfNo) throws Exception;

    /**
     * 查询本地报销记录里，用户今天是否报销过了。
     * @param ylCard
     * @param selfNo
     * @return
     */
    boolean isReimbToday(String ylCard,String selfNo) throws SQLException, IllegalAccessException;

    /**
     * 查询用户今年已报销总额
     * @param selfNo 个人编码
     * @return
     */
    BigDecimal getUserReimbTotal(String selfNo) throws SQLException;

    /**
     * 查询医疗机构今年已报销总额
     * @param ylLocationNo 医疗机构编码
     * @param year 年份
     * @return
     */
    BigDecimal getYlReimbTotal(String ylLocationNo,int year) throws SQLException;

    /**
     * 获取用户今年来最近一次报销记录
     * @param selfNo
     * @return
     */
    ReimbDealRecord getLastRecord(String selfNo) throws SQLException;

    /**
     * 获取用户在指定医疗机构的最近一次报销记录
     * @param selfNo
     * @param ylLocationNo
     * @return
     */
    ReimbDealRecord getLastRecord(String selfNo,String ylLocationNo) throws SQLException;

    /**
     * 查询用户今年来在泥家湖卫生室报销此病的次数
     * @param selfNo
     * @param illnessNo
     * @return
     */
    int getIllnessCount(String selfNo, String illnessNo) throws SQLException;
}
