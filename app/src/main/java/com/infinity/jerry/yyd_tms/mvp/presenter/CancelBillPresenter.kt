/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.mvp.model.BillManagerModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCancelBill
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/23.
 */
class CancelBillPresenter private constructor(iViewCancelBill: IViewCancelBill) {

    var iViewCancelBill: IViewCancelBill? = null
    var model: BillManagerModel? = null

    init {
        this.iViewCancelBill = iViewCancelBill
        this.model = BillManagerModel.getInstance()
    }

    fun cancelBill(uuid: Long) {
        model!!.cancelBilling(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewCancelBill!!.cancelBillSucc()

                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewCancelBill!!.cancelBillError()

                    }

                })
    }


    companion object {
        fun getInstance(iViewCancelBill: IViewCancelBill): CancelBillPresenter {
            return CancelBillPresenter(iViewCancelBill)
        }
    }
}