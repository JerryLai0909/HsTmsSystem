/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.data.request_entity.MyBillRequest
import com.infinity.jerry.yyd_tms.data.request_entity.StringUuid
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.SignPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewSign
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.DateUtil
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/26.
 */
class SignActivityX : BaseActivity(), IViewSign {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val tvSignEnsure: TextView by bindView<TextView>(R.id.tvEnsureSign)
    var adapter: ZCommonAdapter<BillingDrawMain>? = null
    var dialog: Dialog? = null
    var lists: List<BillingDrawMain>? = null
    var presenter: SignPresenter? = null
    var isAllChoosed: Boolean = false

    val tvMoney: TextView by bindView<TextView>(R.id.tvMoney)
    val tvCount: TextView by bindView<TextView>(R.id.tvCount)

    var requestEntity: MyBillRequest? = null


    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        requestEntity = intent.getBundleExtra("bundleExtra").getSerializable("bundleEntity") as MyBillRequest?
        getMyBillsData()
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_sign
    }

    override fun initPresenter() {
        presenter = SignPresenter.getInstance(this)
    }


    override fun onResume() {
        super.onResume()
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.good_sign))
        tvSignEnsure.setOnClickListener { ensureArr() }

    }

    private fun ensureArr() {
        if (lists != null && lists!!.size > 0) {
            var builder = StringBuilder()
            lists!!.forEach {
                if (it.isChoose) {
                    builder.append(it.uuid.toString() + ",")
                }
            }
            if (builder.length > 0) {
                builder.deleteCharAt(builder.length - 1)
                var request = StringUuid()
                request.uuid = builder.toString()
                presenter!!.ensureSignBill(ZGsonUtils.getInstance().getJsonString(request))
                dialog?.show()
            } else {
                Toasty.info(this, getString(R.string.no_bill_for_arrvial)).show()
            }
        } else {
            Toasty.info(this, getString(R.string.no_bill_for_arrvial)).show()
        }
    }

    var isRequest: Boolean = false

    private fun getMyBillsData() {
        if (isRequest) {
            return
        }
        isRequest = true
        GiveMeCommonData.getInstance().giveMeMyBills(requestEntity!!.startTime, requestEntity!!.endTime, ZGsonUtils.getInstance().getJsonString(requestEntity),
                Integer.parseInt(requestEntity!!.pageSize), Integer.parseInt(requestEntity!!.page), object : CommonPresenter.OnMyBillListener {
            override fun getMyBillSucc(lists: MyBillListEntity?) {
                swipeListView.swipeLayout!!.isRefreshing = false
                this@SignActivityX.lists = lists!!.pageList
                initListView()
                requestEntity!!.page = (Integer.parseInt(requestEntity!!.page) + 1).toString()
                isRequest = false
            }

            override fun getMyBillError() {
                isRequest = false
                swipeListView.swipeLayout!!.isRefreshing = false
                Toasty.error(this@SignActivityX, getString(R.string.search_error)).show()
                swipeListView.requestNoData()
            }
        })
    }

    private fun initListView() {
        adapter = object : ZCommonAdapter<BillingDrawMain>(this, lists, R.layout.item_arraival_billing) {
            override fun convert(holder: ZViewHolder?, item: BillingDrawMain?, position: Int) {
                val imChoose = holder!!.getView<ImageView>(R.id.imChoose)
                if (item!!.isChoose) {
                    imChoose.setImageDrawable(ContextCompat.getDrawable(this@SignActivityX, R.mipmap.tms_icon_choose_c_true))
                } else {
                    imChoose.setImageDrawable(ContextCompat.getDrawable(this@SignActivityX, R.mipmap.tms_icon_choose_c_false))
                }
                if (item!!.invoice_status == 4L) {
                    imChoose.visibility = View.VISIBLE
                } else if (item.invoice_status == 5L) {
                    imChoose.visibility = View.GONE
                } else {
                    imChoose.visibility = View.GONE
                }

                val tvTime = holder!!.getView<TextView>(R.id.blTime)
                if (item!!.invoice_date == null) {
                    item!!.invoice_date = ""
                }
                tvTime.text = item.article_number + "  " + DateUtil.getTimetrampWithStringMDHM(item!!.invoice_date)

                val tvState = holder.getView<TextView>(R.id.blState)
                tvState.text = getBillState(item.invoice_status.toInt())

                val tvInit = holder.getView<TextView>(R.id.blInit)
                tvInit.text = getNullablString(item.startStation)

                val tvTermi = holder.getView<TextView>(R.id.blTermi)
                tvTermi.text = getNullablString(item.endStation)

                val tvInitInfo = holder.getView<TextView>(R.id.bl_inInfo)
                tvInitInfo.text = getNullablString(item.consigner) + "  " + getNullablString(item.consigner_phone)

                val tvTerInfo = holder.getView<TextView>(R.id.bl_terInfo)
                tvTerInfo.text = getNullablString(item.consignee) + "  " + getNullablString(item.consignee_phone)

                val tvGood = holder.getView<TextView>(R.id.bl_good)
                tvGood.text = getString(R.string.goods) + "  " + getNullablString(item.goods_name)

                val tvPack = holder.getView<TextView>(R.id.bl_pack)
                tvPack.text = getString(R.string.packages) + "  " + getNullablString(item.packaging)

                val tvPayMethod = holder.getView<TextView>(R.id.blPayMethod)
                tvPayMethod.text = getString(R.string.freight) + ": " + getNullStringForDouble(item.freight) + "元 【" + getNullablString(item.payment_method) + "】"

//                val tvDaishou = holder.getView<TextView>(R.id.tvdaishou)
//                tvDaishou.text = getNullStringForDouble(item.collection_fee)

                val tvFreight = holder.getView<TextView>(R.id.blFreight)
                tvFreight.text = getString(R.string.fee_collection) + ": " + getNullStringForDouble(item.collection_fee) + "元"

                val tvDeliMethod = holder.getView<TextView>(R.id.blDeliMethod)
                tvDeliMethod.text = getString(R.string.method_getGoods) + "  " + getNullablString(item.delivery_method)

                val tvDetail = holder.getView<TextView>(R.id.bl_detail)
                tvDetail.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putSerializable("billDetailEntity", item)
                    startActivity(BillDetailActivity::class.java, bundle)
                }

            }
        }
        swipeListView.listView!!.adapter = adapter
        swipeListView.showListView()
        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            if (this@SignActivityX.lists!![position].invoice_status == 5L) {
                return@setOnItemClickListener
            } else {
                if (this@SignActivityX.lists!![position].isChoose) {
                    this@SignActivityX.lists!![position].isChoose = false
                } else {
                    this@SignActivityX.lists!![position].isChoose = true
                }
            }
            adapter!!.notifyDataSetChanged()
            setTvinfo()
        }
    }

    private fun setTvinfo() {
        if (lists != null) {
            var money = 0.0;
            var count = 0;
            lists!!.forEach {
                if (it.isChoose) {
                    count++
                    if (it.payment_method.equals("到付")) {
                        money += it.total_freight
                    }
                    var temp = it.collection_fee
                    if (temp == null) {
                        temp = 0.0
                    }
                    money += temp
                }
            }
            tvMoney.text = "应收款: " + getZeroableStringForDouble(money) + "元"
            tvCount.text = "共" + count + "单"

        }
    }


    override fun onNetWorkError() {
        dialog?.dismiss()
        swipeListView.requestError()
    }

    override fun getSignBillsSucc(lists: MyBillListEntity) {
        dialog?.dismiss()
    }

    override fun ensureSignBillsSucc() {
        dialog?.dismiss()
        this.finish()
    }

    override fun remoteSignError(type: Int) {
        dialog?.dismiss()
        swipeListView.swipeLayout!!.isRefreshing = false
        when (type) {
            IViewSign.SIGN_BILLS_ENSURE_ERROR -> Toasty.error(this, getString(R.string.arrvial_ensure_error)).show()
            IViewSign.SIGN_BILLS_GET_ERROR -> swipeListView.requestError()
        }
    }


}