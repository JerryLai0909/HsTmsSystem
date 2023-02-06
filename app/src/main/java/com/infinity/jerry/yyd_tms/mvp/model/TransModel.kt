/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.CooperPointEntity
import com.infinity.jerry.yyd_tms.data.request_entity.TransPageEntity
import com.infinity.jerry.yyd_tms.mvp.service.PointSetService
import com.infinity.jerry.yyd_tms.mvp.service.TransService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/7/24.
 */
class TransModel private  constructor(){

    fun getTransList(json: String): Observable<ZCommonEntity<TransPageEntity>> {
        val observable = ZServiceFactory.getInstance()
                .createService(TransService.TransServer::class.java)
                .getTransList(json)
        return observable
    }

    fun addTrans(json: String): Observable<ZCommonEntity<Any>> {
        val observable = ZServiceFactory.getInstance()
                .createService(TransService.TransServer::class.java)
                .addTrans(json)
        return observable
    }

    fun searchPoint(json: String): Observable<ZCommonEntity<CooperPointEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.CooperateServer::class.java)
                .searchPoint(json)
        return observable
    }

    companion object {
        fun getInstance() :TransModel{
            return TransModel()
        }
    }
}