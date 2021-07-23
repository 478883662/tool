package com.zhangb.family.doctor.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by z9104 on 2020/10/4.
 */
@Data
public class ReimbUserBo {

    private String ylCard = "";
    private String masterName = "";
    private String name = "";

    private String syncDate = "";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date reimbDate;
    private String dealResult = "";
    private double remibTotal = 0D;
    private String filePath="";
    /**预览大图*/
    private List<String> preFilePath;
    /**文件主键OID*/
    private String fileOid="";
    /**文件创建时间*/
    private Date fileCreateDate;
    /**是否有效*/
    private String enableFlag;
}
