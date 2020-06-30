package com.futures.knowledge;

import android.app.Application;

import com.futures.knowledge.utils.AssetsDatabaseManager;

/**
 * @ClassName: MyApplication
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/24 9:16
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AssetsDatabaseManager.initManager(getApplicationContext());
    }
}
