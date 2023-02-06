/*
 * Copyrigh()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.data.NewArrivalEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.ArrivalPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewArrival
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.DateUtil
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/19.
 */
class ArrivalActivity : BaseActivity(), IViewArrival {


    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    //    val tvEnusreArr: TextView by bindView<TextView>(R.id.tvEnsureArr)
    var presenter: ArrivalPresenter? = null
//    val llChooseAll: LinearLayout by bindView<LinearLayout>(R.id.llChooseAll)
//    val imChooseX: ImageView by bindView<ImageView>(R.id.imChooseX)

    var adapter: ZCommonAdapter<NewArrivalEntity.ContentBean>? = null
    var diglog: Dialog? = null

    var isAllChoosed: Boolean = false

    var lists: List<NewArrivalEntity.ContentBean>? = null
    override fun initData() {
        diglog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_arrival
    }

    override fun initPresenter() {
        presenter = ArrivalPresenter.getInstance(this)
    }

    private fun sendRequest() {
        diglog?.show()
        presenter!!.getNewArrival(0, 1, 100)
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.good_arrival))
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun ensureArr(item: NewArrivalEntity.ContentBean) {
        val alert = AlertDialog.Builder(this)
                .setMessage("您确定要对此进行到货处理吗？")
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    diglog!!.show()
                    presenter!!.ensureNewArrival(item.id)
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    private fun initListView() {
        adapter = object : ZCommonAdapter<NewArrivalEntity.ContentBean>(this, lists, R.layout.item_arraival_billingx) {
            override fun convert(holder: ZViewHolder?, item: NewArrivalEntity.ContentBean?, position: Int) {
                val tvCarNum = holder!!.getView<TextView>(R.id.tvCarNum)
                tvCarNum.text = item!!.plateNumber
                val tvBatch = holder.getView<TextView>(R.id.tvBatchNum)
                tvBatch.text = "批次号: " + item.batchNumber
                val tvRecInfo = holder.getView<TextView>(R.id.tvRecInfo)
                tvRecInfo.text = if (item.driverName == null) "暂无司机信息" else item.driverName
                val tvTime = holder.getView<TextView>(R.id.tvTime)
                tvTime.text = "时间: " + DateUtil.getTimetrampWithStringMDHM(item.loadingTime)

                val tvEndName = holder.getView<TextView>(R.id.tvEndName)
                tvEndName.text = "发站:" + item.startStation

                val tvInfo = holder.getView<TextView>(R.id.tvInfo)
                tvInfo.text = item.count.toString() + "票" + "|" + item.quantity + "件"

                val tvState = holder.getView<TextView>(R.id.tvState)
                tvState.text = item.arrivalStatus

                val tvEnsure = holder.getView<TextView>(R.id.tvEnsure)
                if (item.arrivalStatus.equals("未确认到货")) {
                    tvState.setTextColor(ContextCompat.getColor(this@ArrivalActivity, R.color.color_blue))
                    tvEnsure.visibility = View.VISIBLE
                } else {
                    tvState.setTextColor(ContextCompat.getColor(this@ArrivalActivity, R.color.gColor_red))
                    tvEnsure.visibility = View.GONE
                }

                tvEnsure.setOnClickListener { ensureArr(item!!) }

            }
        }
        swipeListView.listView!!.adapter = adapter
        swipeListView.showListView()
        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            if (this@ArrivalActivity.lists!![position].isChoose) {
                this@ArrivalActivity.lists!![position].isChoose = false
            } else {
                this@ArrivalActivity.lists!![position].isChoose = true
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun getNewArrivalSucc(entities: NewArrivalEntity) {
        diglog?.dismiss()
        this.lists = entities.content
        if (this.lists!!.isEmpty()) {
            swipeListView.requestNoData()
            return
        }
        initListView()

    }

    override fun onNetWorkError() {
        diglog?.dismiss()
        swipeListView.requestError()
    }

    override fun getArrivalBillsSucc(lists: MyBillListEntity) {
        diglog?.dismiss()
//        this.lists = lists.pageList
//        if (this.lists!!.isEmpty()) {
//            swipeListView.requestNoData()
//            return
//        }
//        initListView()
    }

    override fun ensureArrivalSucc() {
        sendRequest()
    }

    override fun remoteArrivalError(type: Int) {
        diglog?.dismiss()
        swipeListView.swipeLayout!!.isRefreshing = false
        when (type) {
            IViewArrival.ARRIVAL_BILLS_ENSURE_ERROR -> Toasty.error(this, getString(R.string.arrvial_ensure_error)).show()
            IViewArrival.ARRIVAL_BILLS_ERROR -> swipeListView.requestError()
        }
    }
}