package com.cfwin.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间转换工具, 功能抽象于ipos和daq共有项<br/>
 * created by Yao on 2018-07-18
 */
public class DateUtils {

    protected static SimpleDateFormat sf = null;


    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getCurrentDate1() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sf.format(d);
    }

    public static String getCurrentDate2() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getCurrentDateDBMonth() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM");
        return sf.format(d);
    }

    public static String getCurrentDateDBYear() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    public static String getCurrentDateSecond() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sf.format(d);
    }

    /**时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);

    }

    /**将字符串转为时间戳*/
    public static String getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sdf.parse(time);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return ""+(date.getTime()/1000);
    }
}
