package com.example.hhuclassschedule.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.hhuclassschedule.MainActivity;
import com.example.hhuclassschedule.R;
import com.zhuangfei.timetable.model.ScheduleSupport;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    private String myContentText = "Default Content";

    public void setContentText(String contentText){
        myContentText = contentText;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //当系统到我们设定的时间点的时候会发送广播，执行这里
        Log.d(TAG, "onReceive");
//TODO
//        ScheduleSupport.getAllSubjectsWithDay();

        int notID = 1001;
        String myChannelID = "HHU_SCH_NOTIFY";
        String myChannelName = "每日课程提醒";
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String notChannel = createNotificationChannel(context,
                myChannelID, myChannelName, NotificationManager.IMPORTANCE_DEFAULT);
        Intent startIntent = new Intent(context, MainActivity.class);
        Notification notification = new NotificationCompat.Builder(context, myChannelID)
                .setTicker(context.getResources().getString(R.string.app_name))//APP名称
                .setContentTitle("明日课程")//标题
                .setContentText(myContentText)//内容
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(PendingIntent.getActivity(context, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                .build();
        manager.notify(notID, notification);
    }

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

}
