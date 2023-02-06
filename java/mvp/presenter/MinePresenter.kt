/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.mvp.model.MineModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewMine
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/19.
 */
class MinePresenter private constructor(iViewMine: IViewMine) {
    var iViewMine: IViewMine? = null
    var model: MineModel? = null

    init {
        this.iViewMine = iViewMine
        this.model = MineModel.getInstance()
    }

    fun updatePwd(pwd: String, newpwd: String) {
        model!!.udpatePwd(pwd, newpwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewMine!!.updatePwdSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewMine!!.settingError(IViewMine.MINE_UPDATEPWD_ERROR)
                    }

                })
    }


    fun quitLogin() {
        model!!.quitLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewMine!!.loginOutSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewMine!!.settingError(IViewMine.MINE_QUITLOGIN)
                    }
                })
    }


    companion object {
        fun getInstance(iViewMine: IViewMine): MinePresenter {
            return MinePresenter(iViewMine)
        }
    }
}