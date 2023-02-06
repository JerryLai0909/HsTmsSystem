/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.Manifest
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.DeviceConnFactoryManager
import com.infinity.jerry.yyd_tms.bluetooth.ZBtConnFactory
import com.infinity.jerry.yyd_tms.bluetooth.ZBtSetReceiver
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/19.
 */
class MineBTActivityBiao : BaseActivity(), ZBtSetReceiver.OnBluetoothFoundListener {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val listView: ListView by bindView<ListView>(R.id.listView)
    val btSearch: TextView by bindView<TextView>(R.id.tvSearchBt)
    val llConn: LinearLayout by bindView<LinearLayout>(R.id.ll_conn)
    val tvInfo: TextView by bindView<TextView>(R.id.tv_conn_info)
    val tvOpenBle: TextView by bindView<TextView>(R.id.tvOpenBle)
    var receiver: ZBtSetReceiver? = null
    var dialog: Dialog? = null
    var adapter: ZCommonAdapter<BluetoothDevice>? = null

    var factory: ZBtConnFactory? = null
    var intentFilter: IntentFilter? = null


    val REQUEST_BLUETOOTH_PEMISSION = 32
    val REQUEST_COARSE_LOCATION = 34

    var listName: MutableList<BluetoothDevice>? = null

    private var mana: DeviceConnFactoryManager? = null

    var name: String? = ZShrPrefencs.getInstance().blueToothDeviceName2
    var mac: String? = ZShrPrefencs.getInstance().blueToothMac2

    override fun initData() {
        checkPermission()
        receiver = ZBtSetReceiver()
        receiver!!.setOnBluetoothFoundListener(this)
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.searching), true)
        factory = ZBtConnFactory.getInstance()
        intentFilter = IntentFilter()
        intentFilter!!.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter!!.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter!!.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(receiver, intentFilter)
        listName = ArrayList<BluetoothDevice>()
        checkPermission()

        if (name == null) {
            name = ""
        }
        if (mac == null) {
            mac = ""
        }
        tvInfo.text = "当前连接的打印机: " + name + "  " + mac
        if (ZShrPrefencs.getInstance().isOpenPrinter) {
            tvOpenBle.text = "蓝牙打印已启用"
            tvOpenBle.setBackgroundColor(ContextCompat.getColor(this, R.color.color_darkOrange))
        } else {
            tvOpenBle.text = "蓝牙打印已停用"
            tvOpenBle.setBackgroundColor(ContextCompat.getColor(this, R.color.color_darkGray))
        }
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_bluetooth
    }

    override fun initPresenter() {

    }

    override fun initView() {
        titleBar.setTitle("标签打印")
        btSearch.setOnClickListener { searchBt() }
        llConn.setOnClickListener { simpleConn() }
        adapter = object : ZCommonAdapter<BluetoothDevice>(this, listName, R.layout.item_bt_device) {
            override fun convert(holder: ZViewHolder?, item: BluetoothDevice?, position: Int) {
                val tvBtName = holder!!.getView<TextView>(R.id.tv_bt_Name)
                tvBtName.text = item!!.name
                val tvMac = holder.getView<TextView>(R.id.tv_bt_mac)
                tvMac.text = item.address
                val tvConn = holder.getView<TextView>(R.id.tv_conn)
                tvConn.setOnClickListener { connBle(item) }
                val tvDisConn = holder.getView<TextView>(R.id.tv_disconn)
                tvDisConn.setOnClickListener { disConnBle(item) }
            }
        }
        tvOpenBle.setOnClickListener {
            if (!ZShrPrefencs.getInstance().isOpenPrinter) {//打开
                ZShrPrefencs.getInstance().isOpenPrinter = true
                tvOpenBle.text = "蓝牙打印已启用"
                tvOpenBle.setBackgroundColor(ContextCompat.getColor(this, R.color.color_darkOrange))
            } else {
                ZShrPrefencs.getInstance().isOpenPrinter = false
                tvOpenBle.text = "蓝牙打印已停用"
                tvOpenBle.setBackgroundColor(ContextCompat.getColor(this, R.color.color_darkGray))
            }
        }
        listView!!.adapter = adapter
    }

    private fun simpleConn() {

    }

    private fun connBle(item: BluetoothDevice?) {
        try {
            mana = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1]
        } catch (e: Exception) {
        }
        if (mana != null) {
            if (mana!!.connState) {
                if (!item!!.address.equals(mac)) {
                    Toasty.info(this@MineBTActivityBiao, "请先断开 " + name + " 的打印机").show()
                } else {
                    Toasty.info(this@MineBTActivityBiao, "打印机连接成功").show()
                }
            } else {
                receiver!!.judgeTheName2(item)
                name = item!!.name
                mac = item.address
                tvInfo.text = "当前连接的打印机: " + name + "  " + mac
            }
        } else {
            receiver!!.judgeTheName2(item)
            name = item!!.name
            mac = item.address
            tvInfo.text = "当前连接的打印机: " + name + "  " + mac
        }
        ZShrPrefencs.getInstance().saveBlueToothDeviceName2(item.name)
        ZShrPrefencs.getInstance().saveBlueToothMac2(item.address)
        dialog!!.dismiss()
    }

    private fun disConnBle(item: BluetoothDevice?) {
        mHandler.obtainMessage(CONN_STATE_DISCONN).sendToTarget()

    }

    private fun searchBt() {
        listName!!.clear()
        adapter!!.notifyDataSetChanged()
        try {
            factory!!.resetBt()
        } catch (e: Exception) {
            Toasty.error(this, getString(R.string.error_disconnect_port)).show()
        }
        factory!!.searchBlueToothNearBy()
    }


    override fun addDeviceToList(device: BluetoothDevice?) {
        var flag = false
        for (bluetoothDevice in listName!!) {
            if (bluetoothDevice.name.equals(device!!.name) && bluetoothDevice.address.equals(device.address)) {
                flag = true
            }
        }
        if (!flag) {
            listName!!.add(device!!)
            adapter!!.notifyDataSetChanged()
        }

    }

    override fun startSearch() {
        dialog!!.show()
    }

    override fun finishSearch() {
        dialog!!.dismiss()
    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), REQUEST_BLUETOOTH_PEMISSION)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_COARSE_LOCATION)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PEMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toasty.info(this, getString(R.string.forbiddeng_bt))

            }
        }
        if (requestCode == REQUEST_COARSE_LOCATION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toasty.info(this, getString(R.string.forbiddeng_bt))

            }
        }
    }

    override fun onStop() {

        super.onStop()
    }

    override fun onDestroy() {
        factory!!.stopSearchBt()
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private val CONN_STATE_DISCONN = 0x007

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                CONN_STATE_DISCONN -> if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1] != null) {
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].closePort(1)
                    Toasty.info(this@MineBTActivityBiao, "打印机已经断开").show()
                }

            }
        }
    }
}