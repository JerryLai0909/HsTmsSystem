/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CooperCompanyEntity
import com.infinity.jerry.yyd_tms.data.CooperPointEntity
import com.infinity.jerry.yyd_tms.data.TransEntity
import com.infinity.jerry.yyd_tms.data.request_entity.TransPageEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.TranPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewTrans
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/7/24.
 */
class TransAddActivity : BaseActivity(), IViewTrans {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val tvOuter: TextView by bindView<TextView>(R.id.tvOutter)
    val tvInner: TextView by bindView<TextView>(R.id.tvInner)

    val llOuter: LinearLayout by bindView<LinearLayout>(R.id.ll_outter)
    val edName1: EditText by bindView<EditText>(R.id.edStation)
    val edFee1: EditText by bindView<EditText>(R.id.edTransFee)
    val edRemark1: EditText by bindView<EditText>(R.id.remark1)

    val llInner: LinearLayout by bindView<LinearLayout>(R.id.ll_inner)
    val tvSearcom: TextView by bindView<TextView>(R.id.tvSearchCom)
    val sp_2Point: Spinner by bindView<Spinner>(R.id.sp_2)
    val edFee2: EditText by bindView<EditText>(R.id.edTransFee2)
    val edRemark2: EditText by bindView<EditText>(R.id.remark2)
    val tvEnsure: TextView by bindView<TextView>(R.id.tvEnsure)

    val REQUEST_CODE: Int = 32
    var dialog: Dialog? = null
    var presenter: TranPresenter? = null
    var TRANS_TYPE = 0
    val TRANS_INNER = 1
    val TRANS_OUTER = 2
    var requestEntity: TransEntity? = null
    private var billing_uuid: Long? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        requestEntity = TransEntity()
        billing_uuid = intent.extras.getLong("transUuid")
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_trans_add
    }

    override fun initPresenter() {
        presenter = TranPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.transx))
        tvSearcom.setOnClickListener { startActivityForResult(TransCompanyActivity::class.java, REQUEST_CODE) }
        goneAllLayout()
        tvInner.visibility = View.GONE
        llOuter.visibility = View.VISIBLE
        requestEntity!!.transfer_type = 2
        TRANS_TYPE = TRANS_OUTER
        tvInner.visibility = View.GONE
        tvEnsure.setOnClickListener { ensureTrans() }
    }


    private fun ensureTrans() {
        when (TRANS_TYPE) {
            TRANS_OUTER -> {
                if (TextUtils.isEmpty(getEDString(edName1))) {
                    Toasty.info(this, getString(R.string.trans_name_notNUll)).show()
                    return
                }
                if (TextUtils.isEmpty(getEDString(edFee1))) {
                    Toasty.info(this, getString(R.string.tranFee_notNUll)).show()
                    return
                }
                requestEntity!!.billing_uuid = billing_uuid
                requestEntity!!.transit_destination = getEDString(edName1)
                requestEntity!!.point_name= getEDStringOrNull(edName1)
                requestEntity!!.transfer_fee = getEDString(edFee1).toDouble()
                requestEntity!!.remark = getEDStringOrNull(edRemark1)
                presenter!!.addTrans(ZGsonUtils.getInstance().getJsonString(requestEntity))
                dialog!!.show()

            }
            TRANS_INNER -> {
                if (TextUtils.isEmpty(getEDString(edName1))) {
                    Toasty.info(this, getString(R.string.trans_name_notNUll)).show()
                    return
                }
                if (TextUtils.isEmpty(getEDString(edFee1))) {
                    Toasty.info(this, getString(R.string.tranFee_notNUll)).show()
                    return
                }
                requestEntity!!.billing_uuid = billing_uuid
                requestEntity!!.transit_destination = getEDString(edName1)
                requestEntity!!.transfer_fee = getEDString(edFee1).toDouble()
                requestEntity!!.remark = getEDStringOrNull(edRemark1)

            }
        }
    }

    private fun goneAllLayout() {
        llOuter.visibility = View.GONE
        llInner.visibility = View.GONE
        edName1.setText("")
        edFee1.setText("")
        edRemark1.setText("")
        edFee2.setText("")
        edRemark2.setText("")
        requestEntity = TransEntity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val entity = data?.getBundleExtra("companyBundle")?.getSerializable("companyEntity") as CooperCompanyEntity.PageListBean?
            tvSearcom.text = entity!!.company_name
            afterGetCompany(entity.uuid)
        }
    }

    private fun afterGetCompany(uuid: Int) {
        val request = CooperCompanyEntity.PageListBean()
        request.uuid = uuid
        presenter!!.searchPoint(ZGsonUtils.getInstance().getJsonString(request), object : TranPresenter.OnPointSearch {
            override fun getPointSucc(entity: CooperPointEntity) {
                if (entity.pageList.isEmpty()) {
                    return
                }
                sp_2Point.adapter = ArrayAdapter<CooperPointEntity.PageListBean>(this@TransAddActivity, android.R.layout.simple_dropdown_item_1line, entity.pageList)
                sp_2Point.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    }

                }
            }

            override fun getPointError() {
                Toasty.error(this@TransAddActivity, getString(R.string.trans_inner_error)).show()
            }
        })
    }

    override fun onNetWorkError() {
    }

    override fun addTransSucc() {
        Toasty.success(this,getString(R.string.trans_success)).show()
        dialog!!.dismiss()
        finish()
    }

    override fun searchTransSucc(entity: TransPageEntity) {
    }

    override fun transError(type: Int) {
        dialog!!.dismiss()
        Toasty.error(this,getString(R.string.trans_error)).show()

    }

}