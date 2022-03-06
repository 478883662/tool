package com.zhangb.family.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.Map;

public class MyHttpUtil {

    /**
     * @param reqStr
     * @param headerMap
     * @return
     */
    public static <T> T doPost(String reqStr, Map<String,String> headerMap, TypeReference<T> typeReference,String url){
        HttpRequest postResquest = HttpUtil.createPost(url);
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
