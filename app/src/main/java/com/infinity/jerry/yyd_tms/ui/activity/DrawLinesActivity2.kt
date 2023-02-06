/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BillTerimi
import com.infinity.jerry.yyd_tms.data.StationEditEntity
import com.infinity.jerry.yyd_tms.data.StationToSmallEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.ZMesureGridView
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_draw_lines2.*
import kotterknife.bindView
import org.litepal.crud.DataSupport

class DrawLinesActivity2 : BaseActivity() {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.zTitlebar)
    val zSwipelistView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.zListView)

    var bigData: MutableList<StationEditEntity>? = null
    var small: MutableList<StationToSmallEntity>? = null

    var adapter: ZCommonAdapter<StationEditEntity>? = null

    var lists: List<BillTerimi>? = null

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_draw_lines2
    }

    override fun initData() {

    }

    private fun getLines() {
        GiveMeCommonData.getInstance().giveMeAllTerminal(object : CommonPresenter.OnGetAllTerminalListener {
            override fun getTerminalSucc(lists: List<BillTerimi>) {
                zSwipelistView.showListView()
                val entity = StationEditEntity()
                entity.isShow = true
                entity.isLast = true
                entity.name = "所有到站(未分组)"
                lists.forEach {
                    val item = StationToSmallEntity()
                    item.smallId = it.uuid
                    item.stationEditEntityId = -256
                    item.name = it.point_name
                    item.logistics_uuid = it.logistics_uuid
                    entity.smallEntityList.add(item)
                }
                bigData!!.add(entity)
                Log.e("TAG", bigData!!.toString())
                this@DrawLinesActivity2.lists = lists
                initListView()
            }

            override fun getTerminalError() {
                zSwipelistView.requestError()
                Toasty.error(this@DrawLinesActivity2, "获取到站失败").show()
            }

        })
    }

    private fun initListView() {
        adapter = object : ZCommonAdapter<StationEditEntity>(this, bigData, R.layout.list_itemgrid_station) {
            override fun convert(holder: ZViewHolder?, item: StationEditEntity?, position: Int) {
                val llName = holder!!.getView<LinearLayout>(R.id.llName)
//                llName.setOnClickListener {
//                    if (bigData!![position].isLast) {
//                        bigData!![position].isShow = !bigData!![position].isShow
//                        Log.e("TAG", "111" + bigData!!.toString())
//                    }
//                    adapter!!.notifyDataSetChanged()
//                }

                val tvName = holder.getView<TextView>(R.id.tvName)
                tvName.text = item!!.name
                val tvOpen = holder.getView<TextView>(R.id.tvOpen)
                tvOpen.visibility = View.GONE
//
//                if (bigData!![position].isLast) {
//                    tvOpen.visibility = View.VISIBLE
//                } else {
//                    tvOpen.visibility = View.GONE
//                }

                val gridView = holder.getView<ZMesureGridView>(R.id.gridView)

                gridView.adapter = object : ZCommonAdapter<StationToSmallEntity>(this@DrawLinesActivity2, item.smallEntityList, R.layout.item_draw_lines3) {
                    override fun convert(holder: ZViewHolder?, item: StationToSmallEntity?, position: Int) {
                        val tvLinesName = holder!!.getView<AutoScaleTextView>(R.id.tvLinesName)
                        tvLinesName.text = item!!.name
                        val imCooper = holder.getView<ImageView>(R.id.im_core)
                        if (item.logistics_uuid != AppUserToken.getInstance().result.logistics_uuid) {
                            imCooper.visibility = View.VISIBLE
                        } else {
                            imCooper.visibility = View.GONE
                        }
                    }

                }
                gridView.setOnItemClickListener { parent, view, position2, id ->
                    val bundle = Bundle()
                    val bigId = item.smallEntityList[position2].stationEditEntityId
                    val idx = item.smallEntityList[position2].smallId
                    var entity: BillTerimi? = null
                    for (list in this@DrawLinesActivity2.lists!!) {
                        if (list.uuid == idx) {
                            entity = list
                        }
                    }
                    val temp = DataSupport.where("stationEditEntityId = ? AND smallId = ?", bigId.toString(), idx.toString()).findFirst(StationToSmallEntity::class.java)
                    if (temp !== null) {
                        val values = ContentValues()
                        values.put("sortCount", temp!!.sortCount + 1)
                        DataSupport.update(StationToSmallEntity::class.java, values, temp.id)
                    }
                    bundle.putSerializable("lineEntity", entity)
                    startActivity(DrawBillActivity::class.java, bundle)
                }
//                if (item.isLast) {
//                    if (item.isShow) {
//                        gridView.visibility = View.VISIBLE
//                    } else {
//                        gridView.visibility = View.GONE
//                    }
//                }
            }
        }
        zSwipelistView.listView?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        bigData = DataSupport.findAll(StationEditEntity::class.java)
        bigData!!.forEach {
            val id = it.id
            val find = DataSupport.where("stationEditEntityId = ?", id.toString()).order("sortCount DESC").find(StationToSmallEntity::class.java)
            it.smallEntityList = find
        }
        getLines()
    }

    override fun initPresenter() {

    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.lines_choose))
        titleBar.setTitleMode(ZTitlebar.MODE_TEXT)
        titleBar.setTvPlusText("分组显示")
        titleBar.setOnTextModeListener(object : ZTitlebar.OnTextModeListener {
            override fun onClickTextMode() {
                val intent = Intent(this@DrawLinesActivity2, EditStationActivity::class.java)
                startActivity(intent)
            }
        })
        zListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                bigData = DataSupport.findAll(StationEditEntity::class.java)
                bigData!!.forEach {
                    val id = it.id
                    val find = DataSupport.where("stationEditEntityId = ?", id.toString()).order("sortCount DESC").find(StationToSmallEntity::class.java)
                    it.smallEntityList = find
                }
                getLines()
            }
        })

    }


}
