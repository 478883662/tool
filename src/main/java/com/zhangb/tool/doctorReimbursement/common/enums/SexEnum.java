package com.zhangb.tool.doctorReimbursement.common.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by z9104 on 2020/9/23.
 */
public enum SexEnum {

    NAN("1","男")
    ,NV("2","女")
    ,QI_TA("9","其他")
    ;


    SexEnum(String sexNo, String sexName) {
        this.sexNo = sexNo;
        this.sexName = sexName;
    }

    @Getter
    private String sexNo;
    @Getter
    private String sexName;


}
