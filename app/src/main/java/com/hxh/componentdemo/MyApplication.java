package com.hxh.componentdemo;

import android.app.Application;

import com.hxh.route.ARoute;

/**
 * Created by HXH at 2019/9/18
 * 应用Application
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARoute.getInstance().init(this);
    }
}
