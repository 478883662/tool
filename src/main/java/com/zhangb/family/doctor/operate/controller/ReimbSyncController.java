package com.zhangb.family.doctor.operate.controller;

import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.operate.service.IReimbSyncOperateService;
import com.zhangb.family.doctor.operate.service.IReimbYlCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor/sync")
public class ReimbSyncController {

    @Autowired
    private IReimbSyncOperateService reimbSyncOperateService;
    @Autowired
    private IReimbYlCardService reimbYlCardService;

    /**
     *  全量同步信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/syncAll")
    @ResponseBody
    public ViewData syncAll() throws Exception {
        List<ValueDTO> ylCardList = reimbYlCardService.getYlCardNoList();
        ylCardList.forEach(e-> {
            try {
                reimbSyncOperateService.syncAllByYlCard(e.getValue());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return ViewDataUtil.success(true);
    }


}
