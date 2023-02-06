/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.mvp.model.BillDrawModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewBillDraw
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import com.orhanobut.logger.Logger
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/19.
 */
class BillDrawPresenter private constructor(iViewBillDraw: IViewBillDraw) {

    var iViewBillDraw: IViewBillDraw? = null
    var model: BillDrawModel? = null

    init {
        this.iViewBillDraw = iViewBillDraw
        model = BillDrawModel.getInstance()
    }

    fun getBarCode (id :String) {
        model!!.getBarCode(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<String>() {
                    override fun onSuccessZ(t: String?) {
                        Logger.i(t!!.toString())
                        iViewBillDraw!!.drawBillBarCodeSucc(t!!)

                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        Logger.i(throwable.toString())
                        iViewBillDraw!!.drawBillError()
                    }

                })
    }

    fun ensureDrawBilling(json: String) {
        model!!.drawBilling(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<BillingDrawMain>() {
                    override fun onSuccessZ(t: BillingDrawMain?) {
                        Logger.i(t!!.toString())
                        iViewBillDraw!!.drawBillSucc(t!!)

                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        Logger.i(throwable.toString())
                        iViewBillDraw!!.drawBillError()
                    }

                })
    }

    companion object {
        fun getInstance(iViewBillDraw: IViewBillDraw): BillDrawPresenter {
            return BillDrawPresenter(iViewBillDraw)
        }
    }
}