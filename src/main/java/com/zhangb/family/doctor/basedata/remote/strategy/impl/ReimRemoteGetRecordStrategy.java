package com.zhangb.family.doctor.basedata.remote.strategy.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.family.doctor.basedata.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.remote.strategy.IReimbursementRemoteStrategy;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询用户报销记录
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_GET_RECORD_STRATEGY)
public class ReimRemoteGetRecordStrategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

    //查询报销用户（病人）信息
    private static String reqStr1 = "<E:Envelope\n" +
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
            "  from (|tceles |rahc_ot(cyrq, &apos;yyyy&apos;) as nd,&#xd;\n" +
            "               b.bcjgmc as hospital_name,&#xd;\n" +
            "               a.hospital_id,&#xd;\n" +
            "               a.ywid,&#xd;\n" +
            "               a.id,&#xd;\n" +
            "               a.ywlb_son,&#xd;\n" +
            "               a.djhm,&#xd;\n" +
            "               a.ylzh,&#xd;\n" +
            "               a.grbm,&#xd;\n" +
            "               a.grmc,&#xd;\n" +
            "               a.djsj,&#xd;\n" +
            "               a.zje,&#xd;\n" +
            "               a.kbxfy,&#xd;\n" +
            "               a.tczf,&#xd;\n" +
            "               a.tcsjzf,&#xd;\n" +
            "               a.jssj,&#xd;\n" +
            "               a.rysj,&#xd;\n" +
            "               a.cyrq,&#xd;\n" +
            "               a.bcddm,&#xd;\n" +
            "               d.bcjgmc as bcdmc,&#xd;\n" +
            "               a.jsr,&#xd;\n" +
            "               a.jsrxm,&#xd;\n" +
            "               a.yxywbz,&#xd;\n" +
            "               a.serial_icd,&#xd;\n" +
            "               cy_jbmc disease,&#xd;\n" +
            "               a.jsbz,&#xd;\n" +
            "               &apos;0&apos; as bz,&#xd;\n" +
            "               zcbfzf,&#xd;\n" +
            "               a.dbbcje,&#xd;\n" +
            "               (|tceles hgfy&#xd;\n" +
            "                  from v_dbzb_dyybw t&#xd;\n" +
            "                 |erehw a.hospital_id = t.hospital_id&#xd;\n" +
            "                   and a.ywid = t.ywid&#xd;\n" +
            "                   and a.id = t.id) hgfy,&#xd;\n" +
            "               a.thbbcje,&#xd;\n" +
            "               a.mzjzzf,&#xd;\n" +
            "               a.czddje,&#xd;\n" +
            "               a.yyjmje&#xd;\n" +
            "          from yw_dyywb a, v_bcjg b, v_bcjg d&#xd;\n" +
            "         |erehw a.hospital_id = b.bcjgdm(+)&#xd;\n" +
            "           and a.bcddm = d.bcjgdm(+)&#xd;\n" +
            "           and a.grbm = :ls_grbm&#xd;\n" +
            "        union&#xd;\n" +
            "        |tceles nd,&#xd;\n" +
            "               b.bcjgmc as hospital_name,&#xd;\n" +
            "               a.hospital_id,&#xd;\n" +
            "               a.ywid,&#xd;\n" +
            "               a.id,&#xd;\n" +
            "               a.ywlb_son,&#xd;\n" +
            "               a.djhm,&#xd;\n" +
            "               a.ylzh,&#xd;\n" +
            "               a.grbm,&#xd;\n" +
            "               a.grmc,&#xd;\n" +
            "               a.djsj,&#xd;\n" +
            "               a.zje,&#xd;\n" +
            "               a.kbxfy,&#xd;\n" +
            "               a.tczf,&#xd;\n" +
            "               a.tcsjzf,&#xd;\n" +
            "               a.jssj,&#xd;\n" +
            "               a.rysj,&#xd;\n" +
            "               a.cyrq,&#xd;\n" +
            "               a.bcddm,&#xd;\n" +
            "               d.bcjgmc as bcdmc,&#xd;\n" +
            "               a.jsr,&#xd;\n" +
            "               a.jsrxm,&#xd;\n" +
            "               a.yxywbz,&#xd;\n" +
            "               a.serial_icd,&#xd;\n" +
            "               cy_jbmc disease,&#xd;\n" +
            "               a.jsbz,&#xd;\n" +
            "               &apos;1&apos; as bz,&#xd;\n" +
            "               0 as zcbfzf,&#xd;\n" +
            "               a.dbbcje,&#xd;\n" +
            "               (|tceles hgfy&#xd;\n" +
            "                  from v_dbzb_dyybw t&#xd;\n" +
            "                 |erehw a.hospital_id = t.hospital_id&#xd;\n" +
            "                   and a.ywid = t.ywid&#xd;\n" +
            "                   and a.id = t.id) hgfy,&#xd;\n" +
            "               a.thbbcje,&#xd;\n" +
            "               a.mzjzzf,&#xd;\n" +
            "               a.czddje,&#xd;\n" +
            "               a.yyjmje&#xd;\n" +
            "          from yw_dyywb_ls a, v_bcjg b, v_bcjg d&#xd;\n" +
            "         |erehw a.hospital_id = b.bcjgdm(+)&#xd;\n" +
            "           and a.bcddm = d.bcjgdm(+)&#xd;\n" +
            "           and a.grbm = :ls_grbm)&#xd;\n" +
            " order by nd, jssj desc@!:ls_grbm@!%s</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";


    //查询报销用户（病人）信息
    private static String reqStr2 = "<E:Envelope\n" +
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
            "  from (|tceles |rahc_ot(cyrq, &apos;yyyy&apos;) as nd,&#xd;\n" +
            "               b.bcjgmc as hospital_name,&#xd;\n" +
            "               a.hospital_id,&#xd;\n" +
            "               a.ywid,&#xd;\n" +
            "               a.id,&#xd;\n" +
            "               a.ywlb_son,&#xd;\n" +
            "               a.djhm,&#xd;\n" +
            "               a.ylzh,&#xd;\n" +
            "               a.grbm,&#xd;\n" +
            "               a.grmc,&#xd;\n" +
            "               a.djsj,&#xd;\n" +
            "               a.zje,&#xd;\n" +
            "               a.kbxfy,&#xd;\n" +
            "               a.tczf,&#xd;\n" +
            "               a.tcsjzf,&#xd;\n" +
            "               a.jssj,&#xd;\n" +
            "               a.rysj,&#xd;\n" +
            "               a.cyrq,&#xd;\n" +
            "               a.bcddm,&#xd;\n" +
            "               d.bcjgmc as bcdmc,&#xd;\n" +
            "               a.jsr,&#xd;\n" +
            "               a.jsrxm,&#xd;\n" +
            "               a.yxywbz,&#xd;\n" +
            "               a.serial_icd,&#xd;\n" +
            "               cy_jbmc disease,&#xd;\n" +
            "               a.jsbz,&#xd;\n" +
            "               &apos;0&apos; as bz,&#xd;\n" +
            "               zcbfzf,&#xd;\n" +
            "               a.dbbcje,&#xd;\n" +
            "               (|tceles hgfy&#xd;\n" +
            "                  from v_dbzb_dyybw t&#xd;\n" +
            "                 |erehw a.hospital_id = t.hospital_id&#xd;\n" +
            "                   and a.ywid = t.ywid&#xd;\n" +
            "                   and a.id = t.id) hgfy,&#xd;\n" +
            "               a.thbbcje,&#xd;\n" +
            "               a.mzjzzf,&#xd;\n" +
            "               a.czddje,&#xd;\n" +
            "               a.yyjmje&#xd;\n" +
            "          from yw_dyywb a, v_bcjg b, v_bcjg d&#xd;\n" +
            "         |erehw a.hospital_id = b.bcjgdm(+)&#xd;\n" +
            "           and a.bcddm = d.bcjgdm(+)&#xd;\n" +
            "           and a.ywid = %s&#xd;\n" +
            "           and a.grbm = :ls_grbm&#xd;\n" +
            "        union&#xd;\n" +
            "        |tceles nd,&#xd;\n" +
            "               b.bcjgmc as hospital_name,&#xd;\n" +
            "               a.hospital_id,&#xd;\n" +
            "               a.ywid,&#xd;\n" +
            "               a.id,&#xd;\n" +
            "               a.ywlb_son,&#xd;\n" +
            "               a.djhm,&#xd;\n" +
            "               a.ylzh,&#xd;\n" +
            "               a.grbm,&#xd;\n" +
            "               a.grmc,&#xd;\n" +
            "               a.djsj,&#xd;\n" +
            "               a.zje,&#xd;\n" +
            "               a.kbxfy,&#xd;\n" +
            "               a.tczf,&#xd;\n" +
            "               a.tcsjzf,&#xd;\n" +
            "               a.jssj,&#xd;\n" +
            "               a.rysj,&#xd;\n" +
            "               a.cyrq,&#xd;\n" +
            "               a.bcddm,&#xd;\n" +
            "               d.bcjgmc as bcdmc,&#xd;\n" +
            "               a.jsr,&#xd;\n" +
            "               a.jsrxm,&#xd;\n" +
            "               a.yxywbz,&#xd;\n" +
            "               a.serial_icd,&#xd;\n" +
            "               cy_jbmc disease,&#xd;\n" +
            "               a.jsbz,&#xd;\n" +
            "               &apos;1&apos; as bz,&#xd;\n" +
            "               0 as zcbfzf,&#xd;\n" +
            "               a.dbbcje,&#xd;\n" +
            "               (|tceles hgfy&#xd;\n" +
            "                  from v_dbzb_dyybw t&#xd;\n" +
            "                 |erehw a.hospital_id = t.hospital_id&#xd;\n" +
            "                   and a.ywid = t.ywid&#xd;\n" +
            "                   and a.id = t.id) hgfy,&#xd;\n" +
            "               a.thbbcje,&#xd;\n" +
            "               a.mzjzzf,&#xd;\n" +
            "               a.czddje,&#xd;\n" +
            "               a.yyjmje&#xd;\n" +
            "          from yw_dyywb_ls a, v_bcjg b, v_bcjg d&#xd;\n" +
            "         |erehw a.hospital_id = b.bcjgdm(+)&#xd;\n" +
            "           and a.bcddm = d.bcjgdm(+)&#xd;\n" +
            "           and a.ywid = %s&#xd;\n" +
            "           and a.grbm = :ls_grbm)&#xd;\n" +
            " order by nd, jssj desc@!:ls_grbm@!%s</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";

    @Override
    public String execute(String... param) {
        String respStr = "";
        if (param.length == 1) {
            respStr = super.sendHttp(reqStr1, param);
        } else if (param.length == 3) {
            respStr = super.sendHttp(reqStr2, param);
        }

        return respStr;
    }

    @Override
    public List<ReimbDealRecordPO> parse(String resultStr) {
        List<ReimbDealRecordPO> resultList = new ArrayList<>();
        if (StrUtil.hasBlank(resultStr)) {
            return resultList;
        }
        String[] rowList = StrUtil.split(resultStr, "\r\n");
        for (String row : rowList) {
            String[] result = StrUtil.split(row, "\t");
            ReimbDealRecordPO reimbDealRecordPO = new ReimbDealRecordPO();
            reimbDealRecordPO.setReimbNo(result[6]);
            reimbDealRecordPO.setIllNessNo(result[23]);
            reimbDealRecordPO.setIllNessName(result[24]);
            reimbDealRecordPO.setInDate(DateUtil.parseDate(result[16]));
            reimbDealRecordPO.setOutDate(DateUtil.parseDate(result[17]));
            reimbDealRecordPO.setMoney(new BigDecimal(result[14]));
            reimbDealRecordPO.setReimbDate(DateUtil.parseDate(result[15]));
            reimbDealRecordPO.setReimbType(result[5]);
            reimbDealRecordPO.setYlLocation(result[19]);
            reimbDealRecordPO.setYlLocationNo(result[18]);
            reimbDealRecordPO.setYlCard(result[7]);
            reimbDealRecordPO.setSelfNo(result[8]);
            reimbDealRecordPO.setName(result[9]);
            reimbDealRecordPO.setReimbYear(result[0]);
            reimbDealRecordPO.setBizId(result[3]);
            resultList.add(reimbDealRecordPO);
        }
        return resultList;
    }
}
