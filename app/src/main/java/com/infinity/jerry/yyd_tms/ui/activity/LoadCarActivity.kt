/*
 * Copyright (c) 201()7. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
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
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.CarsCommonEntity
import com.infinity.jerry.yyd_tms.data.LoadCarLineEntity
import com.infinity.jerry.yyd_tms.data.LoadCarShitEntity
import com.infinity.jerry.yyd_tms.data.request_entity.LoadCarReuqest
import com.infinity.jerry.yyd_tms.mvp.presenter.LoadCarPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadCar
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZMesuredListView
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/24.
 */

class LoadCarActivity : BaseActivity(), IViewLoadCar {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val tvBillInfo: TextView by bindView<TextView>(R.id.tvBillInfo)
    val listView: ListView by bindView<ZMesuredListView>(R.id.listView)
    val tvChoose: TextView by bindView<TextView>(R.id.tvCarChoose)
    val tvEnsure: TextView by bindView<TextView>(R.id.tvEnsureLoad)

    val edPlateNumber: EditText by bindView<EditText>(R.id.edPlateNumber)
    val edDriverName: EditText by bindView<EditText>(R.id.edDriverName)
    val edDriverPhone: EditText by bindView<EditText>(R.id.edDriverPhone)

    private val REQUEST_FOR_CARS: Int = 24

    var presenter: LoadCarPresenter? = null

    var list: ArrayList<LoadCarLineEntity>? = null

    var dialog: Dialog? = null

    var request: LoadCarReuqest? = null

    override fun initData() {//票据进来
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        var sth = intent.extras?.getSerializable("loadLines") as LoadCarShitEntity?
        list = sth?.list as ArrayList<LoadCarLineEntity>?
        if (list == null) {
            throw IllegalStateException("shit")
        }
        initShitView()
    }

    private fun initShitView() {
        var newList = ArrayList<LoadCarLineEntity>()
        var last = ""
        list!!.forEach {
            it.isHeader = false
            if (!it.terminal_station_name.equals(last)) {
                last = it.terminal_station_name
                newList.add(it)
            }
        }
        listView.adapter = object : ZCommonAdapter<LoadCarLineEntity>(this, newList, R.layout.item_loadcar_info) {
            override fun convert(holder: ZViewHolder?, item: LoadCarLineEntity?, position: Int) {
                val tvInfo = holder!!.getView<TextView>(R.id.tvInfo)
                tvInfo.text = getString(R.string.init_station) + " " + item!!.initial_station_name + " --> " + getString(R.string.termi_station) + " " + item!!.terminal_station_name
            }
        }
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_load_car
    }

    override fun initPresenter() {
        presenter = LoadCarPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.ensure_load))
        tvEnsure.setOnClickListener { ensureLoad() }
        tvChoose.setOnClickListener {
            startActivityForResult(CarsCommonAcitivty::class.java, REQUEST_FOR_CARS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_FOR_CARS) {
            var comCar = data!!.getBundleExtra("carBundle").getSerializable("carEntity") as CarsCommonEntity
            edPlateNumber.setText(getNullablString(comCar.plate_number))
            edDriverName.setText(getNullablString(comCar.driver_name))
            edDriverPhone.setText(getNullablString(comCar.driver_phone))
            edPlateNumber.isEnabled = false
            edDriverName.isEnabled = false
            edDriverPhone.isEnabled = false
        }
    }

    private fun ensureLoad() {
        if (TextUtils.isEmpty(getEDString(edPlateNumber))) {
            Toasty.info(this, getString(R.string.plateNumberNotNull)).show()
            return
        }
        request = LoadCarReuqest()
        var uuids = StringBuilder()
        list!!.forEach {
            uuids.append(it.uuid.toString() + ",")
        }
        uuids.deleteCharAt(uuids.length - 1)
        request!!.uuid = uuids.toString()
        request!!.plate_number = getEDStringOrNull(edPlateNumber)
        request!!.driver_name = getEDStringOrNull(edDriverName)
        request!!.driver_phone = getEDStringOrNull(edDriverPhone)
        presenter!!.loadCar(ZGsonUtils.getInstance().getJsonString(request))
        dialog!!.show()
    }

    override fun loadCarSucc(entity: List<BillingDrawMain>) {
        Toasty.success(this, getString(R.string.load_succ)).show()
        this.dialog!!.dismiss()
        this.finish()
    }

    override fun loadCarError() {
        dialog!!.dismiss()
        Toasty.success(this, getString(R.string.load_error)).show()
    }
}