package com.zhangb.tool.doctorReimbursement.bo;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/9/26.
 */
@Data
public class ReimbDrugBo {

    /**项目编号*/
    private String drugNo;
    /**药品名称*/
    private String drugName;
    /**单价*/
    private String price;
    /**开药数量*/
    private String drugNum;
    /**药品一级细分类*/
    private String oneType;
    /**药品序号  003  004等等*/
    private String drugSeq;


}
