package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.network.base.AppOkHttpClientImpl;
import com.example.myapplication.network.base.HttpCommon;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpCommon.initClient(new AppOkHttpClientImpl());
    }
}
