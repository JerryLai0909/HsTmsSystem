/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CommonGoods
import com.infinity.jerry.yyd_tms.mvp.model.GoodsModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewGoods
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import com.orhanobut.logger.Logger
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/15.
 */
class GoodsPresenter private constructor(iViewGoods: IViewGoods) {

    var iViewGoods: IViewGoods? = null
    var model: GoodsModel? = null

    init {
        this.iViewGoods = iViewGoods
        model = GoodsModel.getInstance()
    }

    fun getGoods() {
        model!!.getGoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<CommonGoods>>() {
                    override fun onSuccZ(entity: List<CommonGoods>?) {
                        Logger.i(entity.toString())
                        iViewGoods!!.getGoodsSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        Logger.i(throwable.toString())
                        iViewGoods!!.remoteGoodError(IViewGoods.GOOD_GET_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewGoods!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: List<CommonGoods>?) {
                    }
                })
    }

    fun addGoods(json: String) {
        model!!.addGoods(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewGoods!!.addGoodsSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewGoods!!.remoteGoodError(IViewGoods.GOOD_ADD_ERROR)
                    }

                })


    }

    fun deleteGoods(json: String) {
        model!!.deleteGoods(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewGoods!!.deleteGoodSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewGoods!!.remoteGoodError(IViewGoods.GOOD_DELETE_ERROR)
                    }

                })
    }


    companion object {
        fun getInstance(iViewGoods: IViewGoods): GoodsPresenter {
            return GoodsPresenter(iViewGoods)
        }
    }


}