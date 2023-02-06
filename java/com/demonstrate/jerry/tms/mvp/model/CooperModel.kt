/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.CooperCompanyEntity
import com.infinity.jerry.yyd_tms.data.CooperEntity
import com.infinity.jerry.yyd_tms.data.CooperPointEntity
import com.infinity.jerry.yyd_tms.mvp.service.PointSetService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/20.
 */
class CooperModel private constructor() {
    init {

    }

    fun getCooperaters(json: String): Observable<ZCommonEntity<CooperEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.CooperateServer::class.java)
                .getCooperates(json)
        return observable
    }

    fun updateCooperState(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.CooperateServer::class.java)
                .updateCooper(json)
        return observable
    }

    fun searchCompany(json: String): Observable<ZCommonEntity<CooperCompanyEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.CooperateServer::class.java)
                .searchCompany(json)
        return observable
    }

    fun searchPoint(json: String): Observable<ZCommonEntity<CooperPointEntity>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.CooperateServer::class.java)
                .searchPoint(json)
        return observable
    }

    fun addCooper(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.CooperateServer::class.java)
                .addCooper(json)
        return observable
    }


    companion object {
        fun getInstance(): CooperModel {
            return CooperModel()
        }
    }
}