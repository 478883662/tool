package com.zhangb.tool.doctorReimbursement.entity;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_illness_t")
public class ReimbIllness {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /** 病例编号*/
    @DbFeildAnnotation("illness_no")
    private String illnessNo;
    /** 病例名称，问诊名称*/
    @DbFeildAnnotation("illness_name")
    private String illnessName;
    /**住院时间（天），通过多个记录算出平均值 默认空需要手动维护*/
    @DbFeildAnnotation("hospital_day")
    private String hospitalDay;
    /** 两次生病的间隔时间，通过多条记录算出平均值,默认空需要手动维护*/
    @DbFeildAnnotation("rest_day")
    private String restDay;
}
