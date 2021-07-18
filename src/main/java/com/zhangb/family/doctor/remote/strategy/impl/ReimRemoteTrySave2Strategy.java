package com.zhangb.family.doctor.remote.strategy.impl;

import com.zhangb.family.doctor.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 保存试算
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE2_STRATEGY)
public class ReimRemoteTrySave2Strategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

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
            "\ts:type=\"y:string\">yy_yyx@#020202103@$6588@!0Of4&lt;;;3:6@!刘庆团@!0@!430621556588@!%s@!1</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";



    // 1bizId
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }
}
