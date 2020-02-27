package com.lxx.img.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileDownloadService {
    public String fileDownload(MultipartFile img)throws Exception;
}
