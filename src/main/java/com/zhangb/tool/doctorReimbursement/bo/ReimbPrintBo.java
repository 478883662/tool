package com.zhangb.tool.doctorReimbursement.bo;

import cn.hutool.core.util.StrUtil;
import com.zhangb.tool.common.annotation.ExportFeildAnnotation;
import lombok.Data;

/**
 * 0@!@$21	0	章怡	女	2000/5/9 0:00:00	430621200005090043	4306210121011214	03
 * 2013/12/15 0:00:00	2013/12/15 0:00:00	支气管肺炎		城关镇城关荣站二村卫生室
 * 0	0	13.65	0	0	2	0	2	7	0	0	13.65	0	0	2	0	2	7	涂美华
 * 24.65	430621556523			0		15	2013120024881250	村卫生室	430621556523
 * 26226881	1	普通门诊	0	721666685	11	2013/12/15 13:42:04	城关镇城关荣站二村卫生室
 * 0	湖南省岳阳县			721666685
 * Created by z9104 on 2020/10/31.
 */
public class ReimbPrintBo {

    /**姓名 ${name}*/
    @ExportFeildAnnotation("name")
    private String name;
    /**性别*/
    @ExportFeildAnnotation("sex")
    private String sex;
    /**年龄*/
    @ExportFeildAnnotation("age")
    private String age;
    /**身份证号*/
    @ExportFeildAnnotation("idCard")
    private String idCard;
    /**医疗账号*/
    @ExportFeildAnnotation("ylCard")
    private String ylCard;
    /**住院号*/
    @ExportFeildAnnotation("zhuYuanHao")
    private String zhuYuanHao;
    /**补偿类别*/
    @ExportFeildAnnotation("reimbType")
    private String reimbType;
    /**家庭地址*/
    @ExportFeildAnnotation("familyLocation")
    private String familyLocation="";
    /**入院日期*/
    @ExportFeildAnnotation("inDate")
    private String inDate;
    /**出院日期*/
    @ExportFeildAnnotation("outDate")
    private String outDate;
    /**住院天数*/
    @ExportFeildAnnotation("zhuYuanDay")
    private String zhuYuanDay;
    /**出院诊断*/
    @ExportFeildAnnotation("illnessName")
    private String illnessName;
    /**医疗费用--床位费*/
    @ExportFeildAnnotation("chuangWeiYlFee")
    private String chuangWeiYlFee;
    /**可报费用--床位费*/
    @ExportFeildAnnotation("chuangWeiCanFee")
    private String chuangWeiCanFee;
    /**医疗费用--护理费*/
    @ExportFeildAnnotation("huLiYlFee")
    private String huLiYlFee;
    /**可报费用--护理费*/
    @ExportFeildAnnotation("huLiCanFee")
    private String huLiCanFee;
    /**医疗费用--西药费*/
    @ExportFeildAnnotation("xiYaoYlFee")
    private String xiYaoYlFee;
    /**可报费用--西药费*/
    @ExportFeildAnnotation("xiYaoCanFee")
    private String xiYaoCanFee;
    /**医疗费用--中药费*/
    @ExportFeildAnnotation("zhongYaoYlFee")
    private String zhongYaoYlFee;
    /**可报费用--中药费*/
    @ExportFeildAnnotation("zhongYaoCanFee")
    private String zhongYaoCanFee;
    /**医疗费用--化验费*/
    @ExportFeildAnnotation("huaYanYlFee")
    private String huaYanYlFee;
    /**可报费用--化验费*/
    @ExportFeildAnnotation("huaYanCanFee")
    private String huaYanCanFee;
    /**医疗费用--诊疗费*/
    @ExportFeildAnnotation("zhenLiaoYlFee")
    private String zhenLiaoYlFee;
    /**可报费用--诊疗费*/
    @ExportFeildAnnotation("zhenLiaoCanFee")
    private String zhenLiaoCanFee;
    /**医疗费用--手术费*/
    @ExportFeildAnnotation("shouShuYlFee")
    private String shouShuYlFee;
    /**可报费用--手术费*/
    @ExportFeildAnnotation("shouShuCanFee")
    private String shouShuCanFee;
    /**医疗费用--检查费*/
    @ExportFeildAnnotation("jianChaYlFee")
    private String jianChaYlFee;
    /**可报费用--检查费*/
    @ExportFeildAnnotation("jianChaCanFee")
    private String jianChaCanFee;
    /**医疗费用--其他费*/
    @ExportFeildAnnotation("othterYlFee")
    private String othterYlFee;
    /**可报费用--其他费*/
    @ExportFeildAnnotation("otherCanFee")
    private String otherCanFee;
    /**医疗费用--总计*/
    @ExportFeildAnnotation("totalYlFee")
    private String totalYlFee;
    /**可报费用--总计*/
    @ExportFeildAnnotation("totalCanFee")
    private String totalCanFee;

    /**核算机构*/
    @ExportFeildAnnotation("heSuanLocation")
    private String heSuanLocation;
    /**核算人*/
    @ExportFeildAnnotation("heSuanPerson")
    private String heSuanPerson;
    /**核算补偿额*/
    @ExportFeildAnnotation("heSuanMoney")
    private String heSuanMoney;
    /**增减补偿额*/
    @ExportFeildAnnotation("zengJianReimbMoney")
    private String zengJianReimbMoney;
    /**实际补偿额*/
    @ExportFeildAnnotation("actualReimbMoney")
    private String actualReimbMoney;
    /**实际补偿额（大写）*/
    @ExportFeildAnnotation("actualReimbMoneyCn")
    private String actualReimbMoneyCn;
    /**累计是否达封顶线*/
    @ExportFeildAnnotation("isTopTotal")
    private String isTopTotal;
    /**核算时间*/
    @ExportFeildAnnotation("heSuanDate")
    private String heSuanDate;
    @ExportFeildAnnotation("reimbNo")
    private String reimbNo;
    @ExportFeildAnnotation("jgLevel")
    private String jgLevel;
    @ExportFeildAnnotation("ylJg")
    private String ylJg;
    //户属性
    @ExportFeildAnnotation("huAttr")
    private String huAttr;


    public String getHuAttr() {
        return huAttr;
    }

    public void setHuAttr(String huAttr) {
        this.huAttr = huAttr;
    }

    public String getYlJg() {
        return ylJg;
    }

    public void setYlJg(String ylJg) {
        this.ylJg = ylJg;
    }

    public String getJgLevel() {
        return jgLevel;
    }

    public void setJgLevel(String jgLevel) {
        this.jgLevel = jgLevel;
    }

    public String getReimbNo() {
        return reimbNo;
    }

    public void setReimbNo(String reimbNo) {
        this.reimbNo = reimbNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getYlCard() {
        return ylCard;
    }

    public void setYlCard(String ylCard) {
        this.ylCard = ylCard;
    }

    public String getZhuYuanHao() {
        return zhuYuanHao;
    }

    public void setZhuYuanHao(String zhuYuanHao) {
        this.zhuYuanHao = zhuYuanHao;
    }

    public String getReimbType() {
        return reimbType;
    }

    public void setReimbType(String reimbType) {
        this.reimbType = reimbType;
    }

    public String getFamilyLocation() {
        return familyLocation;
    }

    public void setFamilyLocation(String familyLocation) {
        if(StrUtil.isBlank(familyLocation)){
            this.familyLocation =" ";
            return;
        }
        this.familyLocation = familyLocation;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getZhuYuanDay() {
        return zhuYuanDay;
    }

    public void setZhuYuanDay(String zhuYuanDay) {
        this.zhuYuanDay = zhuYuanDay;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }

    public String getChuangWeiYlFee() {
        return chuangWeiYlFee;
    }

    public void setChuangWeiYlFee(String chuangWeiYlFee) {
        this.chuangWeiYlFee = chuangWeiYlFee;
    }

    public String getChuangWeiCanFee() {
        return chuangWeiCanFee;
    }

    public void setChuangWeiCanFee(String chuangWeiCanFee) {
        this.chuangWeiCanFee = chuangWeiCanFee;
    }

    public String getHuLiYlFee() {
        return huLiYlFee;
    }

    public void setHuLiYlFee(String huLiYlFee) {
        this.huLiYlFee = huLiYlFee;
    }

    public String getHuLiCanFee() {
        return huLiCanFee;
    }

    public void setHuLiCanFee(String huLiCanFee) {
        this.huLiCanFee = huLiCanFee;
    }

    public String getXiYaoYlFee() {
        return xiYaoYlFee;
    }

    public void setXiYaoYlFee(String xiYaoYlFee) {
        this.xiYaoYlFee = xiYaoYlFee;
    }

    public String getXiYaoCanFee() {
        return xiYaoCanFee;
    }

    public void setXiYaoCanFee(String xiYaoCanFee) {
        this.xiYaoCanFee = xiYaoCanFee;
    }

    public String getZhongYaoYlFee() {
        return zhongYaoYlFee;
    }

    public void setZhongYaoYlFee(String zhongYaoYlFee) {
        this.zhongYaoYlFee = zhongYaoYlFee;
    }

    public String getZhongYaoCanFee() {
        return zhongYaoCanFee;
    }

    public void setZhongYaoCanFee(String zhongYaoCanFee) {
        this.zhongYaoCanFee = zhongYaoCanFee;
    }

    public String getHuaYanYlFee() {
        return huaYanYlFee;
    }

    public void setHuaYanYlFee(String huaYanYlFee) {
        this.huaYanYlFee = huaYanYlFee;
    }

    public String getHuaYanCanFee() {
        return huaYanCanFee;
    }

    public void setHuaYanCanFee(String huaYanCanFee) {
        this.huaYanCanFee = huaYanCanFee;
    }

    public String getZhenLiaoYlFee() {
        return zhenLiaoYlFee;
    }

    public void setZhenLiaoYlFee(String zhenLiaoYlFee) {
        this.zhenLiaoYlFee = zhenLiaoYlFee;
    }

    public String getZhenLiaoCanFee() {
        return zhenLiaoCanFee;
    }

    public void setZhenLiaoCanFee(String zhenLiaoCanFee) {
        this.zhenLiaoCanFee = zhenLiaoCanFee;
    }

    public String getShouShuYlFee() {
        return shouShuYlFee;
    }

    public void setShouShuYlFee(String shouShuYlFee) {
        this.shouShuYlFee = shouShuYlFee;
    }

    public String getShouShuCanFee() {
        return shouShuCanFee;
    }

    public void setShouShuCanFee(String shouShuCanFee) {
        this.shouShuCanFee = shouShuCanFee;
    }

    public String getJianChaYlFee() {
        return jianChaYlFee;
    }

    public void setJianChaYlFee(String jianChaYlFee) {
        this.jianChaYlFee = jianChaYlFee;
    }

    public String getJianChaCanFee() {
        return jianChaCanFee;
    }

    public void setJianChaCanFee(String jianChaCanFee) {
        this.jianChaCanFee = jianChaCanFee;
    }

    public String getOthterYlFee() {
        return othterYlFee;
    }

    public void setOthterYlFee(String othterYlFee) {
        this.othterYlFee = othterYlFee;
    }

    public String getOtherCanFee() {
        return otherCanFee;
    }

    public void setOtherCanFee(String otherCanFee) {
        this.otherCanFee = otherCanFee;
    }

    public String getTotalYlFee() {
        return totalYlFee;
    }

    public void setTotalYlFee(String totalYlFee) {
        this.totalYlFee = totalYlFee;
    }

    public String getTotalCanFee() {
        return totalCanFee;
    }

    public void setTotalCanFee(String totalCanFee) {
        this.totalCanFee = totalCanFee;
    }

    public String getHeSuanLocation() {
        return heSuanLocation;
    }

    public void setHeSuanLocation(String heSuanLocation) {
        this.heSuanLocation = heSuanLocation;
    }

    public String getHeSuanPerson() {
        return heSuanPerson;
    }

    public void setHeSuanPerson(String heSuanPerson) {
        this.heSuanPerson = heSuanPerson;
    }

    public String getHeSuanMoney() {
        return heSuanMoney;
    }

    public void setHeSuanMoney(String heSuanMoney) {
        this.heSuanMoney = heSuanMoney;
    }

    public String getZengJianReimbMoney() {
        return zengJianReimbMoney;
    }

    public void setZengJianReimbMoney(String zengJianReimbMoney) {
        this.zengJianReimbMoney = zengJianReimbMoney;
    }

    public String getActualReimbMoney() {
        return actualReimbMoney;
    }

    public void setActualReimbMoney(String actualReimbMoney) {
        this.actualReimbMoney = actualReimbMoney;
    }

    public String getActualReimbMoneyCn() {
        return actualReimbMoneyCn;
    }

    public void setActualReimbMoneyCn(String actualReimbMoneyCn) {
        this.actualReimbMoneyCn = actualReimbMoneyCn;
    }

    public String getIsTopTotal() {
        return isTopTotal;
    }

    public void setIsTopTotal(String isTopTotal) {
        this.isTopTotal = isTopTotal;
    }

    public String getHeSuanDate() {
        return heSuanDate;
    }

    public void setHeSuanDate(String heSuanDate) {
        this.heSuanDate = heSuanDate;
    }
}
