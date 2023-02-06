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
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.mvp.presenter.RatePresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewRate
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZDoubleFormat
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/19.
 */
class DrawBillOtherFeActivity : BaseActivity() {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edDeclar: EditText by bindView<EditText>(R.id.edDeclarFee)
    val edValue: EditText by bindView<EditText>(R.id.edValue_fee)
    val edDeli: EditText by bindView<EditText>(R.id.edDeli_fee)
    val edRece: EditText by bindView<EditText>(R.id.edRecev_fee)
    val edHandle: EditText by bindView<EditText>(R.id.edHandle_fee)
    val edLiftFork: EditText by bindView<EditText>(R.id.edLiftFork_fee)
    val edPack: EditText by bindView<EditText>(R.id.edPack_fee)
    val edUpStair: EditText by bindView<EditText>(R.id.edUpstair_fee)
    val edCashBack: EditText by bindView<EditText>(R.id.edCashBack_fee)
    val edOwnBack: EditText by bindView<EditText>(R.id.edOwnBack_fee)
    val btCancel: TextView by bindView<TextView>(R.id.tvCancel)
    val btEnsure: TextView by bindView<TextView>(R.id.tvEnsure)
    val tvRate: TextView by bindView<TextView>(R.id.tvRate)
    var edList: MutableList<EditText>? = null

    var entity: BillingDrawMain? = null
    var valueRate: Double = 0.0
    var isRate: Boolean = false

    override fun initData() {
        val bundle = intent.extras
        if (bundle != null) {
            entity = bundle.getSerializable("otherEntity") as BillingDrawMain?
            if (entity != null) {
                edDeclar.setText(getNullStringForDouble(entity?.declared_value))
                edValue.setText(getNullStringForDouble(entity?.valuation_fee))
                edDeli.setText(getNullStringForDouble(entity?.delivery_fee))
                edRece.setText(getNullStringForDouble(entity?.receiving_fee))
                edHandle.setText(getNullStringForDouble(entity?.handling_fee))
                edLiftFork.setText(getNullStringForDouble(entity?.forklift_fee))
                edPack.setText(getNullStringForDouble(entity?.packing_fee))
                edUpStair.setText(getNullStringForDouble(entity?.upstair_fee))
                edCashBack.setText(getNullStringForDouble(entity?.return_fee))
                edOwnBack.setText(getNullStringForDouble(entity?.under_charge_fee))
            }
        }

        edList = ArrayList()
        edList!!.add(edDeclar)
        edList!!.add(edValue)
        edList!!.add(edDeli)
        edList!!.add(edRece)
        edList!!.add(edHandle)
        edList!!.add(edLiftFork)
        edList!!.add(edPack)
        edList!!.add(edUpStair)
        edList!!.add(edCashBack)
        edList!!.add(edOwnBack)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_other_fee
    }

    override fun initPresenter() {
        val presenter = RatePresenter.getInstance(object : IViewRate {
            override fun setRateSucc() {
            }

            override fun setRateError() {
            }

            override fun getRateSucc(rate: Double) {
                valueRate = rate
                tvRate.text = "保价率" + (rate * 1000).toString()
                isRate = true
                edValue.isEnabled = false
            }

            override fun getRateError() {
                tvRate.text = "------"
                Toasty.error(this@DrawBillOtherFeActivity, "保价率获取失败").show()
                isRate = false
            }
        })
        presenter.getRate(AppUserToken.getInstance().result.logistics_uuid)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.other_feex))
        btCancel.setOnClickListener {
            for (editText in edList!!) {
                editText.setText("")
            }
        }
        btEnsure.setOnClickListener {
            ensureFee()
        }

        edDeclar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s!!.toString().isEmpty() && isRate) {
                    edValue.setText(ZDoubleFormat.zFormat((s.toString().toDouble() * valueRate).toString()))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun ensureFee() {
        val stringBuilder = StringBuilder()
        val printBuilder = StringBuilder()
        var totalOtherFee: Double = 0.0
        var isDelivery = false

        val entity = BillingDrawMain()
        if (!TextUtils.isEmpty(getEDString(edDeclar))) {
            entity.declared_value = getEDString(edDeclar).toDouble()
//            stringBuilder.append(getEDString(edDeclar) + ",")
        }else{
            entity.declared_value = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edValue))) {
            entity.valuation_fee = getEDString(edValue).toDouble()
            stringBuilder.append("保" + getEDString(edValue) + ",")
            totalOtherFee += entity.valuation_fee
        } else {
            entity.valuation_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edDeli))) {
            entity.delivery_fee = getEDString(edDeli).toDouble()
            stringBuilder.append("送" + getEDString(edDeli) + ",")
            totalOtherFee += entity.delivery_fee
            isDelivery = true
        } else {
            entity.delivery_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edRece))) {
            entity.receiving_fee = getEDString(edRece).toDouble()
            stringBuilder.append("接" + getEDString(edRece) + ",")
            totalOtherFee += entity.receiving_fee
            printBuilder.append("接" + getEDString(edRece) + ",")
        } else {
            entity.receiving_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edHandle))) {
            entity.handling_fee = getEDString(edHandle).toDouble()
            stringBuilder.append("装" + getEDString(edHandle) + ",")
            totalOtherFee += entity.handling_fee
            printBuilder.append("装" + getEDString(edHandle) + ",")
        } else {
            entity.handling_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edLiftFork))) {
            entity.forklift_fee = getEDString(edLiftFork).toDouble()
            stringBuilder.append("叉" + getEDString(edLiftFork) + ",")
            totalOtherFee += entity.forklift_fee
            printBuilder.append("叉" + getEDString(edLiftFork) + ",")
        } else {
            entity.forklift_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edPack))) {
            entity.packing_fee = getEDString(edPack).toDouble()
            stringBuilder.append("包" + getEDString(edPack) + ",")
            totalOtherFee += entity.packing_fee
            printBuilder.append("包" + getEDString(edPack) + ",")
        } else {
            entity.packing_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edUpStair))) {
            entity.upstair_fee = getEDString(edUpStair).toDouble()
            stringBuilder.append("上" + getEDString(edUpStair) + ",")
            totalOtherFee += entity.upstair_fee
            printBuilder.append("上" + getEDString(edUpStair) + ",")
        } else {
            entity.upstair_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edCashBack))) {
            entity.return_fee = getEDString(edCashBack).toDouble()
//            stringBuilder.append(getEDString(edCashBack) + ",")
        } else {
            entity.return_fee = 0.0
        }

        if (!TextUtils.isEmpty(getEDString(edOwnBack))) {
            entity.under_charge_fee = getEDString(edOwnBack).toDouble()
//            stringBuilder.append(getEDString(edOwnBack) + ",")
        } else {
            entity.under_charge_fee = 0.0
        }

        if (!stringBuilder.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        }
        if (!printBuilder.isEmpty()) {
            printBuilder.deleteCharAt(printBuilder.length - 1)
        }
        val intent = Intent()
        val bundle = Bundle()
        bundle.putSerializable("otherFeeEntity", entity)
        intent.putExtra("otherFeeBundle", bundle)
        intent.putExtra("otherFeeStr", stringBuilder.toString())
        intent.putExtra("otherTotalFee", totalOtherFee)
        intent.putExtra("otherPrint", printBuilder.toString())
        if (isDelivery) {
            intent.putExtra("otherDeli", 999)
        }
        setResult(Activity.RESULT_OK, intent)
        this.finish()

    }

}