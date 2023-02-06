/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by jerry on 2017/6/19.
 */
class MineService {

    interface UpdatePwdServer {
        @FormUrlEncoded
        @POST("User/UpdateUser")
        fun updatePwd(@Field("pwd") pwd: String ,@Field("newpwd") newpwd:String): Observable<ZCommonEntity<Any>>
    }

    interface QuitLoginServer {
        @POST("logout")
        fun quitLogin(): Observable<ZCommonEntity<Any>>
    }
}