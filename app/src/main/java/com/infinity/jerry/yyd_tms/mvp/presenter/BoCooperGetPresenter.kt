/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.CooperCompanyEntity
import com.infinity.jerry.yyd_tms.mvp.model.CooperModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCooperCompany
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoCooperGetPresenter private constructor(iViewCooperCompany: IViewCooperCompany) {
    var iViewCooperCompany: IViewCooperCompany? = null
    var model: CooperModel? = null

    init {
        this.iViewCooperCompany = iViewCooperCompany
        this.model = CooperModel.getInstance()
    }

    fun searCompany(json: String) {
        model!!.searchCompany(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<CooperCompanyEntity>() {
                    override fun onSuccZ(entity: CooperCompanyEntity?) {
                        iViewCooperCompany!!.getCompanySucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewCooperCompany!!.getCompanError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewCooperCompany!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: CooperCompanyEntity?) {
                    }

                })
    }

    companion object {
        fun getInstance(iViewCooperCompany: IViewCooperCompany): BoCooperGetPresenter {
            return BoCooperGetPresenter(iViewCooperCompany)
        }
    }
}