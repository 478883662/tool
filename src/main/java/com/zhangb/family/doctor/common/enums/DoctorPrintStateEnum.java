package com.zhangb.family.doctor.common.enums;

public enum DoctorPrintStateEnum {
    /**未打印*/
    UN_PRINT("1001"),


    ;

    DoctorPrintStateEnum(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }
}
