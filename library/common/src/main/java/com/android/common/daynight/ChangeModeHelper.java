package com.android.common.daynight;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 夜间模式辅助类
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class ChangeModeHelper {
    public static final int MODE_DAY = 1;
    public static final int MODE_NIGHT = 2;
    private static String MODE = "mode";

    public static void setChangeMode(Context ctx, int mode) {
        SharedPreferences sp = ctx.getSharedPreferences("config_mode", Context.MODE_PRIVATE);
        sp.edit().putInt(MODE, mode).commit();
    }

    public static int getChangeMode(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config_mode", Context.MODE_PRIVATE);
        return sp.getInt(MODE, MODE_DAY);
    }
}
