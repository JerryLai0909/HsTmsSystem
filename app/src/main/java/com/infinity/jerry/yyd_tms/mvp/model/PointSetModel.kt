/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BoManDetial
import com.infinity.jerry.yyd_tms.data.BoPointAndManEntity
import com.infinity.jerry.yyd_tms.data.BoPointDetail
import com.infinity.jerry.yyd_tms.data.request_entity.RateSpecialEntity
import com.infinity.jerry.yyd_tms.mvp.service.PointSetService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/20.
 */
class PointSetModel private constructor() {

    init {

    }

    fun getAllPointAndMan(json: String): Observable<ZCommonEntity<BoPointAndManEntity>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.PointAndManServer::class.java)
                .getPointAndMan(json)
        return observable
    }

    fun getPointDetail(json: String): Observable<ZCommonEntity<BoPointDetail>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.PointAndManServer::class.java)
                .getPointDetails(json)
        return observable
    }

    fun updatePointInfo(json: String): Observable<ZCommonEntity<Any>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.PointAndManServer::class.java)
                .updatePointInfo(AppUserToken.getInstance().token, json)
        return observable
    }

    fun getAllMan(json: String): Observable<ZCommonEntity<List<BoManDetial>>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.PointAndManServer::class.java)
                .getAllMan(AppUserToken.getInstance().token, json)
        return observable
    }

    fun updatePointMan(json: String): Observable<ZCommonEntity<Any>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.PointAndManServer::class.java)
                .updatePointMan(AppUserToken.getInstance().token, json)
        return observable
    }

    fun deletePointMan(json: String): Observable<ZCommonEntity<Any>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.PointAndManServer::class.java)
                .deletePointMan(AppUserToken.getInstance().token, json)
        return observable
    }

    fun setRate(json: String):Observable<ZCommonEntity<Any>> {
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.FeeRateServer::class.java)
                .setRate(json)
        return observable
    }

    fun getRate(uuid: Long) :Observable<RateSpecialEntity>{
        val observable = ZServiceFactory.getInstance()
                .createService(PointSetService.FeeRateServer::class.java)
                .getRate(uuid)
        return observable
    }

    companion object {
        fun getInstance(): PointSetModel {
            return PointSetModel()
        }
    }
}