package com.zhangb.family.doctor.basedata.remote.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctor.basedata.factory.ReimbursementStrategyFactory;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.basedata.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by z9104 on 2020/9/23.
 */
@Service
public class CxnhRemoteServiceImpl implements ICxnhRemoteService {

    @Autowired
    private ReimbursementStrategyFactory factory;

    @Override
    public String executeRemote(String strategyKey, String... param) throws BizException {
        //第一个参数必须是策略的key
        IReimbursementRemoteStrategy strategy = factory.getReimbursementRemoteStrategy(strategyKey);
        if (StrUtil.hasBlank(strategyKey)) {
            throw new BizException("未知的策略名");
        }
        return strategy.execute(param);
    }

    @Override
    public <T> T execute(String strategyKey, String... param) throws BizException {
        //第一个参数必须是策略的key
        IReimbursementRemoteStrategy strategy = factory.getReimbursementRemoteStrategy(strategyKey);
        if (StrUtil.hasBlank(strategyKey)) {
            throw new BizException("未知的策略名");
        }
        String str = strategy.execute(param);
        if (StrUtil.hasBlank(strategyKey)) {
            throw new BizException("远端响应为空");
        }
        //解析结果成封装对象
        return strategy.parse(str);
    }
}
