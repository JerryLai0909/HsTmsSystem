/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.support.v4.content.ContextCompat
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.constant.ConstantPool
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.LoginResult
import com.infinity.jerry.yyd_tms.mvp.presenter.LoginPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLogin

/**
 * Created by jerry on 2017/6/12.
 *
 */
class WelcomeActivity : BaseActivity(), IViewLogin {

    var sp: SharedPreferences? = null
    var isLogin: Boolean = false
    var presenter: LoginPresenter? = null

    override fun initData() {

    }

    override fun getLayoutId(): Int {
        SetStatusBarColor(ContextCompat.getColor(this, R.color.color_white))
        return R.layout.activity_welcome
    }

    override fun initPresenter() {
        presenter = LoginPresenter.getInstance(this)
        sp = this.getSharedPreferences(ConstantPool.USER_SHARE, Context.MODE_PRIVATE)
        isLogin = sp!!.getBoolean("is_login", false)
        if (isLogin) {
            Handler().postDelayed({
                presenter!!.userLogin()
            }, 500)
        } else {
            Handler().postDelayed({
                startActivity(LoginActivity::class.java)
                finish()
            }, 500)
        }

    }

    override fun initView() {

    }


    override fun loginSucc(appUser: LoginResult) {
        AppUserToken.getInstance().result = appUser
        startActivity(MainActivity::class.java)
        this.finish()
    }

    override fun loginFail() {
        startActivity(LoginActivity::class.java)
        this.finish()
    }

}