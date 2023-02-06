/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.BoPointAndManEntity
import com.infinity.jerry.yyd_tms.data.PageCountEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.BoPointManPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewPointMan
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/20.
 */
class BoPointManActivity : BaseActivity(), IViewPointMan {
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val btAddPoint: TextView by bindView<TextView>(R.id.tvAddPoint)
    var presenter: BoPointManPresenter? = null
    override fun initData() {
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_bos_point
    }

    override fun initPresenter() {
        presenter = BoPointManPresenter.getInstance(this)
    }

    override fun onResumeFragments() {
        sendRequest()
        super.onResumeFragments()
    }

    private fun sendRequest() {
        swipeListView.swipeLayout!!.isRefreshing = true
        presenter!!.getAllPointAndMan(ZGsonUtils.getInstance().getJsonString(PageCountEntity()))
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.boss_pointAndMan))
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }

        })
        btAddPoint.setOnClickListener {
            startActivity(BoPointDetailActivity::class.java)
        }
    }

    private fun initListView(entity: BoPointAndManEntity) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<BoPointAndManEntity.PageListBean>(this, entity.pageList, R.layout.item_point_man) {
            override fun convert(holder: ZViewHolder?, item: BoPointAndManEntity.PageListBean?, position: Int) {
                var llEditPoint = holder!!.getView<LinearLayout>(R.id.editPoint)
                llEditPoint.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putLong("pointUuid", entity.pageList[position].uuid)
                    startActivity(BoPointDetailActivity::class.java, bundle)
                }
                var tvName = holder!!.getView<TextView>(R.id.tvPm_pointName)
                tvName.text = item!!.point_name + "(" + item.num + "人)"
                var tvOwner = holder!!.getView<TextView>(R.id.tvPm_pointOwner)
                tvOwner.text = item!!.point_owner
                var tvPhone = holder!!.getView<TextView>(R.id.tvPm_pointPhone)
                tvPhone.text = (getString(R.string.entry_phone) + item!!.point_owner_phone_o)
                var tvNumber = holder!!.getView<TextView>(R.id.tvPm_manNumber)
                tvNumber.text = getString(R.string.manConfig)
                tvNumber.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putLong("pointUuid", entity.pageList[position].uuid)
                    startActivity(BoManlistActivity::class.java, bundle)
                }
                var tvDelete = holder!!.getView<TextView>(R.id.tvDelete)
                tvDelete.visibility = View.GONE
                tvDelete.text = "删除"
                tvDelete.setOnClickListener {
                }
            }

        }
    }

    override fun getPointManSucc(entity: BoPointAndManEntity) {
        swipeListView.showListView()
        initListView(entity)
    }

    override fun getPointManError() {
        swipeListView.requestNoData()
    }

    override fun onNetWorkError() {
        swipeListView.requestError()

    }

}