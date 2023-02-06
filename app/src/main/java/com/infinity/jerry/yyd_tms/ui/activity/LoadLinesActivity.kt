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
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.LoadCarLineEntity
import com.infinity.jerry.yyd_tms.data.LoadCarShitEntity
import com.infinity.jerry.yyd_tms.data.TempIdsEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.LoadListEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewBillEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.LoadCarLinesPersenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadCarLine
import com.infinity.jerry.yyd_tms.ui.customview.ZMesuredListView
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/7.
 */
class LoadLinesActivity : BaseActivity(), IViewLoadCarLine {


    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val tvLoadCar: TextView by bindView<TextView>(R.id.loadEnsure)
    var presenter: LoadCarLinesPersenter? = null
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    var adapter: ZCommonAdapter<LoadListEntity>? = null
    var loadLists: List<LoadListEntity>? = null
    var batchNumber: String = ""

    val tvEnsure: TextView by bindView<TextView>(R.id.loadEnsure)
    override fun initData() {
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_loadlines
    }

    override fun initPresenter() {
        presenter = LoadCarLinesPersenter.getInstance(this)
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    private fun sendRequest() {
        presenter!!.getLoadList()
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.loadCar))
        titleBar.setTitleMode(ZTitlebar.MODE_TEXT)
        titleBar.setTvPlusText("装车历史")
        titleBar.setOnTextModeListener(object : ZTitlebar.OnTextModeListener {
            override fun onClickTextMode() {
                startActivityForResult(LoadRecordActivity::class.java, 0)
            }
        })
        tvLoadCar.setOnClickListener {
            startActivity(LoadCarActivity::class.java)
        }
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }

        })
        tvEnsure.setOnClickListener {
            if (batchNumber.equals("")) {
                ensureLoad()
            } else {
                ensureBuZhunag()
            }
        }
    }

    private fun ensureBuZhunag() {
        if (loadLists == null || loadLists!!.isEmpty()) {
            return
        }
        val listIds = ArrayList<Long>()
        loadLists!!.forEach {
            for (item in it.billEntityList) {
                if (item.isChoosed) {
                    listIds.add(item.id)
                }
            }
        }
        val entity = TempIdsEntity()
        entity.ids = listIds
        presenter!!.buZhuang(batchNumber, entity)
    }

    private fun ensureLoad() {//准备装车的后续
        if (loadLists == null || loadLists!!.isEmpty()) {
            return
        }
        val list = ArrayList<LoadCarLineEntity>()

        loadLists!!.forEach {
            for (item in it.billEntityList) {
                if (item.isChoosed) {
                    val entity = LoadCarLineEntity()
                    entity.uuid = item.id.toLong()
                    entity.article_number = item.articleNumber
                    entity.goods_name = item.goodsName
                    entity.initial_station_id = item.initialStationId.toLong()
                    entity.terminal_station_id = item.terminalStationId.toLong()
                    entity.invoice_date = item.invoiceDate
                    entity.transit_destination = item.transitDestination
                    entity.initial_station_name = item.startStation
                    entity.terminal_station_name = item.endStation
                    entity.waybill_number = item.waybillNumber.toString()
                    entity.packaging = item.packaging
                    entity.quantity = item.quantity.toLong()
                    list.add(entity)
                }
            }
        }

        if (list.size == 0) {
            Toasty.info(this, getString(R.string.no_chooose_bill)).show()
            return
        }

        val entity = LoadCarShitEntity()
        entity.list = list
        val bundle = Bundle()
        bundle.putSerializable("loadLines", entity)
        startActivity(LoadCarActivity::class.java, bundle)
    }

    private fun initListView() {
        adapter = object : ZCommonAdapter<LoadListEntity>(this, loadLists, R.layout.item_loadlines) {
            override fun convert(holder: ZViewHolder?, item: LoadListEntity?, position: Int) {

                val tvHeader = holder!!.getView<TextView>(R.id.tvheader)
                tvHeader.text = item!!.endPoint
                val tvDetail = holder.getView<TextView>(R.id.tvDetail)
                tvDetail.text = "(共" + item!!.count + "票/" + item.quantity + "件)"
                val imChooseAll = holder.getView<ImageView>(R.id.chooseAll)

                if (item.isChoose) {
                    imChooseAll.setImageDrawable(ContextCompat.getDrawable(this@LoadLinesActivity, R.mipmap.tms_icon_choose_s_true))
                } else {
                    imChooseAll.setImageDrawable(ContextCompat.getDrawable(this@LoadLinesActivity, R.mipmap.tms_icon_choose_s_false))
                }

                val tvOpen = holder.getView<TextView>(R.id.tvOpen)

                imChooseAll.setOnClickListener {
                    if (item.billEntityList != null && item.billEntityList.size != 0) {
                        chooseAll(item, imChooseAll)
                    } else {
                        Toasty.info(this@LoadLinesActivity, "请先点击展开获取数据").show()
                    }
                }

                val subListView = holder.getView<ZMesuredListView>(R.id.subListView)

                val billAdapter: ZCommonAdapter<NewBillEntity> = object : ZCommonAdapter<NewBillEntity>(this@LoadLinesActivity, item.billEntityList, R.layout.item_loadcar_info) {
                    override fun convert(holder: ZViewHolder?, bill: NewBillEntity?, position: Int) {
                        val imageView = holder!!.getView<ImageView>(R.id.imageView)
                        if (!bill!!.isChoosed) {
                            imageView.setImageDrawable(ContextCompat.getDrawable(this@LoadLinesActivity, R.mipmap.tms_icon_choose_c_false))
                        } else {
                            imageView.setImageDrawable(ContextCompat.getDrawable(this@LoadLinesActivity, R.mipmap.tms_icon_choose_c_true))
                        }
                        val tvInfo = holder!!.getView<TextView>(R.id.tvInfo)
                        tvInfo.setOnClickListener {
                            bill.isChoosed = !bill.isChoosed
                            this.notifyDataSetChanged()
                        }
                        tvInfo.text = AppUserToken.getInstance().result.point_name + " --- " + item.endPoint + "    " + bill.articleNumber
                    }
                }
                subListView.adapter = billAdapter

                tvOpen.setOnClickListener {
                    if (!item.isOpen) {
                        if (item.billEntityList.size == 0) {
                            getEndDetails(item.id, item)
                        }
                        subListView.visibility = View.VISIBLE
                        tvOpen.text = "收折"
                    } else {
                        subListView.visibility = View.GONE
                        tvOpen.text = "展开"
                    }
                    item.isOpen = !item.isOpen
                }

            }

        }
        swipeListView.listView!!.adapter = adapter
        swipeListView.showListView()

        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            adapter?.notifyDataSetChanged()
        }
    }

    override fun getEndPointSucc(dataList: List<NewBillEntity>, item: LoadListEntity) {
        dataList.forEach {
            it.endPointId = item.id
        }
        item.billEntityList.clear()
        item.billEntityList.addAll(dataList)
        adapter!!.notifyDataSetChanged()
    }

    private fun getEndDetails(id: Int, item: LoadListEntity) {
        presenter!!.getEndPointDetails(id, item)
    }

    private fun openRoad(item: LoadCarLineEntity) {
        adapter!!.notifyDataSetChanged()
    }

    private fun chooseAll(item: LoadListEntity?, imChooseAll: ImageView) {
        if (!item!!.isChoose) {
            if (item.billEntityList != null) {
                item.billEntityList.forEach {
                    it.isChoosed = true
                }
            }
        } else {
            if (item.billEntityList != null) {
                item.billEntityList.forEach {
                    it.isChoosed = false
                }
            }
        }
        item.isChoose = !item.isChoose
        adapter!!.notifyDataSetChanged()
    }

    override fun getLoadLinesSucc(entity: List<LoadCarLineEntity>) {

    }

    override fun getLoadListSucc(entity: List<LoadListEntity>) {
        this.loadLists = entity
        if (entity.isEmpty()) {
            swipeListView.requestNoData()
            return
        }
        initListView()
    }

    override fun buZhuangSucc() {
        batchNumber = ""
        sendRequest()
        tvLoadCar.text = "一键装车"
        val intent = Intent(this, LoadRecordActivity::class.java)
        startActivityForResult(intent, 0)
    }

    override fun getLoadLinesError() {
        swipeListView.requestNoData()
    }

    override fun onNetWorkError() {
        swipeListView.requestError()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            batchNumber = data!!.getStringExtra("batchNumber")
            tvLoadCar.text = "补装车"
        }
    }
}