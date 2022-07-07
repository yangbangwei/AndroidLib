package com.android.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;

/**
 * SharedPre管理类
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class SPUtils {

    private static final String NAME = "share.keyvallue";
    public static SharedPreferences mSharedPreferences;

    public synchronized static void getSharedPreferencesInstance(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("share.keyvallue",
                    Context.MODE_PRIVATE);
        }
    }

    /**
     * 存入数据
     */

    public static void putInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public static void putFloat(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    public static void putLong(String key, Long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public static void putDouble(String key, double value) {
        /* 默认没有保存 double 的功能，将double转为字符串后保存 */
        mSharedPreferences.edit().putString(key, String.valueOf(value)).apply();
    }

    /**
     * 读取数据
     */

    public static int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public static double getDouble(String key, double defaultValue) {
        /* 默认不能取double，将字符串解析为doueble */
        String result = mSharedPreferences.getString(key, String.valueOf(defaultValue));
        return Double.valueOf(result);
    }

    /**
     * 获取数据，通过Gson进行转换
     *
     * @param key
     * @param obj
     * @return
     */
    public static void putObject(String key, Object obj) {
        String str = JsonUtils.toJson(obj);
        mSharedPreferences.edit().putString(key, str).apply();
    }

    /**
     * 获取数据，通过Gson进行转换
     *
     * @param key
     * @return
     */
    public static <T> T getObject(String key, Class<T> cls) {
        String str = mSharedPreferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            return JsonUtils.fromJson(str, cls);
        }
    }

    /**
     * 获取数据，通过Gson进行转换
     * 用来将JSON串转为对象，此方法可用来转带泛型的集合，
     * 如：Type为 new TypeToken<List<T>>(){}.getType()
     * ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
     *
     * @param key
     * @return
     */
    public static Object getObject(String key, Type typeOfT) {
        String str = mSharedPreferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            return JsonUtils.fromJson(str, typeOfT);
        }
    }

    /**
     * 清除
     *
     * @param key
     */
    public static void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    /**
     * 清除所有
     */
    public static void removeAll() {
        mSharedPreferences.edit().clear().apply();
    }


}
