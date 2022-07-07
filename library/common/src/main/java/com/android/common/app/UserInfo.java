package com.android.common.app;

/**
 * Created by yangbangwei on 2016/12/14.
 */
public class UserInfo {

    private boolean isLogin;
    private String name;
    private String no;
    private String imgByte;

    public UserInfo(boolean isLogin, String name, String no, String imgByte) {
        this.isLogin = isLogin;
        this.name = name;
        this.no = no;
        this.imgByte = imgByte;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getImgByte() {
        return imgByte;
    }

    public void setImgByte(String imgByte) {
        this.imgByte = imgByte;
    }
}
