package com.example.hhuclassschedule.util;

import android.app.Application;
import android.content.Context;

/**
 * 获取 Context
 */
public class ContextApplication extends Application {
    private static Context context;

   @Override
    public void onCreate(){
       super.onCreate();
       ContextApplication.context = getApplicationContext();
   }
   public static Context getAppContext(){
       return ContextApplication.context;
   }
}
