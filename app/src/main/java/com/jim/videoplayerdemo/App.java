package com.jim.videoplayerdemo;

import android.app.Application;

/**
 * Created by Jim on 2018/5/15 0015.
 */

public class App extends Application {

    public static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }
}
