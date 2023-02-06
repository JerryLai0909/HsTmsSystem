/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.data.NewArrivalEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.*
import rx.Observable

/**
 * Created by jerry on 2017/6/26.
 */
class ArrivalService {

    interface ArrivalServer {
        @FormUrlEncoded
        @POST("arrival/selectArrivalList")
        fun getArrivalBills(@Field("map") json: String): Observable<ZCommonEntity<MyBillListEntity>>


        @GET("arrival/list")
        fun getNewArrival(@Query("offset") offset: Int, @Query("pageNumber") number: Int, @Query("pageSize") size: Int): Observable<ZCommonEntity<NewArrivalEntity>>


        @FormUrlEncoded
        @POST("arrival/updateBillingForArrival")
        fun arrivalEnsure(@Field("map") json: String): Observable<ZCommonEntity<Any>>

        @PUT("arrival/confirm/{batchId}")
        fun ensureNewArrival(@Path("batchId") batchId: Int): Observable<ZCommonEntity<Object>>
    }


}