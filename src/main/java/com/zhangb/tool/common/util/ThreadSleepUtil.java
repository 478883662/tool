package com.zhangb.tool.common.util;

public class ThreadSleepUtil {

    public static void sleep(int i){
        try {
            Thread.sleep(i*1000);
        }catch (Exception e){

        }
    }
}
