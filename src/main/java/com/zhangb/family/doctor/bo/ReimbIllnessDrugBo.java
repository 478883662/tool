package com.zhangb.family.doctor.bo;

import lombok.Data;

import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
public class ReimbIllnessDrugBo {

    //病例
    private Long oReimbIllness;
    //对应的用药集合
    private List<ReimbDrugBo> reimbDrugBoList;
}
