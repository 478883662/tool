package com.zhangb.family.doctorV2.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.common.util.MyHttpUtil;
import com.zhangb.family.doctorV2.constants.DoctorV2RemoteConstant;
import com.zhangb.family.doctor.dto.DoctorV2ReqHeaderDTO;
import com.zhangb.family.doctor.dto.TryCalculate2DataDTO;
import com.zhangb.family.doctor.dto.TryCalculate2InputDTO;
import com.zhangb.family.doctor.dto.TryCalculate2ResultDTO;
import com.zhangb.family.doctorV2.factory.DoctorV2StrategyFactory;
import com.zhangb.family.doctorV2.remote.strategy.IDoctorV2RemoteStrategy;
import com.zhangb.family.doctorV2.service.IDoctorV2ReimbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DoctorV2ReimbServiceImpl implements IDoctorV2ReimbService {

    @Autowired
    private DoctorV2StrategyFactory doctorV2StrategyFactory;

    @Override
    public void tryCalculate() throws BizException {
        //1
        String try1Result = remoteExecute(DoctorV2RemoteConstant.TryCalculate1Strategy);
        if (!StrUtil.equals(try1Result,DoctorV2RemoteConstant.TRY_1_FLAG)){
            throw new BizException("保存试算第1步失败");
        }

        //2
        DoctorV2ReqHeaderDTO tryCalculate2DTO = DoctorV2ReqHeaderDTO.builder()
                .infno("2201")
                .msgid("H43062100453202112262113372064")
                .inf_time(DateUtil.now())
                .input(TryCalculate2InputDTO.builder()
                        .data(TryCalculate2DataDTO.builder()
                                .psn_no("43000050621722721870")
                                .insutype("390")
                                .begntime("2021-12-10 00:00:00")
                                .mdtrt_cert_type("02")
                                .mdtrt_cert_no("430621198807116145")
                                .ipt_otp_no("20211200107250")
                                .atddr_no("H43062100453-03")
                                .dr_name("刘庆团")
                                .dept_code("A02")
                                .dept_name("全科门诊")
                                .caty("A02")
                                .build())
                        .build())
                .build();
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        headerMap.put("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR  3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        headerMap.put("_api_name","2201");
        headerMap.put("_api_version","1.0.0");
        headerMap.put("_api_timestamp","1640524417887");
        headerMap.put("_api_access_key","xzlfa1bTkYCzGuwkDhQUQDI3Ew9wy0CuUMUVxD");
        headerMap.put("_api_signature","1MXRIzlMtSzkSkUfLCosy0fvK5Y=");
        headerMap.put("Host","10.93.33.229:20001");
        headerMap.put("Accept","text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        headerMap.put("Connection","keep-alive");
        headerMap.put("Content-Length","722");
        TryCalculate2ResultDTO tryCalculate2ResultDTO = MyHttpUtil.doPost(JSONUtil.toJsonStr(tryCalculate2DTO),
                headerMap,
                new TypeReference<TryCalculate2ResultDTO>(){},
                "/isp-api/powercsb/2201");


        //3
        String try3Result = remoteExecute(DoctorV2RemoteConstant.TryCalculate3Strategy);
        if (!StrUtil.equals(try3Result,DoctorV2RemoteConstant.TRY_3_FLAG)){
            throw new BizException("保存试算第3步失败");
        }


        //4


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
