/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.mvp.model.PointSetModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewRate
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZRateSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/7/28.
 */
class RatePresenter private constructor(iViewRate: IViewRate) {
    var iViewRate: IViewRate? = null
    var model: PointSetModel? = null

    init {
        this.iViewRate = iViewRate
        this.model = PointSetModel.getInstance()
    }

    fun setRate(json: String) {
        model!!.setRate(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewRate!!.setRateSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewRate!!.setRateError()
                    }
                })
    }

    fun getRate(uuid: Long) {
        model!!.getRate(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZRateSubscriber() {
                    override fun onSuccessZ(t: Double) {
                        iViewRate!!.getRateSucc(t)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewRate!!.getRateError()
                    }
                })
    }


    companion object {
        fun getInstance(iViewRate: IViewRate): RatePresenter {
            return RatePresenter(iViewRate)
        }
    }
}



