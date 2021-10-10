package com.zhangb.family.doctor.operate.bo;

import lombok.Data;

import java.util.List;

@Data
public class DoctorReimbDetailInfo {
    /**今日报销总额*/
    private String todayTotalStr;
    /**今年报销总额*/
    private String toYearTotalStr;
    /**今日报销信息*/
    private List<ReimbUserBo> list;
}
