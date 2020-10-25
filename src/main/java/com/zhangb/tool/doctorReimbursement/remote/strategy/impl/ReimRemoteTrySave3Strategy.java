package com.zhangb.tool.doctorReimbursement.remote.strategy.impl;

import com.zhangb.tool.doctorReimbursement.adapter.ICxnhRemoteAdapter;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.tool.doctorReimbursement.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 保存试算
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE3_STRATEGY)
public class ReimRemoteTrySave3Strategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy{

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
            "\ts:type=\"y:string\">yy_yyx@#100000033@$6588@!0Of4&lt;;;3:6@!刘庆团@!|tceles *&#xd;\n" +
            "  from (|tceles a.jjlbbm, b.jjzhmc, a.ywid, a.id, a.je&#xd;\n" +
            "          from yw_jsjgb a, jbxx_jjzhdyb b&#xd;\n" +
            "         |erehw a.jjlbbm = b.jjzhdm&#xd;\n" +
            "           and a.hospital_id = :ls_hospital_id&#xd;\n" +
            "           and a.ywid = :ls_ywid&#xd;\n" +
            "           and a.id = :ls_id&#xd;\n" +
            "           and je &gt; 0&#xd;\n" +
            "        union&#xd;\n" +
            "        |tceles a.jjlbbm, b.jjzhmc, a.ywid, a.id, a.je&#xd;\n" +
            "          from yw_jsjgb_temp a, jbxx_jjzhdyb b&#xd;\n" +
            "         |erehw a.jjlbbm = b.jjzhdm&#xd;\n" +
            "           and a.hospital_id = :ls_hospital_id&#xd;\n" +
            "           and a.ywid = :ls_ywid&#xd;\n" +
            "           and a.id = :ls_id&#xd;\n" +
            "           and je &gt; 0)&#xd;\n" +
            " order by jjlbbm asc&#xd;\n" +
            "@!:ls_hospital_id#:ls_ywid#:ls_id@!430621556588#%s#1</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";



    // 1bizId  
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        System.out.println("报销试算结果："+respStr);
        return respStr;
    }
}
