/*
 * Copyright (()()c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.data.NewArrivalEntity
import com.infinity.jerry.yyd_tms.mvp.model.ArrivalModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewArrival
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/26.
 */
class ArrivalPresenter private constructor(iViewArrival: IViewArrival) {
    var iViewArrival: IViewArrival? = null
    var model: ArrivalModel? = null

    init {
        this.iViewArrival = iViewArrival
        this.model = ArrivalModel.getInstance()
    }

    fun getNewArrival(offset: Int, number: Int, size: Int) {
        model!!.getNewArrival(offset, number, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<NewArrivalEntity>() {
                    override fun onSuccZ(entity: NewArrivalEntity?) {
                        iViewArrival!!.getNewArrivalSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewArrival!!.remoteArrivalError(IViewArrival.ARRIVAL_BILLS_ENSURE_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewArrival!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: NewArrivalEntity?) {
                    }


                })
    }

    fun ensureNewArrival(batchId: Int) {
        model!!.ensureNewArrival(batchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Object>() {
                    override fun onSuccessZ(t: Object?) {
                        iViewArrival!!.ensureArrivalSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewArrival!!.remoteArrivalError(IViewArrival.ARRIVAL_BILLS_ENSURE_ERROR)
                    }
                })
    }

    fun getArrivalBills(json: String) {
        model!!.getArrivalBills(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<MyBillListEntity>() {
                    override fun onOtherError(errEntity: MyBillListEntity?) {

                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewArrival!!.remoteArrivalError(IViewArrival.ARRIVAL_BILLS_ENSURE_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewArrival!!.onNetWorkError()
                    }

                    override fun onSuccZ(entity: MyBillListEntity?) {
                        iViewArrival!!.getArrivalBillsSucc(entity!!)
                    }
                })
    }


    fun ensureArrivalBills(json: String) {
        model!!.ensureArrivalBills(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewArrival!!.ensureArrivalSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewArrival!!.remoteArrivalError(IViewArrival.ARRIVAL_BILLS_ENSURE_ERROR)
                    }
                })
    }

    companion object {
        fun getInstance(iViewArrival: IViewArrival): ArrivalPresenter {
            return ArrivalPresenter(iViewArrival)
        }
    }
}