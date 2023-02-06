/*
 * Copyrigh()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.BoManDetial
import com.infinity.jerry.yyd_tms.mvp.presenter.BoManAllPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewManAll
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/21.
 */
class BoManlistActivity : BaseActivity(), IViewManAll {


    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val tvAddMan: TextView by bindView<TextView>(R.id.addMan)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)

    var requestUuid: Long? = null

    var presenter: BoManAllPresenter? = null
    override fun initData() {
        requestUuid = intent.extras.getLong("pointUuid")
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_manlist
    }

    override fun initPresenter() {
        presenter = BoManAllPresenter.getInstance(this)
    }

    override fun onResumeFragments() {
        sendRequest()
        super.onResumeFragments()
    }

    private fun sendRequest() {
        var requestEntity = BoManDetial()
        requestEntity.company_uuid = requestUuid
        presenter!!.getAllMan(ZGsonUtils.getInstance().getJsonString(requestEntity))
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.point_man))
        tvAddMan.setOnClickListener {
            var bundle = Bundle()
            bundle.putLong("company_uuid", requestUuid!!)
            startActivity(BoManEditActivity::class.java, bundle)
        }
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun initListView(manLists: List<BoManDetial>) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<BoManDetial>(this, manLists, R.layout.item_manlist) {
            override fun convert(holder: ZViewHolder?, item: BoManDetial?, position: Int) {
                var tvName = holder!!.getView<TextView>(R.id.tvManName)
                tvName.text = item!!.user_name
                var tvPhone = holder!!.getView<TextView>(R.id.tvManPhone)
                tvPhone.text = item!!.phone_one

            }
        }
        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            var bundle = Bundle()
            bundle.putLong("company_uuid", requestUuid!!)
            bundle.putSerializable("manEntity", manLists[position])
            startActivity(BoManEditActivity::class.java, bundle)

        }
        swipeListView.listView!!.setOnItemLongClickListener { parent, view, position, id ->
            showDialog(manLists[position].uuid)
            return@setOnItemLongClickListener false
        }
    }

    private fun showDialog(manUUID: Long) {
        var alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.ensure_deleteman))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    var requet = BoManDetial()
                    requet.uuid = manUUID
                    presenter!!.deleteMan(ZGsonUtils.getInstance().getJsonString(requet))
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }


    override fun getAllManSucc(manLists: List<BoManDetial>) {
        swipeListView.showListView()
        initListView(manLists)
    }

    override fun deleteManSucc() {
        sendRequest()
    }

    override fun remoteManError(type: Int) {
        when (type) {
            IViewManAll.MAN_DELETE_ERROR -> {
                Toasty.error(this, getString(R.string.delete_man_error)).show()
            }
            IViewManAll.MAM_GET_ALL_ERROR -> {
                Toasty.error(this, getString(R.string.get_mans_error)).show()
                swipeListView.requestNoData()
            }

        }
    }

    override fun onNetWorkError() {
        swipeListView.requestError()
    }

}