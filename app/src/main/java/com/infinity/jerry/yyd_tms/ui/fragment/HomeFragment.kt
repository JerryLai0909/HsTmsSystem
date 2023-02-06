/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.fragment

import android.support.v4.content.ContextCompat
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseFragment
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.Declaration
import com.infinity.jerry.yyd_tms.data.DirtyData
import com.infinity.jerry.yyd_tms.mvp.presenter.StatiscPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewStatisc
import com.infinity.jerry.yyd_tms.ui.activity.*
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZDoubleFormat
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/6.
 */
class HomeFragment : BaseFragment(), IViewStatisc {

    val gridView: GridView by bindView<GridView>(R.id.gridView)
    var adapter: ZCommonAdapter<String>? = null
    var presenter: StatiscPresenter? = null
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)

    val tvCount: TextView by bindView<TextView>(R.id.tvBillCount)
    val tvBillsQuantity: TextView by bindView<TextView>(R.id.tvBillQuantity)
    val tvFreight: TextView by bindView<TextView>(R.id.tvFreight2)
    val tvCollect: TextView by bindView<TextView>(R.id.tvCollect)

    //Express
    val onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        when (position) {
            0 -> startActivity(DrawLinesActivity2::class.java)
            1 -> startActivity(LoadLinesActivity::class.java)
            2 -> startActivity(ArrivalActivity::class.java)
            3 -> startActivity(SignActivity::class.java)
            4 -> startActivity(MyBillActivity::class.java)
            5 -> startActivity(BillSearchActivity::class.java)
            6 -> startActivity(DaiShouMainActivity::class.java)
            7 -> startActivity(ReportMainActivity::class.java)
            8 -> startActivity(ErrorMainActivity::class.java)
            9 -> startActivity(TransActivity::class.java)
        }
    }
    //Line
    val onItemClickListener2 = AdapterView.OnItemClickListener { parent, view, position, id ->
        when (position) {
            0 -> startActivity(DrawLinesActivity2::class.java)
            1 -> startActivity(LoadLinesActivity::class.java)
            2 -> startActivity(ArrivalActivity::class.java)
            3 -> startActivity(SignActivity::class.java)
            4 -> startActivity(MyBillActivity::class.java)
            5 -> startActivity(BillSearchActivity::class.java)
            6 -> startActivity(ReportMainActivity::class.java)
            7 -> startActivity(ErrorMainActivity::class.java)
            8 -> startActivity(TransActivity::class.java)
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_main

    override fun initData() {
        presenter = StatiscPresenter.getInstance(this)
    }

    override fun onResume() {
        presenter!!.getStatiscBill()
        initView()
        super.onResume()
    }

    override fun initView() {
        if (AppUserToken.getInstance().result.companyType == "EXPRESS") {
            adapter = object : ZCommonAdapter<String>(activity, DirtyData.mainData, R.layout.item_main_grid) {
                override fun convert(holder: ZViewHolder?, item: String?, position: Int) {
                    val tvItem = holder!!.getView<TextView>(R.id.grid_tv_main)
                    tvItem.setText(DirtyData.mainData.get(position))
                    val imPic = holder!!.getView<ImageView>(R.id.grid_im_main)
                    when (position) {
                        0 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_bill))
                        1 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_load))
                        2 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_arrive))
                        3 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_sign))
                        4 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_trans))
                        5 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_search))
                        6 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_daishou_main))
                        7 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_form))
                        8 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_excep))
                        9 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_other))
                    }
                }
            }
        } else {
            adapter = object : ZCommonAdapter<String>(activity, DirtyData.mainData2, R.layout.item_main_grid) {
                override fun convert(holder: ZViewHolder?, item: String?, position: Int) {
                    val tvItem = holder!!.getView<TextView>(R.id.grid_tv_main)
                    tvItem.setText(DirtyData.mainData2.get(position))
                    val imPic = holder!!.getView<ImageView>(R.id.grid_im_main)
                    when (position) {
                        0 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_bill))
                        1 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_load))
                        2 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_arrive))
                        3 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_sign))
                        4 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_trans))
                        5 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_search))
                        6 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_form))
                        7 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_excep))
                        8 -> imPic.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.tms_main_other))
                    }
                }
            }
        }

        gridView.adapter = adapter
        if (AppUserToken.getInstance().result.companyType.equals("EXPRESS")) {
            gridView.onItemClickListener = onItemClickListener
        } else {
            gridView.onItemClickListener = onItemClickListener2
        }
    }

    override fun getStatiscBillSucc(entity: BillingDrawMain) {
        tvCount.text = entity.totalBill?.toString()
        if (entity.quantity != null) {
            tvBillsQuantity.text = entity.quantity.toString()
        } else {
            tvBillsQuantity.text = "0"
        }
        if (entity.freight != null) {
            tvFreight.text = ZDoubleFormat.zFormat(entity.freight.toString())
        } else {
            tvFreight.text = "0"
        }
        if (entity.collection_fee != null) {
            tvCollect.text = ZDoubleFormat.zFormat(entity.collection_fee.toString())
        } else {
            tvCollect.text = "0"
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            presenter!!.getStatiscBill()
        }
    }

    override fun getStatiscBillError() {
        Toasty.error(activity, getString(R.string.stastic_error)).show()
    }

    override fun getDeclarsSucc(declars: List<Declaration>) {
    }

    override fun getDeclarsError() {
    }

}