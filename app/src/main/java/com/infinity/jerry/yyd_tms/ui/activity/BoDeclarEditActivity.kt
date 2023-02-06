/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.Declaration
import com.infinity.jerry.yyd_tms.mvp.presenter.DeclarsPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewDeclaration
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/15.
 */
class BoDeclarEditActivity : BaseActivity(), IViewDeclaration {

    var presenter: DeclarsPresenter? = null
    var declarEntity: Declaration? = null
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edContent: EditText by bindView<EditText>(R.id.edContent)
    val tvCount: TextView by bindView<TextView>(R.id.tvCount)
    val btEditEnsure: TextView by bindView<TextView>(R.id.tvEditDeclar)

    var lastSort: Int? = 1

    override fun initData() {
        declarEntity = intent.extras?.getSerializable("decEntity") as Declaration?
        var entity = (intent.extras?.getSerializable("lastDec") as Declaration?)
        if (entity != null) {
            lastSort = entity.logistics_sort
        }
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_edit_declar
    }

    override fun initPresenter() {
        presenter = DeclarsPresenter.getIsntance(this)
    }

    override fun initView() {
        if (declarEntity == null) {
            btEditEnsure.setText(getString(R.string.save))
            titleBar.setTitle(getString(R.string.add_declar))
        } else {
            btEditEnsure.setText(getString(R.string.ensure_edit))
            titleBar.setTitle(getString(R.string.edit_dec))
            edContent.setText(declarEntity!!.logistics_notice)
        }
        edContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tvCount.setText(s?.toString()!!.length.toString() + "/100")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        btEditEnsure.setOnClickListener { confirmInfo() }

    }

    private fun confirmInfo() {
        var string = edContent.text.toString()
        if (string == null || string.length == 0) {
            Toasty.info(this, getString(R.string.null_info_declar))
            return
        }

        var confirmEntity = Declaration()
        confirmEntity.uuid = declarEntity?.uuid
        confirmEntity.logistics_notice = string
        confirmEntity.logistics_sort = lastSort

        Logger.i(ZGsonUtils.getInstance().getJsonString(confirmEntity))
        presenter!!.eidtDeclars(ZGsonUtils.getInstance().getJsonString(confirmEntity))

    }

    override fun onNetWorkError() {
    }

    override fun getDeclarsSucc(declas: List<Declaration>) {

    }

    override fun editDeclarSucc() {//this one
        Toasty.success(this, "编辑声明成功").show()
        this.finish()
    }

    override fun deleDeclarSucc() {
    }

    override fun remoteDelarsError(type: Int) {
        Toasty.error(this, "编辑声明失败").show()
    }

}