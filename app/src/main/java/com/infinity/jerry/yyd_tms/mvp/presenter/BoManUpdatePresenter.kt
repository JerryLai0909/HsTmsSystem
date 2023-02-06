/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.mvp.model.PointSetModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewManUpdate
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoManUpdatePresenter private constructor(iViewManUpdate: IViewManUpdate) {

    var iViewManUpdate: IViewManUpdate? = null
    var model: PointSetModel? = null

    init {
        this.iViewManUpdate = iViewManUpdate
        this.model = PointSetModel.getInstance()
    }

    fun updatePointMan(json: String) {
        model!!.updatePointMan(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewManUpdate!!.remoteSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewManUpdate!!.remoteError()

                    }

                })
    }

    companion object {
        fun getInstance(iViewManUpdate: IViewManUpdate): BoManUpdatePresenter {
            return BoManUpdatePresenter(iViewManUpdate)
        }
    }
}