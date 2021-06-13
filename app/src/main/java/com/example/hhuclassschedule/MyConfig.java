package com.example.hhuclassschedule;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hhuclassschedule.adapter.OnMyConfigHandleAdapter;
import com.example.hhuclassschedule.util.ContextApplication;
import com.example.hhuclassschedule.util.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyConfig {
    public static final String TAG = "MyConfig";
//    private Context myContext;
//    public MyConfig(Context myContext) {
//        this.myContext = myContext;
//    }

    /**
     * 保存当前配置信息（缓冲map）至本地文件
     * @param configMap 配置缓冲map
     */
    public static void saveConfig(Map<String, String> configMap) {
        SharedPreferencesUtil sharedPreferencesUtil =
                SharedPreferencesUtil.init(ContextApplication.getAppContext(), MainActivity.CONFIG_FILENAME);
        for (String key : configMap.keySet()) {
            String value = configMap.get(key);
            sharedPreferencesUtil.putString(key, value);
        }
    }


//    public void saveConfig(Map<String, String> configMap){
//        SharedPreferences.Editor editor =
//                myContext.getSharedPreferences(MainActivity.CONFIG_FILENAME,Context.MODE_PRIVATE).edit();
//        for(String key : configMap.keySet()){
//            String value = configMap.get(key);
//            editor.putString(key, value);
//            editor.commit();
//        }
//    }

    /**
     * 从本地配置文件中读取信息至缓冲map
     */
    public static Map<String, String> loadConfig() {
        Map<String, String> configMap;
        SharedPreferencesUtil sharedPreferencesUtil =
                SharedPreferencesUtil.init(ContextApplication.getAppContext(), MainActivity.CONFIG_FILENAME);
        configMap = (Map<String, String>) sharedPreferencesUtil.getAll();
        return configMap;
    }

//    public Map<String, String> loadConfig(){
//        Map<String, String> configMap;
//        SharedPreferences sharedPreferences =
//                myContext.getSharedPreferences(MainActivity.CONFIG_FILENAME,Context.MODE_PRIVATE);
//        configMap = (Map<String, String>) sharedPreferences.getAll();
//        return configMap;
//    }

    /**
     * 从本地配置文件里获取notConfigMap;
     * 默认value都是false
     * @return
     */
    public static Map<String, Boolean> getNotConfigMap(){
        Map<String, String> originMap = MyConfig.loadConfig();
        Map<String, Boolean> notConfigMap = new HashMap<>();
        //初始化
        notConfigMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_OPEN, false);
        notConfigMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN, false);
        notConfigMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE, false);
        notConfigMap.put(OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP, false);
        //从配置文件里读取
        for(String key : originMap.keySet()){
            String value = originMap.get(key);
            if(value == null)
                continue;
            switch (key){
                case OnMyConfigHandleAdapter.CONFIG_NOT_OPEN:
                case OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHEN:
                case OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_WHERE:
                case OnMyConfigHandleAdapter.CONFIG_NOT_SHOW_STEP:
                    if (value.equals(OnMyConfigHandleAdapter.VALUE_TRUE))
                        notConfigMap.put(key, true);
                    else
                        notConfigMap.put(key, false);
                    break;
                default:
                    break;
            }
        }
        return notConfigMap;
    }
}
