package com.zhangb.tool.doctorReimbursement.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 家庭关系
 * Created by z9104 on 2020/9/23.
 */
public enum FamiliyRelationEnum {

    PEI_OU("1","配偶")
    ,ZI("2","子")
    ,NV("3","女")
    ,SUN("4","孙子、孙女或外孙子、外孙女")
    ,FU_MU("5","父母")
    ,ZU_FU_MU("6","祖父母或外祖父母")
    ,XIONG_DI("7","兄、弟、姐、妹")
    ,QI_TA("9","其他")
    ,ER_XI_FU("10","儿媳妇")
    ;

    FamiliyRelationEnum(String relationNo, String relationName) {
        this.relationNo = relationNo;
        this.relationName = relationName;
    }

    @Getter
    private String relationNo;
    @Getter
    private String relationName;

}
