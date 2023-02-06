/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CooperEntity
import com.infinity.jerry.yyd_tms.mvp.model.CooperModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCooperMain
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoCooperMainPresenter private constructor(iViewCooperMain: IViewCooperMain) {
    var iViewCooperMain: IViewCooperMain? = null
    var model: CooperModel? = null

    init {
        this.iViewCooperMain = iViewCooperMain
        this.model = CooperModel.getInstance()
    }


    fun getCooperaters(json: String) {
        model!!.getCooperaters(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<CooperEntity>() {
                    override fun onSuccZ(entity: CooperEntity?) {
                        iViewCooperMain!!.getCoopersSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewCooperMain!!.remoteError(IViewCooperMain.COOPER_GET_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewCooperMain!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: CooperEntity?) {
                    }

                })
    }

    fun updateCooperState(json: String) {
        model!!.updateCooperState(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewCooperMain!!.changeStateSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewCooperMain!!.remoteError(IViewCooperMain.COOPER_REMOTE_ERROR)
                    }

                })
    }


    companion object {
        fun getInstance(iViewCooperMain: IViewCooperMain): BoCooperMainPresenter {
            return BoCooperMainPresenter(iViewCooperMain)
        }


    }

}
