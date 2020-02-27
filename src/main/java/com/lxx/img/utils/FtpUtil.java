package com.lxx.img.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;


/**
 * @author: lingjunhao
 * create at:  2019/11/3  10:23 上午
 * @description:
 */
public class FtpUtil {
    /**
     * 登录FTP服务器
     * **/
    public static boolean login(FTPClient ftp,String host,int port, String username,String password)throws Exception{
        boolean result =false;
        //连接FTP服务器
        try {
            System.out.println("FTP连接开始！！");
            System.out.println("FTP服务器IP:"+host+" 端口号:"+port);
            int reply;
            //连接ftp服务器
            ftp.connect(host,port);
            //登录ftp服务器
            ftp.login(username, password);
            System.out.println("FTP连接成功");
            reply=ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)){
                String replyMessage = ftp.getReplyString().trim();
                throw new Exception("FTP 登录失败，响应消息为:"+replyMessage);
            }
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 关闭FTP服务器
     * **/
    public static void close(FTPClient ftp){
        try {
            if (!ftp.isConnected()){
                ftp.disconnect();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) throws Exception{
        boolean result = false;
        FTPClient ftp = new FTPClient();
        //设置连接超时时间
        //ftp.setConnectTimeout(100*1000);
        try {
            if (!login(ftp,host,port,username,password)){
                 return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)){
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(ftp);
        }
        return result;
    }
    /**
    * 下载指定目录下的指定文件
    * **/
    public static boolean downloadFile(String host, int port, String username, String password, String basePath,
                                       String filePath, String fileName,String localPath,String localFile) throws Exception {
        boolean result=false;
        FTPClient ftp=new FTPClient();
        try {
            if (!login(ftp,host, port, username, password)){
                return result;
            }
            if (!downloadFileByDate(ftp,basePath,filePath,fileName,localPath,localFile)){
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(ftp);
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param fileName 需要从FTP服务器上下载的文件名
     * @param localPath 本地存储位置（空 使用用户当前目录）
     * @param localFileName 本地存储文件名（空 使用文件原始名称）
     * @return 成功返回true，否则返回false
     */
    public static boolean downloadFileByDate(FTPClient ftp,String basePath, String filePath, String fileName,String localPath,String localFileName) {
        boolean result=false;
        try {
            //切换到下载目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在则返回
                System.out.println("目录不存在，文件下载失败！！！");
                return result;
            }else {
                FTPFile[] files=ftp.listFiles(fileName);
                if (files.length==1){
                    FTPFile file=files[0];
                    if (file.isFile()){
                        if (StringUtils.isEmpty(localFileName)){
                            //如果本地存储文件名为空，则用原文件本名
                            localFileName=file.getName();
                        }
                        if (StringUtils.isEmpty(localPath)){
                            //如果本地存储地址为空，则自行设置一个存储文件夹
                            localPath=System.getProperty("user.home");
                        }
                        File localFile =new File(localPath);

                        if (!localFile.exists()){
                            localFile.mkdir();
                        }
                        StringBuilder stringBuilder = new StringBuilder(localPath);
                        stringBuilder.append(File.separator).append(localFileName);

                        try (FileOutputStream out=new FileOutputStream(stringBuilder.toString())){
                            ftp.setBufferSize(BUFFER_SIZE);
                            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

                            result = ftp.retrieveFile(file.getName(),out);
                        }catch (IOException e){
                            e.printStackTrace();
                            System.out.println("文件下载失败！！！");
                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


/**
 *
 System.out.println("FTP连接开始！！");
 System.out.println(host+port);
 int reply;
 ftp.connect(host,port);
 // 连接FTP服务器、
 System.out.println("连接是否成功?");
 // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
 ftp.login(username, password);

 System.out.println("FTP连接成功！！");
 // 登录
 reply = ftp.getReplyCode();
 if (!FTPReply.isPositiveCompletion(reply)) {
 String replymsg = ftp.getReplyString().trim();
 throw new Exception("FTP 登录失败，响应消息:" + replymsg);
 }
 */


}
