package com.zhangb.family.common.util;

/**
 * 线程休眠工具类
 */
public class ThreadSleepUtil {

    public static void sleep(int i){
        try {
            Thread.sleep(i*1000);
        }catch (Exception e){

        }
    }
}
