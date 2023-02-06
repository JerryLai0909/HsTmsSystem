/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model

import com.infinity.jerry.yyd_tms.data.LoginResult
import com.infinity.jerry.yyd_tms.mvp.service.LoginService
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory
import com.infinity.jerry.yyd_tms.utils.EncryptMD5
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import rx.Observable

/**
 * Created by jerry on 2017/6/12.
 */
class LoginModel private constructor() {

    init {

    }

    fun userLogin(username: String, pwd: String): Observable<ZCommonEntity<LoginResult>> {
        val md5 = EncryptMD5.getInstance().getPwdMd5LowerCase(pwd)
        val observable = ZServiceFactory.getInstance()
                .createService(LoginService.LoginServer::class.java)
                .appLogin(username, md5)
        return observable
    }


    fun userLogin(): Observable<ZCommonEntity<LoginResult>> {
        val username = ZShrPrefencs.getInstance().nameAndPwd[0]
        val pwd = ZShrPrefencs.getInstance().nameAndPwd[1]
        val observable = ZServiceFactory.getInstance()
                .createService(LoginService.LoginServer::class.java)
                .appLogin(username, pwd)
        return observable
    }


    companion object {
        fun getInstance(): LoginModel {
            return LoginModel()
        }
    }


}