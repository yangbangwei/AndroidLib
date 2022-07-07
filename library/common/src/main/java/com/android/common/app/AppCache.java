package com.android.common.app;

import com.android.common.utils.JsonUtils;
import com.android.common.utils.SPUtils;

/**
 * 用户信息缓存
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class AppCache {

    private volatile static AppCache instance;

    private static final String USER_INFO = "user_info";

    private AppCache() {
    }

    public static AppCache getInstance() {
        if (null == instance) {
            synchronized (AppCache.class) {
                if (instance == null) {
                    instance = new AppCache();
                }
            }
        }
        return instance;
    }

    /**
     * 读取用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        String login = SPUtils.getString(USER_INFO, "");
        return JsonUtils.fromJson(login, UserInfo.class);
    }

    /**
     * 保存用户信息
     *
     * @return
     */
    public void setUserInfo(UserInfo userInfo) {
        SPUtils.putString(USER_INFO, JsonUtils.toJson(userInfo));
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return false;
        }
        return userInfo.isLogin();
    }

    /**
     * 是否支持刷脸登录
     *
     * @return
     */
    public boolean isFaceLogin() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null && !isLogin()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 清空用户信息
     *
     * @return
     */
    public void clear() {
        SPUtils.remove(USER_INFO);
    }

}
