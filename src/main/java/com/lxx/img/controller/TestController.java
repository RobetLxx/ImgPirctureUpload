package com.lxx.img.controller;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author: lingjunhao
 * create at:  2019/11/3  2:42 下午
 * @description:
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public ModelAndView  Connection(HttpRequest httpRequest, ModelAndView model) throws IOException {
        FTPClient ftp=new FTPClient();
        ftp.setConnectTimeout(12000);
        ftp.connect("192.168.165.133",21);
        boolean result=ftp.login("lingjunhao","123456");
        if(!result) {
            System.out.println("登陸超時");
            if (ftp.isConnected()) {
                // 断开连接
                ftp.disconnect();
            }
            model.setViewName("fail");
            return model;
        }
        // 设置字符编码
        ftp.setControlEncoding("UTF-8");
        //走到这一步就能开始操作所连接服务器上的文件啦
        model.setViewName("success");
        return model;

    }
}
