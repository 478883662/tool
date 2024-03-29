package com.zhangb.family.doctor.basedata.entity;

import com.zhangb.family.common.annotation.DbFeildAnnotation;
import com.zhangb.family.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/10/1.
 */
@Data
@DbTableAnnotation("tool_illness_drug_t")
public class ReimbIllnessDrugPO {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**病例编码*/
    @DbFeildAnnotation("illness_no")
    private String illnessNo;
    /**用药编号*/
    @DbFeildAnnotation("drug_no")
    private String drugNo;
    /**开药数量*/
    @DbFeildAnnotation("drug_num")
    private String drugNum;
}
