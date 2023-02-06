/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.LoadCarLineEntity
import com.infinity.jerry.yyd_tms.data.TempIdsEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.LoadListEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap
import com.infinity.jerry.yyd_tms.mvp.service.LoadCarService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/19.
 */
class LoadCarModel private constructor() {

    init {

    }

    fun getEndPointDetails(id: Int): Observable<ZCommonEntity<NewListWrap>> {
        return ZServiceFactory.getInstance().createService(LoadCarService.LoadLinesServer::class.java).getEndPointDetails(id)
    }

    fun getLoadList(): Observable<ZCommonEntity<List<LoadListEntity>>> {
        return ZServiceFactory.getInstance().createService(LoadCarService.LoadLinesServer::class.java).getLoadList()

    }

    fun getLoadLines(): Observable<ZCommonEntity<List<LoadCarLineEntity>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(LoadCarService.LoadLinesServer::class.java)
                .getLoadLines()
        return observable
    }

    fun loadCar(json: String): Observable<ZCommonEntity<List<BillingDrawMain>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(LoadCarService.LoadCarServer::class.java)
                .loadCar(json)
        return observable
    }

    fun buZhuang(batchId: String, entity: TempIdsEntity): Observable<ZCommonEntity<Object>> {
        var observable = ZServiceFactory.getInstance()
                .createService(LoadCarService.LoadCarServer::class.java)
                .buZHuang(batchId, entity)
        return observable;
    }

    companion object {

        fun getInstance(): LoadCarModel {
            return LoadCarModel()
        }
    }


}