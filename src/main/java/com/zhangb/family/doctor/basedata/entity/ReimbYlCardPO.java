package com.zhangb.family.doctor.basedata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhangb.family.common.annotation.DbFeildAnnotation;
import com.zhangb.family.common.annotation.DbTableAnnotation;
import lombok.Data;

import java.util.Date;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_yl_card_t")
public class ReimbYlCardPO {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**医疗账号，手工录入*/
    @DbFeildAnnotation("yl_card")
    private String ylCard;
    /**户主姓名，手工录入*/
    @DbFeildAnnotation("master_name")
    private String masterName;
    /**创建时间*/
    @DbFeildAnnotation("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;



}
