/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.BoPointDetail
import com.infinity.jerry.yyd_tms.mvp.model.PointSetModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewPointEdit
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/20.
 */
class BoPointEditPresenter private constructor(iViewPointEdit: IViewPointEdit) {


    var iViewPointEdit: IViewPointEdit? = null
    var model: PointSetModel? = null

    init {
        this.iViewPointEdit = iViewPointEdit
        this.model = PointSetModel.getInstance()
    }

    fun getPointDetail(json: String) {
        model!!.getPointDetail(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<BoPointDetail>() {
                    override fun onSuccessZ(entity: BoPointDetail?) {
                        iViewPointEdit!!.getDetailSucc(entity!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewPointEdit!!.remoteError(IViewPointEdit.POINT_DETAIL_ERROR)
                    }

                })
    }

    fun updatePoint(json: String) {
        model!!.updatePointInfo(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewPointEdit!!.remoteSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewPointEdit!!.remoteError(IViewPointEdit.POINT_REMOTE_ERROR)
                    }

                })
    }

    companion object {
        fun getInstance(iViewPointEdit: IViewPointEdit): BoPointEditPresenter {
            return BoPointEditPresenter(iViewPointEdit)
        }
    }
}