package com.zhangb.family.doctorV2.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DoctorV2UniqueIdUtil {

    /**
     * 起始值 1000
     */
    private static volatile int index = 1000;

    public static synchronized String getUniqueId(String prefixStr){
        if (index++>9999){
            index = 1000;
        }
        return prefixStr + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT )+index;
    }

    public static void main(String[] args) {
        //H43062100453 2021 1226 211337 2064
        //H43062100453 2022 0306 113257 1000
        System.out.println(getUniqueId("H43062100453"));
        System.out.println(getUniqueId("H43062100453"));
    }
}
