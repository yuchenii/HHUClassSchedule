package com.example.hhuclassschedule;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hhuclassschedule.util.ContextApplication;
import com.example.hhuclassschedule.util.SharedPreferencesUtil;

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
    public void saveConfig(Map<String, String> configMap) {
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
    public Map<String, String> loadConfig() {
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
}
