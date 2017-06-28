package com.android.inputmethod.pinyin;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.inputmethod.pinyin.net.NetworkManager;

/**
 * Created by HuangBing on 2017/6/28.
 */

public class PinYinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.initDbHelp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
