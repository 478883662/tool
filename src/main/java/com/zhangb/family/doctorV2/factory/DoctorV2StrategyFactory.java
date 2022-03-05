package com.zhangb.family.doctorV2.factory;

import com.zhangb.family.doctorV2.remote.strategy.IDoctorV2RemoteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by z9104 on 2020/9/23.
 */
@Component
public class DoctorV2StrategyFactory {

    @Autowired
    private Map<String, IDoctorV2RemoteStrategy> map;

    public IDoctorV2RemoteStrategy getReimbursementRemoteStrategy(String key){
        return map.get(key);
    }
}
