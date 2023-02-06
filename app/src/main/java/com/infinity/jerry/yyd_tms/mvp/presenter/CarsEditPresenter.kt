/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.mvp.model.CarsModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCarsEdit
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/26.
 */
class CarsEditPresenter private constructor(iViewCarsEdit: IViewCarsEdit) {

    var iViewCarsEdit: IViewCarsEdit? = null
    var model: CarsModel? = null

    init {
        this.iViewCarsEdit = iViewCarsEdit
        this.model = CarsModel.getInstance()
    }

    fun updateCar(json: String) {
        model!!.editCommonCar(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewCarsEdit!!.updateCarsSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewCarsEdit!!.updateCarsError()
                    }

                })
    }

    companion object {
        fun getInstance(iViewCarsEdit: IViewCarsEdit): CarsEditPresenter {
            return CarsEditPresenter(iViewCarsEdit)
        }
    }
}