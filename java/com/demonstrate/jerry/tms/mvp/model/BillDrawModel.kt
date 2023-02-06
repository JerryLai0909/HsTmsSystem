/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.mvp.service.BillService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/19.
 */
class BillDrawModel private constructor() {

    init {

    }

    fun getBarCode(id:String) :Observable<ZCommonEntity<String>> {
        return ZServiceFactory.getInstance().createService(BillService.BillServer::class.java).getBarCode(id)
    }

    fun drawBilling(json: String): Observable<ZCommonEntity<BillingDrawMain>> {
        var observable = ZServiceFactory.getInstance()
                .createService(BillService.BillServer::class.java)
                .drawBilling(json)
        return observable

    }

    companion object {
        fun getInstance(): BillDrawModel {
            return BillDrawModel()
        }
    }

}