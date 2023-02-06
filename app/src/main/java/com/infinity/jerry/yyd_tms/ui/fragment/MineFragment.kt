/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.fragment

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseFragment
import com.infinity.jerry.yyd_tms.bluetooth.ZBtConnFactory
import com.infinity.jerry.yyd_tms.constant.ConstantPool
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.mvp.presenter.MinePresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewMine
import com.infinity.jerry.yyd_tms.ui.activity.*
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import com.tencent.bugly.beta.Beta
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/6.
 */
class MineFragment : BaseFragment(), View.OnClickListener, IViewMine {

    val llPwd: LinearLayout by bindView(R.id.mine_pwd)
    val llBlue: LinearLayout by bindView<LinearLayout>(R.id.mine_blueTooth)
    val llNumber: LinearLayout by bindView<LinearLayout>(R.id.mine_settingNumber)
    val llAihint: LinearLayout by bindView<LinearLayout>(R.id.mine_ai_hint)
    val llVersion: LinearLayout by bindView<LinearLayout>(R.id.mine_versioncheck)
    val llQuitLogin: LinearLayout by bindView<LinearLayout>(R.id.mine_quitLogin)
    val llNetPrinter: LinearLayout by bindView<LinearLayout>(R.id.mine_net_printer)
    val mine_billsetting: LinearLayout by bindView<LinearLayout>(R.id.mine_billsetting)
    val llBlueBiao: LinearLayout by bindView<LinearLayout>(R.id.mine_blueBiao)
    val llBiaoGeshi: LinearLayout by bindView<LinearLayout>(R.id.mine_biaogeshi)

    val tvLoginOut: TextView by bindView<TextView>(R.id.tvLoginOut)


    val tvNumber: TextView by bindView<TextView>(R.id.mine_tvNumber)
    val tvCall: TextView by bindView<TextView>(R.id.mine_service_phone)
    val tvPointName: TextView by bindView<TextView>(R.id.tvPointName)
    val tvUserName: TextView by bindView<TextView>(R.id.tvUserName)


    var presenter: MinePresenter? = null
    override fun initData() {
        presenter = MinePresenter.getInstance(this)
    }

    override fun onResume() {
        tvPointName.text = getNullablString(AppUserToken.getInstance().result.point_name)
        tvUserName.text = getNullablString(AppUserToken.getInstance().result.user_name) + "     " + getNullablString(AppUserToken.getInstance().result.phone_one)
        tvNumber.text = ZShrPrefencs.getInstance().copyCount.toString() + "份"
        super.onResume()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        llPwd.setOnClickListener(this)
        llBlue.setOnClickListener(this)
        llNumber.setOnClickListener(this)
        llAihint.setOnClickListener(this)
        llVersion.setOnClickListener(this)
        llQuitLogin.setOnClickListener(this)
        tvCall.setOnClickListener(this)
        llNetPrinter.setOnClickListener(this)
        mine_billsetting.setOnClickListener(this)
        llBlueBiao.setOnClickListener(this)
        llBiaoGeshi.setOnClickListener(this)

        val address = ConstantPool.BASE_URL
        if (address.contains("119.23.55.214")) {
            tvLoginOut.text = "(正式版)"
        } else {
            tvLoginOut.text = "(测试版)"
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_pwd -> startActivity(MinePwdActivity::class.java)
            R.id.mine_blueTooth -> startActivity(MineBTActivity::class.java)
            R.id.mine_blueBiao -> startActivity(MineBTActivityBiao::class.java)
            R.id.mine_settingNumber -> startActivity(MineNumberActivity::class.java)
            R.id.mine_ai_hint -> startActivity(MineAiHintActivity::class.java)
            R.id.mine_versioncheck -> Beta.checkUpgrade(true, false)
            R.id.mine_quitLogin -> showLoginoutDialog()
            R.id.mine_service_phone -> showCallDialog()
            R.id.mine_net_printer -> startActivity(NetPrinterSetActivity::class.java)
            R.id.mine_billsetting -> startActivity(BillSettingActivity::class.java)
            R.id.mine_biaogeshi -> startActivity(MineBiaoGeShiActivity::class.java)
        }
    }

    private val REQUEST_PHONE_CALL = 64

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
        } else {
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toasty.info(activity, "您拒绝了权限").show()
            } else {
                callPhone()
            }
        }
    }

    private fun callPhone() {
        val callIntent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:" + "02368189156")
        callIntent.data = data
        startActivity(callIntent)
    }

    private fun showCallDialog() {
        val alert = AlertDialog.Builder(activity)
                .setMessage(getString(R.string.ensureCall))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    checkPermission()
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    private fun showLoginoutDialog() {
        val alert = AlertDialog.Builder(activity)
                .setMessage(getString(R.string.ensure_loginOut))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    presenter!!.quitLogin()
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    override fun updatePwdSucc() {

    }

    override fun loginOutSucc() {
        ZBtConnFactory.getInstance().resetBt()
        ZShrPrefencs.getInstance().clearData()
        AppUserToken.getInstance().result = null
        startActivity(LoginActivity::class.java)
    }

    override fun settingError(type: Int) {
    }

}