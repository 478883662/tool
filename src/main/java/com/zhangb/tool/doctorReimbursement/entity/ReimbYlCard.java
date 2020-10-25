package com.zhangb.tool.doctorReimbursement.entity;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_yl_card_t")
public class ReimbYlCard {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**医疗账号，手工录入*/
    @DbFeildAnnotation("yl_card")
    private String ylCard;
    /**户主姓名，手工录入*/
    @DbFeildAnnotation("master_name")
    private String masterName;

}
