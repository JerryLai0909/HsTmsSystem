/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivityWithOutOrientation
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.report.ReportEntity
import com.infinity.jerry.yyd_tms.data.report.ReportPointEntity
import com.infinity.jerry.yyd_tms.data.request_entity.ReportRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.ReportPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewReport
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/7/5.
 */
class ReportDayActivity : BaseActivityWithOutOrientation(), IViewReport {
    override fun getPointListSucc(entity: ReportPointEntity) {

    }

    override fun getPointDetailsSucc(entity: ReportPointEntity) {
    }

    val xxTermial: TextView by bindView<TextView>(R.id.xx_termial)
    val xxWaybilling: TextView by bindView<TextView>(R.id.xx_waybilling)
    val xxArtical: TextView by bindView<TextView>(R.id.xx_artical)
    val xxConsigner: TextView by bindView<TextView>(R.id.xx_consigner)
    val xxConsignee: TextView by bindView<TextView>(R.id.xx_consignee)
    val xxQuantity: TextView by bindView<TextView>(R.id.xx_quantity)
    val xxGood: TextView by bindView<TextView>(R.id.xx_good)
    val xxPack: TextView by bindView<TextView>(R.id.xx_pack)
    val xxFeright: TextView by bindView<TextView>(R.id.xx_feright)
    val xxCollectFee: TextView by bindView<TextView>(R.id.xx_collect_fee)
    val xxTotal: TextView by bindView<TextView>(R.id.xx_total)
    val xxAdvance: TextView by bindView<TextView>(R.id.xx_advance)
    val xxOtherFee: TextView by bindView<TextView>(R.id.xx_other_fee)
    val xxWayPay: TextView by bindView<TextView>(R.id.xx_way_pay)
    val xxWayGet: TextView by bindView<TextView>(R.id.xx_way_get)
    val reportCover: LinearLayout by bindView<LinearLayout>(R.id.report_cover)

    val imBack: ImageView by bindView<ImageView>(R.id.imBack)
    val tvTitle: TextView by bindView<TextView>(R.id.tvTimeTitle)
    val imRefresh: ImageView by bindView<ImageView>(R.id.imRefresh)
    val leftListView: ListView by bindView<ListView>(R.id.left_listView)
    val rightListView: ListView by bindView<ListView>(R.id.rightListView)
    var dialog: Dialog? = null
    var presenter: ReportPresenter? = null
    val mainll: LinearLayout by bindView<LinearLayout>(R.id.main_ll)
    var request = ReportRequest()

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        val time = intent.extras?.getString("reportDay")
        request.day = time
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_report2
    }

    override fun initPresenter() {
        presenter = ReportPresenter.getInstance(this)
    }

    override fun initView() {
        reportCover.visibility = View.GONE
        imBack.setOnClickListener { this.finish() }
        setListViewOnTouchAndScrollListener(leftListView, rightListView)
        imRefresh.setOnClickListener {
            sendRequest()
        }
        reportCover.setOnClickListener {
            reportCover.visibility = View.GONE
        }
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    private fun sendRequest() {
        dialog!!.show()
        presenter!!.getDayList(ZGsonUtils.getInstance().getJsonString(request))
    }

    private fun dealWithData(lists: MutableList<ReportEntity>) {
        val leftList = ArrayList<String>()
        val totalEntity = ReportEntity()
        var i = 0;
        lists.forEach {
            i++
            leftList.add(i.toString())
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
                tvText.setTextColor(ContextCompat.getColor(this@ReportDayActivity, R.color.color_black))
            }
        }
        rightListView.adapter = object : ZCommonAdapter<ReportEntity>(this, lists, R.layout.item_report_list2) {
            override fun convert(holder: ZViewHolder?, item: ReportEntity?, position: Int) {
                val tvBill = holder!!.getView<TextView>(R.id.reList_billCount)
                if (position != lists.size - 1) {
                    tvBill.text = "查看详情"
                    tvBill.isEnabled = true
                    tvBill.setOnClickListener { getBillDetail(lists[position].uuid) }
                } else {
                    tvBill.text = ""
                    tvBill.isEnabled = false
                }

                val tvQuantity = holder.getView<TextView>(R.id.reList_quantity)
                tvQuantity.text = getZeroableInt(item!!.quantity).toString()
                val tvFreight = holder.getView<TextView>(R.id.reList_freight)
                tvFreight.text = get0DoubleOrNull(item.freight)
                val tvValuation = holder.getView<TextView>(R.id.reList_valuation)
                tvValuation.text = get0DoubleOrNull(item.valuation_fee)
                val tvDelivery = holder.getView<TextView>(R.id.reList_delivery)
                tvDelivery.text = get0DoubleOrNull(item.delivery_fee)
                val tvAdvance = holder.getView<TextView>(R.id.reList_advance)
                tvAdvance.text = get0DoubleOrNull(item.advance)
                val tvReceving = holder.getView<TextView>(R.id.reList_receving)
                tvReceving.text = get0DoubleOrNull(item.receiving_fee)
                val tvHandling = holder.getView<TextView>(R.id.reList_handling)
                tvHandling.text = get0DoubleOrNull(item.handling_fee)
                val tvPack = holder.getView<TextView>(R.id.reList_packing)
                tvPack.text = get0DoubleOrNull(item.packing_fee)
                val tvUpstair = holder.getView<TextView>(R.id.reList_upstair)
                tvUpstair.text = get0DoubleOrNull(item.upstair_fee)
                val tvForklist = holder.getView<TextView>(R.id.reList_forklift)
                tvForklist.text = get0DoubleOrNull(item.forklift_fee)
                val tvReturn = holder.getView<TextView>(R.id.reList_return)
                tvReturn.text = get0DoubleOrNull(item.return_fee)
                val tvUnder = holder.getView<TextView>(R.id.reList_under_charge)
                tvUnder.text = get0DoubleOrNull(item.under_charge_fee)
                val tvWare = holder.getView<TextView>(R.id.reList_warehousing)
                tvWare.text = get0DoubleOrNull(item.warehousing_fee)
                val tvTotal = holder.getView<TextView>(R.id.reList_total_freight)
                tvTotal.text = get0DoubleOrNull(item.total_freight)
                val tvCollection = holder.getView<TextView>(R.id.reList_collection_fee)
                tvCollection.text = get0DoubleOrNull(item.collection_fee)
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
            }
        }
    }

    private fun getBillDetail(uuid: Long) {
        dialog!!.show()
        GiveMeCommonData.getInstance().giveMeBillDetail(uuid, object : CommonPresenter.OnGetBillDetailListener {
            override fun getBillDetailSucc(bills: MutableList<BillingDrawMain>?) {
                dialog!!.dismiss()
                if (bills!!.size == 0) {
                    return
                }
                val entity = bills[0]
                xxTermial.text = entity.startStation + " --- " + entity.endStation
                xxWaybilling.text = "运单号 : " + getNullablString(entity.waybill_number)
                if (entity.input_article_number != null) {
                    xxArtical.text = "货号 : " + getNullablString(entity.article_number)+"("+entity.input_article_number+")"
                }else{
                    xxArtical.text = "货号 : " + getNullablString(entity.article_number)
                }
                xxConsigner.text = "发货人信息 : " + getNullablString(entity.consigner) + "  " + getNullablString(entity.consigner_phone)
                xxConsignee.text = getNullablString(entity.consignee)
                xxQuantity.text = entity.quantity.toString()
                xxGood.text = entity.goods_name
                xxPack.text = entity.packaging
                xxFeright.text = get0DoubleOrNull(entity.freight)
                xxCollectFee.text = get0DoubleOrNull(entity.collection_fee)
                xxTotal.text = get0DoubleOrNull(entity.total_freight)
                xxAdvance.text = get0DoubleOrNull(entity.advance)

                xxOtherFee.text = "声明价值 " + get0DoubleOrNull(entity.getDeclared_value()).toString() +
                        "保价费 " + get0DoubleOrNull(entity.getValuation_fee()) +
                        "送货费 " + get0DoubleOrNull(entity.getDelivery_fee()) +
                        "接货费 " + get0DoubleOrNull(entity.getReceiving_fee()) +
                        "装卸费 " + get0DoubleOrNull(entity.getHandling_fee()) +
                        "叉吊费 " + get0DoubleOrNull(entity.getForklift_fee()) +
                        "包装费 " + get0DoubleOrNull(entity.getPacking_fee()) +
                        "上楼费 " + get0DoubleOrNull(entity.getUpstair_fee()) +
                        "回扣 " + get0DoubleOrNull(entity.getReturn_fee()) +
                        "欠返 " + get0DoubleOrNull(entity.getUnder_charge_fee())

                val str = getNullablString(entity.total_freight_receipts).split("&&")
                if (str.size > 1) {
                    xxWayPay.text = str[1]
                }
                xxWayGet.text = entity.delivery_method

                reportCover.visibility = View.VISIBLE
            }

            override fun getBillDetailError() {
                dialog!!.dismiss()
                Toasty.error(this@ReportDayActivity, "查询票据详情失败，请重试").show()
            }
        })
    }

    override fun getBillsSucc(lists: MutableList<ReportEntity>) {
        dialog!!.dismiss()
        if (lists.size == 0) {
            Toasty.info(this, getString(R.string.no_data)).show()
        } else {
            Logger.i(lists.toString())
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