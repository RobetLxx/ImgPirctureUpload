package com.lxx.img.service.impl;

import com.lxx.img.service.FileDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lingjunhao
 * create at:  2019/11/4  5:21 下午
 * @description:
 */
public class FileDownloadServiceImpl implements FileDownloadService {
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
    public String fileDownload(MultipartFile img) throws Exception {
        return null;
    }
}
