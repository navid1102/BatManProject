package com.nf.batmannf;

import android.app.Application;

import com.nf.batmannf.data.SettingManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SettingManager.initialize(getApplicationContext());
    }
}
