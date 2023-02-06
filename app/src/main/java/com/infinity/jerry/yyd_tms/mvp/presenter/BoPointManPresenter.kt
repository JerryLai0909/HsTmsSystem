/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.BoPointAndManEntity
import com.infinity.jerry.yyd_tms.mvp.model.PointSetModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewPointMan
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.orhanobut.logger.Logger
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoPointManPresenter private constructor(iViewPointMan: IViewPointMan) {
    var iViewPointMan: IViewPointMan? = null
    var model: PointSetModel? = null

    init {
        this.iViewPointMan = iViewPointMan
        this.model = PointSetModel.getInstance()
    }

    fun getAllPointAndMan(json: String) {
        model!!.getAllPointAndMan(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<BoPointAndManEntity>() {
                    override fun onSuccZ(entity: BoPointAndManEntity?) {
                        iViewPointMan!!.getPointManSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewPointMan!!.getPointManError()
                        Logger.i(throwable.toString())
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewPointMan!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: BoPointAndManEntity?) {
                    }

                })
    }

    companion object {
        fun getInstance(iViewPointMan: IViewPointMan): BoPointManPresenter {
            return BoPointManPresenter(iViewPointMan)
        }
    }

}