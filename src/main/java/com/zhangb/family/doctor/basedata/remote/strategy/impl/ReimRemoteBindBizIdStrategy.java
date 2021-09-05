package com.zhangb.family.doctor.basedata.remote.strategy.impl;

import com.zhangb.family.doctor.basedata.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.basedata.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 查询报销记录对应的用药
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_BIND_BIZID_STRATEGY)
public class ReimRemoteBindBizIdStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

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
            "\ts:type=\"y:string\">yy_yyx@#020202100@$6588@!0Of4&lt;;;3:6@!刘庆团@!1@!430621556588@!%s@!1@!1101@!0@!@!%s@!%s@!%s@!@!@!@!%s@!无@!6588@!刘庆团@!%s@!@!1@!@!1@!@!@!@!@!@!@!@!0@!%s@!2@!1@!430621556588@!@!20200101@!0</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";

    //1 业务id     2用户个人编码  3用户姓名 4医疗账户  5病例编码    6入院时间  7出院时间
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }

    @Override
    public <T> T parse(String respStr) {
        return null;
    }
}
