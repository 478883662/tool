package com.zhangb.tool.doctorReimbursement.remote.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zhangb.tool.doctorReimbursement.factory.ReimbursementStrategyFactory;
import com.zhangb.tool.doctorReimbursement.remote.service.ICxnhRemoteService;
import com.zhangb.tool.doctorReimbursement.remote.strategy.IReimbursementRemoteStrategy;
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
    public String executeRemote(String strategyKey,String... param) throws Exception {
        //第一个参数必须是策略的key
        IReimbursementRemoteStrategy strategy = factory.getReimbursementRemoteStrategy(strategyKey);
        if(StrUtil.hasBlank(strategyKey)){
            throw new Exception("未知的策略名");
        }
        return strategy.execute(param);
    }
}
