package com.example.customoverlay;

import android.app.Application;
import android.content.Context;

/**
 * Created by shimans on 4/25/17.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
