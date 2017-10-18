package com.karan.materialtest.Activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by Admin on 10/16/2017.
 */

public class MyApplication extends Application
{
    private static MyApplication myApplication;
    public static final String MyApiKey="47ec37919c0cd2b9fa96494103a3b838";
    public static final String language="en-US";
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
