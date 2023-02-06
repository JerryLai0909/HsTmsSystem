/*
 * Copyright (()c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.mvp.model.LoadRecordModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadRecord
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/7/10.
 */
class LoadRecordPresenter private constructor(iViewLoadRecord: IViewLoadRecord) {

    var iViewLoadRecord: IViewLoadRecord? = null
    var model: LoadRecordModel? = null

    init {
        this.iViewLoadRecord = iViewLoadRecord
        this.model = LoadRecordModel.getInstance()
    }

    fun getRecordLists(json: String) {
        model!!.getRecordLists(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<MyBillListEntity>() {
                    override fun onSuccZ(entity: MyBillListEntity?) {
                        iViewLoadRecord!!.getRecordListSucc(entity!!.pageList)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewLoadRecord!!.getrecordListError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewLoadRecord!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: MyBillListEntity?) {
                    }

                })
    }

    fun getNewRecordLists() {
        model!!.getNewRecordList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<NewRecordsEntity>>() {
                    override fun onSuccZ(entity: List<NewRecordsEntity>?) {
                        iViewLoadRecord!!.getNewRecordSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewLoadRecord!!.getrecordListError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewLoadRecord!!.getrecordListError()
                    }

                    override fun onOtherError(errEntity: List<NewRecordsEntity>?) {
                        iViewLoadRecord!!.getrecordListError()
                    }
                })

    }

    fun getLoadByBatchId(batchId: Int) {
        model!!.getLoadByBatchId(batchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<List<BillListTempShit>>() {
                    override fun onSuccessZ(t: List<BillListTempShit>?) {
                        iViewLoadRecord!!.loadrecordsuxcc(t!!)

                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLoadRecord!!.getrecordListError()
                    }
                })
    }

    fun getLoadByBatchIdX(batchId: Int) {
        model!!.getLoadByBatchIdX(batchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<List<BillingDrawMain>>() {
                    override fun onSuccessZ(t: List<BillingDrawMain>?) {
                        iViewLoadRecord!!.getNewloadByBatchIdSucc(t!!)

                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLoadRecord!!.getrecordListError()
                    }
                })
    }

    fun appJiuCuo(entity :TempIdsEntity){
        model!!.appJiuCuo(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewLoadRecord!!.jiuCuoSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLoadRecord!!.getrecordListError()
                    }

                })

    }

    companion object {
        fun getInstance(iViewLoadRecord: IViewLoadRecord): LoadRecordPresenter {
            return LoadRecordPresenter(iViewLoadRecord)
        }
    }
}