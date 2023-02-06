/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/19.
 */
class MineNumberActivity : BaseActivity() {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val spCut: Spinner by bindView<Spinner>(R.id.spinner)
    var cutCount: Int? = null
    override fun initData() {
        cutCount = ZShrPrefencs.getInstance().copyCount
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_print_number
    }

    override fun initPresenter() {

    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.print_number))
        when (cutCount) {
            1 -> spCut.setSelection(0)
            2 -> spCut.setSelection(1)
            3 -> spCut.setSelection(2)
            4 -> spCut.setSelection(3)
            else -> spCut.setSelection(0)
        }

        spCut.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ZShrPrefencs.getInstance().copyCount = position + 1
            }
        }
    }
}