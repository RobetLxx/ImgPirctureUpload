package com.lxx.img.service.impl;

import com.lxx.img.service.FileUploadService;
import com.lxx.img.utils.FtpUtil;
import com.lxx.img.utils.IDUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author: lingjunhao
 * create at:  2019/11/3  10:21 上午
 * @description:
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${vsftp.host}")
    private String host;
    @Value("${vsftp.port}")
    private int port;
    @Value("${vsftp.username}")
    private String username;
    @Value("${vsftp.password}")
    private String password;
    @Value("${vsftp.basePath}")
    private String basePath;
    @Value("${vsftp.filePath}")
    private String filePath;
    @Value("${vsftp.negix}")
    private String negix;
    @Override
    public String fileUpload(MultipartFile img) throws Exception {
        //	获取文件名
        String filename = IDUtils.genImageName() + img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        System.out.println("文件名:"+filename);
        InputStream input = img.getInputStream();
        boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, filename, input);
        if(result){
            //如果长传成功就返回文件的地址
            System.out.println(negix+filename);
            return negix+filename;

        }else {
            System.out.println("传输失败！！！");
        }
        return null;
    }
}
