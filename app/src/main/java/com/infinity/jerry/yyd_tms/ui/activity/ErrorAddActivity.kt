/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.ErrorBillingEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.ErrorPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewErrorRemote
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/7/17.
 * 多货 少货 拒收 货损 其它
 */
class ErrorAddActivity : BaseActivity(), IViewErrorRemote {

    val tv1: TextView by bindView<TextView>(R.id.tv1)
    val tv2: TextView by bindView<TextView>(R.id.tv2)
    val tv3: TextView by bindView<TextView>(R.id.tv3)

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edContent: EditText by bindView<EditText>(R.id.edContent)
    val tvCount: TextView by bindView<TextView>(R.id.tvCount)
    val tvEnusre: TextView by bindView<TextView>(R.id.tvEnsure)

    val spType : Spinner by bindView<Spinner>(R.id.spType)

    var dialog: Dialog? = null
    var presenter: ErrorPresenter? = null

    var requestEntity: ErrorBillingEntity.ErrorEntity = ErrorBillingEntity.ErrorEntity()
    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        val uuid = intent.extras?.getLong("errorUuid")
        if (uuid != null) {
            requestEntity.billing_uuid = uuid
        }
        val entity = intent.extras?.getSerializable("errorEntity") as ErrorBillingEntity.ErrorEntity?
        if (entity != null) {
            requestEntity.uuid = entity.uuid
            edContent.setText(entity.unusual_des)
            tvEnusre.text = "确定修改"
        }
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_error_add
    }

    override fun initPresenter() {
        presenter = ErrorPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.add_error))
        edContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tvCount.setText(s?.toString()!!.length.toString() + "/100")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        tvEnusre.setOnClickListener { showEnsureDialog() }
        spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                requestEntity.unusual_type = resources.getStringArray(R.array.unusal_type)[0]
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                requestEntity.unusual_type = resources.getStringArray(R.array.unusal_type)[position]
            }
        }
    }

    private fun showEnsureDialog() {
        val alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.ask_ensure_error))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    ensureError()
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    private fun ensureError() {
        requestEntity.unusual_des = getEDStringOrNull(edContent)
        requestEntity.processor = AppUserToken.getInstance().result.uuid
        presenter!!.editError(ZGsonUtils.getInstance().getJsonString(requestEntity))
    }

    override fun onNetWorkError() {
        //no
    }

    override fun errorRemoteError(type: Int) {
        Toasty.error(this, getString(R.string.error_edit_error)).show()
    }

    override fun addErorrSucc() {
        Toasty.success(this, getString(R.string.error_edit_succ)).show()
        this.finish()
    }

    override fun searchErrorSucc(entity: ErrorBillingEntity) {
        //no
    }


}