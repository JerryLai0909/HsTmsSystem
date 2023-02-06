/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CooperPointEntity
import com.infinity.jerry.yyd_tms.mvp.model.CooperModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCooperAddPoint
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoCooAddPointPresenter private constructor(iViewCooperAddPoint: IViewCooperAddPoint) {
    var iViewCooperAddPoint: IViewCooperAddPoint? = null
    var model: CooperModel? = null

    init {
        this.iViewCooperAddPoint = iViewCooperAddPoint
        this.model = CooperModel.getInstance()
    }

    fun searchPoint(json: String) {
        model!!.searchPoint(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<CooperPointEntity>() {
                    override fun onSuccZ(entity: CooperPointEntity?) {
                        iViewCooperAddPoint!!.getPointsSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewCooperAddPoint!!.remoteError(IViewCooperAddPoint.GETPOINTS_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewCooperAddPoint!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: CooperPointEntity?) {
                    }

                })
    }

    fun addCooper(json: String) {
        model!!.addCooper(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewCooperAddPoint!!.addCooperSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewCooperAddPoint!!.remoteError(IViewCooperAddPoint.ADDCOOPER_ERROR)
                    }

                })
    }

    companion object {
        fun getInstance(iViewCooperAddPoint: IViewCooperAddPoint): BoCooAddPointPresenter {
            return BoCooAddPointPresenter(iViewCooperAddPoint)
        }
    }
}