package com.zhangb.family.doctor.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.zhangb.family.common.module.ViewData;
import com.zhangb.family.common.util.ViewDataUtil;
import com.zhangb.family.doctor.bo.ReimbUserBo;
import com.zhangb.family.doctor.common.constants.ReimbConstants;
import com.zhangb.family.doctor.dto.ReimbUserDTO;
import com.zhangb.family.doctor.service.IReimbUserService;
import com.zhangb.family.doctor.util.PrintBizUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/doctor/user")
public class ReimbUserController {

    @Autowired
    private IReimbUserService reimbUserService;

    /**
     * 分页查询报销用户
     * @return
     * @throws Exception
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ViewData getUserInfo(@RequestBody ReimbUserDTO reimbUserDTO) throws Exception {
        Page<ReimbUserBo> userList= reimbUserService.getUserListByPage(reimbUserDTO);
        userList.getResult().forEach(e->{
            String fileName = PrintBizUtil.getYlCardName(e.getYlCard(),e.getName())+"."+ ReimbConstants.PIC_TYPE_PNG;
            if (FileUtil.exist(fileName)){
                e.setFilePath(e.getYlCard()+e.getName()+"."+ReimbConstants.PIC_TYPE_PNG);
            }else{
                e.setFilePath(e.getYlCard()+e.getName()+"."+ReimbConstants.PIC_TYPE_JPG);
            }
        });
        return ViewDataUtil.success( userList.toPageInfo());
    }

    /**
     * 删除报销用户
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUserEnableFlag")
    @ResponseBody
    public ViewData updateUserEnableFlag(@RequestBody ReimbUserDTO reimbUserDTO) throws Exception {
        //逻辑删除/恢复用户，置为失效
        return ViewDataUtil.success(reimbUserService.updateUserEnableFlag(reimbUserDTO) );
    }

    /**
     * 上次文件
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws Exception {
        String ylCard = request.getParameter("ylCard");
        String name = request.getParameter("name");
        String fileName=ylCard+name;
        if (file.isEmpty()) {
            throw new Exception("上传文件为空");
        }
        String srcFileName = file.getOriginalFilename();
        String fileType = srcFileName.substring(srcFileName.lastIndexOf(".")+1);
        if (!StrUtil.equals(fileType,ReimbConstants.PIC_TYPE_PNG)
                && !StrUtil.equals(fileType,ReimbConstants.PIC_TYPE_JPG)){
            throw new Exception("文件类型只能是png、jpg");
        }
        String filePath = ReimbConstants.PIC_FILE_PATH; // 上传后的路径
        File dest = new File(filePath + fileName+"."+fileType);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传成功";
    }

}
