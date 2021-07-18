package com.zhangb.family.doctor.entity;

import com.zhangb.family.common.annotation.DbFeildAnnotation;
import com.zhangb.family.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_drug_t")
public class ReimbDrug {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**项目编号*/
    @DbFeildAnnotation("drug_no")
    private String drugNo;
    /**药品名称*/
    @DbFeildAnnotation("drug_name")
    private String drugName;
    /**单价*/
    @DbFeildAnnotation("price")
    private String price;
    /**药品一级细分类*/
    @DbFeildAnnotation("one_type")
    private String oneType;
    /**药品序号  003  004等等*/
    @DbFeildAnnotation("drug_seq")
    private String drugSeq;
}
