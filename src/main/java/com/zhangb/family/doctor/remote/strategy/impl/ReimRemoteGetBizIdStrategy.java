package com.zhangb.family.doctor.remote.strategy.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.zhangb.family.doctor.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 查询报销记录对应的用药
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_GET_BIZID_STRATEGY)
public class ReimRemoteGetBizIdStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

    //生成业务id的报文
    private static String reqStr ="<E:Envelope\n" +
            "\txmlns:E=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "\txmlns:A=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
            "\txmlns:s=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "\txmlns:y=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "\tE:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "<E:Body>\n" +
            "<m:nh_interface\n" +
            "\txmlns:m=\"http://tempuri.org/\">\n" +
            "<m:String_input\n" +
            "\ts:type=\"y:string\">yy_yyx@#000100002@$6588@!0Of4&lt;;;3:6@!刘庆团@!430621556588</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";

    //1 药品编号  2药品名称  3药品细分类  4药品序号  5入院时间 6单价  7数量  8金额
    @Override
    public String execute(String... param) {
        String reqParam = String.format(reqStr , param);
        HttpRequest postResquest = HttpUtil.createPost(RemoteConstants.url);
        super.setHeader(postResquest);
        String respStr = postResquest.body(reqParam).execute().body();
        String result = StrUtil.subBetween(respStr,"<nh_interfaceResult>","</nh_interfaceResult>");
        return StrUtil.removePrefix(result,RemoteConstants.PRE_GET_BIZID_RESP_STR);
    }
}
