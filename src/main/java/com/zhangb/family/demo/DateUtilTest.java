package com.zhangb.family.demo;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DateUtilTest {
    public static void main(String[] args) {
        System.out.println(DateUtil.offsetDay(new Date(),1));
    }
}
