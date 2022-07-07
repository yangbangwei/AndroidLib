package com.android.common.api.interceptor;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志输出
 * Created by yangbangwei on 2016/10/26.
 * Email：bangweiyang@gmail.com
 */
public final class LoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static int LOG_MAXLENGTH = 3000;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        //请求地址请求方法
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append("--> ").append(request.method());
        paramsBuilder.append("  ").append(request.url());
        //收集请求参数，方便调试
        RequestBody body = request.body();
        if (body != null) {
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;

                //添加原请求体
                for (int i = 0; i < formBody.size(); i++) {
                    paramsBuilder.append("&");
                    paramsBuilder.append(formBody.name(i));
                    paramsBuilder.append("=");
                    paramsBuilder.append(formBody.value(i));
                }

            } else if (body instanceof MultipartBody) {
                MultipartBody formBody = (MultipartBody) body;
                //添加原请求体
                for (int i = 0; i < formBody.size(); i++) {
                    paramsBuilder.append("&");
                    paramsBuilder.append(formBody.part(i));
                }

            }
        }
        //请求输出
        Log.d("HTTP", paramsBuilder.toString());

        //返回结果
        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();


        BufferedSource source = responseBody.source();
        // Buffer the entire body.
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        if (contentLength != 0) {
            //输出
            String result = decodeUnicode(buffer.clone().readString(charset));
            if (result.length() > LOG_MAXLENGTH) {
                Log.d("HTTP", result.substring(0, LOG_MAXLENGTH));
                Log.d("HTTP", result.substring(LOG_MAXLENGTH, result.length()));
            } else {
                Log.d("HTTP", result);
            }
        }
        return response;
    }

    private static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
}
