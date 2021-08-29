package com.zhangb.family.doctor.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.bo.DoctorReimbDetailInfo;
import com.zhangb.family.doctor.bo.DoctorReimbResultBo;
import com.zhangb.family.doctor.bo.ReimbUserBo;
import com.zhangb.family.doctor.bo.ReimbYearTotalBo;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.common.enums.DoctorReimbResultEnum;
import com.zhangb.family.doctor.service.IReimbRecordService;
import com.zhangb.family.doctor.service.IReimbService;
import com.zhangb.family.doctor.util.DoctorRespMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/reimb")
public class ReimbController {

    @Autowired
    private IReimbRecordService recordService;
    @Autowired
    private IReimbService reimbService;


    /**
     * 查询年度累计报销金额
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReimbTotal")
    @ResponseBody
    public ViewData getReimbTotal() throws Exception {

        List<ReimbYearTotalBo> resultList = ListUtil.list(false);
        for (int i = 2020; i <= DateUtil.thisYear(); i++) {
            ReimbYearTotalBo reimbYearTotalBo = new ReimbYearTotalBo();
            reimbYearTotalBo.setYear(i);
            reimbYearTotalBo.setTotal(recordService.getYlReimbTotal(RemoteConstants.YL_LOCATION_NO, i));
            resultList.add(reimbYearTotalBo);
        }
        return ViewDataUtil.success(resultList);
    }

    /**
     *  查询今日报销情况
     *
     * @return /image/getImg?oid=2&t=1626969600000
     * @throws Exception
     */
    @RequestMapping("/reimbTotal")
    @ResponseBody
    public ViewData getReimbTotal(@RequestParam("totalDate") String totalDate) throws Exception {
        DoctorReimbDetailInfo doctorTodayReimbInfo = reimbService.getTodayReimbInfo(totalDate);
        return ViewDataUtil.success(doctorTodayReimbInfo);
    }

    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/reimbursement?ylCard=4306210121011214&name=欧凤华
     *
     * @param ylCard 医疗账户
     * @param name   报销人
     * @return
     * @throws Exception
     */
    @RequestMapping("/reimbursement")
    @ResponseBody
    public ViewData reimbursement(@RequestParam(value = "ylCard", required = true) String ylCard,
                                  @RequestParam(value = "name", required = false) String name) throws Exception {
        List<DoctorReimbResultBo> resultList = reimbService.reimbForYlCardAndName(ylCard, name);
        String resultStr = getResultStr(1L, resultList);
        return ViewDataUtil.success(resultStr, DoctorRespMsgUtil.getHtmlStr(resultList));
    }

    /**
     * 按最近一次报销记录给用户报销
     * http://localhost:8085/reimbUser/reimbursement?ylCard=4306210121011214&name=欧凤华
     *
     * @param list 医疗账户_报销人
     * @return
     * @throws Exception
     */
    @RequestMapping("/batchReimbursement")
    @ResponseBody
    public ViewData batchReimbursement(@RequestBody List<ReimbUserBo> list) throws Exception {
        if (CollectionUtil.isEmpty(list)) {
            return ViewDataUtil.bizError("请选择要报销的人！");
        }
        //报销处理结果集
        List<DoctorReimbResultBo> resultList = CollectionUtil.newArrayList();
        for (ReimbUserBo reimbUserBo : list) {
            resultList.addAll(reimbService.reimbForYlCardAndName(reimbUserBo.getYlCard(), reimbUserBo.getName()));
        }
        Long total = Long.valueOf(list.size());
        String resultStr = getResultStr(total, resultList);
        return ViewDataUtil.success(resultStr, DoctorRespMsgUtil.getHtmlStr(resultList));
    }

    private String getResultStr(Long total, List<DoctorReimbResultBo> resultList) {
        Long successCount = resultList.stream().filter(e-> StrUtil.equals(e.getCode(), DoctorReimbResultEnum.SUCCESS.getCode())).count();
        String resultStr =String.format("成功报销%s个，失败%s个", successCount,total-successCount) ;
        return resultStr;
    }

}
