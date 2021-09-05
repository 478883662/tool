package com.zhangb.family.doctor.operate.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by z9104 on 2020/10/3.
 */
@Data
public class ReimbIllnessBo {
    private String name;
    private String selfNo;
    private String cardNo;
    private String releationNum;
    private String ylCard;
    private String masterName;
    private String birthday;
    private String idCard;
    private String age;
    private String illnessNo;
    private String illnessName;
    /**出院时间*/
    private Date outDate;

    private List<ReimbDrugBo> reimbDrugBoList;
}
