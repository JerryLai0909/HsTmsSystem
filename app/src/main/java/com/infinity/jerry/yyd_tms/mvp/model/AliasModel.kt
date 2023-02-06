/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.AliasEntity
import com.infinity.jerry.yyd_tms.mvp.service.PointSetService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/18.
 */
class AliasModel private constructor() {

    init {

    }

    fun getCommonAlias(): Observable<ZCommonEntity<List<AliasEntity>>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.GetCommonAlias::class.java)
                .getCommonAlias()
        return observable
    }

    fun editCommonAlias(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.EditCommonAlias::class.java)
                .editCommonAlias(json)
        return observable
    }

    fun deleteCOmmonAlias(json: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(PointSetService.DeleteCommonAlias::class.java)
                .deleteCOmmonAlias(json)
        return observable
    }


    companion object {
        fun getInstance(): AliasModel {
            return AliasModel()
        }
    }
}