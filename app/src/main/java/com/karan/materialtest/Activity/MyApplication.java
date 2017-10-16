package com.karan.materialtest.Activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by Admin on 10/16/2017.
 */

public class MyApplication extends Application
{
    private static MyApplication myApplication;
    @Override
    public void onCreate()
    {
        super.onCreate();
        myApplication=this;
    }
    public static MyApplication getInstance()
    {
        return myApplication;
    }
    public static Context getAppContext()
    {
        return myApplication.getApplicationContext();
    }
}
