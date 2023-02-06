/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CommonGoods
import com.infinity.jerry.yyd_tms.mvp.presenter.GoodsPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewGoods
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeGridView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import org.litepal.crud.DataSupport

/**
 * Created by jerry on 2017/6/15.
 */
class DrawGoodsActivity : BaseActivity(), IViewGoods, View.OnClickListener {

    val swipeGridView: Z_SwipeGridView  by bindView<Z_SwipeGridView>(R.id.swipeLayout_goods)
    val tvAddGoods: TextView by bindView<TextView>(R.id.tvAddGoods)
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    var presenter: GoodsPresenter? = null
    var adapter: ZCommonAdapter<CommonGoods>? = null

    //添加goods的控件s
    val addGoodPop: LinearLayout by bindView<LinearLayout>(R.id.add_goodsLinear)
    val yesGood: TextView by bindView<TextView>(R.id.yes_good)
    val noGood: TextView by bindView<TextView>(R.id.no_good)
    val edGoodName: EditText by bindView<EditText>(R.id.edGoodsName)
    val coverFrame: FrameLayout by bindView<FrameLayout>(R.id.coverFrame)

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_com_goods
    }

    override fun initPresenter() {
        presenter = GoodsPresenter.getInstance(this)
        var findAll = DataSupport.findAll(CommonGoods::class.java)
        if (findAll.isNotEmpty()) {
            initGridView(findAll)
        } else {
            sendRequest()
        }
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.choose_good))
        addGoodPop.visibility = View.GONE
        coverFrame.visibility = View.GONE
        tvAddGoods.setOnClickListener(this)
        yesGood.setOnClickListener(this)
        noGood.setOnClickListener(this)
        coverFrame.setOnClickListener(this)
        swipeGridView.setZ_OnRefreshlistener(object : Z_SwipeGridView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun sendRequest() {
        swipeGridView.swipeLayout!!.isRefreshing = true
        presenter!!.getGoods()
    }

    private fun initGridView(goods: List<CommonGoods>) {
        adapter = object : ZCommonAdapter<CommonGoods>(this, goods, R.layout.item_grid_goodpack) {
            override fun convert(holder: ZViewHolder?, item: CommonGoods?, position: Int) {
                val tvName = holder!!.getView<TextView>(R.id.item_goodPackName)
                tvName.text = item!!.goods_name
            }
        }
        swipeGridView.gridView!!.adapter = adapter
        swipeGridView.gridView!!.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent()
            intent.putExtra("goodName", goods[position].goods_name)
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }

        swipeGridView.gridView!!.setOnItemLongClickListener { parent, view, position, id ->
            showEnsureDialog(goods, position)
            return@setOnItemLongClickListener false
        }
        swipeGridView.showGridView()
    }

    private fun showEnsureDialog(goods: List<CommonGoods>, position: Int) {
        val dialog = AlertDialog.Builder(this)
                .setMessage(getString(R.string.ask_deletegoods))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    val request = CommonGoods()
                    request.uuid = goods[position].uuid
                    presenter!!.deleteGoods(ZGsonUtils.getInstance().getJsonString(request))
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        dialog.show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.coverFrame -> {
                addGoodPop.visibility = View.GONE
                coverFrame.visibility = View.GONE
            }
            R.id.tvAddGoods -> {
                edGoodName.setText("")
                addGoodPop.visibility = View.VISIBLE
                coverFrame.visibility = View.VISIBLE
            }
            R.id.no_good -> {
                addGoodPop.visibility = View.GONE
                coverFrame.visibility = View.GONE
            }
            R.id.yes_good -> {//add Goods
                val goodName = getEDString(edGoodName)
                val requestGood = CommonGoods()
                requestGood.goods_name = goodName
                coverFrame.visibility = View.GONE
                addGoodPop.visibility = View.GONE
                presenter!!.addGoods(ZGsonUtils.getInstance().getJsonString(requestGood))
            }
        }
    }

    override fun getGoodsSucc(goods: List<CommonGoods>) {
        DataSupport.deleteAll(CommonGoods::class.java)
        DataSupport.saveAll(goods)
        initGridView(goods)
    }

    override fun remoteGoodError(type: Int) {
        when (type) {
            IViewGoods.GOOD_DELETE_ERROR -> Toasty.error(this, getString(R.string.error_deleteGood))
            IViewGoods.GOOD_ADD_ERROR -> Toasty.error(this, getString(R.string.add_goodError))
            IViewGoods.GOOD_GET_ERROR -> {
                Toasty.error(this, getString(R.string.get_goodsError))
                swipeGridView.requestError()
            }

        }
    }

    override fun onNetWorkError() {
        swipeGridView.requestError()
    }

    override fun deleteGoodSucc() {
        sendRequest()
    }

    override fun addGoodsSucc() {
        sendRequest()
    }


}