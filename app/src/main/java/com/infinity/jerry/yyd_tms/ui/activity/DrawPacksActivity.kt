/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CommonPacks
import com.infinity.jerry.yyd_tms.mvp.presenter.PacksPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewPacks
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeGridView
import kotterknife.bindView
import org.litepal.crud.DataSupport

/**
 * Created by jerry on 2017/6/15.
 */
class DrawPacksActivity : BaseActivity(), IViewPacks {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeLayout: Z_SwipeGridView by bindView<Z_SwipeGridView>(R.id.swipeLayout_goods)
    val tvAdd: TextView by bindView<TextView>(R.id.tvAddGoods)
    var presenter: PacksPresenter? = null
    var dialog: Dialog? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_com_goods
    }

    override fun initPresenter() {
        presenter = PacksPresenter.getInstance(this)
        val findAll = DataSupport.findAll(CommonPacks::class.java)

        if (findAll.isNotEmpty()) {
            initListView(findAll)
        } else {
            sendRequest()
        }
    }

    private fun sendRequest() {
        dialog!!.show()
        presenter!!.getPacks()
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.choose_pack))
        tvAdd.visibility = View.GONE
        swipeLayout.setZ_OnRefreshlistener(object : Z_SwipeGridView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun initListView(packs: List<CommonPacks>) {
        swipeLayout.gridView!!.adapter = object : ZCommonAdapter<CommonPacks>(this, packs, R.layout.item_grid_goodpack) {
            override fun convert(holder: ZViewHolder?, item: CommonPacks?, position: Int) {
                var tvContent = holder!!.getView<TextView>(R.id.item_goodPackName)
                tvContent.text = item!!.packing_name
            }
        }
        swipeLayout.gridView!!.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent()
            intent.putExtra("packName", packs[position].packing_name)
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }

        swipeLayout.showGridView()
    }

    override fun getPacksSuccs(packs: List<CommonPacks>) {
        dialog!!.dismiss()
        DataSupport.deleteAll(CommonPacks::class.java)
        DataSupport.saveAll(packs)
        initListView(packs)
    }

    override fun getPacksError() {
        swipeLayout.requestError()
        dialog!!.dismiss()

    }

    override fun onNetWorkError() {
        swipeLayout.requestError()
        dialog!!.dismiss()
    }


}