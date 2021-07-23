package com.zhangb.family.common.controller;

import com.zhangb.family.common.dto.FamilyFileDto;
import com.zhangb.family.common.entity.FamilyFile;
import com.zhangb.family.common.service.FamilyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by z9104 on 2021/7/4.
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private FamilyFileService familyFileService;

    @GetMapping(value="/getImg")
    public void getImg(@RequestParam(value = "oid", required = true) String oid,
                       final HttpServletResponse response) throws IOException {
        FamilyFileDto familyFileDto = new FamilyFileDto();
        familyFileDto.setOid(oid);
        FamilyFile familyFile = familyFileService.getFamilyFile(familyFileDto);
        byte[] data = familyFile.getFile();
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }

}
