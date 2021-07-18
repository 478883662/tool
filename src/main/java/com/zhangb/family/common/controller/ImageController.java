//package com.zhangb.family.common.controller;
//
//import com.zhangb.family.common.dto.MyImage;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
///**
// * Created by z9104 on 2021/7/4.
// */
//public class ImageController {
//
//
//    @PostMapping("/uploadImage")
//    @ResponseBody
//    public String uploadImage(MyImage repairOrderImage){
//        try {
//            InputStream inputStream = repairOrderImage.getImage().getInputStream();
//            byte [] imageByte  = new byte[(int) repairOrderImage.getImage().getSize()];
//            inputStream.read(imageByte);
//
//            RepairOrderBlobs repairOrderBlobs = new RepairOrderBlobs();
//            repairOrderBlobs.setFaultDes("测试");
//
//            repairOrderBlobs.setImage(imageByte);
//
//            repairOrderBlobsMapper.insert(repairOrderBlobs);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ResultMessage.successJsonData();
//    }
//
//
//    @ResponseBody
//    @GetMapping("/getImage")
//    public void getImage(Long orderId,HttpServletResponse response){
//        try {
//            RepairOrderBlobs repairOrderBlobs = repairOrderBlobsMapper.getRecord(orderId);
//
//            byte[] image = repairOrderBlobs.getImage();
//
//            response.setContentType("image/jpeg");
//            response.setCharacterEncoding("UTF-8");
//            OutputStream outputSream = response.getOutputStream();
//            InputStream in = new ByteArrayInputStream(image);
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = in.read(buf, 0, 1024)) != -1) {
//                outputSream.write(buf, 0, len);
//            }
//
//            outputSream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//}
