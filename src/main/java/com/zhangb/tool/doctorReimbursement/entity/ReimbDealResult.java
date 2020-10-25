package com.zhangb.tool.doctorReimbursement.entity;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_deal_result_t")
public class ReimbDealResult {

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
}
