package com.zhangb.family.doctor.entity;

import com.zhangb.family.common.annotation.DbFeildAnnotation;
import com.zhangb.family.common.annotation.DbTableAnnotation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_deal_record_t")
public class ReimbDealRecord {
    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**补偿单号，唯一id */
    @DbFeildAnnotation("REIMB_NO")
    private String reimbNo;
    /**报销年度 */
    @DbFeildAnnotation("REIMB_YEAR")
    private String reimbYear;
    /** 医疗账号*/
    @DbFeildAnnotation("YL_CARD")
    private String ylCard;
    /** 医疗机构编码*/
    @DbFeildAnnotation("YL_LOCATION_NO")
    private String ylLocationNo;
    /** 医疗机构*/
    @DbFeildAnnotation("YL_LOCATION")
    private String ylLocation;
    /** 补偿类别*/
    @DbFeildAnnotation("REIMB_TYPE")
    private String reimbType;
    /** 补偿时间*/
    @DbFeildAnnotation("REIMB_DATE")
    private Date reimbDate;
    /** 入院时间*/
    @DbFeildAnnotation("IN_DATE")
    private Date inDate;
    /** 出院时间*/
    @DbFeildAnnotation("OUT_DATE")
    private Date outDate;
    /** 报销金额*/
    @DbFeildAnnotation("MONEY")
    private BigDecimal money;
    /** 个人编码*/
    @DbFeildAnnotation("SELF_NO")
    private String selfNo;
    /** 用户姓名*/
    @DbFeildAnnotation("NAME")
    private String name;
    /** 病例编号*/
    @DbFeildAnnotation("ILLNESS_NO")
    private String illNessNo;
    /** 病例名称--诊断名称*/
    @DbFeildAnnotation("ILLNESS_NAME")
    private String illNessName;
    /** 业务id，查询病与药的关系记录时的入参*/
    @DbFeildAnnotation("BIZ_ID")
    private String bizId;
}
