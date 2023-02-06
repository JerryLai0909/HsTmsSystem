/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import com.gprinter.aidl.GpService
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs

/**
 * Created by jerry on 2017/6/12.
 */
class ZBtConnFactory private constructor() {

    val adapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val OPEN_BLUETOOTH = BluetoothAdapter.ACTION_REQUEST_ENABLE
    private val BLUETOOTH_TIMEOUT = BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION
    var mGpService: GpService? = null
    var listener: OnPrinterStateListener? = null


    init {
    }

    //检测蓝牙设备
    private fun hasBlueTooth(): Boolean {
        var flag = true
        if (adapter == null) {
            flag = false
        }
        return flag
    }

    //打开蓝牙设备
    public fun openBlueTooth(context: Context) {
        if (!adapter!!.enable()) {
            val intent = Intent(OPEN_BLUETOOTH)
            intent.putExtra(BLUETOOTH_TIMEOUT, 300)
            context.startActivity(intent)
        }
    }


    //搜索蓝牙
    fun searchBlueToothNearBy() {
        if (adapter != null) {
            if (!adapter!!.isDiscovering) {
                adapter.startDiscovery()
            }
        }
    }

    fun stopSearchBt() {
        if (adapter != null) {
            if (adapter!!.isDiscovering) {
                adapter.cancelDiscovery()
            }
        }
    }

    fun connToPrinter(context: Context) {
        val curName = ZShrPrefencs.getInstance().getBlueToothDeviceName()
        simpleConnShit(context)
    }

    fun setOnPrinterStateListener(listener: OnPrinterStateListener) {
        this.listener = listener
    }

    interface OnPrinterStateListener {
        fun stateOn()
        fun stateOff()
    }

    private fun simpleConnShit(context: Context) {
        if (hasBlueTooth()) {
            openBlueTooth(context)
            searchBlueToothNearBy()
        }
    }

    fun checkJBState(): Int? {
        var state: Int? = -256
        return state
    }

    fun startJbPrint() {
        val util = ZPrintUtil()
        util.printJb()
    }


    //重置蓝牙设置
    fun resetBt() {

    }

    companion object {
        var zFactory: ZBtConnFactory? = null
        fun getInstance(): ZBtConnFactory {
            if (zFactory == null) {
                zFactory = ZBtConnFactory()
            }
            return zFactory as ZBtConnFactory
        }
    }
}