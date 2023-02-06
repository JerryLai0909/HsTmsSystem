/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CooperPointEntity
import com.infinity.jerry.yyd_tms.data.request_entity.TransPageEntity
import com.infinity.jerry.yyd_tms.mvp.model.TransModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewTrans
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/7/24.
 */
class TranPresenter private constructor(iViewTrans: IViewTrans) {

    var iViewTrans: IViewTrans? = null
    var model: TransModel? = null

    init {
        this.iViewTrans = iViewTrans
        this.model = TransModel.getInstance()
    }

    fun searchTrans(json: String) {
        model!!.getTransList(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<TransPageEntity>() {
                    override fun onSuccZ(entity: TransPageEntity?) {
                        iViewTrans!!.searchTransSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewTrans!!.transError(IViewTrans.TRANS_GET_ERRLR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewTrans!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: TransPageEntity?) {
                    }

                })
    }

    fun addTrans(json: String) {
        model!!.addTrans(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewTrans!!.addTransSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewTrans!!.transError(IViewTrans.TRANS_ADD_ERROR)
                    }

                })
    }

    fun searchPoint(json: String, iViewShit: OnPointSearch) {
        model!!.searchPoint(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<CooperPointEntity>() {
                    override fun onSuccessZ(t: CooperPointEntity?) {
                        if (t == null) {
                            return
                        }
                        iViewShit.getPointSucc(t)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewShit.getPointError()
                    }

                })

    }

    interface OnPointSearch {
        fun getPointSucc(entity: CooperPointEntity)
        fun getPointError()
    }

    companion object {
        fun getInstance(iViewTrans: IViewTrans): TranPresenter {
            return TranPresenter(iViewTrans)
        }
    }

}