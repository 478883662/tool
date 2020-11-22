package com.zhangb.tool.doctorReimbursement.common.constants;

import java.math.BigDecimal;

/**
 * Created by z9104 on 2020/10/3.
 */
public interface ReimbConstants {
    /**医疗机构每年报销上限额度*/
    BigDecimal YL_TOTAL_YEAR= new BigDecimal(20000);
    /**用户每年报销上限额度*/
    BigDecimal USER_TOTAL_YEAR= new BigDecimal(400);
    /**默认每种病固定治疗6天*/
    int DEFUALT_ILLNESS_COUNT = 6;
    /**两种病相间隔时间至少30天以上*/
    int DEFUALT_ILLNESS_BETWEEN = 30;
    /**图片在word文档中的位置标识码*/
    String CHUFANG_IMG_IN_WORD_STR="__";
    String YLCARD_IMG_IN_WORD_STR="_-";
}
