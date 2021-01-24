package com.alllinkshare.liveTrack.tracking;

import android.app.Application;

import io.paperdb.Paper;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
