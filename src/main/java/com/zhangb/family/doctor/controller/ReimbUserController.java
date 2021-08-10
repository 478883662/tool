package com.zhangb.family.doctor.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.zhangb.family.common.constants.GlobalConstants;
import com.zhangb.family.common.dto.FamilyFileDto;
import com.zhangb.family.common.entity.FamilyFile;
import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.service.FamilyFileService;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.bo.ReimbUserBo;
import com.zhangb.family.doctor.common.constants.ReimbConstants;
import com.zhangb.family.doctor.dto.ReimbUserDTO;
import com.zhangb.family.doctor.dto.ValueDTO;
import com.zhangb.family.doctor.entity.ReimbUserInfo;
import com.zhangb.family.doctor.service.IReimbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor/user")
public class ReimbUserController {

    @Autowired
    private IReimbUserService reimbUserService;
    @Autowired
    private FamilyFileService familyFileService;

    /**
     * 分页查询报销用户
     *
     * @return /image/getImg?oid=2&t=1626969600000
     * @throws Exception
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ViewData getUserInfo(@RequestBody ReimbUserDTO reimbUserDTO) throws Exception {
        Page<ReimbUserBo> userList = reimbUserService.getUserListByPage(reimbUserDTO);
        userList.getResult().forEach(e -> {
            //若查出有关联的文件主键oid，则说明有图片
            if (StrUtil.isNotBlank(e.getFileOid())) {
                e.setFilePath(String.format(GlobalConstants.IMAGE_DOWNLOAD_PRE, e.getFileOid(), e.getFileCreateDate().getTime()));
                List<String> preImgList = new ArrayList<>();
                preImgList.add(e.getFilePath());
                e.setPreFilePath(preImgList);
            }
        });
        return ViewDataUtil.success(userList.toPageInfo());
    }

    @RequestMapping("/getNameList")
    public ViewData getNameList() {
        List<ValueDTO> ylCardList = reimbUserService.getNameList();
        return ViewDataUtil.success(ylCardList);
    }
    @RequestMapping("/getMasterNameList")
    public ViewData getMasterNameList() {
        List<ValueDTO> ylCardList = reimbUserService.getMasterNameList();
        return ViewDataUtil.success(ylCardList);
    }

    /**
     * 删除报销用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUserEnableFlag")
    @ResponseBody
    public ViewData updateUserEnableFlag(@RequestBody ReimbUserDTO reimbUserDTO) throws Exception {
        //逻辑删除/恢复用户，置为失效
        return ViewDataUtil.success(reimbUserService.updateUserEnableFlag(reimbUserDTO));
    }

    /**
     * 上次文件
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    @ResponseBody
    public void upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws Exception {
        String ylCard = request.getParameter("ylCardNo");
        String name = request.getParameter("name");
        String fileName = ylCard + name;
        if (file.isEmpty()) {
            throw new Exception("上传文件为空");
        }
        String srcFileName = file.getOriginalFilename();
        String fileType = srcFileName.substring(srcFileName.lastIndexOf(".") + 1);
        if (!StrUtil.equals(fileType, ReimbConstants.PIC_TYPE_PNG)
                && !StrUtil.equals(fileType, ReimbConstants.PIC_TYPE_JPG)) {
            throw new Exception("文件类型只能是png、jpg");
        }
        ReimbUserInfo reimbUserInfo = new ReimbUserInfo();
        reimbUserInfo.setEnableFlag(GlobalConstants.ENABLE_FLAG_T);
        reimbUserInfo.setName(name);
        reimbUserInfo.setYlCard(ylCard);
        List<ReimbUserInfo> userList = reimbUserService.getAllUserInfo(reimbUserInfo);
        if (CollectionUtil.isEmpty(userList)) {
            throw new Exception("用户不存在或已失效，无法上传图片");
        }
        InputStream is = file.getInputStream();
        byte[] pic = new byte[(int) file.getSize()];
        is.read(pic);
        String pkOid = userList.get(0).getIdCard();
        FamilyFile familyFile = new FamilyFile();
        familyFile.setFileName(fileName + "." + fileType);
        familyFile.setPkOid(pkOid);
        familyFile.setFile(pic);
        FamilyFileDto familyFileDto = new FamilyFileDto();
        familyFileDto.setPkOid(pkOid);
        int count = familyFileService.getFamilyFileCount(familyFileDto);
        if (count > 0) {
            familyFileService.updateFile(familyFile);
            return;
        }
        familyFileService.addFile(familyFile);
    }

}
