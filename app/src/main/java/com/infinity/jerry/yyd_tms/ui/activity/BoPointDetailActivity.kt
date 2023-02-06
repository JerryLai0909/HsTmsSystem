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
import com.infinity.jerry.yyd_tms.data.BoPointDetail
import com.infinity.jerry.yyd_tms.mvp.presenter.BoPointEditPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewPointEdit
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/21.
 */
class BoPointDetailActivity : BaseActivity(), IViewPointEdit {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edName: EditText by bindView<EditText>(R.id.edPointName)
    val edCell: EditText by bindView<EditText>(R.id.edPointCell)
    val edPhone: EditText by bindView<EditText>(R.id.edPointPhone)
    val edAddr: EditText by bindView<EditText>(R.id.edPointAddr)
    val tvEnsure: TextView by bindView<TextView>(R.id.tvEnsurePoint)
    val edOwnerName: EditText by bindView<EditText>(R.id.edOwnerName)

    var dialog: Dialog? = null
    var presenter: BoPointEditPresenter? = null

    var requestEntity: BoPointDetail? = null
    var uuid: Long? = null


    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        uuid = intent.extras?.getLong("pointUuid")
        requestEntity = BoPointDetail()
        requestEntity!!.uuid = uuid
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_point_detail
    }

    override fun initPresenter() {
        presenter = BoPointEditPresenter.getInstance(this)
        if (uuid != null) {
            var entity = BoPointDetail()
            entity.uuid = uuid
            dialog!!.show()
            presenter!!.getPointDetail(ZGsonUtils.getInstance().getJsonString(entity))
        }
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.point_edit))
        tvEnsure.setOnClickListener { enSureInfo() }
    }


    private fun initWindow(entity: BoPointDetail) {
        edName.setText(entity?.point_name)
        edOwnerName.setText(entity?.point_owner)
        edPhone.setText(entity?.point_owner_phone_o)
        edCell.setText(entity?.point_phone)
        edAddr.setText(entity?.point_addr)
    }

    private fun enSureInfo() {
        if (TextUtils.isEmpty(getEDString(edName))) {
            Toasty.info(this, getString(R.string.pointNameNotNull)).show()
            return
        }
        requestEntity!!.point_name = getEDStringOrNull(edName)
        requestEntity!!.point_owner = getEDStringOrNull(edOwnerName)
        requestEntity!!.point_phone = getEDStringOrNull(edCell)
        requestEntity!!.point_owner_phone_o = getEDStringOrNull(edPhone)
        requestEntity!!.point_addr = getEDStringOrNull(edAddr)

        presenter!!.updatePoint(ZGsonUtils.getInstance().getJsonString(requestEntity))
    }

    override fun getDetailSucc(entity: BoPointDetail) {
        dialog!!.dismiss()
        initWindow(entity)
    }

    override fun remoteSucc() {
        Toasty.success(this, "编辑人员成功").show()
        this.finish()

    }

    override fun remoteError(type: Int) {
        dialog!!.dismiss()
        when (type) {
            IViewPointEdit.POINT_DETAIL_ERROR -> Toasty.error(this, getString(R.string.point_detail_error)).show()
            IViewPointEdit.POINT_REMOTE_ERROR -> Toasty.error(this, getString(R.string.point_edit_error)).show()
        }
    }
}