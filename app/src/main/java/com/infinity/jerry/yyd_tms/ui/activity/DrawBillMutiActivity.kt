/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/19.
 */
class DrawBillMutiActivity : BaseActivity(), View.OnClickListener {

    private val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    private val edCash: EditText by bindView<EditText>(R.id.edCashPayment)
    private val edArrive: EditText by bindView<EditText>(R.id.edArrPayment)
    private val edBack: EditText by bindView<EditText>(R.id.edBackPayment)
    private val edMonth: EditText by bindView<EditText>(R.id.edMonthPayment)
    private val edCollect: EditText by bindView<EditText>(R.id.edCollePayment)
    private val tvRedo: TextView by bindView<TextView>(R.id.tvRedo)
    private val tvEnsure: TextView by bindView<TextView>(R.id.tvEnsure)

    private var edList: MutableList<EditText>? = null

    private var entity: BillingDrawMain? = null
    override fun initData() {
        var bundle = intent.extras
        if (bundle != null) {
            entity = bundle.getSerializable("updateEntity") as BillingDrawMain
            if (entity != null) {
                edCash.setText(getNullStringForDouble(entity?.cash_payment))
                edArrive.setText(getNullStringForDouble(entity?.collect_payment))
                edBack.setText(getNullStringForDouble(entity?.back_payment))
                edMonth.setText(getNullStringForDouble(entity?.monthly_payment))
                edCollect.setText(getNullStringForDouble(entity?.payment_deduction))
            }
        }
        edList = ArrayList()
        edList!!.add(edCash)
        edList!!.add(edArrive)
        edList!!.add(edBack)
        edList!!.add(edMonth)
        edList!!.add(edCollect)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_muti_payment
    }

    override fun initPresenter() {
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.muti_pay))
        tvRedo.setOnClickListener(this)
        tvEnsure.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvRedo -> {
                for (editText in edList!!) {
                    editText.setText("")
                }
            }
            R.id.tvEnsure -> {
                ensureInfo()
            }
        }
    }

    private fun ensureInfo() {
        val stringBuilder = StringBuilder()
        val mutiPrint = StringBuilder()
        var totalPayment: Double = 0.0
        val entity = BillingDrawMain()

        if (!TextUtils.isEmpty(getEDString(edCash))) {
            stringBuilder.append(getString(R.string.cash_payment) + getEDString(edCash) + ",")
            entity.cash_payment = getEDString(edCash).toDouble()
            totalPayment += entity.cash_payment
            mutiPrint.append(getString(R.string.cash_payment) + getEDString(edCash) + ",")

        }
        if (!TextUtils.isEmpty(getEDString(edArrive))) {
            stringBuilder.append(getString(R.string.arrive_payment) + getEDString(edArrive) + ",")
            entity.collect_payment = getEDString(edArrive).toDouble()
            totalPayment += entity.collect_payment
            mutiPrint.append(getString(R.string.arrive_payment) + getEDString(edArrive) + ",")

        }
        if (!TextUtils.isEmpty(getEDString(edBack))) {
            stringBuilder.append(getString(R.string.back_payment) + getEDString(edBack) + ",")
            entity.back_payment = getEDString(edBack).toDouble()
            totalPayment += entity.back_payment
            mutiPrint.append(getString(R.string.back_payment) + getEDString(edBack) + ",")
        }
        if (!TextUtils.isEmpty(getEDString(edMonth))) {
            stringBuilder.append(getString(R.string.month_payment) + getEDString(edMonth) + ",")
            entity.monthly_payment = getEDString(edMonth).toDouble()
            totalPayment += entity.monthly_payment
            mutiPrint.append(getString(R.string.month_payment) + getEDString(edMonth) + ",")
        }
        if (!TextUtils.isEmpty(getEDString(edCollect))) {
            stringBuilder.append(getString(R.string.collect_payment) + getEDString(edCollect) + ",")
            entity.payment_deduction = getEDString(edCollect).toDouble()
            totalPayment += entity.payment_deduction
            mutiPrint.append(getString(R.string.collect_payment) + getEDString(edCollect) + ",")
        }
        if (stringBuilder.length > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        } else {
            Toasty.info(this, "请填写至少2项多笔付数据，否则请退出选择单一付款方式").show()
            return
        }
        if (mutiPrint.length > 0) {
            mutiPrint.deleteCharAt(mutiPrint.length - 1)
        }

        val intent = Intent()
        val bundle = Bundle()
        bundle.putSerializable("mutiEntity", entity)
        intent.putExtra("mutiBundle", bundle)
        intent.putExtra("mutiTotal", totalPayment)
        intent.putExtra("mutiString", stringBuilder.toString())
        intent.putExtra("mutiPrint", mutiPrint.toString())
        setResult(Activity.RESULT_OK, intent)
        this.finish()
    }
}