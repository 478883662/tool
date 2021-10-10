package com.zhangb.family.doctor.operate.bo;

import com.zhangb.family.common.annotation.DbFeildAnnotation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
public class ReimbUnPrintRecordBo {

    @DbFeildAnnotation("BIZ_ID")
    private String bizId;
    @DbFeildAnnotation("NAME")
    private String name;
    @DbFeildAnnotation("created_date")
    private Date createdDate;
    @DbFeildAnnotation("ILLNESS_NAME")
    private String illNessName;
    @DbFeildAnnotation("MONEY")
    private BigDecimal money;
    @DbFeildAnnotation("family_location")
    private String familyLocation;
    @DbFeildAnnotation("yl_card")
    private String ylCard;
    @DbFeildAnnotation("yl_location")
    private String ylLocation;
    private String filePath;
    @DbFeildAnnotation("ID_CARD")
    private String idCard;
}
