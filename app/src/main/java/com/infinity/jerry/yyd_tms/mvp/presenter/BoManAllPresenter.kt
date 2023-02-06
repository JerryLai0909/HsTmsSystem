/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.BoManDetial
import com.infinity.jerry.yyd_tms.mvp.model.PointSetModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewManAll
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoManAllPresenter private constructor(iViewManAll: IViewManAll) {

    var iViewManAll: IViewManAll? = null
    var model: PointSetModel? = null

    init {
        this.iViewManAll = iViewManAll
        this.model = PointSetModel.getInstance()
    }

    fun getAllMan(json: String) {
        model!!.getAllMan(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<BoManDetial>>() {
                    override fun onSuccZ(entity: List<BoManDetial>?) {
                        iViewManAll!!.getAllManSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewManAll!!.remoteManError(IViewManAll.MAM_GET_ALL_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewManAll!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: List<BoManDetial>?) {
                    }

                })
    }

    fun deleteMan(json: String) {
        model!!.deletePointMan(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewManAll!!.deleteManSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewManAll!!.remoteManError(IViewManAll.MAN_DELETE_ERROR)
                    }

                })
    }


    companion object {
        fun getInstance(iViewManAll: IViewManAll): BoManAllPresenter {
            return BoManAllPresenter(iViewManAll)
        }
    }

}