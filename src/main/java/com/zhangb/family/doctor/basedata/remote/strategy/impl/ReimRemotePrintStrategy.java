package com.zhangb.family.doctor.basedata.remote.strategy.impl;

import com.zhangb.family.doctor.basedata.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.basedata.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 打印
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_PRINT_STRATEGY)
public class ReimRemotePrintStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

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
            "\ts:type=\"y:string\">yy_yyx@#100000033@$6588@!0Of4&lt;;;3:6@!刘庆团@!|tceles ROUND((MONTHS_BETWEEN(SYSDATE, JBXX_RYXX.CSRQ) + 1) / 12, 0) AS NL,&#xd;\n" +
            "       decode(substr(YW_DYYWB.YWLB, 1, 1),&#xd;\n" +
            "              &apos;1&apos;,&#xd;\n" +
            "              0,&#xd;\n" +
            "              CASE&#xd;\n" +
            "                WHEN YW_DYYWB.ZJE &gt; 0 THEN&#xd;\n" +
            "                 UF_GET_ZYTS(|rahc_ot(YW_DYYWB.RYSJ, &apos;YYYY-MM-DD&apos;),&#xd;\n" +
            "                             |rahc_ot(YW_DYYWB.CYRQ, &apos;YYYY-MM-DD&apos;))&#xd;\n" +
            "                ELSE&#xd;\n" +
            "                 (CASE&#xd;\n" +
            "                WHEN YW_DYYWB.ZJE &lt; 0 THEN&#xd;\n" +
            "                 -UF_GET_ZYTS(|rahc_ot(YW_DYYWB.RYSJ, &apos;YYYY-MM-DD&apos;),&#xd;\n" +
            "                              |rahc_ot(YW_DYYWB.CYRQ, &apos;YYYY-MM-DD&apos;))&#xd;\n" +
            "                ELSE&#xd;\n" +
            "                 0&#xd;\n" +
            "              END) END) AS ZYTS,&#xd;\n" +
            "       YW_DYYWB.GRMC,&#xd;\n" +
            "       decode(JBXX_RYXX.XB, &apos;1&apos;, &apos;男&apos;, &apos;2&apos;, &apos;女&apos;, &apos;其他&apos;) as xb,&#xd;\n" +
            "       JBXX_RYXX.CSRQ,&#xd;\n" +
            "       JBXX_RYXX.SFZHM,&#xd;\n" +
            "       YW_DYYWB.YLZH,&#xd;\n" +
            "       JBXX_RYXX.HNRBH,&#xd;\n" +
            "       YW_DYYWB.ZYH,&#xd;\n" +
            "       YW_DYYWB.BCZHLB_ID,&#xd;\n" +
            "       YW_DYYWB.CYRQ,&#xd;\n" +
            "       YW_DYYWB.RYSJ,&#xd;\n" +
            "       CY_JBMC ,&#xd;\n" +
            "       BS_OPERATION.OPERATION_NAME,&#xd;\n" +
            "       BS_HOSPITAL.HOSPITAL_NAME,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;001&apos;) as lyzfy_cwf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;002&apos;) as lyzfy_hlf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;003&apos;) as lyzfy_xyf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;004&apos;) as lyzfy_zyf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;005&apos;) as lyzfy_hyf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;006&apos;) as lyzfy_zlf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;007&apos;) as lyzfy_ssf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;008&apos;) as lyzfy_jcf,&#xd;\n" +
            "       (|tceles nvl(sum(zfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type in (&apos;009&apos;,&apos;011&apos;)) as lyzfy_qtf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;001&apos;) as kbxfy_cwf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;002&apos;) as kbxfy_hlf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;003&apos;) as kbxfy_xyf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;004&apos;) as kbxfy_zyf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;005&apos;) as kbxfy_hyf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;006&apos;) as kbxfy_zlf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;007&apos;) as kbxfy_ssf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type = &apos;008&apos;) as kbxfy_jcf,&#xd;\n" +
            "       (|tceles nvl(sum(YW_FYFLMXB.kbxfy), 0)&#xd;\n" +
            "          from YW_FYFLMXB&#xd;\n" +
            "         |erehw YW_FYFLMXB.HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           and YW_FYFLMXB.YWID = YW_DYYWB.YWID&#xd;\n" +
            "           and YW_FYFLMXB.ID = YW_DYYWB.ID&#xd;\n" +
            "           and stat_type in (&apos;009&apos;,&apos;011&apos;)) as kbxfy_qtf,&#xd;\n" +
            "       NVL(YW_DYYWB.JSRXM, YW_DYYWB.DJRMC) AS JSR,&#xd;\n" +
            "       YW_DYYWB.KBXFY,&#xd;\n" +
            "       YW_DYYWB.BCDDM,&#xd;\n" +
            "       YW_DYYWB.SHJG_ID,&#xd;\n" +
            "       YW_DYYWB.KJSHR,&#xd;\n" +
            "       (|tceles SUM(ZJBCFW)&#xd;\n" +
            "          FROM YW_FYMXB&#xd;\n" +
            "         |erehw HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           AND YWID = YW_DYYWB.YWID&#xd;\n" +
            "           AND ID = YW_DYYWB.ID) AS TCKJ,&#xd;\n" +
            "       (|tceles MAX(YW_KFYYB.KFYY)&#xd;\n" +
            "          FROM YW_FYMXB, YW_KFYYB&#xd;\n" +
            "         |erehw HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           AND YWID = YW_DYYWB.YWID&#xd;\n" +
            "           AND ID = YW_DYYWB.ID&#xd;\n" +
            "           AND YW_FYMXB.ZJBCLY = YW_KFYYB.KFYYBH) AS KFLY,&#xd;\n" +
            "       YW_DYYWB.TCSJZF,&#xd;\n" +
            "       YW_DYYWB.DJHM,&#xd;\n" +
            "       XT_DMB.DMMC,&#xd;\n" +
            "       YW_DYYWB.HOSPITAL_ID,&#xd;\n" +
            "       YW_DYYWB.YWID,&#xd;\n" +
            "       YW_DYYWB.ID,&#xd;\n" +
            "       A.DMMC,&#xd;\n" +
            "       YW_DYYWB.MZJZZF,&#xd;\n" +
            "       YW_DYYWB.GRBM,&#xd;\n" +
            "       YW_DYYWB.YWLB,&#xd;\n" +
            "       YW_DYYWB.JSSJ,&#xd;\n" +
            "       v_bcjg.bcjgmc,&#xd;\n" +
            "       UF_BCJS_GET_ZYTCZFLJZ(YW_DYYWB.GRBM, substr(YW_DYYWB.bczcsj, 1, 4)) AS ZYTCJJLJ,&#xd;\n" +
            "       decode(UF_BCJS_GET_SFDFDX(:YLJGBH,&#xd;\n" +
            "                                 :YWID,&#xd;\n" +
            "                                 :ID,&#xd;\n" +
            "                                 |rahc_ot(YW_DYYWB.JSSJ, &apos;YYYY&apos;)),&#xd;\n" +
            "              &apos;0&apos;,&#xd;\n" +
            "              &apos;未达&apos;,&#xd;\n" +
            "              &apos;1&apos;,&#xd;\n" +
            "              &apos;已达&apos;) AS SFFXD,&#xd;\n" +
            "       nvl((|tceles xzjgmc&#xd;\n" +
            "             from jbxx_xzjgxx&#xd;\n" +
            "            |erehw xzjb = &apos;1&apos;&#xd;\n" +
            "              and rownum &lt; 2),&#xd;\n" +
            "           &apos;&apos;) as xzjgmc,&#xd;\n" +
            "       (|tceles fzr from bs_jbjg |erehw jbjg_id = YW_DYYWB.BCDDM) as fzr,&#xd;\n" +
            "       JBXX_RYXX.LXDH AS LXDH,&#xd;\n" +
            "       JBXX_RYXX.GRBM AS GRBM,&#xd;\n" +
            "       YW_DYYWB.MXSHRXM AS MXSHRXM,&#xd;\n" +
            "       (|tceles dmmc&#xd;\n" +
            "          FROM xt_dmb&#xd;\n" +
            "         |erehw xt_dmb.dmlbbh = &apos;S101-10&apos;&#xd;\n" +
            "           and dmbh = YW_DYYWB.HSX) AS HSX,&#xd;\n" +
            "       (|tceles SUM(ZJBCFW)&#xd;\n" +
            "          FROM YW_FYMXB&#xd;\n" +
            "         |erehw HOSPITAL_ID = YW_DYYWB.HOSPITAL_ID&#xd;\n" +
            "           AND YWID = YW_DYYWB.YWID&#xd;\n" +
            "           AND ID = YW_DYYWB.ID)  *&#xd;\n" +
            "       UF_BCJS_GET_BCBL(YW_DYYWB.HOSPITAL_ID, YW_DYYWB.ywid, YW_DYYWB.id) as kbxto_sjzf&#xd;\n" +
            "  FROM JBXX_RYXX,&#xd;\n" +
            "       YW_DYYWB,       &#xd;\n" +
            "       BS_OPERATION,&#xd;\n" +
            "       BS_HOSPITAL,&#xd;\n" +
            "       XT_DMB,&#xd;\n" +
            "       XT_DMB A,&#xd;\n" +
            "       v_bcjg&#xd;\n" +
            " |erehw (JBXX_RYXX.GRBM = YW_DYYWB.GRBM) &#xd;\n" +
            "   AND (BS_OPERATION.OPERATION_CODE(+) = YW_DYYWB.OPERATION_CODE)&#xd;\n" +
            "   AND (BS_HOSPITAL.HOSPITAL_ID(+) = YW_DYYWB.HOSPITAL_ID)&#xd;\n" +
            "   AND (BS_HOSPITAL.JGJB = XT_DMB.DMBH)&#xd;\n" +
            "   AND (XT_DMB.DMLBBH = &apos;S201-08&apos;)&#xd;\n" +
            "   AND (YW_DYYWB.YWLB = A.DMBH)&#xd;\n" +
            "   AND (A.DMLBBH = &apos;S301-06&apos;)&#xd;\n" +
            "   AND (YW_DYYWB.HOSPITAL_ID = :YLJGBH)&#xd;\n" +
            "   AND (YW_DYYWB.YWID = :YWID)&#xd;\n" +
            "   AND (YW_DYYWB.ID = :ID)&#xd;\n" +
            "   and (YW_DYYWB.bcddm = v_bcjg.bcjgdm)&#xd;\n" +
            "   and (YW_DYYWB.jsfs = v_bcjg.bcjglx)&#xd;\n" +
            "@!:YLJGBH#:YWID#:ID@!430621556588#%s#1</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>\n";

    //bizid
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        System.out.println(respStr);
        return respStr;
    }

    @Override
    public <T> T parse(String respStr) {
        return null;
    }
}
