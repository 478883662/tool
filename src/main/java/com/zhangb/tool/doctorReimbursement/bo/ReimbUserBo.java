package com.zhangb.tool.doctorReimbursement.bo;

import lombok.Data;

/**
 * Created by z9104 on 2020/10/4.
 */
@Data
public class ReimbUserBo {

    private String ylCard="";
    private String masterName="";
    private String name="";

    private String syncDate="";
    private String reimbDate="";
    private String dealResult="";
    private String dealTime="";
}
