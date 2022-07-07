package com.android.common.base;

import java.io.Serializable;

/**
 * 基类Respose
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class BaseRespose<T> implements Serializable {
    public int code;
    public String msg;

    public T datas;

    public boolean success() {
        return code == 200;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + datas +
                '}';
    }
}
