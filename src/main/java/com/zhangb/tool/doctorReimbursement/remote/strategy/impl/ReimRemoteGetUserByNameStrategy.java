package com.zhangb.tool.doctorReimbursement.remote.strategy.impl;

import com.zhangb.tool.doctorReimbursement.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.tool.doctorReimbursement.adapter.ICxnhRemoteAdapter;
import com.zhangb.tool.doctorReimbursement.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_GET_USER_BY_NAME_STRATEGY)
public class ReimRemoteGetUserByNameStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy{

    //查询报销用户（病人）信息
    private static String reqStr = "<E:Envelope\n" +
            "\txmlns:E=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "\txmlns:A=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
            "\txmlns:s=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "\txmlns:y=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "\tE:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "<E:Body>\n" +
            "<m:nh_interface\n" +
            "\txmlns:m=\"http://tempuri.org/\">\n" +
            "<m:String_input\n" +
            "\ts:type=\"y:string\">yy_yyx@#100000033@$6588@!0Of4&lt;;;3:6@!刘庆团@!|tceles a.zklsh,&#xd;\n" +
            "       a.ylzh,&#xd;\n" +
            "       b.hzxm,&#xd;\n" +
            "       b.znhbh,&#xd;\n" +
            "       a.hnrbh,&#xd;\n" +
            "       a.yhzgx,&#xd;\n" +
            "       a.xm,&#xd;\n" +
            "       a.xb,&#xd;\n" +
            "       a.csrq,&#xd;\n" +
            "       a.sfzhm,&#xd;\n" +
            "       round((months_between(sysdate, a.csrq) + 1) / 12, 0) nl,&#xd;\n" +
            "       a.grbm,&#xd;\n" +
            "       b.xxdz,&#xd;\n" +
            "       a.hsx,&#xd;\n" +
            "       a.hospital_id,&#xd;\n" +
            "       a.ybkh,&#xd;\n" +
            "       a.yxbz,&#xd;\n" +
            "       a.hbm,&#xd;\n" +
            "       a.lxdh,&#xd;\n" +
            "       a.cbnd,&#xd;\n" +
            "       a.chxzjgdm,&#xd;\n" +
            "       a.fkbz,&#xd;\n" +
            "       uf_get_jtgrzh(a.grbm, &apos;0&apos;) jtzhye,&#xd;\n" +
            "       uf_get_jtgrzh(a.grbm, &apos;1&apos;) grzhye,&#xd;\n" +
            "       to_number(uf_get_tczfljz(a.grbm, &apos;MZ&apos;)) mztcye,&#xd;\n" +
            "       0 zycs,&#xd;\n" +
            "       uf_bcjs_get_mbzflj(a.grbm, a.cbnd) tczflj,&#xd;\n" +
            "       a.jzfp,&#xd;\n" +
            "       |rahc_ot(a.startdate, &apos;yyyymmdd&apos;) || &apos;-&apos; ||&#xd;\n" +
            "       |rahc_ot(a.enddate, &apos;yyyymmdd&apos;) bzsj&#xd;\n" +
            "  from jbxx_ryxx a, jbxx_hxx b&#xd;\n" +
            " |erehw a.hbm = b.hbm&#xd;\n" +
            "   and a.yxbz = &apos;1&apos;&#xd;\n" +
            "   and a.chsx &lt;&gt; &apos;A&apos;&#xd;\n" +
            "   and a.dqzt = &apos;1&apos; &#xd;\n" +
            "   and a.cbnd = :ls_nd&#xd;\n" +
            "   and a.xm like :ls_xm || &apos;%%&apos;&#xd;\n" +
            "   and a.ylzh like :ls_ylzh || &apos;%%&apos;&#xd;\n" +
            "   and nvl(a.ybkh, &apos; &apos;) like :ls_ybkh || &apos;%%&apos;&#xd;\n" +
            "   and nvl(a.zklsh, &apos; &apos;) like :ls_zklsh || &apos;%%&apos;&#xd;\n" +
            "   and nvl(a.sfzhm, &apos; &apos;) like :ls_sfzhm || &apos;%%&apos;&#xd;\n" +
            "@!:ls_nd#:ls_xm#:ls_ylzh#:ls_ybkh#:ls_zklsh#:ls_sfzhm@!2020#%s####</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";

    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }
}
