/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.data.NewArrivalEntity
import com.infinity.jerry.yyd_tms.mvp.service.ArrivalService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/26.
 */
class ArrivalModel private constructor() {


    fun getArrivalBills(json: String): Observable<ZCommonEntity<MyBillListEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ArrivalService.ArrivalServer::class.java)
                .getArrivalBills(json)
        return observable
    }

    fun getNewArrival(offset: Int, number: Int, size: Int): Observable<ZCommonEntity<NewArrivalEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ArrivalService.ArrivalServer::class.java)
                .getNewArrival(offset, number, size)
        return observable
    }


    fun ensureArrivalBills(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ArrivalService.ArrivalServer::class.java)
                .arrivalEnsure(json)
        return observable
    }

    fun ensureNewArrival(batchId: Int): Observable<ZCommonEntity<Object>> {
        var observable = ZServiceFactory.getInstance()
                .createService(ArrivalService.ArrivalServer::class.java).ensureNewArrival(batchId)
        return observable
    }

    companion object {
        fun getInstance(): ArrivalModel {
            return ArrivalModel()
        }
    }
}