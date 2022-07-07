package com.android.common.api.interceptor;

import com.android.common.app.AppCache;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 添加用户身份验证
 * Created by yangbangwei on 2016/10/26.
 * Email：bangweiyang@gmail.com
 */
public class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request orgRequest = chain.request();

        RequestBody body = orgRequest.body();

        //用户登录后，添加用户认证
        if (AppCache.getInstance().isLogin()
                && body != null) {
            RequestBody newBody = null;
            //根据类型添加KEY认证
            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body);
            }
            //生成新的请求
            if (null != newBody) {
                Request newRequest = orgRequest.newBuilder()
                        .url(orgRequest.url())
                        .method(orgRequest.method(), newBody)
                        .build();

                return chain.proceed(newRequest);
            }
        }

        return chain.proceed(orgRequest);

    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //添加appcode
//        builder.addFormDataPart("key", AppCache.getInstance().getUserInfo().getAccess_token());

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }

        return builder.build();
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body) {
        FormBody.Builder builder = new FormBody.Builder();

        //添加appcode
//        builder.add("key", AppCache.getInstance().getUserInfo().getAccess_token());

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
        }

        return builder.build();
    }
}
