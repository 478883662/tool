package com.zhangb.family.doctor.basedata.dao;

import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReimbDealRecordDao {

    /**
     * 获取报销记录里相同病例最新的一次报销记录
     * @param illNessNo
     * @return
     */
    ReimbDealRecordPO getNewDealRecord(@Param("illNessNo") String illNessNo);
}
