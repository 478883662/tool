package com.zhangb.family.doctor.common.enums;

public enum DoctorReimbResultEnum {

    SUCCESS("0000","报销成功")

    ;

    DoctorReimbResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
