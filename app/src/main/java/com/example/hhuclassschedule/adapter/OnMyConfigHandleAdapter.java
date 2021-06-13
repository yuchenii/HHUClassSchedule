package com.example.hhuclassschedule.adapter;

import android.util.Log;

import com.zhuangfei.timetable.TimetableView;

public class OnMyConfigHandleAdapter {

    public OnMyConfigHandleAdapter() {
    }


    private static final String TAG = "OnMyConfigHandleAdapter";

    public static final String CONFIG_SHOW_WEEKENDS = "config_show_weekends";
    public static final String CONFIG_SHOW_NOT_CUR_WEEK = "config_show_not_cur_week";
    /**
     * 存储的是开学日期，需利用其他工具动态计算当前周;
     * 存储格式"yy-MM-dd HH:mm:ss"
     */
    public static final String CONFIG_CUR_WEEK = "config_current_week";
    public static final String CONFIG_SHOW_TIME = "config_show_time";
    public static final String CONFIG_NOT_OPEN = "config_not_open";
    public static final String CONFIG_NOT_SHOW_WHERE = "config_not_where";
    public static final String CONFIG_NOT_SHOW_WHEN = "config_not_when";
    public static final String CONFIG_NOT_SHOW_STEP = "config_not_step";


    public static final String VALUE_TRUE = "config_value_true";
    public static final String VALUE_FALSE = "config_value_false";

    /**
     * 应用配置信息
     * @param key 信息的键
     * @param value 信息的值
     * @param mView 待设置的view
     */
    public void onParseConfig(String key, String value, TimetableView mView) {
        if (mView == null || key == null || value == null) return;
        switch (key) {
            case CONFIG_SHOW_WEEKENDS:
                if (value.equals(VALUE_TRUE)) mView.isShowWeekends(true);
                else mView.isShowWeekends(false);
                break;
            case CONFIG_SHOW_NOT_CUR_WEEK:
                if (value.equals(VALUE_TRUE)) mView.isShowNotCurWeek(true);
                else mView.isShowNotCurWeek(false);
                break;
            case CONFIG_CUR_WEEK:
                mView.curWeek(value);//value为开学周的时间
                break;
            default:
                break;
        }
        mView.updateView();
    }

}
