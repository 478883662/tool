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
    String PIC_TYPE_PNG ="png";
    String PIC_TYPE_JPG="jpg";
    /**用户医疗账户图文件夹路径*/
    String PIC_FILE_PATH="D:/temp/ylCardPic/";
    /**处方图文件夹路径*/
    String CHUFANG_PIC_PATH="D:/temp/chufang/";
    /**未打印文件夹路径*/
    String UN_PRINT_PATH = "D:/temp/unprint/";
    String UN_PRINT_SRC_PATH =UN_PRINT_PATH+"src/";
    String UN_PRINT_CHUFANG_PATH=UN_PRINT_PATH+"chufang/";
    String UN_PRINT_YLINFO_PATH=UN_PRINT_PATH+"ylinfo/";


}
