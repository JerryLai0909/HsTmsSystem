/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.test.mvpp;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jerry on 2017/7/18.
 */

public class LoginServer {

    //注意这个地方的地址。retrofit 构建的时候回有一个baseurl ，根据地址把公共部分 设置为baseurl ，后面的请求就写后面的2级路径就行了
    interface Login{
        @FormUrlEncoded
        @POST("index.php/userController/login")
        Observable<LoginEntity> loginApp(@Field("name") String name, @Field("pwd") String pwd);
    }
}
