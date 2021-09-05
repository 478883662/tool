package com.zhangb.family.doctor.basedata.remote.service;

import com.zhangb.family.common.exception.BizException;

/**
 * Created by z9104 on 2020/9/23.
 */
public interface ICxnhRemoteService {

    /**
     * 远程调用
     *
     * @param param
     * @return
     */
    String executeRemote(String strategyKey, String... param) throws BizException;

    /**
     * 远程调用
     *
     * @param param
     * @return
     */
    <T> T execute(String strategyKey, String... param) throws BizException;
}
