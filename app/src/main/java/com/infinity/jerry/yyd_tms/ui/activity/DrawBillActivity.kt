/*
 * Copyrigh()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.*
import com.infinity.jerry.yyd_tms.constant.ConstantPool
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BillTerimi
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.database.BillingDestinationDB
import com.infinity.jerry.yyd_tms.data.database.BillingDrawDataBase
import com.infinity.jerry.yyd_tms.mvp.presenter.BillDrawPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.RecordPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewBillDraw
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewRecord
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.BounceScrollView
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.RepeatableSpinner
import com.infinity.jerry.yyd_tms.utils.DistinctQuery
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import net.posprinter.posprinterface.UiExecute
import org.litepal.crud.DataSupport
import org.litepal.crud.callback.FindMultiCallback

/**
 * Created by jerry on 2017/6/11.
 * 这个是用来专门开票的
 */
class DrawBillActivity : BaseActivity(), View.OnClickListener, IViewBillDraw, IViewRecord {
    override fun addRecordSucc() {
        Log.e("TAG", "添加记录成功")
    }

    override fun addRecordError() {
        Log.e("TAG", "添加记录失败")
    }

    private val tvInit: AutoScaleTextView by bindView<AutoScaleTextView>(R.id.tvInit)
    private val tvTermi: AutoScaleTextView by bindView<AutoScaleTextView>(R.id.tvTermi)
    private val spTypeSend: Spinner by bindView(R.id.spTypeSend)
    private val popCard: CardView by bindView<CardView>(R.id.popCard)
    private val llDestination: LinearLayout by bindView<LinearLayout>(R.id.llDestination)
    private val edDestination: EditText by bindView<EditText>(R.id.edDestination)
    private val edConEEName: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsigneeName)
    private val edConEEPhone: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsigneePhone)
    private val edGoods: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edGoods)
    private val edPackages: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edPackages)
    private val edQuatity: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edQuatity)
    private val edFreight: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edFreight)
    private val edAcvance: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edAdvance)
    private val edCollection: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edCollectionFee)
    private val edConRRName: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsignerName)
    private val edConRRPhone: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edConsignerPhone)
    private val edRecipe: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edBackRecipe)
    private val edPrinCount: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edPrintCount)
    private val edMark: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edMark)
    private val edZhongliang: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edZhongliang)
    private val edTiji: AutoCompleteTextView by bindView<AutoCompleteTextView>(R.id.edTiji)

    private val tvOtherFee: TextView by bindView<TextView>(R.id.tvOtherFees)
    private val tvTotalFee: TextView by bindView<TextView>(R.id.tvTotalFees)
    private val tvMutiPay: TextView by bindView<TextView>(R.id.tvMutiPay)
    private val btCommonGoods: TextView by bindView<TextView>(R.id.btCommonGoods)
    private val btCommonPacks: TextView by bindView<TextView>(R.id.btCommonPackage)
    private val btOtherFee: TextView by bindView<TextView>(R.id.btOtherFee)
    private val imBlueTooth: ImageView by bindView<ImageView>(R.id.imBlueTooth)
    private val btQuit: TextView by bindView<TextView>(R.id.btQuit)
    private val btEnsure: TextView by bindView<TextView>(R.id.btEnsureSave)
    private val spPayMethod: Spinner by bindView<RepeatableSpinner>(R.id.spPayMethod)
    private val spGetMethod: Spinner by bindView<RepeatableSpinner>(R.id.spGetMethod)
    private val scrollView: BounceScrollView by bindView<BounceScrollView>(R.id.scrollView)
    private val edSelfInput: EditText by bindView<EditText>(R.id.edInputNumber)
    private val tvss: TextView by bindView<TextView>(R.id.tvss)

    private var printReciver: ZPrintReceiver? = null
    private var printFilter: IntentFilter? = null
    private var connReceiver: ZBtConnReceiver? = null
    private var connFilter: IntentFilter? = null
    private var factory: ZBtConnFactory? = null
    private var presenter: BillDrawPresenter? = null
    private var dialog: Dialog? = null
    private var mainBillEntity: BillingDrawMain? = null

    private val GET_GOODS_FOR_BILL: Int = 12
    private val GET_PACKS_FOR_BILL: Int = 14
    private val GET_MUTIPAH_FOR_BILL: Int = 16
    private val GET_OTHERFEE_FOR_BILL: Int = 18

    private var strPayMethod = "现付"
    private var strGetMethod = "自提"

    private val SINGLE_PAY: Int = 1
    private val MUTI_PAY: Int = 2
    private var STATE_PAY: Int = SINGLE_PAY

    private var pointY = 0f
    private var isFirst = false


    private var otherFeeTotal: Double = 0.0
    private var freight: Double = 0.0
    private var advance: Double = 0.0
    private var collectFee: Double = 0.0 //代收货款
    private var allFeeTotal: Double = 0.0//运费合计

    private var isMutiExcute: Boolean = false
    private var mutiTotal: Double = 0.0

    private var isTrans: Boolean = false
    private var isConnSucc: Boolean = false

    private var otherPrint: String? = ""
    private var mutiPrint: String? = ""

    private var popWindow: PopupWindow? = null

    private var tempReciveName: String = ""
    private var tempSendName: String = ""

    private var dataList: MutableList<BillingDrawDataBase>? = null

    private var threadPool: ThreadPool = ThreadPool.getInstantiation()

    private var is_e: Boolean = false
    private var is_r: Boolean = false

    private var is_ep: Boolean = false
    private var is_rp: Boolean = false

    private var mana: DeviceConnFactoryManager? = null

    private var presenterX: RecordPresenter? = null

    override fun initData() {
        Log.e("TAG", "type " + ZShrPrefencs.getInstance().biaogeshi)
        presenterX = RecordPresenter(this)
        dataList = ArrayList()
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.update), false)
        mainBillEntity = BillingDrawMain()
        printReciver = ZPrintReceiver(this)

        val termi = intent.extras.getSerializable("lineEntity") as BillTerimi
        mainBillEntity!!.initial_station_id = termi.uuid
        mainBillEntity!!.terminal_station_id = termi.uuid
        tvInit.text = AppUserToken.getInstance().result.point_name
        tvTermi.text = termi.point_name

        try {
            mana = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0]
        } catch (e: Exception) {
        }
        if (mana != null) {
            if (mana!!.connState) {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@DrawBillActivity, R.mipmap.tms_icon_bluetooth_true))
            } else {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@DrawBillActivity, R.mipmap.tms_icon_bluetooth_false))
            }
        } else {
        }
    }

    private val REQUEST_BLUETOOTH_PEMISSION = 64
    private val REQUEST_COARSE_LOCATION = 128

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), REQUEST_BLUETOOTH_PEMISSION)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_COARSE_LOCATION)
        }
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor(ContextCompat.getColor(this, R.color.color_white))
        return R.layout.activity_drawbill
    }

    override fun initPresenter() {
        presenter = BillDrawPresenter.getInstance(this)
    }

    private fun showDestinationPop() {
        val dataList: List<BillingDestinationDB>? = DataSupport
                .order("weight DESC")
                .limit(12)
                .find(BillingDestinationDB::class.java)
        if (dataList != null) {
            if (!dataList.isEmpty()) {
                val view = LayoutInflater.from(this).inflate(R.layout.view_popwindow, null)
                val popGrid = view.findViewById(R.id.popGrid) as GridView
                val dialog = AlertDialog.Builder(this)
                        .setMessage("历史目的地")
                        .setView(view)
                        .create()
                popGrid.adapter = object : ZCommonAdapter<BillingDestinationDB>(this, dataList, R.layout.spinner_text) {
                    override fun convert(holder: ZViewHolder?, item: BillingDestinationDB?, position: Int) {
                        val tvDes = holder!!.getView<TextView>(R.id.spinner_tv)
                        tvDes.text = item!!.destination
                    }
                }
                popGrid.onItemClickListener = object : AdapterView.OnItemClickListener {
                    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        edDestination.setText(dataList[position].destination)
                        dialog?.dismiss()
                        edDestination.requestFocus()
                        if (edDestination.text.isNotEmpty()) {
                            edDestination.setSelection(edDestination.text.length)
                        }
                    }
                }
                dialog.show()
            }
        }

    }

    override fun initView() {
        edPrinCount.setText(ZShrPrefencs.getInstance().copyCount.toString())
        btCommonGoods.setOnClickListener(this)
        btCommonPacks.setOnClickListener(this)
        btOtherFee.setOnClickListener(this)
        btEnsure.setOnClickListener(this)
        btQuit.setOnClickListener(this)
        imBlueTooth.setOnClickListener(this)
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
                        edDestination.requestFocus()
                        showDestinationPop()
                    }
                }
            }
        }
        spPayMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val paymethod = ZShrPrefencs.getInstance().payMethod
                if (paymethod.isNotEmpty()) {
                    strPayMethod = paymethod
                } else {
                    strPayMethod = getString(R.string.cash_payment)
                }
                tvMutiPay.text = strPayMethod + ": " + allFeeTotal
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainBillEntity!!.cash_payment = null
                mainBillEntity!!.collect_payment = null
                mainBillEntity!!.back_payment = null
                mainBillEntity!!.monthly_payment = null
                mainBillEntity!!.payment_deduction = null
                if (position != 5) {
                    STATE_PAY = SINGLE_PAY
                    strPayMethod = resources.getStringArray(R.array.method_pay)[position]
                    tvMutiPay.text = strPayMethod + ": " + allFeeTotal
                } else {
                    STATE_PAY = MUTI_PAY
                    isMutiExcute = false
                    strPayMethod = resources.getStringArray(R.array.method_pay)[5]
                    startActivityForResult(DrawBillMutiActivity::class.java, GET_MUTIPAH_FOR_BILL)
                }
            }
        }

        tvss.setOnClickListener {
            val intent = Intent(DrawBillActivity@ this, RemarkActivity::class.java)
            startActivityForResult(intent, 2999)
        }
        spGetMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val delimathod = ZShrPrefencs.getInstance().deliMethod
                if (delimathod.isNotEmpty()) {
                    strGetMethod = delimathod
                } else {
                    strGetMethod = getString(R.string.self_get)
                }
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strGetMethod = resources.getStringArray(R.array.method_getGoods)[position]
            }
        }
        edAcvance.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    advance = 0.0
                } else {
                    try {
                        advance = s!!.toString().toDouble()
                    } catch (e: NumberFormatException) {
                        advance = 0.0
                        Toasty.error(this@DrawBillActivity, getString(R.string.help_please))
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
                        Toasty.error(this@DrawBillActivity, getString(R.string.help_please))
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
                        Toasty.error(this@DrawBillActivity, getString(R.string.help_please))
                    }
                }
                getAllTotalFee()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        edConEEName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val ss = s!!.toString().trim()
                if (!TextUtils.isEmpty(ss) && !is_e && !ss.contains("   ")) {
                    DataSupport.where("reciveName LIKE ?", "%$ss%")
                            .findAsync(BillingDrawDataBase::class.java).listen(object : FindMultiCallback {
                                override fun <T : Any?> onFinish(t: MutableList<T>?) {
                                    dataList = t as MutableList<BillingDrawDataBase>
                                    dataList = DistinctQuery.distincetReciveName(dataList)
                                    if (dataList != null || dataList!!.size != 0) {
                                        ai_consignee()
                                    }
                                }
                            })
                }
                is_e = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edConEEName.setOnItemClickListener { parent, view, position, id ->
            is_ep = true
            is_e = true
            edConEEName.setText(dataList!![position].reciveName)
            edConEEPhone.setText(dataList!![position].recivePhone)
        }

        edConEEPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val ss = s!!.toString().trim()
                if (!TextUtils.isEmpty(ss) && !is_ep && !ss.contains("   ")) {
                    if (ss.length < 4) {
                        return
                    }
                    DataSupport.where("recivePhone LIKE ?", "%$ss")
                            .findAsync(BillingDrawDataBase::class.java).listen(object : FindMultiCallback {
                                override fun <T : Any?> onFinish(t: MutableList<T>?) {
                                    dataList = t as MutableList<BillingDrawDataBase>
                                    dataList = DistinctQuery.distinctRecivePhone(dataList)
                                    if (dataList != null || dataList!!.size != 0) {
                                        ai_eePhone()
                                    }
                                }
                            })
                }
                is_ep = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edConEEPhone.setOnItemClickListener { parent, view, position, id ->
            is_e = true
            is_ep = true
            edConEEName.setText(dataList!![position].reciveName)
            edConEEPhone.setText(dataList!![position].recivePhone)
        }

        edConRRName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val ss = s!!.toString().trim()
                if (!TextUtils.isEmpty(ss) && !is_r && !ss.contains("   ")) {
                    DataSupport.where("sendName LIKE ?", "%$ss%")
                            .findAsync(BillingDrawDataBase::class.java).listen(object : FindMultiCallback {
                                override fun <T : Any?> onFinish(t: MutableList<T>?) {
                                    dataList = t as MutableList<BillingDrawDataBase>
                                    dataList = DistinctQuery.distinctSendName(dataList)
                                    if (dataList != null || dataList!!.size != 0) {
                                        ai_consigner()
                                    }
                                }
                            })
                }
                is_r = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        edConRRName.setOnItemClickListener { parent, view, position, id ->
            is_r = true
            is_rp = true
            edConRRName.setText(dataList!![position].sendName)
            edConRRPhone.setText(dataList!![position].sendPhone)
        }

        edConRRPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val ss = s!!.toString().trim()
                if (!TextUtils.isEmpty(ss) && !is_rp && !ss.contains("   ")) {
                    if (ss.length < 4) {
                        return
                    }
                    DataSupport.where("sendPhone LIKE ?", "%$ss")
                            .findAsync(BillingDrawDataBase::class.java).listen(object : FindMultiCallback {
                                override fun <T : Any?> onFinish(t: MutableList<T>?) {
                                    dataList = t as MutableList<BillingDrawDataBase>
                                    dataList = DistinctQuery.distinctSendPhone(dataList)
                                    if (dataList != null || dataList!!.size != 0) {
                                        ai_rrPhone()
                                    }
                                }
                            })

                }
                is_rp = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edConRRPhone.setOnItemClickListener { parent, view, position, id ->
            is_r = true
            is_rp = true
            edConRRName.setText(dataList!![position].sendName)
            edConRRPhone.setText(dataList!![position].sendPhone)
        }

        edMark.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                val ss = s!!.toString().trim()
//                if (!TextUtils.isEmpty(ss)) {
//                    DataSupport.where("remark LIKE ?", "%$ss%")
//                            .findAsync(BillingDrawDataBase::class.java).listen(object : FindMultiCallback {
//                                override fun <T : Any?> onFinish(t: MutableList<T>?) {
//                                    dataList = t as MutableList<BillingDrawDataBase>
//                                    dataList = DistinctQuery.distincetRemark(dataList)
//                                    if (dataList != null || dataList!!.size != 0) {
//                                        ai_remark()
//                                    }
//                                }
//                            })
//                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        )
        edMark.setOnItemClickListener { parent, view, position, id ->
            val temp = dataList!![position].remark
            edMark.setText(temp)
        }

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

        val paymethod = ZShrPrefencs.getInstance().payMethod
        if (paymethod.isNotEmpty()) {
            strPayMethod = paymethod
            when (paymethod) {
                "现付" -> spPayMethod.setSelection(0)
                "到付" -> spPayMethod.setSelection(1)
//                "回付" -> spPayMethod.setSelection(2)
//                "月结" -> spPayMethod.setSelection(3)
//                "货款扣" -> spPayMethod.setSelection(4)
//                "多笔付" -> spPayMethod.setSelection(5)
            }
        } else {
            strPayMethod = getString(R.string.cash_payment)
        }
        val delimathod = ZShrPrefencs.getInstance().deliMethod
        if (delimathod.isNotEmpty()) {
            strGetMethod = delimathod
            when (delimathod) {
                "自提" -> spGetMethod.setSelection(0)
                "送货" -> spGetMethod.setSelection(1)
            }
        } else {
            strGetMethod = getString(R.string.self_get)
        }
    }
    //AutoCompleteTextView adapter 的方法

    private fun ai_consignee() {
        val arrList = ArrayList<String>()
        dataList!!.forEach {
            arrList.add(it.reciveName + "   " + it.recivePhone)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrList)
        edConEEName.setAdapter(adapter)
        edConEEName.showDropDown()
    }

    private fun ai_eePhone() {
        val arrList = ArrayList<String>()
        dataList!!.forEach {
            arrList.add(it.reciveName + "   " + it.recivePhone)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrList)
        edConEEPhone.setAdapter(adapter)
        edConEEPhone.showDropDown()
    }

    private fun ai_rrPhone() {
        val arrList = ArrayList<String>()
        dataList!!.forEach {
            arrList.add(it.sendName + "   " + it.sendPhone)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrList)
        edConRRPhone.setAdapter(adapter)
        edConRRPhone.showDropDown()
    }

    private fun ai_consigner() {
        val arrList = ArrayList<String>()
        dataList!!.forEach {
            arrList.add(it.sendName + "   " + it.sendPhone)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrList)
        edConRRName.setAdapter(adapter)
        edConRRName.showDropDown()
    }

    private fun ai_remark() {
        val arrList = ArrayList<String>()
        dataList!!.forEach {
            arrList.add(it.remark + "m")
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrList)
        edMark.setAdapter(adapter)
        edMark.showDropDown()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btCommonGoods -> startActivityForResult(DrawGoodsActivity::class.java, GET_GOODS_FOR_BILL)
            R.id.btCommonPackage -> startActivityForResult(DrawPacksActivity::class.java, GET_PACKS_FOR_BILL)
            R.id.btOtherFee -> startActivityForResult(DrawBillOtherFeActivity::class.java, GET_OTHERFEE_FOR_BILL)
            R.id.btQuit -> showQuitDialog()
            R.id.btEnsureSave -> ensureDrawBill()
            R.id.imBlueTooth -> {
                if (mana != null) {
                    if (mana!!.connState) {
                        imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@DrawBillActivity, R.mipmap.tms_icon_bluetooth_true))
                        Toasty.info(this@DrawBillActivity, "打印机连接正常").show()
                    } else {
                        imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@DrawBillActivity, R.mipmap.tms_icon_bluetooth_false))
                        Toasty.error(this@DrawBillActivity, "打印机未连接,请到个人中心设置").show()
                    }
                } else {
                    Toasty.error(this@DrawBillActivity, "打印机未连接,请到个人中心设置").show()
                }
            }
        }
    }

    private fun getOtherFeeFromResult(entity: BillingDrawMain) {
        mainBillEntity!!.declared_value = null
        mainBillEntity!!.valuation_fee = null
        mainBillEntity!!.delivery_fee = null
        mainBillEntity!!.receiving_fee = null
        mainBillEntity!!.handling_fee = null
        mainBillEntity!!.forklift_fee = null
        mainBillEntity!!.packing_fee = null
        mainBillEntity!!.upstair_fee = null
        mainBillEntity!!.return_fee = null
        mainBillEntity!!.under_charge_fee = null
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
        mainBillEntity!!.cash_payment = null
        mainBillEntity!!.collect_payment = null
        mainBillEntity!!.back_payment = null
        mainBillEntity!!.monthly_payment = null
        mainBillEntity!!.payment_deduction = null
        mainBillEntity!!.cash_payment = entity.cash_payment
        mainBillEntity!!.collect_payment = entity.collect_payment
        mainBillEntity!!.back_payment = entity.back_payment
        mainBillEntity!!.monthly_payment = entity.monthly_payment
        mainBillEntity!!.payment_deduction = entity.payment_deduction
    }

    private fun showQuitDialog() {
        val alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.quit_billing))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    this.finish()
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->

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
        if (ZShrPrefencs.getInstance().blueToothDeviceName.equals(ConstantPool.BT_XY_NAME)) {
            if (AppUserToken.getInstance().isXy) {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@DrawBillActivity, R.mipmap.tms_icon_bluetooth_true))
            } else {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@DrawBillActivity, R.mipmap.tms_icon_bluetooth_false))
            }
        }
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
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
            if (getEDString(edPackages) == "") {
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
        } else if (resultCode == Activity.RESULT_OK && requestCode == 2999) {
            val remark = data!!.getStringExtra("remark")
            edMark.setText(remark.toString())
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

        val advance = getEDStringOrNull(edAcvance)?.toDoubleOrNull()

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
//        mainBillEntity!!.delivery_method = getEDString()a

        mainBillEntity!!.number_of_copies = getEDStringOrNull(edRecipe)?.toLongOrNull()
        mainBillEntity!!.transit_destination = getEDStringOrNull(edDestination)
        mainBillEntity!!.remarks = getEDStringOrNull(edMark)
        mainBillEntity!!.delivery_method = strGetMethod
        mainBillEntity!!.payment_method = strPayMethod

        mainBillEntity!!.freight = getEDStringOrNull(edFreight)?.toDoubleOrNull()
        mainBillEntity!!.advance = getEDStringOrNull(edAcvance)?.toDoubleOrNull()
        mainBillEntity!!.collection_fee = getEDStringOrNull(edCollection)?.toDoubleOrNull()

        mainBillEntity!!.input_article_number = getEDStringOrNull(edSelfInput)
        mainBillEntity!!.weight = getEDStringOrNull(edZhongliang)
        mainBillEntity!!.volume = getEDStringOrNull(edTiji)

        when (spPayMethod.selectedItemPosition) {
            0 -> mainBillEntity!!.cash_payment = allFeeTotal
            1 -> mainBillEntity!!.collect_payment = allFeeTotal
            2 -> mainBillEntity!!.back_payment = allFeeTotal
            3 -> mainBillEntity!!.monthly_payment = allFeeTotal
            4 -> mainBillEntity!!.payment_deduction = allFeeTotal
        }
        mainBillEntity!!.total_freight = allFeeTotal

        mainBillEntity!!.total_freight_receipts = getNullablString(otherPrint) + "&&" + getNullablString(mutiPrint)

        Logger.i(ZGsonUtils.getInstance().getJsonString(mainBillEntity))
        presenter!!.ensureDrawBilling(ZGsonUtils.getInstance().getJsonString(mainBillEntity))
        dialog!!.show()
    }

    private fun remoteDataBase(entity: BillingDrawMain) {
        var temp = ""
        var consigner_temp = ""
        var consignee_temp = ""
        if (entity.remarks == null) {
            temp = ""
        } else {
            temp = entity.remarks
            val findFirst = DataSupport.where("remark = ? ",
                    temp).findFirst(BillingDrawDataBase::class.java)
            if (findFirst == null) {
                val billEntity = BillingDrawDataBase()
                billEntity.remark = temp
                billEntity.save()
            }
        }
        if (entity.consigner == null) {
            consigner_temp = ""
        } else {
            consigner_temp = entity.consigner
        }

        if (entity.consignee == null) {
            consignee_temp = ""
        } else {
            consignee_temp = entity.consignee
        }

        val send = DataSupport.where("sendName = ? ",
                consigner_temp).findFirst(BillingDrawDataBase::class.java)
        if (send == null) {
            val billEntity = BillingDrawDataBase()
            billEntity.sendName = entity.consigner
            billEntity.sendPhone = entity.consigner_phone
            billEntity.billWeight = 1
            billEntity.save()
        } else {
            val values = ContentValues()
            if (send.sendName != null && entity.consigner_phone != null) {
                values.put("sendName", entity.consigner)
                values.put("sendPhone", entity.consigner_phone)
            }
            values.put("billWeight", send.billWeight + 1)
            DataSupport.update(BillingDrawDataBase::class.java, values, send.id.toLong())
        }

        val recive = DataSupport.where("reciveName = ? ",
                consignee_temp).findFirst(BillingDrawDataBase::class.java)
        if (recive == null) {
            val billEntity = BillingDrawDataBase()
            billEntity.reciveName = entity.consignee
            billEntity.recivePhone = entity.consignee_phone
            billEntity.billWeight = 1
            billEntity.save()
        } else {
            val values = ContentValues()
            if (recive.reciveName != null && entity.consignee_phone != null) {
                values.put("reciveName", entity.consignee)
                values.put("recivePhone", entity.consignee_phone)
            }
            values.put("billWeight", recive.billWeight + 1)
            DataSupport.update(BillingDrawDataBase::class.java, values, recive.id.toLong())
        }


        if (entity.transit_destination != null) {
            val destination = DataSupport.where("destination = ?", entity.transit_destination).findFirst(BillingDestinationDB::class.java)
            if (destination == null) {
                val desEntity = BillingDestinationDB()
                desEntity.destination = entity.transit_destination
                desEntity.weight = 1
                desEntity.save()
            } else {
                val values = ContentValues()
                values.put("weight", destination.weight + 1)
                DataSupport.update(BillingDestinationDB::class.java, values, destination.id.toLong())
            }
        }
    }

    private var finalEntity: BillingDrawMain? = null

    override fun drawBillSucc(entity: BillingDrawMain) {
        Toasty.success(this, getString(R.string.bill_succee)).show()
        this.finalEntity = entity
        Log.e("TAG", entity.toString())
        this.finalEntity!!.barCode = entity.bar_code

        if (ZShrPrefencs.getInstance().netOn == 0) {
            Log.e("TAG", DeviceConnFactoryManager.getDeviceConnFactoryManagers().toString())
            startPrint(this.finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
            startBluePos(this.finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
            overThisActivitu()
        } else {
            startPrint2(finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
        }

        dataList = DataSupport.order("billWeight DESC").limit(5).find(BillingDrawDataBase::class.java)
        remoteDataBase(entity)
//        presenter!!.getBarCode(entity.uuid.toString())
        presenterX!!.addBillRecord(entity.uuid.toString(), entity.collection_fee.toString(), "TICKET")
    }

    override fun drawBillBarCodeSucc(barCode: String) {
        if (finalEntity == null) {
            return
        }
        this.finalEntity!!.barCode = barCode

        if (ZShrPrefencs.getInstance().netOn == 0) {
            startPrint(this.finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
            overThisActivitu()
        } else {
            startPrint2(finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
        }
    }

    override fun drawBillBarCodeError(barCode: String) {
        Toasty.error(this, "获取条形码失败").show()
        if (finalEntity == null) {
            return
        }
        this.finalEntity!!.barCode = barCode
        if (ZShrPrefencs.getInstance().netOn == 0) {
            startPrint(this.finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
            overThisActivitu()
        } else {
            startPrint2(finalEntity!!, ZPrintReceiver.REQUEST_BILL, 0, false)
        }
    }

    private val REQUEST_CODE = 0x004

    /**
     * 连接状态断开
     */
    private val CONN_STATE_DISCONN = 0x007
    /**
     * 使用打印机指令错误
     */
    private val PRINTER_COMMAND_ERROR = 0x008

    val MESSAGE_UPDATE_PARAMETER = 0x009

    val CONN_MOST_DEVICES = 0x11
    private val CONN_PRINTER = 0x12


    /**
     * ESC查询打印机实时状态指令
     */
    private val esc = byteArrayOf(0x10, 0x04, 0x02)

    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                CONN_STATE_DISCONN -> if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] != null) {
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].closePort(0)
                }
                PRINTER_COMMAND_ERROR ->
                    Toasty.error(this@DrawBillActivity, "错误").show()

                CONN_PRINTER ->
                    Toasty.error(this@DrawBillActivity, "未连接").show()

                MESSAGE_UPDATE_PARAMETER -> {
                    val strIp = msg.getData().getString("Ip")
                    val strPort = msg.getData().getString("Port")
                    //初始化端口信息
                    DeviceConnFactoryManager.Build()
                            //设置端口连接方式
                            .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                            //设置端口IP地址
                            .setIp(strIp)
                            //设置端口ID（主要用于连接多设备）
                            .setId(0)
                            //设置连接的热点端口号
                            .setPort(Integer.parseInt(strPort))
                            .build()
                    threadPool = ThreadPool.getInstantiation()
                    threadPool.addTask { DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].openPort() }
                }
                else -> {
                }
            }
        }
    }

    private var mHandler2: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                CONN_STATE_DISCONN -> if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1] != null) {
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].closePort(1)
                }
                PRINTER_COMMAND_ERROR ->
                    Toasty.error(this@DrawBillActivity, "错误").show()

                CONN_PRINTER ->
                    Toasty.error(this@DrawBillActivity, "未连接").show()

                MESSAGE_UPDATE_PARAMETER -> {
                    val strIp = msg.getData().getString("Ip")
                    val strPort = msg.getData().getString("Port")
                    //初始化端口信息
                    DeviceConnFactoryManager.Build()
                            //设置端口连接方式
                            .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                            //设置端口IP地址
                            .setIp(strIp)
                            //设置端口ID（主要用于连接多设备）
                            .setId(1)
                            //设置连接的热点端口号
                            .setPort(Integer.parseInt(strPort))
                            .build()
                    threadPool = ThreadPool.getInstantiation()
                    threadPool.addTask { DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].openPort() }
                }
                else -> {
                }
            }
        }
    }

    private fun startPrint2(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        val ipAddress = ZShrPrefencs.getInstance().netESCIp
        val ipAddressX = ZShrPrefencs.getInstance().netPosIp
        if (ipAddress == null || ipAddress == "" || ipAddressX == null || ipAddressX == "") {
            Toasty.error(this@DrawBillActivity, "部分错误，请前往个人中心网口打印设置ip")
        }
        dialog!!.dismiss()
        print2Check(entity, state, number, isSign)
    }

    private fun print2Check(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        if (MainActivity.binder != null) {
            MainActivity.binder!!.checkLinkedState(object : UiExecute {
                override fun onsucess() {
                    Log.e("TAG", "onSucess")
                    printReciver!!.xsetBillPrintEntity(entity, state, number, isSign)
                    printReciver!!.sendBillRecepit(number)
                    overThisActivitu()
                }

                override fun onfailed() {
                    print2ConnPiao(entity, state, number, isSign)
                    Log.e("TAG", "onFailed")
                }
            })
        }
        if (MainActivity.binderX != null) {
            MainActivity.binderX!!.checkLinkedState(object : UiExecute {
                override fun onsucess() {
                    Log.e("TAG", "onSucessX")
                    val type: Int = ZShrPrefencs.getInstance().biaogeshi
                    if (type == 0) {
                        printReciver!!.sendPosRecepit(entity)
                    } else if (type == 1) {
                        printReciver!!.sendPosRecepitSimple(entity)
                    } else if (type == 2) {
                        printReciver!!.sendPosRecepitBigCompany(entity)
                    }
                }

                override fun onfailed() {
                    Log.e("TAG", "onFailedX")
                    print2ConnBiao(entity, state, number, isSign)
                }
            })
        }
    }

    private fun print2ConnPiao(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        val piaoIp = ZShrPrefencs.getInstance().netESCIp
        MainActivity.binder!!.connectNetPort(piaoIp, 9100, object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "票据 连接成功 准备打印")
                printReciver!!.xsetBillPrintEntity(entity, state, number, isSign)
                printReciver!!.sendBillRecepit(number)
                overThisActivitu()
            }

            override fun onfailed() {
                Log.e("TAG", "票据 连接失败 ")
                Toasty.error(this@DrawBillActivity, "打印失败，请前往个人中心设置")
                overThisActivitu()

            }
        })
    }

    private fun print2ConnBiao(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        val biaoIp = ZShrPrefencs.getInstance().netPosIp
        MainActivity.binderX!!.connectNetPort(biaoIp, 9100, object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "标签 连接成功 准备打印")
                val type: Int = ZShrPrefencs.getInstance().biaogeshi
                if (type == 0) {
                    printReciver!!.sendPosRecepit(entity)
                } else if (type == 1) {
                    printReciver!!.sendPosRecepitSimple(entity)
                } else if (type == 2) {
                    printReciver!!.sendPosRecepitBigCompany(entity)
                }
                overThisActivitu()
            }

            override fun onfailed() {
                Log.e("TAG", "标签 连接失败 ")
                Toasty.error(this@DrawBillActivity, "打印失败，请前往个人中心设置")
                overThisActivitu()
            }
        })
    }

    private fun overThisActivitu() {
        Handler().postDelayed({
            dialog?.dismiss()
            this@DrawBillActivity.finish()
        }, 2000)
    }

    private fun startPrint(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].connState) {
            Toasty.error(this, "请先连接打印机").show()
            return
        }
        threadPool = ThreadPool.getInstantiation()
        threadPool.addTask {
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].currentPrinterCommand === PrinterCommand.ESC) {
                printReciver!!.xsetBillPrintEntity(entity, state, number, isSign)
                printReciver!!.sendBillRecepit(number)
            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget()
            }
        }
    }

    private fun startBluePos(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1] == null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].connState) {
            Toasty.error(this, "请先连接打印机").show()
            return
        }
        threadPool = ThreadPool.getInstantiation()
        threadPool.addTask {
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].currentPrinterCommand === PrinterCommand.TSC) {
                Log.e("TAG", "sucess......")
                val type: Int = ZShrPrefencs.getInstance().biaogeshi
                if (type == 0) {
                    printReciver!!.sendPosBB(entity)
                } else if (type == 1) {
                    printReciver!!.sendPosBBSimple(entity)
                } else if (type == 2) {
                    printReciver!!.sendPostBBBigCompany(entity)
                }
            } else {
                mHandler2.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget()
            }
        }
    }

    override fun drawBillError() {
        dialog?.dismiss()
        dataList = DataSupport.order("billWeight DESC").limit(5).find(BillingDrawDataBase::class.java)
        Toasty.error(this, "开票失败").show()
    }

    override fun onDestroy() {
//        unregisterReceiver(printReciver)
//        unregisterReceiver(connReceiver)
        super.onDestroy()
    }

    fun btnDisConn(view: View) {
        mHandler.obtainMessage(CONN_STATE_DISCONN).sendToTarget()
    }
}