package com.zhuhuibao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String date2Str(Date target, String pattern) {
        if (null == target) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String targetStr = "";
        try {
            targetStr = sdf.format(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetStr;
    }

    public static Date str2Date(String target, String pattern) {
        if (null == target) {
            return new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String str2DateFormat(String src) {
        if (src == null || "".equals(src)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long seconds = 0;
            seconds = Long.parseLong(src);
            return sdf.format(new Date(seconds));
        } catch (NumberFormatException e) {
            return src;
        }
    }

    /**
     * 时间加减计算
     *
     * @param date
     * @param offset
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date date2Sub(Date date, int field, int offset) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(field, offset);

        return c.getTime();
    }

    /**
     * 相差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long dayDiff(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static void main(String[] args) throws ParseException {
//        Date date = DateUtils.date2Sub(DateUtils.str2Date("2016-03-02 20:16:21", "yyyy-MM-dd HH:mm:ss"), 12, 10);
//        System.out.println(date);
//        Date date1 = new Date();
//        System.out.println(date1);
//        System.out.println(date1.before(date));
        String start = "2016-06-20 16:42:41";
        String end = "2017-06-21 00:00:00";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = sf.parse(start);
        Date endTime = sf.parse(end);
        long days = dayDiff(startTime, endTime);
        System.out.println(days);
    }
}
