package com.android.common.api.convert;

import android.content.Intent;

import com.android.common.app.AppConfig;
import com.android.common.base.BaseApplication;
import com.android.common.base.BaseRespose;
import com.android.common.baserx.ServerException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class MGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseRespose httpStatus = gson.fromJson(response, BaseRespose.class);
        //异常状态的处理
        if (!httpStatus.success()) {
            value.close();
            //token超时，重新登录
            if (httpStatus.code == -1) {
                //强制弹窗,重新登录
                Intent intent = new Intent(AppConfig.FORCE_OFFLINE);
                BaseApplication.getAppContext().sendBroadcast(intent);
            }
            throw new ServerException(httpStatus.code, httpStatus.msg);
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}