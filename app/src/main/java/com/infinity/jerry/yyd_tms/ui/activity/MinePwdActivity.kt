/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.request_entity.MinePwdEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.MinePresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewMine
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.EncryptMD5
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/19.
 */
class MinePwdActivity : BaseActivity(), IViewMine {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edOldPwd: EditText by bindView<EditText>(R.id.edOldPwd)
    val edNewPwd: EditText by bindView<EditText>(R.id.edNewPwd)
    val edConfirm: EditText by bindView<EditText>(R.id.edConfirmPwd)
    val btEnsure: TextView by bindView<TextView>(R.id.tvEnsure)

    var presenter: MinePresenter? = null
    override fun initData() {
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_update_pwd
    }

    override fun initPresenter() {
        presenter = MinePresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.update_owd))
        btEnsure.setOnClickListener { ensureUpdate() }
    }

    private fun ensureUpdate() {
        val oldPwd = getEDString(edOldPwd)
        val newPwd = getEDString(edNewPwd)
        val confirm = getEDString(edConfirm)
        if (checkPwd(oldPwd) && checkPwd(newPwd)) {
            if (newPwd == confirm) {
                val request = MinePwdEntity()
                request.pwd = EncryptMD5.getInstance().getPwdMd5LowerCase(oldPwd)
                request.newpwd = EncryptMD5.getInstance().getPwdMd5LowerCase(newPwd)
                presenter!!.updatePwd(request.pwd, request.newpwd)
            } else {
                Toasty.info(this, getString(R.string.pwd_notsamge)).show()
            }
        }
    }

    private fun checkPwd(pwd: String): Boolean {
        if (pwd.length < 6 || pwd.length > 18) {
            Toasty.info(this, getString(R.string.pwd_format)).show()
            return false
        } else {
            return true
        }
    }

    override fun updatePwdSucc() {
        Toasty.info(this, "修改密码成功").show()
        startActivity(LoginActivity::class.java)
        this.finish()
    }

    override fun loginOutSucc() {
    }

    override fun settingError(type: Int) {
        Toasty.error(this, "密码错误，不允许修改").show()
    }

}