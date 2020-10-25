package com.zhangb.tool.doctorReimbursement.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

import java.util.Date;

/**
 * Created by z9104 on 2020/10/3.
 */
public class ReimbServiceImplTest {
    public static void main(String[] args) {
        System.out.println(getRandomDate().toLocaleString());
    }

    private static Date getRandomDate() {
        int randomDay = NumberUtil.generateRandomNumber(1,240,1)[0];
        return DateUtil.offsetDay(DateUtil.beginOfYear(new Date()),randomDay);
    }
}
