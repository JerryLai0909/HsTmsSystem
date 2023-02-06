/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.mvp.model.LoadCarModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadCar
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by jerry on 2017/6/26.
 */
class LoadCarPresenter private constructor(iViewLoadCar: IViewLoadCar) {
    var iViewLoadCar: IViewLoadCar? = null
    var model: LoadCarModel? = null

    init {
        this.iViewLoadCar = iViewLoadCar
        this.model = LoadCarModel.getInstance()
    }

    fun loadCar(json: String) {
        model!!.loadCar(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<List<BillingDrawMain>>() {
                    override fun onSuccessZ(t: List<BillingDrawMain>?) {
                        iViewLoadCar!!.loadCarSucc(t!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLoadCar!!.loadCarError()
                    }

                })
    }

    companion object {
        fun getInstance(iViewLoadCar: IViewLoadCar): LoadCarPresenter {
            return LoadCarPresenter(iViewLoadCar)
        }
    }
}