/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网络工具.
 * 
 * @author luhuanan
 */
public final class NetUtil {

    /** log. */
    private static final Logger LOG = LoggerFactory.getLogger(NetUtil.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Constructor.
     */
    private NetUtil() {
    }

    /**
     * http下载.
     * 
     * @param urlStr 下载地址
     * @param destFile 目标文件
     * @throws IOException
     */
    public static void httpDownload(String urlStr, String destFile) {
        if (StringUtils.isBlank(urlStr) || StringUtils.isBlank(destFile)) {
            LOG.error("Params is null");
            return;
        }
        URL url = null;
        URLConnection conn = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            url = new URL(urlStr);
            conn = url.openConnection();
            conn.connect();
            bis = new BufferedInputStream(conn.getInputStream());
            fos = new FileOutputStream(destFile);
            bos = new BufferedOutputStream(fos);
            byte[] bs = new byte[1024];
            int len = 0;
            while ((len = bis.read(bs)) != -1) {
                bos.write(bs, 0, len);
            }
        } catch (IOException e) {
            LOG.error("IOException", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
        }
    }

    /**
     * 从FTP服务器下载文件.
     * 
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return 下载是否成功
     */
//    public static boolean ftpDownload(String host, int port, String username, String password, String remotePath,
//            String fileName, String localPath) {
//        FTPClient ftp = new FTPClient();
//        OutputStream out = null;
//        try {
//            ftp.connect(host, port);
//            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
//            boolean logined = ftp.login(username, password); // 登录
//            if (!logined) {
//                ftp.disconnect();
//                return false;
//            }
//            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
//                ftp.disconnect();
//                return false;
//            }
//            ftp.setControlEncoding(DEFAULT_ENCODING);
//            ftp.enterLocalPassiveMode();
//            ftp.changeWorkingDirectory(remotePath); // 转移到FTP服务器目录
//            File localFile = new File(localPath + File.separator + fileName);
//            out = new FileOutputStream(localFile);
//            ftp.retrieveFile(fileName, out);
//            out.flush();
//            ftp.logout();
//            return true;
//        } catch (IOException e) {
//            LOG.error(FormatService.logFormat("IOException"), e);
//        } finally {
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException e) {
//                    LOG.error(FormatService.logFormat("FTP IOException"), e);
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    LOG.error(FormatService.logFormat("File IOException"), e);
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 设置cookie.
     * 
     * @param response 相应
     * @param name cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie.
     * 
     * @param request 请求
     * @param name cookie名字
     * @return cookie值
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 删除cookie.
     * 
     * @param response 相应
     * @param name cookie名字
     */
    public static void delCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

}
