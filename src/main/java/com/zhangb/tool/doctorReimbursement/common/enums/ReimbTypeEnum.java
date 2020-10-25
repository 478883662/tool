package com.zhangb.tool.doctorReimbursement.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by z9104 on 2020/9/26.
 */
public enum ReimbTypeEnum {

    YIBAN_MENZHEN("1101","一般门诊")
    ,JIATING_ACCOUNT("1104","家庭账户")
    ;

    ReimbTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;
    @Getter
    private String name;
}
