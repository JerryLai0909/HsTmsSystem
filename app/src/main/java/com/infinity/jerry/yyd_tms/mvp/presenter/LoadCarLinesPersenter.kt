/*
 * Copyright (()c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.LoadCarLineEntity
import com.infinity.jerry.yyd_tms.data.TempIdsEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.LoadListEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap
import com.infinity.jerry.yyd_tms.mvp.model.LoadCarModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadCarLine
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/19.
 */
class LoadCarLinesPersenter private constructor(iViewLoadCarLine: IViewLoadCarLine) {

    var iViewLoadCarLine: IViewLoadCarLine? = null
    var model: LoadCarModel? = null

    init {
        this.iViewLoadCarLine = iViewLoadCarLine
        this.model = LoadCarModel.getInstance()
    }

    fun getLoadCarLines() {
        model!!.getLoadLines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<LoadCarLineEntity>>() {
                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewLoadCarLine!!.getLoadLinesError()
                    }

                    override fun onSuccZ(entity: List<LoadCarLineEntity>?) {
                        if (entity != null) {
                            iViewLoadCarLine!!.getLoadLinesSucc(entity!!)
                        } else {
                            iViewLoadCarLine!!.getLoadLinesError()
                        }
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewLoadCarLine!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: List<LoadCarLineEntity>?) {
                    }
                })
    }

    fun getLoadList() {
        model!!.getLoadList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<List<LoadListEntity>>() {
                    override fun onSuccZ(entity: List<LoadListEntity>?) {
                        if (entity != null) {
                            iViewLoadCarLine!!.getLoadListSucc(entity!!)
                        } else {
                            iViewLoadCarLine!!.getLoadLinesError()
                        }
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewLoadCarLine!!.getLoadLinesError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewLoadCarLine!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: List<LoadListEntity>?) {
                    }

                })
    }


    fun getEndPointDetails(id: Int, item: LoadListEntity) {
        model!!.getEndPointDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<NewListWrap>() {
                    override fun onSuccZ(entity: NewListWrap?) {
                        iViewLoadCarLine!!.getEndPointSucc(entity!!.content, item)
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewLoadCarLine!!.getLoadLinesError()
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewLoadCarLine!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: NewListWrap?) {
                    }
                })

    }

    fun buZhuang(batchId: String, entity: TempIdsEntity) {
        model!!.buZhuang(batchId, entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Object>() {
                    override fun onSuccessZ(t: Object?) {
                        iViewLoadCarLine!!.buZhuangSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewLoadCarLine!!.getLoadLinesError()
                    }

                })
    }

    companion object {
        fun getInstance(iViewLoadCarLine: IViewLoadCarLine): LoadCarLinesPersenter {
            return LoadCarLinesPersenter(iViewLoadCarLine)
        }
    }
}