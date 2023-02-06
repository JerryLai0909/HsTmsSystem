/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.mvp.service.MineService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import rx.Observable

/**
 * Created by jerry on 2017/6/19.
 */
class MineModel private constructor() {

    init {

    }

    fun udpatePwd(pwd: String, newpwd: String): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(MineService.UpdatePwdServer::class.java)
                .updatePwd(pwd, newpwd)
        return observable

    }

    fun quitLogin(): Observable<ZCommonEntity<Any>> {
        var observable = ZServiceFactory.getInstance()
                .createService(MineService.QuitLoginServer::class.java)
                .quitLogin()
        return observable

    }

    companion object {
        fun getInstance(): MineModel {
            return MineModel()
        }
    }
}