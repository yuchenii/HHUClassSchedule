package com.example.hhuclassschedule.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.hhuclassschedule.MainActivity;
import com.example.hhuclassschedule.MyConfig;
import com.example.hhuclassschedule.MySubject;
import com.example.hhuclassschedule.NotificationConfigActivity;
import com.example.hhuclassschedule.R;
import com.example.hhuclassschedule.SubjectRepertory;
import com.example.hhuclassschedule.adapter.OnMyConfigHandleAdapter;
import com.example.hhuclassschedule.util.ContextApplication;
import com.example.hhuclassschedule.util.SharedPreferencesUtil;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleSupport;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接收系统广播后启动，发出一条通知，提醒第二天的课程；
 * 没有开学时不会发出通知。
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    private String myContentText = "Default Content";
    private int curWeek, curDay;//当前周次，当前星期几
    private int targetWeek, targetDay;//明天周次，明天星期几
    Boolean notIsShowWhen;
    Boolean notIsShowWhere;
    Boolean notIsShowStep;
    @Override
    public void onReceive(Context context, Intent intent) {
        //当系统到我们设定的时间点的时候会发送广播，执行这里
        Log.d(TAG, "onReceive");
        //获取信息，决定通知的内容
        Map<String, Boolean> notConfigMap = MyConfig.getNotConfigMap();
        notIsShowWhen = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN);
        notIsShowWhere = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE);
        notIsShowStep = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP);
        //计算明天的日期
        curWeek = getCurWeek();
        curDay = getCurDay();
        if(curDay == 6){//周日的下一天为周一
            targetDay = 0;
            targetWeek ++;
        } else {
            targetDay = curDay + 1;
            targetWeek = curWeek;
        }
        //还没有开学，不通知
        if(targetWeek <= 0){
            Log.d(TAG, "targetWeek <= 0");
            return;
        }
        List<Schedule> scheduleList = ScheduleSupport.transform(getOriginalData());
        List<Schedule> finalData = ScheduleSupport.getHaveSubjectsWithDay(scheduleList, targetWeek, targetDay);

        if(finalData == null){
            Log.d(TAG, "finalData is NULL");
            return;
        }
        ScheduleSupport.sortList(finalData);
        myContentText = getContentText(finalData, notIsShowWhen, notIsShowWhere, notIsShowStep);

        String myChannelID = "HHU_SCH_NOTIFY";
        String myChannelName = "次日课程提醒";
        int notID = 1001;
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String notChannel = createNotificationChannel(context,
                myChannelID, myChannelName, NotificationManager.IMPORTANCE_DEFAULT);
        Intent startIntent = new Intent(context, MainActivity.class);
        Notification notification = new NotificationCompat.Builder(context, myChannelID)
                .setTicker(context.getResources().getString(R.string.app_name))//APP名称
                .setContentTitle("明日课程")//标题
                .setContentText("展开以查看")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(myContentText))//内容 使用长文本展示
                .setSmallIcon(R.mipmap.ic_launcher_trans)
                .setContentIntent(PendingIntent.getActivity(context, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                .build();
        manager.notify(notID, notification);
    }

    /**
     * 若Android版本大于等于8,创建channel并返回；否在返回空
     * @param context
     * @param channelID 渠道id，须唯一
     * @param channelNAME 渠道名，给用户看的
     * @param level
     * @return
     */
    protected String createNotificationChannel(Context context, String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {//Android版本大于等于8
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {//Android版本低于8，返回空
            return null;
        }
    }

    /**
     * 获取所有的课程信息
     * @return  课程信息
     */
    private List<MySubject> getOriginalData(){
        String subjectListJson = SharedPreferencesUtil.init(ContextApplication.getAppContext(),
                "SP_Data_List").getString("SUBJECT_LIST", null);
        List<MySubject> mySubjects;
        if (subjectListJson == null) {
            mySubjects = SubjectRepertory.loadDefaultSubjects();
        } else {
            mySubjects = MainActivity.toGetSubjects();
        }
        return mySubjects;
    }

    /**
     * 计算出当前周
     * @return
     */
    private int getCurWeek(){
        Map<String, String> configMap = MyConfig.loadConfig();
        for(String key : configMap.keySet()){
            if(key.equals(OnMyConfigHandleAdapter.CONFIG_CUR_WEEK)) {
                Log.d(TAG, "load CUR_WEEK " + configMap.get(key));
                return ScheduleSupport.timeTransfrom(configMap.get(key));
            }
        }
        return 0;
    }

    /**
     * 计算出当前是周几
     * @return 周一：0 周二：1 ......周六：5 周日：6
     */
    private int getCurDay(){
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 6;
        }
        return (calendar.get(Calendar.DAY_OF_WEEK) - 2);
    }

    public void setContentText(String contentText){
        myContentText = contentText;
    }

    /**
     * 获取通知的内容；默认已包含课程名
     * @param finalData 当天的课程信息
     * @param showWhen  是否通知上课时间
     * @param showWhere 是否通知上课地点
     * @param showStep  是否通知上课时长（节数）
     * @return
     */
    public String getContentText(List<Schedule> finalData, boolean showWhen, boolean showWhere
                                 , boolean showStep){
        Log.d(TAG, "getContentText:" + showWhen + " " + showWhere +" "+ showStep);
        StringBuilder contentTextBuilder = new StringBuilder();
        for(Schedule course : finalData){
            contentTextBuilder.append(course.getName());
            contentTextBuilder.append("\n");
            if(showWhen) {
                contentTextBuilder.append("\t第").append(course.getStart()).append("节课;");
            }
            if(showWhere) {
                contentTextBuilder.append("\t").append(course.getRoom()).append("；");
            }
            if(showStep) {
                contentTextBuilder.append("\t课程时长").append(course.getStep()).append("节课；");
            }
            contentTextBuilder.append("\n");
        }
        Log.d(TAG, "contentText:" + contentTextBuilder.toString());
        if(contentTextBuilder.toString().isEmpty())
            return "明天没有课程";
        return contentTextBuilder.toString();
    }
}
