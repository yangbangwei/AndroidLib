package com.android.common.api.interceptor;


import com.android.common.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 有网络时,缓存时间1分钟
 * 无网络时,强制读取缓存,缓存保存时长为一个月
 * Created by yangbangwei on 2016/9/1.
 * Email：bangweiyang@gmail.com
 */
public class CachedInterceptor implements Interceptor {

  private static final int MAX_AGE = 60;

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    if (!NetWorkUtils.isNetConnected()) {
      request = request.newBuilder()
          //强制使用缓存
          .cacheControl(CacheControl.FORCE_CACHE).build();
    }

    Response response = chain.proceed(request);

    if (NetWorkUtils.isNetConnected()) {
      int maxAge = MAX_AGE; // 有网络时 设置缓存超时1分钟
      response.newBuilder()
          .header("Cache-Control", "public, max-age=" + maxAge)
          .removeHeader("Pragma")
          .build();
    } else {
      int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
      response.newBuilder()
          .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
          .removeHeader("Pragma")
          .build();
    }
    return response;
  }
}
