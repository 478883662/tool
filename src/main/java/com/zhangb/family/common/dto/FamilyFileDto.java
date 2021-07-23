package com.zhangb.family.common.dto;

import lombok.Data;

@Data
public class FamilyFileDto {

    private String oid;

    /**外键业务id*/
    private String pkOid;

}
