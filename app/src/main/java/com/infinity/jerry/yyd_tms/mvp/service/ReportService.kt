/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.report.ReportEntity
import com.infinity.jerry.yyd_tms.data.report.ReportPointEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.*
import rx.Observable

/**
 * Created by jerry on 2017/7/5.
 */
class ReportService {

    interface MonthReportServer {
        @FormUrlEncoded
        @POST("report/selectReportList")
        fun getMonthReport(@Field("map") json: String): Observable<ZCommonEntity<MutableList<ReportEntity>>>
    }

    interface DayReportServer {
        @FormUrlEncoded
        @POST("report/selectReportListByDay")
        fun getDayReport(@Field("map") json: String): Observable<ZCommonEntity<MutableList<ReportEntity>>>
    }


    interface ReportPointServer {
        @GET("point/revenue/rank")
        fun getPointBillList(@Query("dateType") dataType: String, @Query("boardType") boardType: String): Observable<ZCommonEntity<ReportPointEntity>>

        @GET("point/revenue/rank/detail/{id}")
        fun getPointBillDetails(@Path("id") id: String, @Query("dateType") dataType: String, @Query("boardType") boardType: String): Observable<ZCommonEntity<ReportPointEntity>>
    }


}