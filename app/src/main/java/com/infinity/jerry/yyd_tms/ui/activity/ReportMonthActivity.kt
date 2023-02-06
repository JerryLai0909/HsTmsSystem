/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivityWithOutOrientation
import com.infinity.jerry.yyd_tms.data.report.ReportEntity
import com.infinity.jerry.yyd_tms.data.report.ReportPointEntity
import com.infinity.jerry.yyd_tms.data.request_entity.ReportRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.ReportPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewReport
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.DoubleDatePickerDialog2
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.RepeatableSpinner
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jerry on 2017/7/5.
 */
class ReportMonthActivity : BaseActivityWithOutOrientation(), IViewReport {
    override fun getPointDetailsSucc(entity: ReportPointEntity) {

    }

    override fun getPointListSucc(entity: ReportPointEntity) {
    }

    val imBack: ImageView by bindView<ImageView>(R.id.imBack)
    val tvCondition: TextView by bindView<TextView>(R.id.tvTime)
    val spinner: RepeatableSpinner by bindView<RepeatableSpinner>(R.id.spinner)
    val imRefresh: ImageView by bindView<ImageView>(R.id.imRefresh)
    val leftListView: ListView by bindView<ListView>(R.id.left_listView)
    val rightListView: ListView by bindView<ListView>(R.id.rightListView)
    var dialog: Dialog? = null
    var presenter: ReportPresenter? = null
    val mainll: LinearLayout by bindView<LinearLayout>(R.id.main_ll)
    val ca = Calendar.getInstance()
    val mYear = ca.get(Calendar.YEAR)
    val mMonth = ca.get(Calendar.MONTH) + 1
    val mDay = ca.get(Calendar.DAY_OF_MONTH)
    var year: String = ""
    var month: String = ""
    var day: String = ""
    var lastMonth: String = ""
    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        year = mYear.toString()
        if (mMonth < 10) {
            month = "0" + mMonth.toString()
            lastMonth = "0" + (mMonth - 1).toString()
        } else {
            month = mMonth.toString()
            lastMonth = (mMonth - 1).toString()
        }
        if (mDay < 10) {
            day = "0" + mDay.toString()
        } else {
            day = mDay.toString()
        }

    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_report
    }

    override fun initPresenter() {
        presenter = ReportPresenter.getInstance(this)
    }

    override fun initView() {
        imBack.setOnClickListener { this.finish() }
        setListViewOnTouchAndScrollListener(leftListView, rightListView)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val request = ReportRequest()
                when (position) {
                    0 -> {
                        tvCondition.setText("今天")
                        val monthl = mMonth
                        var monthStr = ""
                        if (monthl < 10) {
                            monthStr = "0" + monthl
                        } else {
                            monthStr = monthl.toString()
                        }
                        request.day = year + "-" + monthStr + "-" + day
                        sendRequest(request)
                    }
                    1 -> {
                        tvCondition.setText("当月")
                        request.month = year + "-" + month
                        sendRequest(request)
                    }
                    2 -> {
                        tvCondition.setText("上月")
                        request.month = year + "-" + lastMonth
                        sendRequest(request)
                    }
                    3 -> {
                        val dialog = DoubleDatePickerDialog2(this@ReportMonthActivity, object : DoubleDatePickerDialog2.OnDateSetListener {
                            override fun onDateSet(startDatePicker: DatePicker?, startYear: Int, startMonthOfYear: Int, startDayOfMonth: Int, endDatePicker: DatePicker?, endYear: Int, endMonthOfYear: Int, endDayOfMonth: Int) {
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

                                request.startTime = startYear.toString() + "-" + monthStr + "-" + startDay
                                request.setEndTime(endYear.toString() + "-" + endMonthStr + "-" + endDay)
                                tvCondition.setText(request.startTime + " --> " + request.endTime)
                                sendRequest(request)
                            }
                        }, mYear, mMonth, mDay)
                        dialog.show()
                    }
                }
            }
        }
    }

    private fun sendRequest(request: ReportRequest) {
        dialog!!.show()
        presenter!!.getMonthList(ZGsonUtils.getInstance().getJsonString(request))
    }

    private fun dealWithData(lists: MutableList<ReportEntity>) {
        val leftList = ArrayList<String>()
        val totalEntity = ReportEntity()
        lists.forEach {
            leftList.add(it.days)
            totalEntity.totalBill += getZeroableInt(it.totalBill)
            totalEntity.quantity += getZeroableInt(it.quantity)
            totalEntity.freight += getZeroableDouble(it.freight)
            totalEntity.valuation_fee += getZeroableDouble(it.valuation_fee)
            totalEntity.delivery_fee += getZeroableDouble(it.delivery_fee)
            totalEntity.advance += getZeroableDouble(it.advance)
            totalEntity.receiving_fee += getZeroableDouble(it.receiving_fee)
            totalEntity.handling_fee += getZeroableDouble(it.handling_fee)
            totalEntity.packing_fee += getZeroableDouble(it.packing_fee)
            totalEntity.upstair_fee += getZeroableDouble(it.upstair_fee)
            totalEntity.forklift_fee += getZeroableDouble(it.forklift_fee)
            totalEntity.return_fee += getZeroableDouble(it.return_fee)
            totalEntity.under_charge_fee += getZeroableDouble(it.under_charge_fee)
            totalEntity.warehousing_fee += getZeroableDouble(it.warehousing_fee)
            totalEntity.total_freight += getZeroableDouble(it.total_freight)
            totalEntity.collection_fee += getZeroableDouble(it.collection_fee)
            totalEntity.cash_payment += getZeroableDouble(it.cash_payment)
            totalEntity.collect_payment += getZeroableDouble(it.collect_payment)
            totalEntity.back_payment += getZeroableDouble(it.back_payment)
            totalEntity.monthly_payment += getZeroableDouble(it.monthly_payment)
            totalEntity.payment_deduction += getZeroableDouble(it.payment_deduction)
        }
        leftList.add(getString(R.string.sum))
        lists.add(totalEntity)
        initListViews(leftList, lists)

    }

    private fun initListViews(leftList: ArrayList<String>, lists: MutableList<ReportEntity>) {
        leftListView.adapter = object : ZCommonAdapter<String>(this, leftList, R.layout.item_report_left) {
            override fun convert(holder: ZViewHolder?, item: String?, position: Int) {
                val tvText = holder!!.getView<AutoScaleTextView>(R.id.tvText)
                tvText.text = item!!
            }
        }
        rightListView.adapter = object : ZCommonAdapter<ReportEntity>(this, lists, R.layout.item_report_list) {
            override fun convert(holder: ZViewHolder?, item: ReportEntity?, position: Int) {
                val tvBill = holder!!.getView<TextView>(R.id.reList_billCount)
                tvBill.text = getZeroableInt(item!!.totalBill).toString()
                val tvQuantity = holder.getView<TextView>(R.id.reList_quantity)
                tvQuantity.text = getZeroableInt(item.quantity).toString()
                val tvFreight = holder.getView<TextView>(R.id.reList_freight)
                tvFreight.text = get0DoubleOrNull(item.freight)

                val tvCashPayment = holder.getView<TextView>(R.id.reList_cash_payment)
                tvCashPayment.text = get0DoubleOrNull(item.cash_payment)
                val tvCollePayment = holder.getView<TextView>(R.id.reList_collect_payment)
                tvCollePayment.text = get0DoubleOrNull(item.collect_payment)
                val tvBack = holder.getView<TextView>(R.id.reList_back_payment)
                tvBack.text = get0DoubleOrNull(item.back_payment)
                val tvMonthly = holder.getView<TextView>(R.id.reList_monthly_payment)
                tvMonthly.text = get0DoubleOrNull(item.monthly_payment)
                val tvPaymenDeduction = holder.getView<TextView>(R.id.reList_payment_deduction)
                tvPaymenDeduction.text = get0DoubleOrNull(item.payment_deduction)

                val tvOtherFee = holder.getView<TextView>(R.id.reList_otherFee)
                tvOtherFee.text = get0DoubleOrNull(item.valuation_fee + item.delivery_fee
                        + item.advance + item.receiving_fee + item.handling_fee + item.packing_fee
                        + item.upstair_fee + item.forklift_fee + item.warehousing_fee)

                val tvBackFee = holder.getView<TextView>(R.id.reList_backFee)
                tvBackFee.text = get0DoubleOrNull(item.return_fee + item.under_charge_fee)


                val tvTotal = holder.getView<TextView>(R.id.reList_total_freight)
                tvTotal.text = get0DoubleOrNull(item.total_freight)

                val tvCollection = holder.getView<TextView>(R.id.reList_collection_fee)
                tvCollection.text = get0DoubleOrNull(item.collection_fee)

            }
        }
        leftListView.setOnItemClickListener { parent, view, position, id ->
            if (position == lists.size - 1) return@setOnItemClickListener
            val bundle = Bundle()
            bundle.putString("reportDay", leftList[position])
            startActivity(ReportDayActivity::class.java, bundle)
        }
    }

    override fun getBillsSucc(lists: MutableList<ReportEntity>) {
        dialog!!.dismiss()
        if (lists.size == 0) {
            Toasty.info(this, getString(R.string.no_data)).show()
        } else {
            dealWithData(lists)
        }

    }

    override fun getBillsError() {
        dialog!!.dismiss()
    }

    override fun onNetWorkError() {
        dialog!!.dismiss()
    }

    fun setListViewOnTouchAndScrollListener(listView1: ListView, listView2: ListView) {
        //设置listview2列表的scroll监听，用于滑动过程中左右不同步时校正
        listView2.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                //如果停止滑动
                if (scrollState == 0 || scrollState == 1) {
                    //获得第一个子view
                    val subView = view.getChildAt(0)

                    if (subView != null) {
                        val top = subView.top
                        val top1 = listView1.getChildAt(0).top
                        val position = view.firstVisiblePosition

                        //如果两个首个显示的子view高度不等
                        if (top != top1) {
                            listView1.setSelectionFromTop(position, top)
                            listView2.setSelectionFromTop(position, top)

                        }
                    }
                }
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                val subView = view.getChildAt(0)
                if (subView != null) {
                    val top = subView.top
                    //      //如果两个首个显示的子view高度不等
                    //                    int top1 = listView1.getChildAt(0).getTop();
                    //                    if (!(top1 - 7 < top && top < top1 + 7)) {
                    listView1.setSelectionFromTop(firstVisibleItem, top)
                    listView2.setSelectionFromTop(firstVisibleItem, top)
                    //                    }
                }
            }
        })

        //设置listview1列表的scroll监听，用于滑动过程中左右不同步时校正
        listView1.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                if (scrollState == 0 || scrollState == 1) {
                    //获得第一个子view
                    val subView = view.getChildAt(0)

                    if (subView != null) {
                        val top = subView.top
                        val top1 = listView2.getChildAt(0).top
                        val position = view.firstVisiblePosition

                        //如果两个首个显示的子view高度不等
                        if (top != top1) {
                            listView1.setSelectionFromTop(position, top)
                            listView2.setSelectionFromTop(position, top)
                        }
                    }
                }
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                val subView = view.getChildAt(0)
                if (subView != null) {
                    val top = subView.top
                    listView1.setSelectionFromTop(firstVisibleItem, top)
                    listView2.setSelectionFromTop(firstVisibleItem, top)

                }
            }
        })
    }

}