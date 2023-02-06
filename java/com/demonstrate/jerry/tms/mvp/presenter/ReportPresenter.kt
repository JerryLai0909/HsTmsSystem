/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.report.ReportEntity
import com.infinity.jerry.yyd_tms.data.report.ReportPointEntity
import com.infinity.jerry.yyd_tms.mvp.model.ReportModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewReport
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/7/5.
 */
class ReportPresenter private constructor(iViewReport: IViewReport) {

    var iViewReport: IViewReport? = null
    var model: ReportModel? = null

    init {
        this.iViewReport = iViewReport
        this.model = ReportModel.getInstance()
    }

    fun getMonthList(json: String) {
        model!!.getMonthBills(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<MutableList<ReportEntity>>() {
                    override fun onSuccZ(entity: MutableList<ReportEntity>?) {
                        iViewReport!!.getBillsSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewReport!!.getBillsError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewReport!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: MutableList<ReportEntity>?) {
                    }

                })
    }

    fun getDayList(json: String) {
        model!!.getDayBills(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<MutableList<ReportEntity>>() {
                    override fun onSuccZ(entity: MutableList<ReportEntity>?) {
                        iViewReport!!.getBillsSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewReport!!.getBillsError()

                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewReport!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: MutableList<ReportEntity>?) {
                    }

                })
    }


    fun getPointList(dataType: String, boardType: String) {
        model!!.getPointBillList(dataType, boardType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<ReportPointEntity>() {
                    override fun onOtherError(errEntity: ReportPointEntity?) {

                    }

                    override fun onSuccZ(entity: ReportPointEntity?) {
                        iViewReport!!.getPointListSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewReport!!.getBillsError()

                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewReport!!.onNetWorkError()
                    }


                })
    }

    fun getPointDetails(id: String, dataType: String, boardType: String) {
        model!!.getPointBillDetails(id, dataType, boardType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<ReportPointEntity>() {
                    override fun onOtherError(errEntity: ReportPointEntity?) {

                    }

                    override fun onSuccZ(entity: ReportPointEntity?) {
                        iViewReport!!.getPointListSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewReport!!.getBillsError()

                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewReport!!.onNetWorkError()
                    }


                })
    }

    companion object {
        fun getInstance(iViewReport: IViewReport): ReportPresenter {
            return ReportPresenter(iViewReport)
        }
    }
}