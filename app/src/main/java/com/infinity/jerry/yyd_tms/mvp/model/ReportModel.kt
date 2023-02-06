/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.report.ReportEntity
import com.infinity.jerry.yyd_tms.data.report.ReportPointEntity
import com.infinity.jerry.yyd_tms.mvp.service.ReportService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/7/5.
 */
class ReportModel private constructor() {


    fun getMonthBills(json: String): Observable<ZCommonEntity<MutableList<ReportEntity>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ReportService.MonthReportServer::class.java)
                .getMonthReport(json)
        return observable
    }

    fun getDayBills(json: String): Observable<ZCommonEntity<MutableList<ReportEntity>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ReportService.DayReportServer::class.java)
                .getDayReport(json)
        return observable
    }

    fun getPointBillList(dataType: String, boardType: String): Observable<ZCommonEntity<ReportPointEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ReportService.ReportPointServer::class.java)
                .getPointBillList(dataType, boardType)
        return observable
    }

    fun getPointBillDetails(id: String, dataType: String, boardType: String): Observable<ZCommonEntity<ReportPointEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ReportService.ReportPointServer::class.java)
                .getPointBillDetails(id, dataType, boardType)
        return observable
    }

    companion object {
        fun getInstance(): ReportModel {
            return ReportModel()
        }
    }
}