/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CommonPacks
import com.infinity.jerry.yyd_tms.mvp.model.PacksModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewPacks
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.orhanobut.logger.Logger
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/15.
 */
class PacksPresenter private constructor(iViewPacks: IViewPacks) {

    var iViewPacks: IViewPacks? = null
    var model: PacksModel? = null

    init {
        this.iViewPacks = iViewPacks
        this.model = PacksModel.getInstance()
    }

    fun getPacks() {
        model!!.getAllPacks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<CommonPacks>>() {
                    override fun onSuccZ(entity: List<CommonPacks>?) {
                        Logger.i(entity.toString())
                        iViewPacks!!.getPacksSuccs(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        Logger.i(throwable.toString())
                        iViewPacks!!.getPacksError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewPacks!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: List<CommonPacks>?) {
                    }

                })

    }

    companion object {
        fun getInstance(iViewPacks: IViewPacks): PacksPresenter {
            return PacksPresenter(iViewPacks)
        }
    }
}