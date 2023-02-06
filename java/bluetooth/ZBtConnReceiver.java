/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.infinity.jerry.yyd_tms.app.KotlingApplication;
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs;

import java.lang.reflect.Method;

import es.dmoral.toasty.Toasty;

/**
 * Created by root on 4/8/17.
 */

public class ZBtConnReceiver extends BroadcastReceiver {

    private String currentBtName = "";
    private ILPrinter ilPrinter;

    private IBtConnBack connBack;

    private OnBtSearchingListener mListener;

    public ZBtConnReceiver() {
        currentBtName = ZShrPrefencs.getInstance().getBlueToothDeviceName();
    }

    public ZBtConnReceiver(IBtConnBack connBack) {
        currentBtName = ZShrPrefencs.getInstance().getBlueToothDeviceName();
        this.connBack = connBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        String name = ZShrPrefencs.getInstance().getBlueToothDeviceName();
        if (name == null) {
            return;
        }
        if (device == null) {
            return;
        }
        if (device.getName() != null) {
            if (!device.getName().equals(name)) {
                Log.e("TAG", "not target " + device.getName());
            } else {
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.e("TAG", "check bluetooth   - >  " + device.getName() + "    " + device.getAddress());
                    if (device.getName() != null) {
                        judgeTheName(device);
                    }
                } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                    if (device.getName() != null) {
                        judgeTheName(device);
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    if (mListener != null) {
                        mListener.startSearchBT();
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    if (mListener != null) {
                        mListener.finishSearchBT();
                    }
                }
            }
        }
    }

    public void setOnBtSearchListener(OnBtSearchingListener mListener) {
        this.mListener = mListener;
    }

    public interface OnBtSearchingListener {
        void startSearchBT();

        void finishSearchBT();
    }

    private void judgeTheName(BluetoothDevice device) {
        if (!ZShrPrefencs.getInstance().isOpenPrinter()) {
            Toasty.info(KotlingApplication.Companion.getApp(), "未启用蓝牙打印").show();
            return;
        }
        String btName = device.getName();
        if (btName == null) {
            return;
        }
        int bondState = device.getBondState();
        switch (bondState) {
            case BluetoothDevice.BOND_BONDED:
                String macAddress = device.getAddress();
                //初始化话DeviceConnFactoryManager
                new DeviceConnFactoryManager.Build()
                        .setId(0)
                        //设置连接方式
                        .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                        //设置连接的蓝牙mac地址
                        .setMacAddress(macAddress)
                        .build();
                //打开端口
                DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].openPort();
                connBack.btConnSucc();
                break;
            case BluetoothDevice.BOND_NONE:
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (Exception e) {
                    e.printStackTrace();
                    connBack.btConnError();
                }
                break;
        }
    }


}
