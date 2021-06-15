package com.example.hhuclassschedule;

import android.annotation.SuppressLint;
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

import com.example.hhuclassschedule.adapter.OnMyConfigHandleAdapter;
import com.example.hhuclassschedule.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class NotificationConfigActivity extends AppCompatActivity {

    protected static final String TAG = "NotConfigActivity";
    /**
     * 用于存储、读取本地配置文件
     */
    protected static Map<String, String> configMap;
    /**
     * 用于应用配置
     */
    protected static Map<String, Boolean> notConfigMap;

    static Boolean notIsOpen;
    static Boolean notIsShowWhen;
    static Boolean notIsShowWhere;
    static Boolean notIsShowStep;

    int setHour = 22;
    int setMinute = 0;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_config);
        //设置工具栏
        initToolbar("通知设置");
        //初始化configMap<String, String>
        configMap = new HashMap<String, String>();
        //初始化noteConfigMap<String, Boolean>
        notConfigMap = MyConfig.getNotConfigMap();
        //初始化Switch
        Switch switchNotOpen = findViewById(R.id.switch_notification_tomorrow);
        Switch switchShowWhen = findViewById(R.id.switch_notification_showWhen);
        Switch switchShowWhere = findViewById(R.id.switch_notification_showWhere);
        Switch switchShowStep = findViewById(R.id.switch_notification_showStep);
        notIsOpen = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_OPEN);
        notIsShowWhen = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN);
        notIsShowWhere = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE);
        notIsShowStep = notConfigMap.get(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP);
        switchNotOpen.setChecked(notIsOpen);
        switchShowWhen.setChecked(notIsShowWhen);
        switchShowWhere.setChecked(notIsShowWhere);
        switchShowStep.setChecked(notIsShowStep);
        //为switch添加监听器
        setNotOpenSwitch(switchNotOpen, setHour, setMinute);
        setNotSetSwitch(switchShowWhen);
        setNotSetSwitch(switchShowWhere);
        setNotSetSwitch(switchShowStep);
    }

    /**
     * 初始化 toolbar
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

    /**
     * 配置通知开启开关
     * @param mySwitch 需要配置的开关
     * @param setHour   定时通知的时间-小时
     * @param setMinute 定时通知的时间-分钟
     */
    protected void setNotOpenSwitch(Switch mySwitch, int setHour, int setMinute)
    {

        mySwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {//被选中
                            if (NotificationManagerCompat.from(NotificationConfigActivity.this).areNotificationsEnabled()) {
                                //允许系统通知
                                startRemind(setHour, setMinute);     //22:00通知
                                Toast.makeText(NotificationConfigActivity.this,
                                        "通知已打开", Toast.LENGTH_SHORT).show();
                                notIsOpen = true;
                                configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_OPEN, OnMyConfigHandleAdapter.VALUE_TRUE);
                                MyConfig.saveConfig(configMap);
                            } else {
                                //未允许系统通知
                                Toast.makeText(NotificationConfigActivity.this,
                                        "请先在系统设置中允许通知", Toast.LENGTH_SHORT).show();
                                mySwitch.setChecked(false);
                            }
                        }
                        else {//被关闭
                            if (NotificationManagerCompat.from(NotificationConfigActivity.this).areNotificationsEnabled()) {
                                //允许系统通知
                                stopRemind();
                                Toast.makeText(NotificationConfigActivity.this, "通知已关闭", Toast.LENGTH_SHORT).show();
                                notIsOpen = false;
                                configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_OPEN, OnMyConfigHandleAdapter.VALUE_FALSE);
                                MyConfig.saveConfig(configMap);
                            }
                            else      //禁止系统通知
                                Toast.makeText(NotificationConfigActivity.this, "请先在系统设置中允许通知", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    /**
     * 配置通知设置开关
     * @param mySwitch 需要配置的开关
     */
    protected void setNotSetSwitch(Switch mySwitch){
        mySwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        switch (mySwitch.getId()){
                            case R.id.switch_notification_showWhen:
                                if(isChecked){
                                    Log.d(TAG, "showWhen set to on");
                                    notIsShowWhen = true;
                                    configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN, OnMyConfigHandleAdapter.VALUE_TRUE);
                                }else{
                                    notIsShowWhen = false;
                                    Log.d(TAG, "showWhen  set to off");
                                    configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN, OnMyConfigHandleAdapter.VALUE_FALSE);
                                }
                                MyConfig.saveConfig(configMap);
                                break;
                            case R.id.switch_notification_showWhere:
                                if(isChecked){
                                    Log.d(TAG, "showWhere  set to on");
                                    notIsShowWhere = true;
                                    configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE, OnMyConfigHandleAdapter.VALUE_TRUE);
                                }else{
                                    notIsShowWhere = false;
                                    Log.d(TAG, "showWhere  set to off");
                                    configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE, OnMyConfigHandleAdapter.VALUE_FALSE);
                                }
                                MyConfig.saveConfig(configMap);
                                break;
                            case R.id.switch_notification_showStep:
                                if(isChecked){
                                    Log.d(TAG, "showStep  set to on");
                                    notIsShowStep = true;
                                    configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP, OnMyConfigHandleAdapter.VALUE_TRUE);
                                }else{
                                    Log.d(TAG, "showStep  set to off");
                                    notIsShowStep = false;
                                    configMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP, OnMyConfigHandleAdapter.VALUE_FALSE);
                                }
                                MyConfig.saveConfig(configMap);
                                break;
                            default:
                                Log.d(TAG, "default;    info:" + mySwitch.getId());
                                break;
                        }
                        reStartRemind(notIsOpen, setHour, setMinute);
                    }
                }
        );
    }


    /**
     * 开启定时通知
     * @param setHour 发出通知的时间-小时
     * @param setMinute 发出通知的时间-分钟
     */
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
        //intent添加额外信息
        intent.putExtra(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN, notIsShowWhen);
        intent.putExtra(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE, notIsShowWhere);
        intent.putExtra(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP, notIsShowStep);
        PendingIntent pi = PendingIntent.getBroadcast(NotificationConfigActivity.this, 0, intent, 0);
        //得到AlarmManager实例
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
       //设定重复提醒，提醒周期为一天（24H）
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);

        Log.d(NotificationConfigActivity.TAG, "startRemind");
    }

    /**
     * 在修改通知内容后重新启动通知；若通知本身未开启，则不启动
     * @param notIsOpen
     * @param setHour
     * @param setMinute
     */
    protected void reStartRemind(boolean notIsOpen, int setHour, int setMinute){
        if(notIsOpen) {
            stopRemind();
            startRemind(setHour, setMinute);
        }
        Toast.makeText(NotificationConfigActivity.this, "通知已修改", Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭通知
     */
    protected void stopRemind() {
        Intent intent = new Intent(NotificationConfigActivity.this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(NotificationConfigActivity.this, 0,
                intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        //取消警报
        am.cancel(pi);
        Log.d(TAG, "stopRemind");
    }

}
