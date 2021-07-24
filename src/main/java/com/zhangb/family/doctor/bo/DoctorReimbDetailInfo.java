package com.zhangb.family.doctor.bo;

import lombok.Data;

import java.util.List;

@Data
public class DoctorReimbDetailInfo {
    /**今日报销总额*/
    private String totalStr;
    /**今日报销信息*/
    private List<ReimbUserBo> list;
}
