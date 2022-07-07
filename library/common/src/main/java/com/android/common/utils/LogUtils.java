package com.android.common.utils;


import com.android.common.app.AppConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Logger日志输出工具类
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class LogUtils {
    public static boolean DEBUG_ENABLE = true;// 是否调试模式

    /**
     * 在application调用初始化
     */
    public static void logInit(boolean debug) {
        DEBUG_ENABLE = debug;
        if (DEBUG_ENABLE) {
            Logger.init(AppConfig.APP_TAG)
                    .methodCount(2)
                    .hideThreadInfo()
                    .logLevel(LogLevel.FULL)
                    .methodOffset(0);
        } else {
            Logger.init()
                    .methodCount(3)
                    .hideThreadInfo()
                    .logLevel(LogLevel.NONE)
                    .methodOffset(2);
        }
    }

    public static void logd(String tag, String message) {
        if (DEBUG_ENABLE) {
            Logger.d(tag, message);
        }
    }

    public static void logd(String message) {
        if (DEBUG_ENABLE) {
            Logger.d(message);
        }
    }

    public static void loge(Throwable throwable, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(throwable, message, args);
        }
    }

    public static void loge(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(message, args);
        }
    }

    public static void logi(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.i(message, args);
        }
    }

    public static void logv(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logw(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logwtf(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.wtf(message, args);
        }
    }

    public static void logjson(Object object) {
        if (DEBUG_ENABLE) {
            Logger.json(JsonUtils.toJson(object));
        }
    }

    public static void logxml(String message) {
        if (DEBUG_ENABLE) {
            Logger.xml(message);
        }
    }
}
