package com.android.common.utils;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 多语言工具类
 */
public class LanguageUtils {

    public static final String LANGUAGE = "language";
    public static final String AUTO = "auto";
    public static final String CHINESE = "zh";
    public static final String ENGLISH = "en";
    public static final String EN_TYPE = "1";
    public static final String CH_TYPE = "2";

    /**
     * 获取当前语言类型
     *
     * @return
     */
    public static String getLanguageType() {
        return SPUtils.getString(LANGUAGE, AUTO);
    }

    /**
     * 英文1，中文2
     *
     * @return
     */
    public static String getType() {
        String type = getLanguageType();
        if (type.equals(ENGLISH)) {
            return EN_TYPE;
        }
        return CH_TYPE;
    }

    /**
     * 获取当前语言类型
     *
     * @return
     */
    public static void setLanguageType(String type) {
        SPUtils.putString(LANGUAGE, type);
    }

    /**
     * 设置语言类型
     *
     * @param context
     */
    public static void setLanguage(Context context) {
        String type = getLanguageType();
        //根据读取到的数据，进行设置
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (type) {
            case AUTO:
                configuration.setLocale(getSysLocale());
                break;
            case ENGLISH:
                configuration.setLocale(Locale.ENGLISH);
                break;
            case CHINESE:
                configuration.setLocale(Locale.CHINESE);
                break;
            default:
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    /**
     * 获取系统语言
     *
     * @param
     * @return
     */
    public static Locale getSysLocale() {
        return Resources.getSystem().getConfiguration().locale;
    }
}
