/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.fragment

import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseFragment
import com.infinity.jerry.yyd_tms.ui.activity.BoCooperMainActivity
import com.infinity.jerry.yyd_tms.ui.activity.BoDeclarConfActivity
import com.infinity.jerry.yyd_tms.ui.activity.BoPointManActivity
import com.infinity.jerry.yyd_tms.ui.activity.BoValueActivity
import com.infinity.jerry.yyd_tms.ui.customview.ZBossItemView
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/6.
 * BossFragment
 */
class FinanceFragment : BaseFragment() {
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val declarConfig: ZBossItemView by bindView<ZBossItemView>(R.id.declar_conf)
    val pointManConfig: ZBossItemView by bindView<ZBossItemView>(R.id.point_conf)
    val cooperConfig: ZBossItemView by bindView<ZBossItemView>(R.id.cooper_conf)
    val valueConfig :ZBossItemView by bindView<ZBossItemView>(R.id.declar_value)
    override fun initData() {

    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_finance
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.boss_config))
        titleBar.noBack()

        declarConfig.setOnClickListener { startActivity(BoDeclarConfActivity::class.java) }
        pointManConfig.setOnClickListener { startActivity(BoPointManActivity::class.java) }
        cooperConfig.setOnClickListener { startActivity(BoCooperMainActivity::class.java) }
        valueConfig.setOnClickListener{startActivity(BoValueActivity::class.java)}
    }

}