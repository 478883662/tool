package com.zhangb.family.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FamilyFile {

    private Long oid;

    private String fileName;

    private byte[] file;
    /**外键业务id*/
    private String pkOid;

    private Date createDate;
}
