package com.zhangb.family.doctor.remote.strategy.impl;

import com.zhangb.family.doctor.adapter.ICxnhRemoteAdapter;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.remote.strategy.IReimbursementRemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 保存试算
 * Created by z9104 on 2020/9/23.
 */
@Service(ReimbRemoteStrategyKeyConstants.REIMB_TRY_SAVE1_STRATEGY)
public class ReimRemoteTrySave1Strategy extends ICxnhRemoteAdapter implements IReimbursementRemoteStrategy {

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
            "\ts:type=\"y:string\">yy_yyx@#000000042@$6588@!0Of4&lt;;;3:6@!刘庆团@!up_bcjs_scyw_jcsj@!&lt;?xml version=&quot;1.0&quot; encoding=&quot;gb2312&quot; standalone=&quot;no&quot;?&gt;\n" +
            "\n" +
            "&lt;dw_bcgl_grxx_lr&gt;&lt;dw_bcgl_grxx_lr_row&gt;&lt;zklsh&gt;%s&lt;/zklsh&gt;&lt;ylzh&gt;%s&lt;/ylzh&gt;&lt;hzxm&gt;%s&lt;/hzxm&gt;&lt;znhbh/&gt;&lt;hnrbh/&gt;&lt;yhzgx&gt;2&lt;/yhzgx&gt;&lt;xm&gt;%s&lt;/xm&gt;&lt;xb&gt;1&lt;/xb&gt;&lt;csrq&gt;%s&lt;/csrq&gt;&lt;sfzhm&gt;%s&lt;/sfzhm&gt;&lt;nl&gt;%s&lt;/nl&gt;&lt;grbm&gt;%s&lt;/grbm&gt;&lt;xxdz&gt;城关镇-&amp;gt;跃进村-&amp;gt;1组&lt;/xxdz&gt;&lt;hsx&gt;1&lt;/hsx&gt;&lt;jtzhye&gt;0&lt;/jtzhye&gt;&lt;grzhye/&gt;&lt;hospital_id&gt;&lt;/hospital_id&gt;&lt;hospital_name&gt;&lt;/hospital_name&gt;&lt;ybkh&gt;&lt;/ybkh&gt;&lt;yxbz&gt;1&lt;/yxbz&gt;&lt;fkbz&gt;0&lt;/fkbz&gt;&lt;lxdh&gt;&lt;/lxdh&gt;&lt;zycs&gt;0&lt;/zycs&gt;&lt;tczflj&gt;&lt;/tczflj&gt;&lt;mztcye/&gt;&lt;cbnd&gt;%s&lt;/cbnd&gt;&lt;jzfp&gt;0&lt;/jzfp&gt;&lt;/dw_bcgl_grxx_lr_row&gt;&lt;/dw_bcgl_grxx_lr&gt;\n" +
            "@!&lt;?xml version=&quot;1.0&quot; encoding=&quot;gb2312&quot; standalone=&quot;no&quot;?&gt;\n" +
            "\n" +
            "&lt;dw_bcgl_zlxx_temp&gt;&lt;dw_bcgl_zlxx_temp_row&gt;&lt;ylbm&gt;430621556588&lt;/ylbm&gt;&lt;yljg&gt;城关镇泥家湖村卫生室&lt;/yljg&gt;&lt;yljgjb&gt;1&lt;/yljgjb&gt;&lt;zyh&gt; &lt;/zyh&gt;&lt;jsdh&gt; &lt;/jsdh&gt;&lt;ryzdbm&gt;%s&lt;/ryzdbm&gt;&lt;ryzd_icd&gt;无&lt;/ryzd_icd&gt;&lt;ssdm&gt; &lt;/ssdm&gt;&lt;ssmc&gt;&lt;/ssmc&gt;&lt;rysj&gt;%s&lt;/rysj&gt;&lt;cysj&gt;&lt;/cysj&gt;&lt;zyts&gt;0&lt;/zyts&gt;&lt;ks&gt; &lt;/ks&gt;&lt;ysdm&gt; &lt;/ysdm&gt;&lt;ysmc&gt; &lt;/ysmc&gt;&lt;bclb&gt;1101&lt;/bclb&gt;&lt;bclbbm&gt;11&lt;/bclbbm&gt;&lt;ryzdxh&gt;0&lt;/ryzdxh&gt;&lt;ksbm&gt; &lt;/ksbm&gt;&lt;wdyymc&gt; &lt;/wdyymc&gt;&lt;ywid&gt;%s&lt;/ywid&gt;&lt;id&gt;1&lt;/id&gt;&lt;bcddm&gt;430621556588&lt;/bcddm&gt;&lt;jsbz&gt;1&lt;/jsbz&gt;&lt;tckj&gt;0&lt;/tckj&gt;&lt;kfly&gt;&lt;/kfly&gt;&lt;zbbz&gt;0&lt;/zbbz&gt;&lt;jzdh&gt;&lt;/jzdh&gt;&lt;weight&gt;0&lt;/weight&gt;&lt;blood&gt;0&lt;/blood&gt;&lt;tczf_other&gt;&lt;/tczf_other&gt;&lt;zlfs&gt;0&lt;/zlfs&gt;&lt;zlfs_name&gt;0&lt;/zlfs_name&gt;&lt;ryzdmc&gt;%s&lt;/ryzdmc&gt;&lt;bczcsj&gt;20200101&lt;/bczcsj&gt;&lt;bclb_son&gt;1101&lt;/bclb_son&gt;&lt;bllx&gt;&lt;/bllx&gt;&lt;/dw_bcgl_zlxx_temp_row&gt;&lt;/dw_bcgl_zlxx_temp&gt;\n" +
            "</m:String_input>\n" +
            "</m:nh_interface>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";



    //1 cardNo    2医疗账号  3户主姓名  4生日  5身份证
    // 6年龄   7个人编码  8报销年度 9病例编码  10入院日期 11bizId  11病例名称
    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }
}
