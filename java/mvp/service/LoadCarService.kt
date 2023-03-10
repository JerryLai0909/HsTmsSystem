/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.LoadCarLineEntity
import com.infinity.jerry.yyd_tms.data.TempIdsEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.LoadListEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.*
import rx.Observable

/**
 * Created by jerry on 2017/6/19.
 */
class LoadCarService {

    interface LoadLinesServer {
        @POST("loading/selectBillingForLoading")
        fun getLoadLines(): Observable<ZCommonEntity<List<LoadCarLineEntity>>>

        @GET("loading/list")
        fun getLoadList(): Observable<ZCommonEntity<List<LoadListEntity>>>

        @GET("loading/list/{id}?pageSize=300&pageNumber=1")
        fun getEndPointDetails(@Path("id") id: Int): Observable<ZCommonEntity<NewListWrap>>
    }

    interface LoadCarServer {
        @FormUrlEncoded
        @Headers("Content-Type: application/x-www-form-urlencoded")
        @POST("loading/saveLoadingBatch")
        fun loadCar(@Field("map") json: String): Observable<ZCommonEntity<List<BillingDrawMain>>>

        @PUT("loading/up/{batchId}")
        fun buZHuang(@Path("batchId") batchId: String, @Body entity: TempIdsEntity): Observable<ZCommonEntity<Object>>
    }


}