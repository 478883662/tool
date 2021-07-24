package com.zhangb.family.doctor.bo;

import com.zhangb.family.doctor.common.enums.DoctorReimbResultEnum;
import lombok.Data;

@Data
public class DoctorReimbResultBo {

    public DoctorReimbResultBo(String ylCardNo, String name, DoctorReimbResultEnum doctorReimbResultEnum) {
        this.ylCardNo = ylCardNo;
        this.name = name;
        this.code = doctorReimbResultEnum.getCode();
        this.errMsg = doctorReimbResultEnum.getMsg();
    }

    public DoctorReimbResultBo(String ylCardNo, String name, String code, String errMsg) {
        this.ylCardNo = ylCardNo;
        this.name = name;
        this.code = code;
        this.errMsg = errMsg;
    }

    public DoctorReimbResultBo(String ylCardNo, String name) {
        this.ylCardNo = ylCardNo;
        this.name = name;
    }


    private String ylCardNo;

    private String name;
    /**报销处理状态码*/
    private String code;
    /**报销处理处理描述*/
    private String errMsg;

    public DoctorReimbResultBo setCodeMsg(DoctorReimbResultEnum doctorReimbResultEnum){
        this.code = doctorReimbResultEnum.getCode();
        this.errMsg = doctorReimbResultEnum.getMsg();
        return this;
    }

    public DoctorReimbResultBo setCodeMsg(String code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
        return this;
    }
}
