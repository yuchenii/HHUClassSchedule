package com.example.hhuclassschedule.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间相关的计算工具
 */
public class TimeCalUtil {

    /**
     * 将"yyyy-MM-dd HH:mm:ss"格式的时间字符串 转换成 Date型
     */
    public static Date str2Date(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将Date型 转换成 "yyyy-MM-dd HH:mm:ss"格式的时间字符串
     */
    public static String date2Str(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(date);
    }

    /**
     * 计算某一时间 某几周前的Date:
     * 计算date时间在weeksNum周前的Date
     */
    public static Date calWeeksAgo(Date date, int weeksNum){
        return new Date(date.getTime() - weeksNum * 7 * 24 * 60 * 60 * 1000);
    }

}
