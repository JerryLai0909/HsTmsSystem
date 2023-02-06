package com.infinity.jerry.yyd_tms.bluetooth;


import android.bluetooth.BluetoothDevice;

import com.gprinter.aidl.GpService;

/**
 * Created by root on 4/8/17.
 */

public class LJBPrinterImpl implements ILPrinter {

    private GpService mGpService;
    private String address;

    public LJBPrinterImpl() {
    }

    public LJBPrinterImpl(String address) {
        this.address = address;
    }

    @Override
    public void connectPrinter(BluetoothDevice device, IBtCallBack callBack) {
        int rel = 0;
//        try {//使用端口1，4代表模式为蓝牙模式，蓝牙地址，最后默认为0
//            rel = mGpService.openPort(1, 4, device.getAddress(), 0);
//            Log.e("TAG", "11111");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
//        if (r != GpCom.ERROR_CODE.SUCCESS) {
//            callBack.onBtConnError();
//        } else {
//            ZShrPrefencs.getInstance().saveBlueToothDeviceName(device.getName());
//            Log.e("TAG", "jb连接成功 " + GpCom.getErrorText(r) + " 蓝牙名字 " + device.getName());
//            callBack.onBtConnSucc();
//            ZBtConnFactory.Companion.getInstance().getAdapter().cancelDiscovery();
//        }
//
    }
}
