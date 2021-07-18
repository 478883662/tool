package com.zhangb.family.doctor.common.constants;

/**
 * Created by z9104 on 2020/9/23.
 */
public interface ReimbRemoteStrategyKeyConstants {

    /**通过姓名获取用户信息*/
    String REIMB_GET_USER_BY_NAME_STRATEGY = "reimbGetUserByNameStrategy";
    /**通过医疗账号获取用户信息*/
    String REIMB_GET_USER_BY_YLCARD_STRATEGY = "reimbGetUserByYlCardStrategy";
    /**通过医疗账号获取用户信息*/
    String REIMB_GET_RECORD_STRATEGY = "reimbGetRecordStrategy";
    /**通过医疗账号+记录业务id查询病与药品的关系*/
    String REIMB_GET_ILLNESS_DRUG_STRATEGY = "reimbGetIllnessDrugStrategy";
    /**给指定病选择药*/
    String REIMB_SELECT_DRUG_STRATEGY = "reimbSelectDrugStrategy";
    /**生成业务id接口*/
    String REIMB_GET_BIZID_STRATEGY = "reimbGetBizIdStrategy";
    /**绑定业务id接口*/
    String REIMB_BIND_BIZID_STRATEGY = "reimbBindBizIdStrategy";
    /**保存试算接口*/
    String REIMB_TRY_SAVE_STRATEGY = "reimbTrySaveStrategy";
    /**保存试算接口1*/
    String REIMB_TRY_SAVE1_STRATEGY = "reimbTrySave1Strategy";
    /**保存试算接口2*/
    String REIMB_TRY_SAVE2_STRATEGY = "reimbTrySave2Strategy";
    /**保存试算接口3*/
    String REIMB_TRY_SAVE3_STRATEGY = "reimbTrySave3Strategy";
    /**正式补偿*/
    String REIMB_STRATEGY = "reimbStrategy";
    /**打印*/
    String REIMB_PRINT_STRATEGY = "reimbPrintStrategy";
    ;
}
