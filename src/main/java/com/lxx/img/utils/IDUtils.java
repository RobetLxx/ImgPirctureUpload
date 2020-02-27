package com.lxx.img.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Random;

/**
 * @author: lingjunhao
 * create at:  2019/11/3  10:22 上午
 * @description:
 */
/**
        * 各种id生成策略
        * @version 1.0
        */
public class IDUtils {
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        return str;
    }
}
