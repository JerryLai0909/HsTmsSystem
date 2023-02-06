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
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.ErrorBillingEntity
import com.infinity.jerry.yyd_tms.data.PageCountEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.ErrorPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewErrorRemote
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/7/17.
 */
class ErrorMainActivity : BaseActivity(), IViewErrorRemote {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)

    var presenter: ErrorPresenter? = null
    var dialog: Dialog? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_error_main
    }

    override fun initPresenter() {
        presenter = ErrorPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.error_remote))
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    private fun sendRequest() {
        swipeListView.swipeLayout!!.isRefreshing = true
        presenter!!.searchError(ZGsonUtils.getInstance().getJsonString(PageCountEntity()))
    }

    private fun initListView(pageList: MutableList<ErrorBillingEntity.ErrorEntity>) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<ErrorBillingEntity.ErrorEntity>(this, pageList, R.layout.item_error) {
            override fun convert(holder: ZViewHolder?, item: ErrorBillingEntity.ErrorEntity?, position: Int) {

                val tvBillNumber = holder!!.getView<TextView>(R.id.tvErrorBillnumber)
                tvBillNumber.text = getString(R.string.billing_number) + " " + getNullablString(item!!.waybill_number)

                val tvType = holder.getView<TextView>(R.id.tvErrorType)
                tvType.text = "类别 "+getNullablString(item.unusual_type)

                val tvInitInfo = holder.getView<TextView>(R.id.bl_inInfo)
                tvInitInfo.text = getNullablString(item.consigner) + "  " + getNullablString(item.consigner_phone)

                val tvTerInfo = holder.getView<TextView>(R.id.bl_terInfo)
                tvTerInfo.text = getNullablString(item.consignee) + "  " + getNullablString(item.consignee_phone)

                val tvDes = holder.getView<TextView>(R.id.tvErrorDes)
                tvDes.text = getString(R.string.descrip)+getNullablString(item.unusual_des)


                val tvState = holder.getView<TextView>(R.id.tvErrorState)
                val tvDeal = holder.getView<TextView>(R.id.tvErrorDeal)
                tvState.text = item.statusStr
                if (item.status == 1) {
                    tvDeal.text = ""
                    tvDeal.isEnabled = false
                } else {
                    tvDeal.text = "标记为已处理"
                    tvDeal.isEnabled = true
                    tvDeal.setOnClickListener { dealError(item) }
                }
            }
        }
        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            if (pageList[position].status == 1) {
                return@setOnItemClickListener
            }
            val bundle = Bundle()
            bundle.putSerializable("errorEntity",pageList[position])
            startActivity(ErrorAddActivity::class.java,bundle)
        }
    }

    private fun dealError(item: ErrorBillingEntity.ErrorEntity?) {
        var request = ErrorBillingEntity.ErrorEntity()
        request.uuid = item!!.uuid
        request.status = 1
        presenter!!.editError(ZGsonUtils.getInstance().getJsonString(request))
        dialog?.show()
    }

    override fun searchErrorSucc(entity: ErrorBillingEntity) {
        swipeListView.showListView()
        initListView(entity.pageList)
    }

    override fun errorRemoteError(type: Int) {
        when (type) {
            IViewErrorRemote.ERROR_SEARCH_ERROR -> swipeListView.requestNoData()
            IViewErrorRemote.ERROR_EDIT_ERROR -> Toasty.error(this, getString(R.string.deal_error_error)).show()
        }
    }

    override fun onNetWorkError() {
        swipeListView.requestError()
    }

    override fun addErorrSucc() {
        dialog?.dismiss()
        Toasty.success(this, getString(R.string.deal_error_succ)).show()
        sendRequest()
    }


}