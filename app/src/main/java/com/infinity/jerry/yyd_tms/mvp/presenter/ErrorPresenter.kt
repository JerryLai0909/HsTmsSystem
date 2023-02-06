package com.infinity.jerry.yyd_tms.mvp.presenter

import com.infinity.jerry.yyd_tms.data.ErrorBillingEntity
import com.infinity.jerry.yyd_tms.mvp.model.ErrorModel
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewErrorRemote
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZNetSubscriber
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jerry on 2017/7/17.
 */
class ErrorPresenter private constructor(iViewErrorRemote: IViewErrorRemote) {

    var iViewErrorRemote: IViewErrorRemote? = null
    var model: ErrorModel? = null

    init {
        this.iViewErrorRemote = iViewErrorRemote
        this.model = ErrorModel.getInstance()
    }

    fun editError(json :String){
        model!!.addError(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZResultSubscriber<Any>() {
                    override fun onSuccessZ(t: Any?) {
                        iViewErrorRemote!!.addErorrSucc()
                    }

                    override fun onErrorZ(throwable: Throwable?) {
                        iViewErrorRemote!!.errorRemoteError(IViewErrorRemote.ERROR_EDIT_ERROR)
                    }

                })

    }

    fun searchError(json: String) {
        model!!.searchError(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ZNetSubscriber<ErrorBillingEntity>() {
                    override fun onSuccZ(entity: ErrorBillingEntity?) {
                        if (entity == null) {
                            iViewErrorRemote!!.errorRemoteError(IViewErrorRemote.ERROR_SEARCH_ERROR)
                        }else{
                            iViewErrorRemote!!.searchErrorSucc(entity!!)
                        }
                    }

                    override fun onErrorZ(throwable: Throwable?, errString: String?) {
                        iViewErrorRemote!!.errorRemoteError(IViewErrorRemote.ERROR_SEARCH_ERROR)
                    }

                    override fun onNetErrorZ(throwable: Throwable?) {
                        iViewErrorRemote!!.onNetWorkError()
                    }

                    override fun onOtherError(errEntity: ErrorBillingEntity?) {
                    }

                })
    }

    companion object {
        fun getInstance(iViewErrorRemote: IViewErrorRemote): ErrorPresenter {
            return ErrorPresenter(iViewErrorRemote)
        }
    }

}