package com.zhangb.family.doctor.operate.dao;

import com.zhangb.family.doctor.operate.bo.ReimbUserBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DoctorReimbDao {

    /**
     * 查询今日报销信息
     * @param today
     * @return
     */
    List<ReimbUserBo> getTodayReimbInfo(@Param("today") String today);

    /**
     * 查询今日成功报销总额
     * @param today
     * @return
     */
    BigDecimal getTodayTotalReimb(String today);
}
