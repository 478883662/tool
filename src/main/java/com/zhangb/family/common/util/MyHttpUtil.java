package com.zhangb.family.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zhangb.family.doctorV2.constants.DoctorV2RemoteConstant;

import java.util.HashMap;

public class MyHttpUtil {

    /**
     * 远程调用长信农合接口
     * @param reqStr
     * @param headerMap
     * @return
     */
    public <T> T  sendHttp(String reqStr, HashMap<String,String> headerMap, TypeReference<T> typeReference){
        HttpRequest postResquest = HttpUtil.createPost(DoctorV2RemoteConstant.url);
        if (CollectionUtil.isNotEmpty(headerMap)){
            for (String key :headerMap.keySet()){
                postResquest.header(key,headerMap.get(key));
            }
        }
        //调用外部接口
        String respStr = postResquest.body(reqStr).charset("UTF-8").setReadTimeout(180000).execute().body();
        return JSONUtil.toBean(respStr,typeReference,true);
    };
}
