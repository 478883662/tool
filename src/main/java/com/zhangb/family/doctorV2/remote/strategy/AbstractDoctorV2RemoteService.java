package com.zhangb.family.doctorV2.remote.strategy;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.zhangb.family.doctorV2.constants.DoctorV2RemoteConstant;


public abstract class AbstractDoctorV2RemoteService {

    /**
     * 设置请求头
     * @param httpResquest
     */
    protected void setHeader(HttpRequest httpResquest){
        httpResquest.header("Host","49.234.46.221");
        httpResquest.header("Connection","Keep-Alive");
        httpResquest.header("User-Agent","EasySoap++/0.6");
        httpResquest.header("Content-Type","text/xml; charset=\"UTF-8\"");
        httpResquest.header("SOAPAction","\"http://tempuri.org/exec\"");
        httpResquest.header("Content-Length","\"1992\"");
    }

    /**
     * 远程调用长信农合接口
     * @param reqStr
     * @param param
     * @return
     */
    public String sendHttp(String reqStr,String... param){
        String reqParam = String.format(reqStr , param);
        HttpRequest postResquest = HttpUtil.createPost(DoctorV2RemoteConstant.url);
        setHeader(postResquest);
        String respStr = postResquest.body(reqParam).charset("UTF-8").setReadTimeout(180000).execute().body();
        String result = StrUtil.subBetween(respStr,"<execResult>","</execResult>");
        return result;
    };


}
