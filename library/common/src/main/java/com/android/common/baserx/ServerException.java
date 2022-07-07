package com.android.common.baserx;

/**
 * des:服务器请求异常
 * Created by xsf
 * on 2016.09.10:16
 */
public class ServerException extends RuntimeException {

    private int mErrorCode;

    public ServerException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return mErrorCode == 500;
    }
}
