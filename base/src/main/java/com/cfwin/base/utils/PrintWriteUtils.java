package com.cfwin.base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 该类从daq项目抽取而来，主要用于日志写入外部文件<br/>
 * create at 2018-06-11 by Yao
 */
public class PrintWriteUtils {

    public static String LOGPATH = "/sdcard/" + "/BAISON/%1$s/log/";
    public static final String NAME = getCurrentDateString() + ".txt";// 日志名字


    private static String getCurrentDateString() {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        Date nowDate = new Date();
        result = sdf.format(nowDate);
        return result;
    }
    /**
     * 向文件中写入错误信息
     *
     * @param info
     */

    public static void writeErrorLog(String info) {
        File dir = new File(LOGPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir,NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((info+"").getBytes());
            fileOutputStream.write("\r\n".getBytes());
            fileOutputStream.write("==============================================".getBytes());
            fileOutputStream.write("\r\n".getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
