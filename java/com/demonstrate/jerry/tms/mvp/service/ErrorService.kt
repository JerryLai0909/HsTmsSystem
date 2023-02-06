/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.ErrorBillingEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by jerry on 2017/7/17.
 */
class ErrorService {

    interface ErrorAddServer {
        @FormUrlEncoded
        @POST("unusual/saveUnusual")
        fun addError(@Field("map") json: String): Observable<ZCommonEntity<Any>>

    }

    interface  ErrorRemoteServer{
        @FormUrlEncoded
        @POST("unusual/selectUnusual")
        fun searchError(@Field("map") json :String) : Observable<ZCommonEntity<ErrorBillingEntity>>
    }
}