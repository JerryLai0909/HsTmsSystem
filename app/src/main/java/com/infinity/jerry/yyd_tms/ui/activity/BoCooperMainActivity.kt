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
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CooperEntity
import com.infinity.jerry.yyd_tms.data.PageCountEntity
import com.infinity.jerry.yyd_tms.data.request_entity.CooperRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.BoCooperMainPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCooperMain
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/20.
 */
class BoCooperMainActivity : BaseActivity(), IViewCooperMain {
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val btAddCooper: TextView by bindView<TextView>(R.id.addCooper)

    var presenter: BoCooperMainPresenter? = null
    var dialog: Dialog? = null
    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_cooper_main
    }

    override fun initPresenter() {
        presenter = BoCooperMainPresenter.getInstance(this)
        sendRequest()
    }

    private fun sendRequest() {
        presenter!!.getCooperaters(ZGsonUtils.getInstance().getJsonString(PageCountEntity()))
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.cooperate))
        btAddCooper.setOnClickListener {
            startActivity(BoCooperCompanyActivity::class.java)
        }
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun initListView(entity: CooperEntity) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<CooperEntity.PageListBean>(this, entity.pageList, R.layout.item_coopers) {
            override fun convert(holder: ZViewHolder?, item: CooperEntity.PageListBean?, position: Int) {
                val tvName = holder!!.getView<TextView>(R.id.tvCooperName)
                tvName.text = item!!.company_name
                val tvPoint = holder.getView<TextView>(R.id.tvPointName)
                tvPoint.text = item.point_name
                //需要根据状态切换显示
                val tvState = holder!!.getView<TextView>(R.id.tvCooperState)
                val tvChange = holder!!.getView<TextView>(R.id.tvCooperChange)

                when (item!!.partner_status) {
                    1 -> {
                        tvState.text = getString(R.string.point_on)
                        tvChange.text = getString(R.string.point_off)
                        tvChange.background = ContextCompat.getDrawable(this@BoCooperMainActivity,R.drawable.trans_not)
                    }
                    2 -> {
                        tvState.text = getString(R.string.point_off)
                        tvChange.text = getString(R.string.point_on)
                        tvChange.background = ContextCompat.getDrawable(this@BoCooperMainActivity,R.drawable.trans_yes)
                    }
                }
                tvChange.setOnClickListener {
                    val request = CooperRequest()
                    request.uuid = item.uuid.toLong()
                    if (item.partner_status == 1) {
                        request.partner_status = 2
                    } else {
                        request.partner_status = 1
                    }
                    presenter!!.updateCooperState(ZGsonUtils.getInstance().getJsonString(request))
                    dialog!!.show()
                }
            }
        }
        swipeListView.showListView()


    }

    override fun getCoopersSucc(entity: CooperEntity) {
        dialog!!.dismiss()
        initListView(entity)
    }

    override fun changeStateSucc() {
        dialog!!.dismiss()
        sendRequest()
    }

    override fun remoteError(type: Int) {
        dialog!!.dismiss()
        when (type) {
            IViewCooperMain.COOPER_REMOTE_ERROR -> Toasty.error(this, "修改状态失败，请重试").show()
            IViewCooperMain.COOPER_GET_ERROR -> swipeListView.requestNoData()
        }
    }

    override fun onNetWorkError() {
        dialog!!.dismiss()
        swipeListView.requestError()
    }

}