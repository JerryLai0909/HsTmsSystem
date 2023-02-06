/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CarsCommonEntity
import com.infinity.jerry.yyd_tms.mvp.model.CarsModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCars
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/18.
 */
class CarsPresenter private constructor(iViewCars: IViewCars) {

    var iViewCars: IViewCars? = null
    var model: CarsModel? = null

    init {
        this.iViewCars = iViewCars
        this.model = CarsModel.getInstance()
    }

    fun getCommonCars() {
        model!!.getCommonCars()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<CarsCommonEntity>>() {
                    override fun onSuccZ(entity: List<CarsCommonEntity>?) {
                        iViewCars!!.getCarsSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewCars!!.carsRemoteError(IViewCars.CARS_GET_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewCars!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: List<CarsCommonEntity>?) {
                    }

                })
    }

    fun deleteCommonCar(json: String) {
        model!!.deleteCommonCar(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewCars!!.deleteCarSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewCars!!.carsRemoteError(IViewCars.CARS_DELETE_ERROR)
                    }

                })
    }


    companion object {
        fun getInstance(iViewCars: IViewCars): CarsPresenter {
            return CarsPresenter(iViewCars)
        }
    }
}