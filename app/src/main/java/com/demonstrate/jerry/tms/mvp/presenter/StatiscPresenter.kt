/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.Declaration
import com.infinity.jerry.yyd_tms.mvp.model.StatiscModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewStatisc
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/27.
 */
class StatiscPresenter private constructor(iViewStatisc: IViewStatisc) {

    var iViewStatisc: IViewStatisc? = null
    var model: StatiscModel? = null

    init {
        this.iViewStatisc = iViewStatisc
        this.model = StatiscModel.getInstance()
    }

    fun getStatiscBill() {
        model!!.getStatisBill()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<BillingDrawMain>() {
                    override fun onSuccessZ(t: BillingDrawMain?) {
                        iViewStatisc!!.getStatiscBillSucc(t!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewStatisc!!.getStatiscBillError()
                    }
                })
    }

    fun getDeclars() {
        model!!.getDeclars()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<List<Declaration>>() {
                    override fun onSuccessZ(t: List<Declaration>?) {
                        iViewStatisc!!.getDeclarsSucc(t!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewStatisc!!.getDeclarsError()
                    }

                })
    }

    companion object {
        fun getInstance(iViewStatisc: IViewStatisc): StatiscPresenter {
            return StatiscPresenter(iViewStatisc)
        }
    }
}