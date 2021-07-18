package com.zhangb.family.doctor.entity;

import com.zhangb.family.common.annotation.DbFeildAnnotation;
import com.zhangb.family.common.annotation.DbTableAnnotation;
import lombok.Data;

import java.util.Date;

/**
 * Created by z9104 on 2020/10/31.
 */
@Data
@DbTableAnnotation("tool_reimb_print_t")
public class ReimbPrintInfo {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**业务id，用于打印*/
    @DbFeildAnnotation("biz_id")
    private String bizId;
    /**打印状态,1001：待打印，1002：已打印*/
    @DbFeildAnnotation("print_state")
    private String printState;
    /**创建时间*/
    @DbFeildAnnotation("created_date")
    private Date createdDate;

}
