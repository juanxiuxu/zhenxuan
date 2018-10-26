/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * 文件工具类
 *
 * @author tianyu07.
 * @date 16/7/22 11:05.
 */
public class FileUtil {

    private static final int DEFAULT_WRITE_SIZE = 1000;
    private static final String DEFAULT_CHARSET = "utf-8";
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final Integer FILE_NAME_LEN = 30;
    private static final String NEXT_LINE = "\r";

    public static boolean createFile(String dirAndFilename) {
        // 校验文件名合法性
        if (!isValidFileName(dirAndFilename)) {
            logger.info("cannnot create file with filename:{}", dirAndFilename);
            return false;
        }

        File file = new File(dirAndFilename);
        if (file.exists()) {
            logger.info("file:{} ,already exist", dirAndFilename);
            return false;
        }

        try {
            return file.createNewFile();
        } catch (IOException e) {
            logger.error("create file:{} failed,e:{}", dirAndFilename, e);
            return false;
        }
    }

    public static boolean addContent(String fileName, List<String> lines) {
        boolean res = false;
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            for (String line : lines) {
                writer.write(new String(line.getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET));
                writer.write(NEXT_LINE);
            }
            res = true;
        } catch (IOException e) {
            logger.error("create fileWriter failed,filename:{},e:{}", fileName, e);
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close(); // TODO 确认文件关闭失败
                    res = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    private static boolean isValidFileName(String filename) {
        boolean res = true;

        if (filename == null || filename.isEmpty()) {
            return false;
        }
        if (filename.length() > FILE_NAME_LEN) {
            res = false;
        }
        return res;
    }

}
