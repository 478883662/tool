package com.zhangb.tool.doctorReimbursement.entity;

import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;
import lombok.Data;

/**
 * Created by z9104 on 2020/9/24.
 */
@Data
@DbTableAnnotation("tool_patient_t")
public class ReimbUserInfo {

    @DbFeildAnnotation(value = "OID",isPrimary = true)
    private Long oid;
    /**证编码*/
    @DbFeildAnnotation("CARD_NO")
    private String cardNo;
    /**医疗账号*/
    @DbFeildAnnotation("YL_CARD")
    private String ylCard;
    /**户主姓名*/
    @DbFeildAnnotation("MASTER_NAME")
    private String masterName;
    /**后缀id*/
    @DbFeildAnnotation("SUF_ID")
    private String sufId;
    /**用户在医疗账户的序号*/
    @DbFeildAnnotation("SEQ")
    private String seq;
    /**与户主的关系编码*/
    @DbFeildAnnotation("RELEATION_NUM")
    private String releationNum;
    /**姓名*/
    @DbFeildAnnotation("NAME")
    private String name;
    /**生日*/
    @DbFeildAnnotation("BIRTHDAY")
    private String birthday;
    /**身份证*/
    @DbFeildAnnotation("ID_CARD")
    private String idCard;
    /**年龄*/
    @DbFeildAnnotation("AGE")
    private String age;
    /**个人编码*/
    @DbFeildAnnotation("SELF_NO")
    private String selfNo;
    /**前缀id*/
    @DbFeildAnnotation("PRE_ID")
    private String preId;
    /**性别*/
    @DbFeildAnnotation("SEX")
    private String sex;
    /**家庭住址*/
    @DbFeildAnnotation("family_location")
    private String familyLocation;
}
