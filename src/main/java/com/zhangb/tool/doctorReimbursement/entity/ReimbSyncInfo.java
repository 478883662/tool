package com.zhangb.tool.doctorReimbursement.entity;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

import java.util.Date;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
@DbTableAnnotation("tool_sync_record_t")
public class ReimbSyncInfo {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**编号*/
    @DbFeildAnnotation("id")
    private String id;
    /**同步日期*/
    @DbFeildAnnotation("sync_Date")
    private String syncDate;
    /**同步类型*/
    @DbFeildAnnotation("sync_type")
    private String syncType;
    /**创建时间*/
    @DbFeildAnnotation("create_date")
    private Date createDate;
}
