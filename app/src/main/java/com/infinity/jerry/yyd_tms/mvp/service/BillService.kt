/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.CommonGoods
import com.infinity.jerry.yyd_tms.data.CommonPacks
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.*
import rx.Observable

/**
 * Created by jerry on 2017/6/14.
 */
class BillService {

    //当日统计票据
    interface StatisticsServer {
        @POST("billManager/selectCostForDay")
        fun getStatisBill(): Observable<ZCommonEntity<BillingDrawMain>>
    }

    //货物
    interface GoodsServer {
        @POST("goodsManager/selectGoodsList")
        fun getGoods(): Observable<ZCommonEntity<List<CommonGoods>>>

        @FormUrlEncoded
        @POST("goodsManager/deleteGoods")
        fun deleteGoods(@Field("map") json: String): Observable<ZCommonEntity<Any>>

        @FormUrlEncoded
        @POST("goodsManager/addGoods")
        fun addGoods(@Field("map") json: String): Observable<ZCommonEntity<Any>>

    }

    //包装
    interface PacksServer {
        @POST("pack/selectPackAll")
        fun getPacks(): Observable<ZCommonEntity<List<CommonPacks>>>
    }

    //开票
    interface BillServer {
        @FormUrlEncoded
        @POST("billing/saveBilling")
        fun drawBilling(@Field("map") json: String): Observable<ZCommonEntity<BillingDrawMain>>

        @GET("billing/barCode/{id}")
        fun getBarCode(@Path("id") id: String): Observable<ZCommonEntity<String>>

        @POST("billing/collect/log/mobile/{id}")
        @FormUrlEncoded
        fun addRecord(@Path("id") id: String, @Field("amount") amount: Long, @Field("collectOperation") oper: String): Observable<ZCommonEntity<Any>>?
    }

    interface BillingManagerServer {
        //删除票据
        @FormUrlEncoded
        @POST("billManager/billCancel")
        fun cancelBilling(@Field("uuid") uuid: Long): Observable<ZCommonEntity<Any>>
    }


}