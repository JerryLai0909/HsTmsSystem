/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.Declaration
import com.infinity.jerry.yyd_tms.mvp.model.DeclarsModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewDeclaration
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/6/15.
 */
class DeclarsPresenter private constructor(iViewDeclaration: IViewDeclaration) {

    var iViewDeclar: IViewDeclaration? = null
    var model: DeclarsModel? = null

    init {
        this.iViewDeclar = iViewDeclaration
        this.model = DeclarsModel.getInstance()
    }

    fun getDeclars() {
        model!!.getDeclars()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<List<Declaration>>() {


                    override fun onSuccessZ(t: List<Declaration>?) {
                        iViewDeclar!!.getDeclarsSucc(t!!)
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewDeclar!!.remoteDelarsError(IViewDeclaration.DECLARS_GET_ERROR)
                    }


                })
    }

    fun deleteDeclars(json: String) {
        model!!.deleDeclars(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {

                    override fun onSuccessZ(t: Any?) {
                        iViewDeclar!!.deleDeclarSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewDeclar!!.remoteDelarsError(IViewDeclaration.DECLARS_DELETE_ERROR)
                    }

                })
    }

    fun eidtDeclars(json: String) {
        model!!.editDeclars(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {

                    override fun onSuccessZ(t: Any?) {
                        iViewDeclar!!.editDeclarSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewDeclar!!.remoteDelarsError(IViewDeclaration.DECLARS_EDIT_ERROR)
                    }

                })

    }


    companion object {
        fun getIsntance(iViewDeclaration: IViewDeclaration): DeclarsPresenter {
            return DeclarsPresenter(iViewDeclaration)
        }
    }


}