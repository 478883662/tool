package com.zhangb.tool.doctorReimbursement.bo;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import com.zhangb.tool.doctorReimbursement.entity.ReimbDrug;
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
