package com.zhangb.tool.doctorReimbursement.entity;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/10/1.
 */
@Data
@DbTableAnnotation("tool_illness_drug_t")
public class ReimbIllnessDrug {

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
