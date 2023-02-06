/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.LoginResult
import com.infinity.jerry.yyd_tms.mvp.model.LoginModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLogin
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/12.
 */
class LoginPresenter private constructor(iViewLogin: IViewLogin) {

    var iViewLogin: IViewLogin? = null
    var model: LoginModel? = null

    init {
        this.iViewLogin = iViewLogin
        this.model = LoginModel.getInstance()
    }

    fun userLogin(username: String, pwd: String) {
        model!!.userLogin(username, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<LoginResult>() {
                    override fun onSuccessZ(t: LoginResult?) {
                        iViewLogin!!.loginSucc(t!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLogin!!.loginFail()
                    }

                })
    }

    fun userLogin() {
        model!!.userLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<LoginResult>() {
                    override fun onSuccessZ(t: LoginResult?) {
                        iViewLogin!!.loginSucc(t!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLogin!!.loginFail()
                    }

                })
    }


    companion object {
        fun getInstance(iViewLogin: IViewLogin): LoginPresenter {
            return LoginPresenter(iViewLogin)
        }
    }


}