/*
 * Copyrigh()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.PageCountEntity
import com.infinity.jerry.yyd_tms.data.TransEntity
import com.infinity.jerry.yyd_tms.data.request_entity.TransPageEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.TranPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewTrans
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.StringUtils
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import kotterknife.bindView

/**
 * Created by jerry on 2017/7/24.
 */
class TransActivity : BaseActivity(), IViewTrans {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    var presneter: TranPresenter? = null

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_trans_main;
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    private fun sendRequest() {
        swipeListView.swipeLayout!!.isRefreshing = true
        presneter!!.searchTrans(ZGsonUtils.getInstance().getJsonString(PageCountEntity()))
    }

    override fun initPresenter() {
        presneter = TranPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.trans_list))
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun initListView(pageList: MutableList<TransEntity>) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<TransEntity>(this, pageList, R.layout.item_trans_item) {
            override fun convert(holder: ZViewHolder?, item: TransEntity?, position: Int) {
                val tvTime = holder!!.getView<TextView>(R.id.trans_time)
                tvTime.text = getString(R.string.time) + item!!.transfer_date
                val tvOper = holder.getView<TextView>(R.id.trans_oper)
                tvOper.text = "操作员: "+ StringUtils.getNullablString(item.user_name)

                val tvStart = holder.getView<TextView>(R.id.tvStart)
                tvStart.text = item.operation_point_name
                val tvEnd = holder.getView<TextView>(R.id.tvEnd)
                tvEnd.text = item.transit_destination

                val tvRemark = holder.getView<TextView>(R.id.trans_remark)
                tvRemark.text = getString(R.string.remark) + getNullablString(item.remark)
            }
        }
        swipeListView.showListView()
    }

    override fun searchTransSucc(entity: TransPageEntity) {
        if (entity?.pageList.isEmpty()) {
            swipeListView.requestNoData()
            return
        }
        initListView(entity?.pageList)
    }

    override fun onNetWorkError() {
        swipeListView.requestError()
    }

    override fun addTransSucc() {
    }

    override fun transError(type: Int) {
        swipeListView.requestNoData()
    }
}