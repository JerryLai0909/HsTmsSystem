/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.z_mvp_utils;

import com.infinity.jerry.yyd_tms.data.AppUserToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 2/10/17.
 */

public class ZCommonIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = AppUserToken.getInstance().getToken();
        if (token != null && token.length() > 0) {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("token", token)
                    .build();
            return chain.proceed(request);
        } else {
            Request request = chain.request()
                    .newBuilder()
                    .build();
            return chain.proceed(request);
        }

    }
}
