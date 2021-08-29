package com.zhangb.family.doctor.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.dto.YlCardDTO;
import com.zhangb.family.doctor.dto.ValueDTO;
import com.zhangb.family.doctor.entity.ReimbUserInfo;
import com.zhangb.family.doctor.entity.ReimbYlCard;
import com.zhangb.family.doctor.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.service.IReimbRecordService;
import com.zhangb.family.doctor.service.IReimbSyncService;
import com.zhangb.family.doctor.service.IReimbUserService;
import com.zhangb.family.doctor.service.IReimbYlCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by z9104 on 2021/5/5.
 */
@RestController
@RequestMapping("/doctor/ylCard")
public class ReimbYlCardController {

    @Autowired
    private IReimbYlCardService reimbYlCardService;
    @Autowired
    private IReimbSyncService reimbSyncService;
    @Autowired
    private IReimbUserService reimbUserService;
    @Autowired
    private IReimbRecordService reimbRecordService;
    @Autowired
    private ICxnhRemoteService remoteService;

    /**
     * 分页查询医疗账号信息
     * http://localhost:8086/doctor/ylCard/getYlCardList
     *
     * @return
     */
    @RequestMapping("/getYlCardList")
    public ViewData getYlCardList(@RequestBody YlCardDTO ylCardDTO) {
        Page<ReimbYlCard> ylCardList = reimbYlCardService.getYlCardByPage(ylCardDTO);
        return ViewDataUtil.success(ylCardList.toPageInfo());
    }

    /**
     * 查询所有的医疗账号
     * http://localhost:8086/doctor/ylCard/getYlCardNoList
     *
     * @return
     */
    @RequestMapping("/getYlCardNoList")
    public ViewData getYlCardNoList() {
        List<ValueDTO> ylCardList = reimbYlCardService.getYlCardNoList();
        return ViewDataUtil.success(ylCardList);
    }

    @RequestMapping("/deleteYlCard")
    public ViewData deleteYlCard(@RequestParam("ylCard") String ylCard) {
        return ViewDataUtil.success(reimbYlCardService.deleteYlCard(ylCard));
    }

    /**
     * 添加医疗账户
     * http://localhost:8085/reimbUser/addYlCard?ylCard=4306210121011214&masterName=欧凤华
     *
     * @param ylCardDTO
     * @return
     * @throws Exception
     */
    @RequestMapping("/addYlCard")
    @ResponseBody
    public ViewData addYlCard(@RequestBody YlCardDTO ylCardDTO) throws Exception {
        if (StrUtil.hasBlank(ylCardDTO.getYlCard())) {
            return ViewDataUtil.bizError("FAIL:医疗账号不能为空");
        }
        //从远端获取医疗账号下的所有人员
        List<ReimbUserInfo> userInfoList = reimbSyncService.syncUserByYlCard(ylCardDTO.getYlCard());

        for (ReimbUserInfo reimbUserInfo : userInfoList) {
            //2、同步报销记录
            reimbRecordService.syncRecord(reimbUserInfo.getSelfNo());
        }

        //只要不是FAIL开头就是正常业务返回
        ReimbYlCard reimbYlCard = new ReimbYlCard();
        reimbYlCard.setYlCard(ylCardDTO.getYlCard());
        reimbYlCard.setMasterName(userInfoList.get(0).getMasterName());
        reimbYlCardService.addYlCard(reimbYlCard);
        return ViewDataUtil.success("保存医疗账户成功");
    }
}
