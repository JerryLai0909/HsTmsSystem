package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.ErrorBillingEntity
import com.infinity.jerry.yyd_tms.mvp.service.ErrorService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/7/17.
 */

class ErrorModel private constructor() {

    fun addError(json: String): Observable<ZCommonEntity<Any>> {
        val observable = ZServiceFactory.getInstance()
                .createService(ErrorService.ErrorAddServer::class.java)
                .addError(json)

        return observable
    }

    fun searchError(json: String): Observable<ZCommonEntity<ErrorBillingEntity>> {
        val observable = ZServiceFactory.getInstance()
                .createService(ErrorService.ErrorRemoteServer::class.java)
                .searchError(json)
        return observable
    }

    companion object {
        fun getInstance() : ErrorModel {
            return ErrorModel()
        }
    }

}