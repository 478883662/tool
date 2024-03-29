package com.zhangb.family.doctor.operate.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
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
    private BigDecimal remibTotal;
    private String filePath="";
    /**预览大图*/
    private List<String> preFilePath;
    /**文件主键OID*/
    private String fileOid="";
    /**文件创建时间*/
    private Date fileCreateDate;
    /**是否有效*/
    private String enableFlag;
    /**打印记录oid*/
    private String printOid;
    /**打印状态*/
    private String printState;
}
