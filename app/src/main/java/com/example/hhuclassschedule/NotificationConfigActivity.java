package com.example.hhuclassschedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.example.hhuclassschedule.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.TimeZone;

public class NotificationConfigActivity extends AppCompatActivity {

    protected static final String TAG = "NotConfigActivity";

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_config);
        initToolbar("通知设置");

        Switch notTomorrowSwitch = findViewById(R.id.switch_notification_tomorrow);

    }

    /**
     * 初始化 toolbar
     *
     * @param title toolbar标题
     */
    protected void initToolbar(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = findViewById(R.id.toolbar_title);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) textView.getLayoutParams();
        layoutParams.setMarginStart(160);
        textView.setLayoutParams(layoutParams);
        textView.setText(title);
    }

    protected void setNotSwitch(Switch mySwitch, int setHour, int setMinute)
    {
        mySwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {//被选中
                            if (NotificationManagerCompat.from(NotificationConfigActivity.this).areNotificationsEnabled()) {
                                //允许系统通知
                                startRemind(22, 0);     //22:00通知
                                Toast.makeText(NotificationConfigActivity.this,
                                        "通知已打开", Toast.LENGTH_SHORT).show();
                            } else {
                                //未允许系统通知
                                Toast.makeText(NotificationConfigActivity.this,
                                        "请先在系统设置中允许通知", Toast.LENGTH_SHORT).show();
                                mySwitch  .setChecked(false);
                            }
                        }
                        else {//被关闭
                            if (NotificationManagerCompat.from(NotificationConfigActivity.this).areNotificationsEnabled()) {
                                //允许系统通知
                                stopRemind();
                                Toast.makeText(NotificationConfigActivity.this, "通知已关闭", Toast.LENGTH_SHORT).show();
                            }
                            else      //禁止系统通知
                                Toast.makeText(NotificationConfigActivity.this, "请先在系统设置中允许通知", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    protected void startRemind(int setHour, int setMinute) {
        //得到日历实例，主要是为了下面的获取时间
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        //获取当前毫秒值
        long systemTime = System.currentTimeMillis();
        //是设置日历的时间，主要是让日历的年月日和当前同步
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //设置在几点（小时）提醒
        mCalendar.set(Calendar.HOUR_OF_DAY, setHour);
        //设置在几分提醒
        mCalendar.set(Calendar.MINUTE, setMinute);
        //下面这两个看字面意思也知道
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        long selectTime = mCalendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        //AlarmReceiver.class为广播接受者
        Intent intent = new Intent(NotificationConfigActivity.this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(NotificationConfigActivity.this, 0, intent, 0);
        //得到AlarmManager实例
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);

       //设定重复提醒，提醒周期为一天（24H）
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);

        Log.d(NotificationConfigActivity.TAG, "startRemind");
    }

    protected void stopRemind() {
        Intent intent = new Intent(NotificationConfigActivity.this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(NotificationConfigActivity.this, 0,
                intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        //取消警报
        am.cancel(pi);
        Toast.makeText(this, "关闭了提醒", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "stopRemind");
    }

}
