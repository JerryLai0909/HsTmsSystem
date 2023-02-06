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
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.mvp.presenter.BillDrawPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.RecordPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewBillDraw
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewRecord
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.BounceScrollView
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.RepeatableSpinner
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/11.
 * 这个是用来专门开票的
 */
class DrawBillActivityX : BaseActivity(), View.OnClickListener, IViewBillDraw, IViewRecord {

    override fun addRecordSucc() {
        Log.e("TAG", "添加记录成功")
    }

    override fun addRecordError() {
        Log.e("TAG", "添加记录失败")
    }

    override fun drawBillBarCodeSucc(barCode: String) {
    }

    override fun drawBillBarCodeError(barCode: String) {
    }

    private val tvInit: AutoScaleTextView by bindView<AutoScaleTextView>(R.id.tvInit)
    private val tvTermi: AutoScaleTextView by bindView<AutoScaleTextView>(R.id.tvTermi)
    private val spTypeSend: Spinner by bindView<Spinner>(R.id.spTypeSend)
    private val llDestination: LinearLayout by bindView<LinearLayout>(R.id.llDestination)
    private val edDestination: EditText by bindView<EditText>(R.id.edDestination)
    private val edConEEName: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsigneeName)
    private val edConEEPhone: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsigneePhone)
    private val edGoods: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edGoods)
    private val edPackages: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edPackages)
    private val edQuatity: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edQuatity)
    private val edFreight: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edFreight)
    private val edAdvance: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edAdvance)
    private val edCollection: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edCollectionFee)
    private val edConRRName: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsignerName)
    private val edConRRPhone: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsignerPhone)
    private val edRecipe: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edBackRecipe)
    private val edPrinCount: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edPrintCount)
    private val edMark: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edMark)
    private val tvOtherFee: TextView by bindView<TextView>(R.id.tvOtherFees)
    private val tvTotalFee: TextView by bindView<TextView>(R.id.tvTotalFees)
    private val tvMutiPay: TextView by bindView<TextView>(R.id.tvMutiPay)
    private val btCommonGoods: TextView by bindView<TextView>(R.id.btCommonGoods)
    private val btCommonPacks: TextView by bindView<TextView>(R.id.btCommonPackage)
    private val btOtherFee: TextView by bindView<TextView>(R.id.btOtherFee)
    private val btQuit: TextView by bindView<TextView>(R.id.btQuit)
    private val btEnsure: TextView by bindView<TextView>(R.id.btEnsureSave)
    private val spPayMethod: Spinner by bindView<RepeatableSpinner>(R.id.spPayMethod)
    private val spGetMethod: Spinner by bindView<RepeatableSpinner>(R.id.spGetMethod)
    private val scrollView: BounceScrollView by bindView<BounceScrollView>(R.id.scrollView)
    private var presenter: BillDrawPresenter? = null
    private var dialog: Dialog? = null
    private var mainBillEntity: BillingDrawMain? = null

    private val edZhongliang: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edZhongliang)
    private val edTiji: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edTiji)

    private val edSelfInput: EditText by bindView<EditText>(R.id.edInputNumber)

    private val GET_GOODS_FOR_BILL: Int = 12
    private val GET_PACKS_FOR_BILL: Int = 14
    private val GET_MUTIPAH_FOR_BILL: Int = 16
    private val GET_OTHERFEE_FOR_BILL: Int = 18

    private var strPayMethod = "现付"
    private var strGetMethod = "自提"

    private val SINGLE_PAY: Int = 1
    private val MUTI_PAY: Int = 2
    private var STATE_PAY: Int = SINGLE_PAY


    private var otherFeeTotal: Double = 0.0
    private var freight: Double = 0.0
    private var advance: Double = 0.0
    private var collectFee: Double = 0.0 //代收货款
    private var allFeeTotal: Double = 0.0//运费合计
    private var isMutiExcute: Boolean = false
    private var mutiTotal: Double = 0.0
    private var isTrans: Boolean = false
    private var isConnSucc: Boolean = false
    private var pointY = 0f
    private var isFirst = false

    private var presenterX: RecordPresenter? = null

    private var otherPrint: String? = ""
    private var mutiPrint: String? = ""

    private var noStart = true

    override fun initData() {
        presenterX = RecordPresenter(this)
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.update), false)
        mainBillEntity = intent.extras?.getSerializable("billEntity") as BillingDrawMain?
        otherFeeTotal = 0.0
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.valuation_fee)
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.delivery_fee)
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.receiving_fee)
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.handling_fee)
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.forklift_fee)
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.packing_fee)
        otherFeeTotal += getDoubleNotNull(mainBillEntity!!.upstair_fee)
        val stringBuilder = StringBuilder()

        if (mainBillEntity!!.valuation_fee != null && !mainBillEntity!!.valuation_fee.equals("") && mainBillEntity!!.valuation_fee != 0.0) {
            stringBuilder.append("保" + mainBillEntity!!.valuation_fee + ",")
        }

        if (mainBillEntity!!.delivery_fee != null && !mainBillEntity!!.delivery_fee.equals("") && mainBillEntity!!.delivery_fee != 0.0) {
            stringBuilder.append("送" + mainBillEntity!!.delivery_fee + ",")
        }

        if (mainBillEntity!!.receiving_fee != null && !mainBillEntity!!.receiving_fee.equals("") && mainBillEntity!!.receiving_fee != 0.0) {
            stringBuilder.append("接" + mainBillEntity!!.delivery_fee + ",")
        }

        if (mainBillEntity!!.handling_fee != null && !mainBillEntity!!.handling_fee.equals("") && mainBillEntity!!.handling_fee != 0.0) {
            stringBuilder.append("装" + mainBillEntity!!.handling_fee + ",")
        }

        if (mainBillEntity!!.forklift_fee != null && !mainBillEntity!!.forklift_fee.equals("") && mainBillEntity!!.forklift_fee != 0.0) {
            stringBuilder.append("叉" + mainBillEntity!!.forklift_fee + ",")
        }

        if (mainBillEntity!!.packing_fee != null && !mainBillEntity!!.packing_fee.equals("") && mainBillEntity!!.packing_fee != 0.0) {
            stringBuilder.append("包" + mainBillEntity!!.forklift_fee + ",")
        }

        if (mainBillEntity!!.upstair_fee != null && !mainBillEntity!!.upstair_fee.equals("") && mainBillEntity!!.upstair_fee != 0.0) {
            stringBuilder.append("上" + mainBillEntity!!.upstair_fee + ",")
        }

        if (!stringBuilder.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        }

        tvOtherFee.text = stringBuilder.toString() + "  总计" + otherFeeTotal.toString()
        otherPrint = stringBuilder.toString()
    }

    private fun initWindow() {
        tvInit.text = getNullablString(mainBillEntity?.startStation)
        tvTermi.text = getNullablString(mainBillEntity?.endStation)

        val destination = mainBillEntity?.transit_destination
        if (destination != null && destination.length > 0) {
            spTypeSend.setSelection(1)
            edDestination.visibility = View.VISIBLE
            edDestination.setText(destination)
        }
        edConEEName.setText(getNullablString(mainBillEntity?.consignee))
        edConEEPhone.setText(getNullablString(mainBillEntity?.consignee_phone))

        edGoods.setText(getNullablString(mainBillEntity?.goods_name))
        edPackages.setText(getNullablString(mainBillEntity?.packaging))
        edQuatity.setText(getZeroableStringForLong(mainBillEntity?.quantity))
        edFreight.setText(getZeroableStringForDouble(mainBillEntity?.freight))
        edAdvance.setText(getZeroableStringForDouble(mainBillEntity?.advance))
        edCollection.setText(getZeroableStringForDouble(mainBillEntity?.collection_fee))
        tvTotalFee.text = getZeroableStringForDouble(mainBillEntity?.total_freight)
        edSelfInput.setText(getNullablString(mainBillEntity?.input_article_number))


        allFeeTotal = mainBillEntity?.total_freight!!

        mainBillEntity?.invoice_date = null
        mainBillEntity?.invoicedate = null

        mutiTotal = getDoubleNotNull(mainBillEntity?.cash_payment) +
                getDoubleNotNull(mainBillEntity?.collect_payment) +
                getDoubleNotNull(mainBillEntity?.back_payment) +
                getDoubleNotNull(mainBillEntity?.monthly_payment) +
                getDoubleNotNull(mainBillEntity?.payment_deduction)
        when (mainBillEntity?.payment_method) {
            "现付" -> {
                spPayMethod.setSelection(0)
                tvMutiPay.text = "现付" + getZeroableStringForDouble(mainBillEntity?.cash_payment) + "总计" + mutiTotal.toString()
            }
            "到付" -> {
                spPayMethod.setSelection(1)
                tvMutiPay.text = "到付" + getZeroableStringForDouble(mainBillEntity?.collect_payment) + "总计" + mutiTotal.toString()
            }
            "回付" -> {
                spPayMethod.setSelection(2)
                tvMutiPay.text = "回付" + getZeroableStringForDouble(mainBillEntity?.back_payment) + "总计" + mutiTotal.toString()
            }
            "月结" -> {
                spPayMethod.setSelection(3)
                tvMutiPay.text = "月结" + getZeroableStringForDouble(mainBillEntity?.monthly_payment) + "总计" + mutiTotal.toString()
            }
            "货款扣" -> {
                spPayMethod.setSelection(4)
                tvMutiPay.text = "货款扣" + getZeroableStringForDouble(mainBillEntity?.payment_deduction) + "总计" + mutiTotal.toString()
            }
            "多笔付" -> {
                noStart = false
                spPayMethod.setSelection(5)
                tvMutiPay.text = "现付" + getZeroableStringForDouble(mainBillEntity?.cash_payment) +
                        "到付" + getZeroableStringForDouble(mainBillEntity?.collect_payment) +
                        "回付" + getZeroableStringForDouble(mainBillEntity?.back_payment) +
                        "月结" + getZeroableStringForDouble(mainBillEntity?.monthly_payment) +
                        "货款扣" + getZeroableStringForDouble(mainBillEntity?.payment_deduction) +
                        "总计" + mutiTotal.toString()
            }
        }
        when (mainBillEntity?.delivery_method) {
            "自提" -> spGetMethod.setSelection(0)
            "送货" -> spGetMethod.setSelection(1)
        }

        edConRRName.setText(getNullablString(mainBillEntity?.consigner))
        edConRRPhone.setText(getNullablString(mainBillEntity?.consigner_phone))
        edRecipe.setText(getZeroableStringForLong(mainBillEntity?.number_of_copies))
        edMark.setText(getNullablString(mainBillEntity?.remarks))

        edZhongliang.setText(getNullablString(mainBillEntity?.weight))
        edTiji.setText(getNullablString(mainBillEntity?.volume))
    }


    override fun getLayoutId(): Int {
        SetStatusBarColor(ContextCompat.getColor(this, R.color.color_white))
        return R.layout.activity_drawbill_x
    }

    override fun initPresenter() {
        presenter = BillDrawPresenter.getInstance(this)
    }

    override fun initView() {
        btCommonGoods.setOnClickListener(this)
        btCommonPacks.setOnClickListener(this)
        btOtherFee.setOnClickListener(this)
        btEnsure.setOnClickListener(this)
        btQuit.setOnClickListener(this)
        spTypeSend.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        llDestination.visibility = View.GONE
                        edDestination.setText("")
                        isTrans = false
                        mainBillEntity!!.transit_destination = null
                    }
                    1 -> {
                        isTrans = true
                        llDestination.visibility = View.VISIBLE
                    }
                }
            }
        }
        spPayMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                strPayMethod = getString(R.string.arrive_payment)
                tvMutiPay.text = strPayMethod + ": " + allFeeTotal

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainBillEntity!!.collect_payment = 0.0
                mainBillEntity!!.cash_payment = 0.0
                mainBillEntity!!.back_payment = 0.0
                mainBillEntity!!.monthly_payment = 0.0
                mainBillEntity!!.payment_deduction = 0.0
                if (position != 5) {
                    STATE_PAY = SINGLE_PAY
                    strPayMethod = resources.getStringArray(R.array.method_pay)[position]
                    tvMutiPay.text = strPayMethod + ": " + allFeeTotal
                } else {
                    STATE_PAY = MUTI_PAY
                    isMutiExcute = false
                    strPayMethod = resources.getStringArray(R.array.method_pay)[5]
                    if (noStart) {
                        val bundle = Bundle()
                        bundle.putSerializable("updateEntity", mainBillEntity)
                        startActivityForResult(DrawBillMutiActivity::class.java, bundle, GET_MUTIPAH_FOR_BILL)
                    }
                    noStart = true
                }
            }
        }
        spGetMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                strGetMethod = getString(R.string.self_get)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strGetMethod = resources.getStringArray(R.array.method_getGoods)[position]
            }
        }
        edAdvance.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    advance = 0.0
                } else {
                    try {
                        advance = s!!.toString().toDouble()
                    } catch (e: NumberFormatException) {
                        advance = 0.0
                        Toasty.error(this@DrawBillActivityX, getString(R.string.help_please))
                    }
                }
                getAllTotalFee()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        edCollection.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    collectFee = 0.0
                } else {
                    try {
                        collectFee = s!!.toString().toDouble()

                    } catch (e: NumberFormatException) {
                        collectFee = 0.0
                        Toasty.error(this@DrawBillActivityX, getString(R.string.help_please))
                    }
                }
                getAllTotalFee()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        edFreight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    freight = 0.0
                } else {
                    try {
                        freight = s!!.toString().toDouble()
                    } catch (e: NumberFormatException) {
                        freight = 0.0
                        Toasty.error(this@DrawBillActivityX, getString(R.string.help_please))
                    }
                }
                getAllTotalFee()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        initWindow()

        scrollView.setOnTouchListener { v, event ->
            if (!isFirst) {
                pointY = event.y
                isFirst = true
            }
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y
                    if (Math.abs(pointY - y) > 50) {
                        closeTheKeyBorad()
                    }
                }
                MotionEvent.ACTION_UP -> isFirst = false
            }
            false
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btCommonGoods -> startActivityForResult(DrawGoodsActivity::class.java, GET_GOODS_FOR_BILL)
            R.id.btCommonPackage -> startActivityForResult(DrawPacksActivity::class.java, GET_PACKS_FOR_BILL)
            R.id.btOtherFee -> {
                val bundle = Bundle()
                bundle.putSerializable("otherEntity", mainBillEntity)
                startActivityForResult(DrawBillOtherFeActivity::class.java, bundle, GET_OTHERFEE_FOR_BILL)
            }
            R.id.btQuit -> showQuitDialog()
            R.id.btEnsureSave -> ensureDrawBill()
        }
    }


    private fun getOtherFeeFromResult(entity: BillingDrawMain) {
        mainBillEntity!!.declared_value = 0.0
        mainBillEntity!!.valuation_fee = 0.0
        mainBillEntity!!.delivery_fee = 0.0
        mainBillEntity!!.receiving_fee = 0.0
        mainBillEntity!!.handling_fee = 0.0
        mainBillEntity!!.forklift_fee = 0.0
        mainBillEntity!!.packing_fee = 0.0
        mainBillEntity!!.upstair_fee = 0.0
        mainBillEntity!!.return_fee = 0.0
        mainBillEntity!!.under_charge_fee = 0.0
        mainBillEntity!!.declared_value = entity.declared_value
        mainBillEntity!!.valuation_fee = entity.valuation_fee
        mainBillEntity!!.delivery_fee = entity.delivery_fee
        mainBillEntity!!.receiving_fee = entity.receiving_fee
        mainBillEntity!!.handling_fee = entity.handling_fee
        mainBillEntity!!.forklift_fee = entity.forklift_fee
        mainBillEntity!!.packing_fee = entity.packing_fee
        mainBillEntity!!.upstair_fee = entity.upstair_fee
        mainBillEntity!!.return_fee = entity.return_fee
        mainBillEntity!!.under_charge_fee = entity.under_charge_fee
    }

    private fun getMutiPayResult(entity: BillingDrawMain) {
        mainBillEntity!!.cash_payment = 0.0
        mainBillEntity!!.collect_payment = 0.0
        mainBillEntity!!.back_payment = 0.0
        mainBillEntity!!.monthly_payment = 0.0
        mainBillEntity!!.payment_deduction = 0.0
        mainBillEntity!!.cash_payment = entity.cash_payment
        mainBillEntity!!.collect_payment = entity.collect_payment
        mainBillEntity!!.back_payment = entity.back_payment
        mainBillEntity!!.monthly_payment = entity.monthly_payment
        mainBillEntity!!.payment_deduction = entity.payment_deduction
    }

    private fun showQuitDialog() {
        val alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.quit_update_bill))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    this.finish()
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    private fun getAllTotalFee() {
        allFeeTotal = freight + advance + otherFeeTotal
        tvTotalFee.text = allFeeTotal.toString()
        if (STATE_PAY == SINGLE_PAY) {
            tvMutiPay.text = strPayMethod + ": " + allFeeTotal
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GET_GOODS_FOR_BILL) {
            if (getEDString(edGoods).equals("")) {
                edGoods.setText(data!!.getStringExtra("goodName"))
            } else {
                edGoods.setText(getEDString(edGoods) + "," + data!!.getStringExtra("goodName"))
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == GET_PACKS_FOR_BILL) {
            if (getEDString(edPackages).equals("")) {
                edPackages.setText(data!!.getStringExtra("packName"))
            } else {
                edPackages.setText(getEDString(edPackages) + "," + data!!.getStringExtra("packName"))
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == GET_MUTIPAH_FOR_BILL) {//多笔付
            val billMuti = data!!.getBundleExtra("mutiBundle")?.getSerializable("mutiEntity") as BillingDrawMain
            getMutiPayResult(billMuti)
            val mutiMethod = data.getStringExtra("mutiString")
            mutiTotal = data.getDoubleExtra("mutiTotal", 0.0)
            tvMutiPay.setText(getString(R.string.muti_pay) + " " + mutiMethod + " 总计" + mutiTotal)
            isMutiExcute = true
            val aa = data.getStringExtra("mutiPrint")
            if (aa != null) {
                mutiPrint = aa
            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == GET_OTHERFEE_FOR_BILL) {//其他费用
            val billDraw = data!!.getBundleExtra("otherFeeBundle")?.getSerializable("otherFeeEntity") as BillingDrawMain
            getOtherFeeFromResult(billDraw)
            val strOtherFee = data.getStringExtra("otherFeeStr")
            otherFeeTotal = data.getDoubleExtra("otherTotalFee", 0.0)
            tvOtherFee.text = strOtherFee + "  总计" + otherFeeTotal.toString()
            //还传过来了一个otherFeeTotal
            val aa = data.getStringExtra("otherPrint")
            if (aa != null) {
                otherPrint = aa
            }
            val isDeli = data.getIntExtra("otherDeli", 0)
            if (isDeli == 999) {
                spGetMethod.setSelection(1)
            } else {
                spGetMethod.setSelection(0)
            }
            getAllTotalFee()
        }

    }

    private fun ensureDrawBill() {
        if (STATE_PAY == MUTI_PAY && !isMutiExcute) {
            Toasty.info(this, getString(R.string.get_paymethod)).show()
            return
        }
        if (STATE_PAY == MUTI_PAY && isMutiExcute) {
            if (mutiTotal != allFeeTotal) {
                Toasty.info(this, getString(R.string.not_right_mutiAndTotal)).show()
                startActivityForResult(DrawBillMutiActivity::class.java, GET_MUTIPAH_FOR_BILL)
                return
            }
        }
        if (TextUtils.isEmpty(getEDString(edQuatity))) {
            Toasty.info(this, getString(R.string.get_Quantity)).show()
            return
        }
        if (TextUtils.isEmpty(getEDString(edFreight))) {
            Toasty.info(this, getString(R.string.get_Freight)).show()
            return
        }

        if (isTrans && TextUtils.isEmpty(getEDString(edDestination))) {
            Toasty.info(this, getString(R.string.get_Destination)).show()
            return
        }

        val advance = getEDStringOrNull(edAdvance)?.toDoubleOrNull()

        if (advance !== null && !strPayMethod.equals("到付") && !strPayMethod.equals("多笔付")) {
            Toasty.info(this, "检测到您有垫付费" + advance + "元,您的付款方式只能选择'到付'或者'多笔付'").show()
            return
        }

        mainBillEntity!!.consigner = getEDStringOrNull(edConRRName)
        mainBillEntity!!.consigner_phone = getEDStringOrNull(edConRRPhone)
        mainBillEntity!!.consignee = getEDStringOrNull(edConEEName)
        mainBillEntity!!.consignee_phone = getEDStringOrNull(edConEEPhone)
        mainBillEntity!!.goods_name = getEDStringOrNull(edGoods)
        mainBillEntity!!.quantity = getEDString(edQuatity)?.toLongOrNull()
        mainBillEntity!!.packaging = getEDStringOrNull(edPackages)
//        mainBillEntity!!.delivery_method = getEDString()
        mainBillEntity!!.weight = getEDStringOrNull(edZhongliang)
        mainBillEntity!!.volume = getEDStringOrNull(edTiji)
        mainBillEntity!!.number_of_copies = getEDStringOrNull(edPrinCount)?.toLongOrNull()
        mainBillEntity!!.transit_destination = getEDStringOrNull(edDestination)
        var str = getEDStringOrNull(edMark)
        if (str == null) {
            str = ""
        }
        mainBillEntity!!.remarks = str
        mainBillEntity!!.delivery_method = strGetMethod
        mainBillEntity!!.payment_method = strPayMethod

        mainBillEntity!!.freight = getEDStringOrNull(edFreight)?.toDoubleOrNull()
        var temp = 0.0
        if (getEDStringOrNull(edAdvance) == null) {
            temp = 0.0
        } else {
            temp = getEDStringOrNull(edAdvance).toDouble()
        }
        mainBillEntity!!.advance = temp
        mainBillEntity!!.collection_fee = getEDStringOrNull(edCollection)?.toDoubleOrNull()

        mainBillEntity!!.input_article_number = getEDStringOrNull(edSelfInput)

        when (spPayMethod.selectedItemPosition) {
            0 -> mainBillEntity!!.cash_payment = allFeeTotal
            1 -> mainBillEntity!!.collect_payment = allFeeTotal
            2 -> mainBillEntity!!.back_payment = allFeeTotal
            3 -> mainBillEntity!!.monthly_payment = allFeeTotal
            4 -> mainBillEntity!!.payment_deduction = allFeeTotal
        }

        mainBillEntity!!.total_freight = allFeeTotal

        mainBillEntity!!.total_freight_receipts = otherPrint + "&&" + mutiPrint

        Logger.i(ZGsonUtils.getInstance().getJsonString(mainBillEntity))
        presenter!!.ensureDrawBilling(ZGsonUtils.getInstance().getJsonString(mainBillEntity))
        dialog!!.show()
    }

    override fun drawBillSucc(entity: BillingDrawMain) {

        Toasty.success(this, getString(R.string.update_bill_succ)).show()
        presenterX!!.addBillRecord(entity.uuid.toString(), entity.collection_fee.toString(), "MODIFY")
        Handler().postDelayed({
            dialog?.dismiss()
            this@DrawBillActivityX.finish()
        }, 2000)
    }

    override fun drawBillError() {
        dialog?.dismiss()
        Toasty.success(this, getString(R.string.update_bill_error)).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}