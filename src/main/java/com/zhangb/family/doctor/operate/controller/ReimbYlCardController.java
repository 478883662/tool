package com.zhangb.family.doctor.operate.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.basedata.entity.ReimbYlCardPO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.basedata.remote.dto.YlCardDTO;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.basedata.service.IReimbSyncService;
import com.zhangb.family.doctor.operate.service.IReimbRecordService;
import com.zhangb.family.doctor.operate.service.IReimbSyncOperateService;
import com.zhangb.family.doctor.operate.service.IReimbUserService;
import com.zhangb.family.doctor.operate.service.IReimbYlCardService;
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
    private IReimbSyncOperateService reimbSyncOperateService;
    @Autowired
    private IReimbUserService reimbUserService;
    @Autowired
    private IReimbRecordService reimbRecordService;
    @Autowired
    private ICxnhRemoteService remoteService;
    @Autowired
    private IReimbSyncService reimbSyncService;



    /**
     * 分页查询医疗账号信息
     * http://localhost:8086/doctor/ylCard/getYlCardList
     *
     * @return
     */
    @RequestMapping("/getYlCardList")
    public ViewData getYlCardList(@RequestBody YlCardDTO ylCardDTO) {
        Page<ReimbYlCardPO> ylCardList = reimbYlCardService.getYlCardByPage(ylCardDTO);
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
        reimbSyncOperateService.syncAllByYlCard(ylCardDTO.getYlCard());
        return ViewDataUtil.success("保存医疗账户成功");
    }
}
