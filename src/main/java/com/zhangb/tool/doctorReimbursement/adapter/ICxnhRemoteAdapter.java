package com.zhangb.tool.doctorReimbursement.adapter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.zhangb.tool.doctorReimbursement.common.constants.RemoteConstants;

/**
 * Created by z9104 on 2020/9/23.
 */
public abstract class ICxnhRemoteAdapter {

    /**
     * 设置请求头
     * @param httpResquest
     */
    protected void setHeader(HttpRequest httpResquest){
        httpResquest.header("Host","222.247.54.146:8086");
        httpResquest.header("Connection","Keep-Alive");
        httpResquest.header("User-Agent","EasySoap++/0.6");
        httpResquest.header("Content-Type","text/xml; charset=\"UTF-8\"");
        httpResquest.header("SOAPAction","\"http://tempuri.org/nh_interface\"");
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
        HttpRequest postResquest = HttpUtil.createPost(RemoteConstants.url);
        setHeader(postResquest);
        String respStr = postResquest.body(reqParam).charset("UTF-8").setReadTimeout(180000).execute().body();
        String result = StrUtil.subBetween(respStr,"<nh_interfaceResult>","</nh_interfaceResult>");
        return StrUtil.removePrefix(result,RemoteConstants.PRE_RESP_STR);
    };
}
