package com.lxx.img.controller;

import com.lxx.img.service.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: lingjunhao
 * create at:  2019/11/3  10:19 上午
 * @description:
 */
@Controller
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;

    @RequestMapping("/fileUpload")
    public String fileUpload(MultipartFile img, Model model){
        try {
            String src = fileUploadService.fileUpload(img);
            if (src!=null){
                model.addAttribute("url",src);
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }

}
