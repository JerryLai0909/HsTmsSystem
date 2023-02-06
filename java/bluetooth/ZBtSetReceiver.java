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

public class ZBtSetReceiver extends BroadcastReceiver {


    private OnBluetoothFoundListener mListener;
    private IBtConnBack connBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.e("TAG", "check bluetooth   - >  " + device.getName() + "    " + device.getAddress());

            String deName = device.getName();
            if (deName != null && ((deName.toLowerCase().contains("printer")) || deName.toLowerCase().contains("qr"))) {
                if (mListener != null) {
                    mListener.addDeviceToList(device);
                }
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            if (mListener != null) {
                mListener.startSearch();
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            if (mListener != null) {
                mListener.finishSearch();
            }
        }
    }


    public void judgeTheName(BluetoothDevice device) {
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

                break;
            case BluetoothDevice.BOND_NONE:
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void judgeTheName2(BluetoothDevice device) {
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
                        .setId(1)
                        //设置连接方式
                        .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                        //设置连接的蓝牙mac地址
                        .setMacAddress(macAddress)
                        .build();
                //打开端口
                DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].openPort();

                break;
            case BluetoothDevice.BOND_NONE:
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void setOnBluetoothFoundListener(OnBluetoothFoundListener listener) {
        this.mListener = listener;
    }

    public interface OnBluetoothFoundListener {
        void addDeviceToList(BluetoothDevice device);

        void startSearch();

        void finishSearch();
    }
}
