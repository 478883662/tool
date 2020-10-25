package com.zhangb.tool.doctorReimbursement.factory;

import com.zhangb.tool.doctorReimbursement.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by z9104 on 2020/9/23.
 */
@Component
public class ReimbursementStrategyFactory {

    @Autowired
    private Map<String,IReimbursementRemoteStrategy> map;

    public IReimbursementRemoteStrategy getReimbursementRemoteStrategy(String key){
        return map.get(key);
    }
}
