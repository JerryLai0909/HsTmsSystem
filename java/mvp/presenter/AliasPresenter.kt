/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.mvp.model.AliasModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewAlias
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/18.
 */
class AliasPresenter private constructor(iViewAlias: IViewAlias) {

    var iViewAlias: IViewAlias? = null
    var model: AliasModel? = null

    init {
        this.iViewAlias = iViewAlias
        this.model = AliasModel.getInstance()
    }

    fun getCommonAlias() {
        model!!.getCommonAlias()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun editCommonAlias(json: String) {
        model!!.editCommonAlias(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun deleteCommonAlias(json: String) {
        model!!.deleteCOmmonAlias(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    companion object {
        fun getInstance(iViewAlias: IViewAlias): AliasPresenter {
            return AliasPresenter(iViewAlias)
        }
    }
}