package com.zhangb.tool.doctorReimbursement.bo;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
public class ReimbUnPrintRecordBo {

    private String bizId;
    private String name;
    private Date createdDate;
    private String illNessName;
    private BigDecimal money;
    private String familyLocation;
    private String ylCard;
    private String ylLocation;
    private String filePath;
}
