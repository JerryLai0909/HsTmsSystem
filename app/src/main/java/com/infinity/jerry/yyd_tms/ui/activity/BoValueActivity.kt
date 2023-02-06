/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.RateEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.RatePresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewRate
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZDoubleFormat
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/7/20.
 */
class BoValueActivity : BaseActivity(), IViewRate {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edValue: EditText by bindView<EditText>(R.id.edValue)
//    val edCollec: EditText by bindView<EditText>(R.id.edCollect)
    var presenter: RatePresenter? = null
    var dialog: Dialog? = null
    val tvEnsure : TextView by bindView<TextView>(R.id.tvEnsure)

    var request : RateEntity ? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this,getString(R.string.loading),false)
        request = RateEntity()
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_value
    }

    override fun initPresenter() {
        presenter = RatePresenter.getInstance(this)
        dialog?.show()
        presenter!!.getRate(AppUserToken.getInstance().result.logistics_uuid)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.rate_setting))
        tvEnsure.setOnClickListener { ensureRate() }
    }

    private fun ensureRate() {
        if (TextUtils.isEmpty(getEDString(edValue))) {
            request!!.insured_rate = 0.0
        }else{
            request!!.insured_rate = getEDString(edValue).toDouble() / 1000
        }
//        if (TextUtils.isEmpty(getEDString(edCollec))) {
//            request!!.commission_rate = 0.0
//        }else{
//            request!!.commission_rate  = getEDString(edCollec).toDouble() / 1000
//        }
        request!!.logistics_uuid = AppUserToken.getInstance().result.logistics_uuid

        presenter!!.setRate(ZGsonUtils.getInstance().getJsonString(request))
        dialog!!.show()
    }


    override fun setRateSucc() {
        dialog!!.dismiss()
        Toasty.success(this, "修改费率成功").show()
        this.finish()
    }

    override fun setRateError() {
        dialog!!.dismiss()
        Toasty.error(this, "修改费率失败").show()
    }

    override fun getRateSucc(rate: Double) {
        dialog!!.dismiss()
        edValue.setText(ZDoubleFormat.zFormat((rate*1000).toString()))
    }

    override fun getRateError() {
        dialog!!.dismiss()
        Toasty.error(this,getString(R.string.valuerate_error)).show()
    }
}