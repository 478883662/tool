package com.zhangb.family.doctorV2.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctorV2.constants.DoctorV2RemoteConstant;
import com.zhangb.family.doctorV2.factory.DoctorV2StrategyFactory;
import com.zhangb.family.doctorV2.remote.strategy.IDoctorV2RemoteStrategy;
import com.zhangb.family.doctorV2.service.IDoctorV2ReimbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorV2ReimbServiceImpl implements IDoctorV2ReimbService {

    @Autowired
    private DoctorV2StrategyFactory doctorV2StrategyFactory;

    @Override
    public void tryCalculate() throws BizException {
        //保存试算第一步
        String try1Result = remoteExecute(DoctorV2RemoteConstant.TryCalculate1Strategy);
        if (!StrUtil.equals(try1Result,DoctorV2RemoteConstant.TRY_BEGIN_FLAG)){
            throw new BizException("保存试算第一步失败");
        }


    }



    @Override
    public void reimb() {

    }


    /**
     * 远程调用xml外部接口
     * @param strategyKey
     * @return
     */
    private String remoteExecute(String strategyKey,String... param) {
        //第一个参数必须是策略的key
        IDoctorV2RemoteStrategy strategy = doctorV2StrategyFactory.getReimbursementRemoteStrategy(strategyKey);
        String try1Result = strategy.execute(param);
        return try1Result;
    }
}
