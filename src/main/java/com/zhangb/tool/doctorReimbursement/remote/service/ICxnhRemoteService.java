package com.zhangb.tool.doctorReimbursement.remote.service;

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
    String executeRemote(String strategyKey,String... param) throws Exception;
}
