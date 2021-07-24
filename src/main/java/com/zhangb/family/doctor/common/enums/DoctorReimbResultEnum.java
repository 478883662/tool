package com.zhangb.family.doctor.common.enums;

public enum DoctorReimbResultEnum {

    SUCCESS("0000","报销成功"),
    /**入参医疗账号不能为空*/
    EMPTY_YL_CARD("0001","入参医疗账号不能为空"),
    /**没有找到用药处方*/
    NO_DRUG("0002","没有找到用药处方"),
    /**报销失败，远端没有报销成功的记录*/
    REIMB_FAIL("0003","报销失败，远端没有报销成功的记录"),
    /**报销失败，系统异常*/
    REIMB_ERROR("1001","报销失败，系统异常"),
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
