package com.zhangb.tool.doctorReimbursement.remote.strategy.impl;

import com.zhangb.tool.doctorReimbursement.adapter.ICxnhRemoteAdapter;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.tool.doctorReimbursement.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 查询报销记录对应的用药
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_GET_ILLNESS_DRUG_STRATEGY)
public class ReimRemoteGetIllnessDrugStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy{

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
            "\ts:type=\"y:string\">yy_yyx@#100000033@$6588@!0Of4&lt;;;3:6@!刘庆团@!|tceles rownum as hs,&#xd;\n" +
            "       a.hospital_id,&#xd;\n" +
            "       a.ywid,&#xd;\n" +
            "       a.id,&#xd;\n" +
            "       a.fyxh,&#xd;\n" +
            "       a.medi_item_type,&#xd;\n" +
            "       a.item_code,&#xd;\n" +
            "       a.item_name,&#xd;\n" +
            "       a.model_name,&#xd;\n" +
            "       a.hosp_code,&#xd;\n" +
            "       a.hosp_name,&#xd;\n" +
            "       a.hosp_model,&#xd;\n" +
            "       a.stat_type,&#xd;\n" +
            "       a.dj,&#xd;\n" +
            "       a.yl,&#xd;\n" +
            "       a.jldw,&#xd;\n" +
            "       a.gg,&#xd;\n" +
            "       a.je,&#xd;\n" +
            "       a.jrbxje,&#xd;\n" +
            "       a.jrbxje as jrbxje_temp,&#xd;\n" +
            "       a.lrrdm,&#xd;\n" +
            "       a.lrrxm,&#xd;\n" +
            "       a.lrsj,&#xd;\n" +
            "       a.jsbcfw,&#xd;\n" +
            "       a.zjbcfw,&#xd;\n" +
            "       a.zjbcly,&#xd;\n" +
            "       a.zxshrxm,&#xd;\n" +
            "       a.zjbcly as zjbcly_temp,&#xd;\n" +
            "       a.yzcfh,&#xd;\n" +
            "       a.shbz,&#xd;\n" +
            "       a.shbz as shbz_temp,&#xd;\n" +
            "       a.tckj,&#xd;\n" +
            "       a.kfly,&#xd;\n" +
            "       a.self_scale, &#xd;\n" +
            "       a.fyfssj,&#xd;\n" +
            "       a.gdzfje&#xd;\n" +
            "  from yw_fymxb a, bs_hospital e&#xd;\n" +
            " |erehw a.hospital_id = e.hospital_id&#xd;\n" +
            "   and a.hospital_id = :as_hospital_id&#xd;\n" +
            "   and a.ywid = :as_ywid&#xd;\n" +
            "   and a.id = :as_id&#xd;\n" +
            " order by fyxh&#xd;\n" +
            "@!:as_hospital_id#:as_ywid#:as_id@!%s#%s#1</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>\n";

    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }
}
