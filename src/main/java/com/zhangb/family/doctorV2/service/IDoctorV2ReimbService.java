package com.zhangb.family.doctorV2.service;

import com.zhangb.family.common.exception.BizException;

public interface IDoctorV2ReimbService {

    /**
     * 试算
     */
    void tryCalculate() throws BizException;

    /**
     * 报销
     */
    void reimb();



}
