package com.zhangb.family.doctor.remote.strategy;

/**
 * Created by z9104 on 2020/9/23.
 */
public interface IReimbursementRemoteStrategy {

    String execute(String... param);

    <T> T parse(String respStr);
}
