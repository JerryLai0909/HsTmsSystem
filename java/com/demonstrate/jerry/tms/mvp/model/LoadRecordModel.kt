/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.mvp.service.LoadRecordService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/7/10.
 */
class LoadRecordModel private constructor() {

    fun getRecordLists(json: String): Observable<ZCommonEntity<MyBillListEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(LoadRecordService.LoadRecordServer::class.java)
                .getLoadRecord(1, 1000, json)
        return observable
    }

    fun getNewRecordList(): Observable<ZCommonEntity<List<NewRecordsEntity>>> {
        return ZServiceFactory.getInstance()
                .createService(LoadRecordService.LoadRecordServer::class.java)
                .getLoadRecordNew()
    }

    fun getLoadByBatchId(batch: Int): Observable<ZCommonEntity<List<BillListTempShit>>> {
        return ZServiceFactory.getInstance()
                .createService(LoadRecordService.LoadRecordServer::class.java)
                .getLoadListByBatchId(batch,true)
    }

    fun getLoadByBatchIdX(batch: Int): Observable<ZCommonEntity<List<BillingDrawMain>>> {
        return ZServiceFactory.getInstance()
                .createService(LoadRecordService.LoadRecordServer::class.java)
                .getLoadListByBatchIdX(batch,false)
    }

    fun appJiuCuo(entity:TempIdsEntity) :Observable<ZCommonEntity<Any>>{
        return ZServiceFactory.getInstance()
                .createService(LoadRecordService.LoadRecordServer::class.java)
                .loadJiuCuo(entity)

    }



    companion object {
        fun getInstance(): LoadRecordModel {
            return LoadRecordModel()
        }
    }
}