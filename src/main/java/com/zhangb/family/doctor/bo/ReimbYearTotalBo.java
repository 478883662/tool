package com.zhangb.family.doctor.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReimbYearTotalBo {
    /**年份*/
    private int year;
    /**年度累计报销金额*/
    private BigDecimal total;
}
