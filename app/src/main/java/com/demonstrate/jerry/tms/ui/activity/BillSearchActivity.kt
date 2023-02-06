/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.data.request_entity.MyBillRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.*
import com.infinity.jerry.yyd_tms.utils.DateUtil
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jerry on 2017/6/13.
 */
class BillSearchActivity : BaseActivity() {
    private val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    private val coverFrame: FrameLayout by bindView<FrameLayout>(R.id.coverFrame)
    private val condition: LinearLayout by bindView<LinearLayout>(R.id.condition)
    private val animDuration: Long = 300

    private val gridFee: ZMesureGridView by bindView<ZMesureGridView>(R.id.grid_fee)
    private val gridState: ZMesureGridView by bindView<ZMesureGridView>(R.id.grid_state)
    private val gridPayMethos: ZMesureGridView by bindView<ZMesureGridView>(R.id.grid_payMethod)

    private val feeList: MutableList<SearchFee> = DirtyData.searchFeeList
    private val stateList: MutableList<SearchState> = DirtyData.searchStateList
    private val methodList: MutableList<SearchPayMethod> = DirtyData.searchPayMethod

    private var adapterFee: ZCommonAdapter<SearchFee>? = null
    private var adapterState: ZCommonAdapter<SearchState>? = null
    private var adapterMethod: ZCommonAdapter<SearchPayMethod>? = null
    //spinner 选择发站 到站
    private val spInit: Spinner by bindView<Spinner>(R.id.spInit)
    private val spTermi: Spinner by bindView<Spinner>(R.id.spTermi)
    private val timePicker: LinearLayout by bindView<LinearLayout>(R.id.timeChoose)
    private val tvTime: TextView by bindView<TextView>(R.id.tvTime)
    private val tvEnsure: TextView by bindView<TextView>(R.id.tvEnsureSearch)
    private val tvClear: TextView by bindView<TextView>(R.id.tvClear)

    private val edConsignerName: EditText by bindView<EditText>(R.id.edConsignerName)
    private val edConsignerPhone: EditText by bindView<EditText>(R.id.edConsignerPhone)
    private val edConsigneeName: EditText by bindView<EditText>(R.id.edConsigneeName)
    private val edConsigneePhone: EditText by bindView<EditText>(R.id.edConsigneePhone)
    private val edBatchNumber: EditText by bindView<EditText>(R.id.edBatchNumber)
    private val selfArtiNumber: EditText by bindView<EditText>(R.id.edSelfBillNumber)


    private val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    var requestEntity: MyBillRequest? = null
    var isCondition: Boolean = false

    val calendar: Calendar = Calendar.getInstance()

    var dialog: Dialog? = null

    var adapter: ZCommonAdapter<BillingDrawMain>? = null

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_billsearch
    }

    override fun initPresenter() {

    }

    override fun initData() {
        requestEntity = MyBillRequest()
        requestEntity!!.pageSize = "10";
        requestEntity!!.startTime = DateUtil.getCurrentDataYMD();
        requestEntity!!.endTime = DateUtil.getCurrentDataYMD();
        tvTime.setText(DateUtil.getCurrentDataYMD() + " 到 " + DateUtil.getCurrentDataYMD())
//        requestEntity!!.startTime = DateUtil.getCurrentDataYMD()
//        requestEntity!!.endTime = DateUtil.getCurrentDataYMD()
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        GiveMeCommonData.getInstance().giveMeSuperTerminal(object : CommonPresenter.OnSuperTerminalListener {
            override fun getSuperTermiSucc(lists: MutableList<PointEntity>?) {
                initSpinner(lists)
            }

            override fun getSuperTermiError() {
                Toasty.error(this@BillSearchActivity, getString(R.string.terminal_error)).show()
            }

        })
        sendRequest()
    }

    private fun initSpinner(lists: MutableList<PointEntity>?) {
        var strList = ArrayList<String>()
        strList.add(getString(R.string.all))
        lists?.forEach {
            strList.add(it.point_name)
        }
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strList)
        spInit.adapter = adapter
        spTermi.adapter = adapter
        spInit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                requestEntity!!.initial_station_id = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    requestEntity!!.initial_station_id = null
                } else {
                    requestEntity!!.initial_station_id = lists!![position - 1].uuid.toString()
                }
            }
        }
        spTermi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                requestEntity!!.terminal_station_id = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    requestEntity!!.terminal_station_id = null
                } else {
                    requestEntity!!.terminal_station_id = lists!![position - 1].uuid.toString()
                }
            }
        }
    }


    override fun initView() {
        titleBar.setTitle(getString(R.string.superSearch))
        titleBar.setTitleMode(ZTitlebar.Companion.MODE_TEXT)
        titleBar.setTvPlusText(getString(R.string.condition))
        titleBar.setOnTextModeListener(object : ZTitlebar.OnTextModeListener {
            override fun onClickTextMode() {
                if (!isCondition) {
                    startCondition()
                } else {
                    endCondition()
                }
            }
        })
        coverFrame.setOnClickListener({
            endCondition()
        })
        condition.visibility = View.GONE
        coverFrame.visibility = View.GONE
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                requestEntity!!.page = "1"
                sendRequest()
            }
        })
        feeList.forEach {
            it.isChoose = false
        }
        feeList[0].isChoose = true
        adapterFee = object : ZCommonAdapter<SearchFee>(this, feeList, R.layout.item_sear_grid) {
            override fun convert(holder: ZViewHolder?, item: SearchFee?, position: Int) {
                var tvState = holder!!.getView<TextView>(R.id.search_state)
                tvState.setText(item!!.key)
                if (item.isChoose) {
                    tvState.setBackgroundColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_orange))
                    tvState.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_white))
                } else {
                    tvState.setBackgroundColor(ContextCompat.getColor(this@BillSearchActivity, R.color.level4_gray_light3))
                    tvState.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_black))
                }
            }
        }
        gridFee.adapter = adapterFee
        gridFee.setOnItemClickListener { parent, view, position, id ->
            if (!feeList[position].isChoose) {
                for (searchFee in feeList) {
                    searchFee.isChoose = false
                }
                feeList[position].isChoose = true
                adapterFee!!.notifyDataSetChanged()
                if (position == 0) {
                    requestEntity!!.feeType = null
                } else {
                    requestEntity!!.feeType = feeList[position].value
                }

            }
        }
        stateList.forEach {
            it.isChoose = false
        }
        stateList[0].isChoose = true
        adapterState = object : ZCommonAdapter<SearchState>(this, stateList, R.layout.item_sear_grid) {
            override fun convert(holder: ZViewHolder?, item: SearchState?, position: Int) {
                var tvState = holder!!.getView<TextView>(R.id.search_state)
                tvState.setText(item!!.stateName)
                if (item.isChoose) {
                    tvState.setBackgroundColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_orange))
                    tvState.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_white))
                } else {
                    tvState.setBackgroundColor(ContextCompat.getColor(this@BillSearchActivity, R.color.level4_gray_light3))
                    tvState.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_black))
                }
            }
        }
        gridState.adapter = adapterState
        gridState.setOnItemClickListener { parent, view, position, id ->
            if (!stateList[position].isChoose) {
                for (searchFee in stateList) {
                    searchFee.isChoose = false
                }
                stateList[position].isChoose = true
                adapterState!!.notifyDataSetChanged()
            }
            if (position == 0) {
                requestEntity!!.invoice_status = null
            } else {
                requestEntity!!.invoice_status = stateList[position].state.toString()
            }

        }
        methodList.forEach { it.isChoose = false }
        methodList[0].isChoose = true
        adapterMethod = object : ZCommonAdapter<SearchPayMethod>(this, methodList, R.layout.item_sear_grid) {
            override fun convert(holder: ZViewHolder?, item: SearchPayMethod?, position: Int) {
                var tvState = holder!!.getView<TextView>(R.id.search_state)
                tvState.setText(item!!.stateName)
                if (item.isChoose) {
                    tvState.setBackgroundColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_orange))
                    tvState.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_white))
                } else {
                    tvState.setBackgroundColor(ContextCompat.getColor(this@BillSearchActivity, R.color.level4_gray_light3))
                    tvState.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_black))
                }

            }
        }
        gridPayMethos.adapter = adapterMethod
        gridPayMethos.setOnItemClickListener { parent, view, position, id ->
            if (!methodList[position].isChoose) {
                for (searchFee in methodList) {
                    searchFee.isChoose = false
                }
                methodList[position].isChoose = true
                adapterMethod!!.notifyDataSetChanged()
            }
            if (position == 0) {
                requestEntity!!.payment_method = null
            } else {
                requestEntity!!.payment_method = methodList[position].state.toString()
            }
        }

        //date picker
        timePicker.setOnClickListener {
            val dialog = DoubleDatePickerDialog(this, DoubleDatePickerDialog.OnDateSetListener { startDatePicker, startYear, startMonthOfYear, startDayOfMonth, endDatePicker, endYear, endMonthOfYear, endDayOfMonth ->
                Logger.i(startYear.toString() + " " + startMonthOfYear + " " + startDayOfMonth + "    "
                        + endYear + "   " + endMonthOfYear + "   " + endDayOfMonth)

                tvTime.setText(startYear.toString() + "-" + (startMonthOfYear + 1) + "-" + startDayOfMonth + " 到 "
                        + endYear + "-" + (endMonthOfYear + 1) + "-" + endDayOfMonth)

                val month = startMonthOfYear + 1
                var monthStr = ""
                if (month < 10) {
                    monthStr = "0" + month.toString()
                } else {
                    monthStr = month.toString()
                }

                val endMonth = endMonthOfYear + 1
                var endMonthStr = ""
                if (endMonth < 10) {
                    endMonthStr = "0" + endMonth.toString()
                } else {
                    endMonthStr = endMonth.toString()
                }

                var startDay = ""
                if (startDayOfMonth < 10) {
                    startDay = "0" + startDayOfMonth.toString()
                } else {
                    startDay = startDayOfMonth.toString()
                }

                var endDay = ""
                if (endDayOfMonth < 10) {
                    endDay = "0" + endDayOfMonth.toString()
                } else {
                    endDay = endDayOfMonth.toString()
                }

                requestEntity!!.startTime = startYear.toString() + "-" + monthStr + "-" + startDay
                requestEntity!!.endTime = endYear.toString() + "-" + endMonthStr + "-" + endDay


                val start = startYear.toString() + "-" + monthStr + "-" + startDay + " 00:00:00"
                val end = endYear.toString() + "-" + endMonthStr + "-" + endDay + " 00:00:00"
                val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                sss = sf.parse(start).time
                eee = sf.parse(end).time

                Log.e("TAG", (eee - sss).toString())

                if (eee - sss > 259200000) {
                    Toasty.info(this@BillSearchActivity, "时间筛选不能超过3天").show()
                }


            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        tvEnsure.setOnClickListener { ensureSearch() }
        tvClear.setOnClickListener { clearEveryThing() }
    }

    var sss = 0L
    var eee = 0L

    private fun ensureSearch() {
        requestEntity!!.consigner = getEDStringOrNull(edConsignerName)
        requestEntity!!.consigner_phone = getEDStringOrNull(edConsignerPhone)
        requestEntity!!.consignee = getEDStringOrNull(edConsigneeName)
        requestEntity!!.consignee_phone = getEDStringOrNull(edConsigneePhone)
        requestEntity!!.article_number = getEDStringOrNull(edBatchNumber)
        Logger.i(ZGsonUtils.getInstance().getJsonString(requestEntity))
        requestEntity!!.ownArticleNumber = getEDStringOrNull(selfArtiNumber)
        requestEntity!!.page = "1"
        sendRequest()
    }

    
    var isRequest: Boolean = false
    private fun sendRequest() {
        if (isRequest) {
            return
        }
        if (eee - sss > 259200000) {
            Toasty.info(this@BillSearchActivity, "时间筛选不能超过3天").show()
            return
        }
        dialog!!.show()
        isRequest = true
        Log.e("TAG", "223 " + requestEntity!!.toString())
        GiveMeCommonData.getInstance().giveMeSuperBills(ZGsonUtils.getInstance().getJsonString(requestEntity)
                , Integer.parseInt(requestEntity!!.pageSize), Integer.parseInt(requestEntity!!.page),
                object : CommonPresenter.OnSuperBillListener {
                    override fun getSuperBillSucc(bills: MyBillListEntity?) {
                        Log.e("TAG", "323 " + bills!!.toString())
                        swipeListView.swipeLayout!!.isRefreshing = false
                        if (condition.isShown) {
                            endCondition()
                        }
                        if (Integer.parseInt(requestEntity!!.page) > 1) {
                            adapter!!.appendListData(bills!!.pageList)
                            swipeListView.showListView()
                        } else {
                            initListView(bills!!.pageList)
                        }
                        requestEntity!!.page = (Integer.parseInt(requestEntity!!.page) + 1).toString()
                        isRequest = false
                        dialog!!.dismiss()
                    }

                    override fun getSuperBillError() {
                        isRequest = false
                        swipeListView.swipeLayout!!.isRefreshing = false
                        Toasty.error(this@BillSearchActivity, getString(R.string.bill_search_error)).show()
                        swipeListView.requestNoData()
                        dialog!!.dismiss()
                    }
                })
    }

    private fun initListView(lists: MutableList<BillingDrawMain>?) {
        adapter = object : ZCommonAdapter<BillingDrawMain>(this, lists!!, R.layout.item_billing) {
            override fun convert(holder: ZViewHolder?, item: BillingDrawMain?, position: Int) {
                val tvNumber = holder!!.getView<TextView>(R.id.blNumber)
                tvNumber.text = item!!.article_number

                val tvTime = holder!!.getView<TextView>(R.id.blTime)
                tvTime.text = DateUtil.getTimetrampWithStringYYMDHM(item!!.invoicedate)

                val tvState = holder.getView<TextView>(R.id.blState)
                tvState.text = getBillState(item.invoice_status.toInt())

                val tvInit = holder.getView<TextView>(R.id.blInit)
                tvInit.text = getNullablString(item.startStation)

                val tvTermi = holder.getView<TextView>(R.id.blTermi)
                if (item.transit_destination != null) {
                    tvTermi.text = getNullablString(item.endStation) + "(" + item.transit_destination + ")"
                } else {
                    tvTermi.text = getNullablString(item.endStation)
                }
                val tvInitInfo = holder.getView<TextView>(R.id.bl_inInfo)
                tvInitInfo.text = getNullablString(item.consigner) + "  " + getNullablString(item.consigner_phone)

                val tvTerInfo = holder.getView<TextView>(R.id.bl_terInfo)
                tvTerInfo.text = getNullablString(item.consignee) + "  " + getNullablString(item.consignee_phone)

                val tvGood = holder.getView<TextView>(R.id.bl_good)
                tvGood.text = getString(R.string.goods) + "  " + getNullablString(item.goods_name)

                val tvPack = holder.getView<TextView>(R.id.bl_pack)
                tvPack.text = getString(R.string.packages) + "  " + getNullablString(item.packaging)

                val tvPayMethod = holder.getView<AutoScaleTextView>(R.id.blPayMethod)
                tvPayMethod.text = "运费: " + getNullStringForDouble(item.freight) + " [" + getNullablString(item.payment_method) + "]"

                val tvFreight = holder.getView<AutoScaleTextView>(R.id.blFreight)
                tvFreight.text = "件数: " + item.quantity + " [" + getNullablString(item.delivery_method) + "]"

                val tvDeliMethod = holder.getView<TextView>(R.id.blDeliMethod)
                tvDeliMethod.text = "备注: " + getNullablString(item.remarks)

                val tvCollectxx = holder.getView<TextView>(R.id.tvCollectxx)
                tvCollectxx.text = "代收: " + getNullablString(item.collection_fee.toString())


                val tvDetail = holder.getView<TextView>(R.id.bl_detail)

                tvDetail.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putSerializable("billDetailEntity", item)
                    startActivityForResult(BillDetailActivity::class.java, bundle, 1)
                }

                var bt_1 = holder.getView<TextView>(R.id.bt_1)
                var bt_2 = holder.getView<TextView>(R.id.bt_2)
                var bt_3 = holder.getView<TextView>(R.id.bt_3)

                if (item.is_unusual == 1) {
                    bt_3.visibility = View.VISIBLE
                    bt_3.text = "订单异常!"
                    bt_3.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_orange))
                    bt_3.isEnabled = false
                } else {
                    bt_3.visibility = View.VISIBLE
                    bt_3.text = "添加异常"
                    bt_3.setTextColor(ContextCompat.getColor(this@BillSearchActivity, R.color.color_darkBlue))
                    bt_3.isEnabled = true
                    bt_3.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putLong("errorUuid", item.uuid)
                        startActivity(ErrorAddActivity::class.java, bundle)
                    }
                }

                var bt_4 = holder.getView<TextView>(R.id.bt_4)
                bt_4.visibility = View.VISIBLE
                bt_4.text = "中转"

                bt_4.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putLong("transUuid", item.uuid)
                    startActivity(TransAddActivity::class.java, bundle)
                }


            }
        }
        swipeListView.listView!!.adapter = adapter
        swipeListView.showListView()
        swipeListView.listView!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                if (view!!.lastVisiblePosition == view.count - 1) {
                    sendRequest()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestEntity!!.page = "1"
        sendRequest()
    }


    private fun clearEveryThing() {
        requestEntity = MyBillRequest()
        spInit.setSelection(0)
        spTermi.setSelection(0)
        edConsignerName.setText("")
        edConsignerPhone.setText("")
        edConsigneeName.setText("")
        edConsigneePhone.setText("")
        edBatchNumber.setText("")
        feeList.forEach { it.isChoose = false }
        feeList[0].isChoose = true
        stateList.forEach { it.isChoose = false }
        stateList[0].isChoose = true
        methodList.forEach { it.isChoose = false }
        methodList[0].isChoose = true
        adapterFee?.notifyDataSetChanged()
        adapterState?.notifyDataSetChanged()
        adapterMethod?.notifyDataSetChanged()
        tvTime.text = getString(R.string.can_choose_time)
    }

    //view's animation
    private fun startCondition() {
        coverFrame.visibility = View.VISIBLE
        condition.visibility = View.VISIBLE
        var anim = AnimationUtils.makeInAnimation(this, false)
        anim.duration = animDuration
        condition.animation = anim
        coverFrame.alpha = 0f
        coverFrame.animate().alpha(0.8f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        coverFrame.isEnabled = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        coverFrame.isEnabled = true
                    }
                })
    }

    private fun endCondition() {
        coverFrame.animate().alpha(0f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        coverFrame.isEnabled = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        coverFrame.isEnabled = true
                        coverFrame.visibility = View.GONE
                    }
                })
        var anim = AnimationUtils.makeOutAnimation(this, true)
        anim.duration = animDuration
        condition.animation = anim
        condition.visibility = View.GONE


    }
}