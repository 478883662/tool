package com.zhangb.family.common.module;

import lombok.Data;

/**
 * Created by z9104 on 2021/5/5.
 */
@Data
public class ViewData {

    public ViewData(RespEnum respInfo) {
        this.code = respInfo.code;
        this.msg = respInfo.msg;
    }

    private String code;
    private String msg;
    private Object obj;

    public enum RespEnum {
        SUCCESS("0000","成功"),
        BIZ_ERROR("0005","参数错误"),
        SYSTEM_ERROR("0009","系统错误");

        RespEnum(String code, String msg) {
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
}
