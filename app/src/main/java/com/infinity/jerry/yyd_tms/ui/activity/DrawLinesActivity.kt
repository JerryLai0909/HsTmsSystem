/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BillTerimi
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeGridView
import kotterknife.bindView

class DrawLinesActivity : BaseActivity() {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.zTitlebar)
    val zGridView: Z_SwipeGridView by bindView<Z_SwipeGridView>(R.id.zGridView)

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_draw_lines
    }

    override fun initData() {
        getLines()
    }

    private fun getLines() {
        zGridView.swipeLayout!!.isRefreshing = true
        GiveMeCommonData.getInstance().giveMeAllTerminal(object : CommonPresenter.OnGetAllTerminalListener {
            override fun getTerminalSucc(lists: List<BillTerimi>) {
                zGridView.showGridView()
                initListView(lists)
            }

            override fun getTerminalError() {
                zGridView.requestError()
            }

        })
    }

    private fun initListView(lists: List<BillTerimi>) {
        zGridView.gridView?.adapter = object : ZCommonAdapter<BillTerimi>(this, lists, R.layout.item_draw_lines) {
            override fun convert(holder: ZViewHolder?, item: BillTerimi?, position: Int) {
                val tvLinesName = holder!!.getView<AutoScaleTextView>(R.id.tvLinesName)
                tvLinesName.text = item!!.point_name
                val imCooper = holder.getView<ImageView>(R.id.im_core)
                if (item.logistics_uuid != AppUserToken.getInstance().result.logistics_uuid) {
                    imCooper.visibility = View.VISIBLE
                } else {
                    imCooper.visibility = View.GONE
                }
            }
        }
        zGridView.gridView!!.setOnItemClickListener { parent, view, position, id ->
            val bundle = Bundle()
            bundle.putSerializable("lineEntity", lists[position])
            startActivity(DrawBillActivity::class.java, bundle)
        }
    }

    override fun initPresenter() {

    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.lines_choose))
        titleBar.setTitleMode(ZTitlebar.MODE_TEXT)
        titleBar.setTvPlusText("编辑到站")
        titleBar.setOnTextModeListener(object :ZTitlebar.OnTextModeListener{
            override fun onClickTextMode() {
                val intent  = Intent(this@DrawLinesActivity,EditStationActivity::class.java)
                startActivity(intent)
            }
        })
        zGridView.setZ_OnRefreshlistener(object : Z_SwipeGridView.Z_OnRefreshListener {
            override fun onRefresh() {
                getLines()
            }
        })

    }


}
