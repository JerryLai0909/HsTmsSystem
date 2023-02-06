/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.Declaration
import com.infinity.jerry.yyd_tms.mvp.presenter.DeclarsPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewDeclaration
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/15.
 */
class BoDeclarConfActivity : BaseActivity(), IViewDeclaration {
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeList: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeLayout_declar)
    val btAddDelar: TextView by bindView<TextView>(R.id.addDeclars)
    var presenter: DeclarsPresenter? = null
    var adapter: ZCommonAdapter<Declaration>? = null

    var dialog: Dialog? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_dec_conf_main
    }

    override fun initPresenter() {
        presenter = DeclarsPresenter.getIsntance(this)
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    override fun initView() {
        titleBar.setTitle("声明列表")
        btAddDelar.setOnClickListener { startActivity(BoDeclarEditActivity::class.java) }
        swipeList.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }

        })
    }

    private fun sendRequest() {
        presenter!!.getDeclars()
        swipeList.swipeLayout!!.isRefreshing = true
    }


    override fun onNetWorkError() {
        swipeList.requestError()
    }

    private fun initListView(declas: List<Declaration>) {
        adapter = object : ZCommonAdapter<Declaration>(this, declas, R.layout.item_declar) {
            override fun convert(holder: ZViewHolder?, item: Declaration?, position: Int) {
                var tvSort = holder!!.getView<TextView>(R.id.tvSort)
                tvSort.text = (position + 1).toString()
                var tvContent = holder!!.getView<TextView>(R.id.tvContent)
                tvContent.text = item!!.logistics_notice
            }
        }
        var listView = swipeList.listView!!
        listView.adapter = adapter
        listView.setOnItemClickListener({ parent, view, position, id ->
            var bundle = Bundle()
            bundle.putSerializable("decEntity", declas.get(position))
            bundle.putSerializable("lastDec", declas.get(declas.size - 1))
            startActivity(BoDeclarEditActivity::class.java, bundle)
        })
        swipeList.showListView()

        listView.setOnItemLongClickListener { parent, view, position, id ->
            showDeleDiaglog(declas[position].uuid)
            return@setOnItemLongClickListener false
        }

    }

    private fun showDeleDiaglog(uuid: Long) {
        var alert = AlertDialog.Builder(this)
                .setMessage("您确定要删除这条声明吗？")
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    this@BoDeclarConfActivity.dialog!!.show()
                    var declar = Declaration()
                    declar.uuid = uuid
                    presenter!!.deleteDeclars(ZGsonUtils.getInstance().getJsonString(declar))
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    override fun getDeclarsSucc(declas: List<Declaration>) {
        initListView(declas)
        AppUserToken.getInstance().declars = declas
    }

    override fun editDeclarSucc() {
    }

    override fun deleDeclarSucc() {
        Toasty.success(this, "删除声明成功").show()
        dialog!!.dismiss()
        sendRequest()
    }

    override fun remoteDelarsError(type: Int) {
        when (type) {
            IViewDeclaration.DECLARS_GET_ERROR -> swipeList.requestNoData()
            IViewDeclaration.DECLARS_DELETE_ERROR -> Toasty.success(this, "删除声明失败，请重试").show()
        }
    }

}