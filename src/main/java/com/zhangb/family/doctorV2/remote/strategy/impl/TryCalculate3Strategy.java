package com.zhangb.family.doctorV2.remote.strategy.impl;

import com.zhangb.family.doctorV2.constants.DoctorV2RemoteConstant;
import com.zhangb.family.doctorV2.remote.strategy.AbstractDoctorV2RemoteService;
import com.zhangb.family.doctorV2.remote.strategy.IDoctorV2RemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 保存试算3
 * Created by z9104 on 2020/9/23.
 */
@Service(DoctorV2RemoteConstant.TryCalculate3Strategy)
public class TryCalculate3Strategy extends AbstractDoctorV2RemoteService implements IDoctorV2RemoteStrategy {

    //生成业务id的报文
    private static String reqStr ="<E:Envelope\n" +
            "\txmlns:E=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "\txmlns:A=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
            "\txmlns:s=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "\txmlns:y=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "\tE:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "<E:Body>\n" +
            "<m:exec\n" +
            "\txmlns:m=\"http://tempuri.org/\">\n" +
            "<m:String_input\n" +
            "\ts:type=\"y:string\">insu_clinic@#000000034@$H43062100453-03@!123456@!刘庆团@!update yygle50 set scsbz = &apos;1&apos;, sdjlsh = &apos;53006572&apos;,mdtrt_cert_type = &apos;02&apos;,nbrxx_zhye = &apos;&apos; where sbrxx01 = &apos;20211200107250&apos;</m:String_input>\n" +
            "</m:exec>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";



    @Override
    public String execute(String... param) {
        String respStr = super.sendHttp(reqStr,param);
        return respStr;
    }

    /*
   <?xml version="1.0" encoding="utf-8"?>
<soap:Envelope
    xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soap:Body>
        <execResponse
            xmlns="http://tempuri.org/">
            <execResult>0@!</execResult>
        </execResponse>
    </soap:Body>
</soap:Envelope>

    */


}
