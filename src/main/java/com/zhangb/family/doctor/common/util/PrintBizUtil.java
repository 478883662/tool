package com.zhangb.family.doctor.common.util;

import java.io.File;

public class PrintBizUtil {

    public static String getYlCardName(String ylCard,String name){
        return "D:"+ File.separator+"temp"+File.separator+"ylCardPic"+File.separator+ylCard+name;
    }

}
