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
import com.infinity.jerry.yyd_tms.data.BoManDetial
import com.infinity.jerry.yyd_tms.mvp.presenter.BoManUpdatePresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewManUpdate
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/21.
 */
class BoManEditActivity : BaseActivity(), IViewManUpdate {

    val edName: EditText by bindView<EditText>(R.id.edManName)
    val edPhone: EditText by bindView<EditText>(R.id.edManPhone)
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val tvEnsure: TextView by bindView<TextView>(R.id.tvEnsureEdit)
    var presenter: BoManUpdatePresenter? = null

    var requestEntity: BoManDetial? = null

    var company_uuid: Long? = null
    var uuid: Long? = null


    var dialog: Dialog? = null
    override fun initData() {
        requestEntity = BoManDetial()
        var bundle = intent.extras
        company_uuid = bundle.getLong("company_uuid")

        var entity = bundle.getSerializable("manEntity") as BoManDetial?
        edName.setText(entity?.user_name)
        edPhone.setText(entity?.phone_one)
        uuid = entity?.uuid
        requestEntity!!.company_uuid = company_uuid
        requestEntity!!.uuid = uuid
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_manedit
    }

    override fun initPresenter() {
        presenter = BoManUpdatePresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.man_edit))
        tvEnsure.setOnClickListener { ensureInfo() }


    }

    private fun ensureInfo() {
        var name = getEDString(edName)
        var phone = getEDString(edPhone)
        if (TextUtils.isEmpty(name)) {
            Toasty.info(this, getString(R.string.nameNotNull)).show()
            edName.requestFocus()
            return
        }
        if (TextUtils.isEmpty(phone)) {
            Toasty.info(this, getString(R.string.phoneNotNull)).show()
            edPhone.requestFocus()
            return
        }
        requestEntity!!.user_name = name
        requestEntity!!.phone_one = phone
        presenter!!.updatePointMan(ZGsonUtils.getInstance().getJsonString(requestEntity))
    }

    override fun remoteSucc() {
        Toasty.success(this, getString(R.string.remote_succ)).show()
        this.finish()
    }

    override fun remoteError() {
        Toasty.error(this, getString(R.string.errorNetWork))
    }
}