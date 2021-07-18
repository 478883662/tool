package com.zhangb.family.doctor.remote.strategy.impl;

import com.zhangb.family.doctor.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 查询报销记录对应的用药
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_SELECT_DRUG_STRATEGY)
public class ReimRemoteSelectDrugStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

    //查询报销用户（病人）信息
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
            "\ts:type=\"y:string\">yy_yyx@#020202101@$6588@!0Of4&lt;;;3:6@!刘庆团@!430621556588@!%s@!1@$2\t%s\t%s\t%s\t%s\t\t\t\t%s\t%s\t%s\t%s\t\t0\t\t0\t6588\t刘庆团\t0\t1\t0</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";

    //1 药品编号  2药品名称  3药品细分类  4药品序号  5入院时间 6单价  7数量  8金额
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }
}
