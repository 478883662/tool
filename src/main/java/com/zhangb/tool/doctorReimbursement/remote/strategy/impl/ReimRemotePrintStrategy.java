package com.zhangb.tool.doctorReimbursement.remote.strategy.impl;

import com.zhangb.tool.doctorReimbursement.adapter.ICxnhRemoteAdapter;
import com.zhangb.tool.doctorReimbursement.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.tool.doctorReimbursement.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 打印
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_PRINT_STRATEGY)
public class ReimRemotePrintStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy{

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
            "\ts:type=\"y:string\">yy_yyx@#100000033@$6588@!0Of4&lt;;;3:6@!刘庆团@!  |tceles DISTINCT(ROUND((MONTHS_BETWEEN(SYSDATE, JBXX_RYXX_LS.CSRQ) + 1) / 12, 0)) AS NL,&#xd;\n" +
            "         decode(substr(YW_DYYWB_LS.YWLB,1,1), &apos;1&apos;,0,&#xd;\n" +
            "         CASE WHEN YW_DYYWB_LS.ZJE &gt; 0 THEN&#xd;\n" +
            "                   UF_GET_ZYTS(|rahc_ot(YW_DYYWB_LS.RYSJ, &apos;YYYY-MM-DD&apos;),&#xd;\n" +
            "                   |rahc_ot(YW_DYYWB_LS.CYRQ, &apos;YYYY-MM-DD&apos;))&#xd;\n" +
            "         ELSE&#xd;\n" +
            "        (CASE WHEN YW_DYYWB_LS.ZJE &lt; 0 THEN&#xd;\n" +
            "                  -UF_GET_ZYTS(|rahc_ot(YW_DYYWB_LS.RYSJ, &apos;YYYY-MM-DD&apos;),&#xd;\n" +
            "                   |rahc_ot(YW_DYYWB_LS.CYRQ, &apos;YYYY-MM-DD&apos;))&#xd;\n" +
            "         ELSE&#xd;\n" +
            "         0&#xd;\n" +
            "         END) END) AS ZYTS,   &#xd;\n" +
            "         YW_DYYWB_LS.GRMC,   &#xd;\n" +
            "         decode(JBXX_RYXX_LS.XB,&apos;1&apos;,&apos;男&apos;,&apos;2&apos;,&apos;女&apos;,&apos;其他&apos;) as xb,   &#xd;\n" +
            "         JBXX_RYXX_LS.CSRQ,   &#xd;\n" +
            "         JBXX_RYXX_LS.SFZHM,   &#xd;\n" +
            "         YW_DYYWB_LS.YLZH,   &#xd;\n" +
            "         JBXX_RYXX_LS.HNRBH,   &#xd;\n" +
            "         YW_DYYWB_LS.ZYH,   &#xd;\n" +
            "         YW_DYYWB_LS.BCZHLB_ID,   &#xd;\n" +
            "         YW_DYYWB_LS.CYRQ,   &#xd;\n" +
            "         YW_DYYWB_LS.RYSJ,   &#xd;\n" +
            "         cy_jbmc DISEASE,   &#xd;\n" +
            "         BS_OPERATION.OPERATION_NAME,   &#xd;\n" +
            "         BS_HOSPITAL.HOSPITAL_NAME,   &#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;001&apos;) as lyzfy_cwf ,&#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;002&apos;) as lyzfy_hlf ,&#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;003&apos;) as lyzfy_xyf ,&#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;004&apos;) as lyzfy_zyf ,&#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;005&apos;) as lyzfy_hyf ,&#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;006&apos;) as lyzfy_zlf ,&#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;007&apos;) as lyzfy_ssf , &#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;008&apos;) as lyzfy_jcf , &#xd;\n" +
            "         (|tceles nvl(sum(zfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;009&apos;) as lyzfy_qtf ,  &#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;001&apos;) as kbxfy_cwf ,&#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;002&apos;) as kbxfy_hlf ,&#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;003&apos;) as kbxfy_xyf ,&#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;004&apos;) as kbxfy_zyf ,&#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;005&apos;) as kbxfy_hyf ,&#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;006&apos;) as kbxfy_zlf ,&#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;007&apos;) as kbxfy_ssf , &#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;008&apos;) as kbxfy_jcf , &#xd;\n" +
            "         (|tceles nvl(sum(YW_FYFLMXB_LS.kbxfy),0) from YW_FYFLMXB_LS |erehw YW_FYFLMXB_LS.HOSPITAL_ID = YW_DYYWB_LS.HOSPITAL_ID and YW_FYFLMXB_LS.YWID = YW_DYYWB_LS.YWID and YW_FYFLMXB_LS.ID = YW_DYYWB_LS.ID and stat_type = &apos;009&apos;) as kbxfy_qtf ,&#xd;\n" +
            "         NVL(YW_DYYWB_LS.JSRXM,YW_DYYWB_LS.DJRMC) AS JSR,  &#xd;\n" +
            "         YW_DYYWB_LS.KBXFY,&#xd;\n" +
            "\t\t\tYW_DYYWB_LS.BCDDM,   &#xd;\n" +
            "         YW_DYYWB_LS.SHJG_ID,   &#xd;\n" +
            "         YW_DYYWB_LS.KJSHR,   &#xd;\n" +
            "         YW_DYYWB_LS.TCKJ,   &#xd;\n" +
            "         YW_DYYWB_LS.KFLY,&#xd;\n" +
            "         YW_DYYWB_LS.TCSJZF,&#xd;\n" +
            "         YW_DYYWB_LS.DJHM,&#xd;\n" +
            "         XT_DMB.DMMC,&#xd;\n" +
            "         YW_DYYWB_LS.HOSPITAL_ID,&#xd;\n" +
            "         YW_DYYWB_LS.YWID,&#xd;\n" +
            "         YW_DYYWB_LS.ID,&#xd;\n" +
            "         A.DMMC,&#xd;\n" +
            "         YW_DYYWB_LS.MZJZZF,&#xd;\n" +
            "\t\t\tYW_DYYWB_LS.GRBM,&#xd;\n" +
            "\t\t\tYW_DYYWB_LS.YWLB,&#xd;\n" +
            "\t\t   YW_DYYWB_LS.JSSJ,&#xd;\n" +
            "\t\t\tv_bcjg.bcjgmc,&#xd;\n" +
            "\t\t\tUF_BCJS_GET_ZYTCZFLJZ(YW_DYYWB_LS.GRBM,substr(YW_DYYWB_LS.bczcsj,1,4)) AS ZYTCJJLJ,&#xd;\n" +
            "\t\t\tnvl((|tceles xzjgmc from jbxx_xzjgxx |erehw xzjb = &apos;1&apos; and rownum &lt; 2),&apos;&apos;) as xzjgmc,&#xd;\n" +
            "\t\t   (|tceles fzr from bs_jbjg |erehw jbjg_id=YW_DYYWB_LS.BCDDM) as fzr,&#xd;\n" +
            "\t\t\tJBXX_RYXX_LS.LXDH AS LXDH,&#xd;\n" +
            "\t\t\tJBXX_RYXX_LS.GRBM AS GRBM&#xd;\n" +
            "    FROM JBXX_RYXX_LS,   &#xd;\n" +
            "         YW_DYYWB_LS,           &#xd;\n" +
            "         BS_OPERATION,&#xd;\n" +
            "         BS_HOSPITAL,&#xd;\n" +
            "         XT_DMB,&#xd;\n" +
            "         XT_DMB A,&#xd;\n" +
            "\t\t\tv_bcjg&#xd;\n" +
            "   |erehw ( JBXX_RYXX_LS.ND = YW_DYYWB_LS.ND) AND&#xd;\n" +
            "\t\t   ( JBXX_RYXX_LS.GRBM = YW_DYYWB_LS.GRBM ) and       &#xd;\n" +
            "         ( BS_OPERATION.OPERATION_CODE(+) = YW_DYYWB_LS.OPERATION_CODE) AND&#xd;\n" +
            "         ( BS_HOSPITAL.HOSPITAL_ID(+) = YW_DYYWB_LS.HOSPITAL_ID ) AND&#xd;\n" +
            "         ( BS_HOSPITAL.JGJB = XT_DMB.DMBH) AND&#xd;\n" +
            "         ( XT_DMB.DMLBBH = &apos;S201-08&apos; ) AND&#xd;\n" +
            "         ( YW_DYYWB_LS.YWLB = A.DMBH) AND&#xd;\n" +
            "         ( A.DMLBBH = &apos;S301-06&apos; ) AND&#xd;\n" +
            "         ( YW_DYYWB_LS.HOSPITAL_ID = :YLJGBH ) AND  &#xd;\n" +
            "         ( YW_DYYWB_LS.YWID = :YWID ) AND  &#xd;\n" +
            "         ( YW_DYYWB_LS.ID = :ID ) and&#xd;\n" +
            "\t\t\t(YW_DYYWB_LS.bcddm = v_bcjg.bcjgdm(+)) and&#xd;\n" +
            "\t\t\t(YW_DYYWB_LS.jsfs = v_bcjg.bcjglx(+)) &#xd;\n" +
            "        &#xd;\n" +
            "@!:YLJGBH#:YWID#:ID@!430621556588#%s#1</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";

    //bizid
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }
}
