/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by jerry on 2017/6/26.
 */
class SignService {

    interface SignServer {
        @FormUrlEncoded
        @POST("sign/selectSignList")
        fun getSignBills(@Field("map") json: String, @Field("pageSize") size: Int, @Field("page") page: Int): Observable<ZCommonEntity<MyBillListEntity>>


        @FormUrlEncoded
        @POST("sign/updateBillingForSign")
        fun ensureSingbill(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }

}