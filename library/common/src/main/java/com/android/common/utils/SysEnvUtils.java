package com.android.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.android.common.base.BaseApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 本地运行环境信息
 */
public final class SysEnvUtils {

    /***
     * Log输出标识
     **/
    private static final String TAG = SysEnvUtils.class.getSimpleName();

    /***
     * 屏幕显示材质
     **/
    private static final DisplayMetrics mDisplayMetrics = new DisplayMetrics();

    /**
     * 上下文
     **/
    private static final Context context = BaseApplication.getAppContext();

    /**
     * 操作系统名称(GT-I9100G)
     ***/
    public static final String MODEL_NUMBER = Build.MODEL;

    /**
     * 操作系统名称(I9100G)
     ***/
    public static final String DISPLAY_NAME = Build.DISPLAY;

    /**
     * 操作系统版本(4.4)
     ***/
    public static final String OS_VERSION = Build.VERSION.RELEASE;

    /***
     * 屏幕宽度
     **/
    public static final int SCREEN_WIDTH = getDisplayMetrics().widthPixels;

    /***
     * 屏幕高度
     **/
    public static final int SCREEN_HEIGHT = getDisplayMetrics().heightPixels;

    /***
     * 本机手机号码
     **/
    public static final String PHONE_NUMBER = ((TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();

    /***
     * 获取IMEI
     **/
    public static final String DEVICE_ID = ((TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

    /***
     * 获取到SIM卡唯一编号ID；
     **/
    public static final String IMEI = ((TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();

    /***
     * 获取到客户ID，即IMSI；
     **/
    public static final String IMSI = ((TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

    /***
     * Activity之间数据传输数据对象Key
     **/
    public static final String ACTIVITY_DTO_KEY = "ACTIVITY_DTO_KEY";

    /**
     * 获取系统显示材质
     ***/
    public static DisplayMetrics getDisplayMetrics() {
        WindowManager windowMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics;
    }

    /**
     * 获取摄像头支持的分辨率
     ***/
    public static List<Camera.Size> getSupportedPreviewSizes(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        return sizeList;
    }

    /**
     * 获取系统内核版本
     *
     * @return
     */
    public static String getKernelVersion() {
        String strVersion = "";
        FileReader mFileReader = null;
        BufferedReader mBufferedReader = null;
        try {
            mFileReader = new FileReader("/proc/version");
            mBufferedReader = new BufferedReader(mFileReader, 8192);
            String str2 = mBufferedReader.readLine();
            strVersion = str2.split("\\s+")[2];// KernelVersion

        } catch (Exception e) {
            Log.e(TAG, "获取系统内核版本失败，原因：" + e.getMessage());
        } finally {
            try {
                mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strVersion;
    }

    /***
     * 获取MAC地址
     *
     * @return
     */
    public static String getMacAddress() {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            return wifiInfo.getMacAddress();
        } else {
            return "";
        }
    }

    /**
     * 获取运行时间
     *
     * @return 运行时间(单位/s)
     */
    public static long getRunTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        return ut;
    }

    /**
     * 判断是否为模拟器环境需要权限 <uses-permission
     * android:name="android.permission.READ_PHONE_STATE" />
     *
     * @param mContext 上下文
     * @return
     */
    public static boolean isEmulator(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = telephonyManager.getDeviceId();
        // 如果 运行的 是一个 模拟器
        if (deviceID == null || deviceID.trim().length() == 0
                || deviceID.matches("0+")) {
            return true;
        }
        return false;
    }
}
