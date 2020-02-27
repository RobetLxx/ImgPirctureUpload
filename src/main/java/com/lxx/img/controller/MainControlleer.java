package com.lxx.img.controller;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * @author: lingjunhao
 * create at:  2019/11/3  2:48 下午
 * @description:
 */
public class MainControlleer {
    public static void main(String[] args) throws IOException {
        FTPClient ftp=new FTPClient();
        ftp.setConnectTimeout(12000);
        ftp.connect("192.168.1.104");
        boolean result=ftp.login("lingjunhao","123456");
        if(!result) {
            System.out.println("登陸超時");
            if (ftp.isConnected()) {
                // 断开连接
                ftp.disconnect();
            }
        }
        // 设置字符编码
        ftp.setControlEncoding("UTF-8");
        System.out.println("登录成功");
        //走到这一步就能开始操作所连接服务器上的文件啦
    }
}
