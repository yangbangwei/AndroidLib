package com.android.common.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.multidex.MultiDex;

import com.android.common.utils.CrashUtils;
import com.android.common.utils.FileUtils;
import com.android.common.utils.LogUtils;
import com.android.common.utils.SPUtils;

/**
 * 基类Application
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        LogUtils.logd("onCreate");
        // 构建项目所需的所有文件目录
        FileUtils.createDir(this);

        // 缓存初始化
        SPUtils.getSharedPreferencesInstance(this);

        // 对全局未捕获的异常进行捕捉并写入SD卡
        Thread.setDefaultUncaughtExceptionHandler(new CrashUtils());
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
