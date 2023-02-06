/*
 * Copyright (()c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.mvp.model.SignModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewSign
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/26.
 */
class SignPresenter private constructor(iViewSign: IViewSign) {

    var iViewSign: IViewSign? = null
    var model: SignModel? = null

    init {
        this.iViewSign = iViewSign
        this.model = SignModel.getInstance()
    }

    fun getSignbills(json: String) {
        model!!.getSignBills(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<MyBillListEntity>() {
                    override fun onOtherError(errEntity: MyBillListEntity?) {

                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewSign!!.remoteSignError(IViewSign.SIGN_BILLS_GET_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewSign!!.onNetWorkError()
                    }

                    override fun onSuccZ(entity: MyBillListEntity?) {

                        iViewSign!!.getSignBillsSucc(entity!!)
                    }
                })
    }

    fun ensureSignBill(json: String) {
        model!!.ensureSignBills(json).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onErrorZ(throwable: Throwable?) {
                        iViewSign!!.remoteSignError(IViewSign.SIGN_BILLS_ENSURE_ERROR)
                    }

                    override fun onSuccessZ(t: Any?) {
                        iViewSign!!.ensureSignBillsSucc()
                    }
                })
    }


    companion object {
        fun getInstance(iViewSign: IViewSign): SignPresenter {
            return SignPresenter(iViewSign)
        }
    }
}