package com.zhangb.family.doctor.basedata.entity;

import com.zhangb.family.common.annotation.DbFeildAnnotation;
import com.zhangb.family.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_deal_result_t")
public class ReimbDealResultPO {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**医疗账号*/
    @DbFeildAnnotation("yl_card")
    private String ylCard;
    /**姓名*/
    @DbFeildAnnotation("name")
    private String name;
    /**处理时间*/
    @DbFeildAnnotation("deal_time")
    private String dealTime;
    /**处理结果*/
    @DbFeildAnnotation("deal_result")
    private String dealResult;
    /**处理结果编码*/
    @DbFeildAnnotation("deal_code")
    private String dealCode;
}
