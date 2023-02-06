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
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CooperPointEntity
import com.infinity.jerry.yyd_tms.data.request_entity.CooperListRequest
import com.infinity.jerry.yyd_tms.data.request_entity.PointAddRequest
import com.infinity.jerry.yyd_tms.data.request_entity.PointSearchRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.BoCooAddPointPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCooperAddPoint
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/21.
 */
class BoCooperAddActivity : BaseActivity(), IViewCooperAddPoint {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    var dialog: Dialog? = null
    var presenter: BoCooAddPointPresenter? = null

    var uuid: Long? = null
    var pointRequest: PointSearchRequest? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        pointRequest = PointSearchRequest()
        uuid = intent.extras!!.getLong("companyUuid")
        pointRequest!!.uuid = uuid.toString()
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_cooper_add
    }

    override fun initPresenter() {
        presenter = BoCooAddPointPresenter.getInstance(this)
        sendPointRequst()
    }

    private fun sendPointRequst() {
        dialog?.show()
        presenter!!.searchPoint(ZGsonUtils.getInstance().getJsonString(pointRequest))
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.add_point_cooper))
    }

    private fun initListView(entity: CooperPointEntity) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<CooperPointEntity.PageListBean>(this, entity.pageList, R.layout.item_cooper_point) {
            override fun convert(holder: ZViewHolder?, item: CooperPointEntity.PageListBean?, position: Int) {
                val tvName = holder!!.getView<TextView>(R.id.tvCooperName)
                tvName.text = item!!.point_name
                val tvAdd = holder!!.getView<TextView>(R.id.tvAddCooper)
                tvAdd.setOnClickListener {
                    showAddEnsureDialog(item, position)
                }
            }
        }
    }

    private fun showAddEnsureDialog(item: CooperPointEntity.PageListBean, position: Int) {
        var alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.ensure_add_cooper))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    val request = PointAddRequest()
                    request.partner_company_id = item.logistics_uuid.toString()
                    request.partner_point_id = item.uuid.toString()
                    val listRequest = CooperListRequest()
                    val lists = ArrayList<PointAddRequest>()
                    lists.add(request)
                    listRequest.lists = lists
                    this@BoCooperAddActivity.dialog?.show()
                    presenter!!.addCooper("[" + ZGsonUtils.getInstance().getJsonString(request) + "]")
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()

                })
                .create()
        alert.show()
    }

    override fun getPointsSucc(entity: CooperPointEntity) {
        dialog?.dismiss()
        initListView(entity)
    }

    override fun addCooperSucc() {
        dialog?.dismiss()
        Toasty.success(this, getString(R.string.add_cooper_succ)).show()
        this.finish()
    }

    override fun remoteError(type: Int) {
        dialog?.dismiss()


        when (type) {
            IViewCooperAddPoint.ADDCOOPER_ERROR -> Toasty.success(this, getString(R.string.add_cooper_error)).show()
            IViewCooperAddPoint.GETPOINTS_ERROR -> {
                Toasty.error(this, getString(R.string.get_cooper_error)).show()
                swipeListView.requestError()
            }
        }
    }

    override fun onNetWorkError() {
        dialog?.dismiss()
        swipeListView.requestError()
    }
}