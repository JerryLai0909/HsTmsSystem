/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.CarsCommonEntity
import com.infinity.jerry.yyd_tms.mvp.service.PointSetService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/18.
 */
class CarsModel private constructor() {

    init {
    }

    fun getCommonCars(): Observable<ZCommonEntity<List<CarsCommonEntity>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.GetCommonCars::class.java)
                .getCommonCars()
        return observable
    }

    fun editCommonCar(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.EditCommonCars::class.java)
                .editCommonCars(json)
        return observable
    }

    fun deleteCommonCar(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.DeleteCommonCars::class.java)
                .deleteCommonCars(json)
        return observable
    }


    companion object {
        fun getInstance(): CarsModel {
            return CarsModel()
        }
    }
}