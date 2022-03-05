package com.zhangb.family.doctorV2.remote.strategy.impl;

import com.zhangb.family.doctorV2.constants.DoctorV2RemoteConstant;
import com.zhangb.family.doctorV2.remote.strategy.AbstractDoctorV2RemoteService;
import com.zhangb.family.doctorV2.remote.strategy.IDoctorV2RemoteStrategy;
import org.springframework.stereotype.Service;

/**
 * 保存试算
 * Created by z9104 on 2020/9/23.
 */
@Service(DoctorV2RemoteConstant.TryCalculate1Strategy)
public class TryCalculate1Strategy extends AbstractDoctorV2RemoteService implements IDoctorV2RemoteStrategy {

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
            "\ts:type=\"y:string\">insu_clinic@#000000035@$H43062100453-03@!123456@!刘庆团@!select caty From t_his_dept Where hosp_dept_codg = &apos;A02&apos;</m:String_input>\n" +
            "</m:exec>\n" +
            "</E:Body>\n" +
            "</E:Envelope>";



    //1 业务id     2用户个人编码  3用户姓名 4医疗账户  5病例编码    6入院时间  7出院时间
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
               <execResult>0@!@!A02</execResult>
           </execResponse>
       </soap:Body>
   </soap:Envelope>

    */


}
