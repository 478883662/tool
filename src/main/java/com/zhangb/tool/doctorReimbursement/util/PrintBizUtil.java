package com.zhangb.tool.doctorReimbursement.util;

import com.zhangb.tool.doctorReimbursement.bo.ReimbUnPrintRecordBo;

import java.io.File;

public class PrintBizUtil {

    public static String getYlCardName(String ylCard,String name){
        return "D:"+ File.separator+"temp"+File.separator+"ylCardPic"+File.separator+ylCard+name;
    }
}
