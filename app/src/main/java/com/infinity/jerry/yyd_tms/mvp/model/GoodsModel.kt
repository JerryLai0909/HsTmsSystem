/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.CommonGoods
import com.infinity.jerry.yyd_tms.mvp.service.BillService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/15.
 */
class GoodsModel private constructor() {

    fun getGoods(): Observable<ZCommonEntity<List<CommonGoods>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(BillService.GoodsServer::class.java)
                .getGoods()
        return observable
    }

    fun deleteGoods(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(BillService.GoodsServer::class.java)
                .deleteGoods(json)
        return observable
    }

    fun addGoods(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(BillService.GoodsServer::class.java)
                .addGoods(json)
        return observable
    }


    companion object {
        fun getInstance(): GoodsModel {
            return GoodsModel()
        }
    }


}