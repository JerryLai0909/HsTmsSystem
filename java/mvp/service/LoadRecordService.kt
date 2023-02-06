/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.*
import rx.Observable

/**
 * Created by jerry on 2017/7/10.
 */
class LoadRecordService {

    interface LoadRecordServer {
        @FormUrlEncoded
        @POST("loading/selectBillingForLoadingByAll")
        fun getLoadRecord(@Field("page") page: Int, @Field("pageSize") pageSize: Int, @Field("map") json: String): Observable<ZCommonEntity<MyBillListEntity>>


        @GET("loading/history/list")
        fun getLoadRecordNew(): Observable<ZCommonEntity<List<NewRecordsEntity>>>

        @GET("loading/history/list/{batchId}")
        fun getLoadListByBatchIdX(@Path("batchId") batchId: Int,@Query("isGroup") boolean: Boolean): Observable<ZCommonEntity<List<BillingDrawMain>>>

        @GET("loading/history/list/{batchId}")
        fun getLoadListByBatchId(@Path("batchId") batchId: Int,@Query("isGroup") boolean: Boolean): Observable<ZCommonEntity<List<BillListTempShit>>>


        @PUT("loading/correction/app")
        fun loadJiuCuo(@Body entity: TempIdsEntity):Observable<ZCommonEntity<Any>>
    }
}