package com.lxx.img.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lingjunhao
 * create at:  2019/11/3  10:20 上午
 * @description:
 */
public interface FileUploadService {
    public String fileUpload(MultipartFile img)throws Exception;
}
