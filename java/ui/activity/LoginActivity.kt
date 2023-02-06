/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.app.AppManager
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.ZBtConnFactory
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.LoginResult
import com.infinity.jerry.yyd_tms.mvp.presenter.LoginPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLogin
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.utils.EncryptMD5
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/13.
 */
class LoginActivity : BaseActivity(), IViewLogin {
    val edName: EditText by bindView<EditText>(R.id.edName)
    val edPwd: EditText by bindView<EditText>(R.id.edPwd)
    val btEnsure: Button by bindView<Button>(R.id.btEnsure)

    var userName: String = ""
    var userPwd: String = ""

    var dialog: Dialog? = null
    var presenter: LoginPresenter? = null
    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
    }

    override fun getLayoutId(): Int {
        SetTranslanteBar()
        return R.layout.activity_login

    }

    override fun initPresenter() {
        presenter = LoginPresenter.getInstance(this)
    }

    override fun initView() {
        btEnsure.setOnClickListener({
            userName = getEDString(edName)
            userPwd = getEDString(edPwd)
            dialog!!.show()
            presenter!!.userLogin(userName, userPwd)
        })
    }


    override fun loginSucc(appUser: LoginResult) {
        dialog!!.dismiss()
        ZShrPrefencs.getInstance().setNameAndPwd(userName, EncryptMD5.getInstance().getPwdMd5LowerCase(userPwd))
//        ZShrPrefencs.getInstance().setNameAndPwd(userName,userPwd)
        AppUserToken.getInstance().result = appUser
        startActivity(MainActivity::class.java)
        this.finish()
    }

    override fun loginFail() {
        dialog!!.dismiss()
        Toasty.error(this, "网络请求异常").show()
    }

    private var backTime: Long = 0

    override fun onBackPressed() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - backTime > 2000) {//如果两次按键时间间隔大于800毫秒，则不退出
            backTime = secondTime//更新firstTime
            Toasty.info(this, "再按一次退出程序").show()
        } else {
            ZBtConnFactory.getInstance().resetBt()
            AppManager.getAppManager().AppExit(this, true)
        }
    }
}