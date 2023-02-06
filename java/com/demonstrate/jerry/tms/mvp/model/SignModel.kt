/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.mvp.service.SignService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/26.
 */
class SignModel private constructor() {


    fun getSignBills(json: String): Observable<ZCommonEntity<MyBillListEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(SignService.SignServer::class.java)
                .getSignBills(json, 1000, 1)
        return observable
    }

    fun ensureSignBills(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(SignService.SignServer::class.java)
                .ensureSingbill(json)
        return observable
    }

    companion object {
        fun getInstance(): SignModel {
            return SignModel()
        }
    }
}