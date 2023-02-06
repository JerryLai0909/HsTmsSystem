/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CarsCommonEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.CarsEditPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCarsEdit
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/18.
 */
class CarsEditAcitivty : BaseActivity(), IViewCarsEdit {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val btEnsure: TextView by bindView<TextView>(R.id.btEnsureCars)
    val edPlateNumber: EditText by bindView<EditText>(R.id.edPlateNumber)
    val edDriverName: EditText by bindView<EditText>(R.id.edDriverName)
    val edDriverPhone: EditText by bindView<EditText>(R.id.edDriverPhone)
    var presenter: CarsEditPresenter? = null
    var uuid: Long? = null

    override fun initData() {
        var entity: CarsCommonEntity? = intent.extras?.getSerializable("carEntity") as CarsCommonEntity?
        uuid = entity?.uuid
        edPlateNumber.setText(getNullablString(entity?.plate_number))
        edDriverName.setText(getNullablString(entity?.driver_name))
        edDriverPhone.setText(getNullablString(entity?.driver_phone))
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_cars_edit
    }

    override fun initPresenter() {
        presenter = CarsEditPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.cars_edit))
        btEnsure.setOnClickListener {
            ensureAdd()
        }
    }

    private fun ensureAdd() {
        if (TextUtils.isEmpty(getEDString(edPlateNumber))) {
            Toasty.info(this, getString(R.string.plateNumberNotNull)).show()
            return
        }

        var entity = CarsCommonEntity()
        entity.uuid = uuid
        entity.plate_number = getEDStringOrNull(edPlateNumber)
        entity.driver_name = getEDStringOrNull(edDriverName)
        entity.driver_phone = getEDStringOrNull(edDriverPhone)
        presenter!!.updateCar(ZGsonUtils.getInstance().getJsonString(entity))
    }

    override fun updateCarsSucc() {
        Toasty.info(this, getString(R.string.update_car_succ)).show()
        this.finish()
    }

    override fun updateCarsError() {
        Toasty.info(this, getString(R.string.update_car_error)).show()

    }

}