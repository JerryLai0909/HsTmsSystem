/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import net.posprinter.asynncTask.PosAsynncTask;
import net.posprinter.posprinterface.BackgroundInit;
import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.PosPrinterDev;
import net.posprinter.utils.RoundQueue;

import java.util.List;

/**
 * Created by jerry on 2018/7/27.
 * You are good enough to do everthing
 */
public class MyService2 extends Service {

    private PosPrinterDev xPrinterDev;
    private PosPrinterDev.ReturnMessage mMsg;
    private boolean isConnected = false;
    private RoundQueue<byte[]> que;
    private IBinder myBinder = new MyService2.MyBinder();

    public MyService2() {
    }

    private RoundQueue<byte[]> getinstaceRoundQueue() {
        if (this.que == null) {
            this.que = new RoundQueue(500);
        }

        return this.que;
    }

    public void onCreate() {
        super.onCreate();
        this.que = this.getinstaceRoundQueue();
    }

    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.xPrinterDev != null) {
            this.xPrinterDev.Close();
        }

    }

    public class MyBinder extends Binder implements IMyBinder {
        public MyBinder() {
        }

        public void connectNetPort(final String ethernetIP, final int ethernetPort, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService2.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.Ethernet, ethernetIP, ethernetPort);
                    MyService2.this.mMsg = MyService2.this.xPrinterDev.Open();
                    if (MyService2.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService2.this.isConnected = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void connectBtPort(final String bluetoothID, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService2.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.Bluetooth, bluetoothID);
                    MyService2.this.mMsg = MyService2.this.xPrinterDev.Open();
                    if (MyService2.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService2.this.isConnected = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void connectUsbPort(final Context context, final String usbPathName, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService2.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.USB, context, usbPathName);
                    MyService2.this.mMsg = MyService2.this.xPrinterDev.Open();
                    if (MyService2.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService2.this.isConnected = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void disconnectCurrentPort(UiExecute execute) {
            if (xPrinterDev == null) {
                return;
            }
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService2.this.mMsg = MyService2.this.xPrinterDev.Close();
                    if (MyService2.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.ClosePortSuccess)) {
                        MyService2.this.isConnected = false;
                        if (MyService2.this.que != null) {
                            MyService2.this.que.clear();
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void write(final byte[] data, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    if (data != null) {
                        MyService2.this.mMsg = MyService2.this.xPrinterDev.Write(data);
                        if (MyService2.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                            MyService2.this.isConnected = true;
                            return true;
                        }

                        MyService2.this.isConnected = false;
                    }

                    return false;
                }
            });
            task.execute(new Void[0]);
        }

        public void writeDataByYouself(UiExecute execute, final ProcessData processData) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    List<byte[]> list = processData.processDataBeforeSend();
                    if (list != null) {
                        for (int i = 0; i < list.size(); ++i) {
                            if (MyService2.this.xPrinterDev == null) {
                                MyService2.this.isConnected = false;
                                return false;
                            }

                            MyService2.this.mMsg = MyService2.this.xPrinterDev.Write((byte[]) list.get(i));
                        }

                        if (MyService2.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                            MyService2.this.isConnected = true;
                            return true;
                        }

                        MyService2.this.isConnected = false;
                    }

                    return false;
                }
            });
            task.execute(new Void[0]);
        }

        @Override
        public void writeDataByUSB(UiExecute uiExecute, ProcessData processData) {

        }

        @Override
        public void disconnetNetPort(UiExecute uiExecute) {

        }

        public void acceptdatafromprinter(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService2.this.que = MyService2.this.getinstaceRoundQueue();
                    byte[] buffer = new byte[4];
                    MyService2.this.que.clear();
                    Log.i("TAG", MyService2.this.xPrinterDev.Read(buffer).GetErrorCode().toString());

                    for (; MyService2.this.xPrinterDev.Read(buffer).GetErrorCode().equals(PosPrinterDev.ErrorCode.ReadDataSuccess); MyService2.this.que.addLast(buffer)) {
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException var3) {
                            var3.printStackTrace();
                            return false;
                        }
                    }

                    MyService2.this.isConnected = false;
                    return false;
                }
            });
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }

        public RoundQueue<byte[]> readBuffer() {
            new RoundQueue(500);
            RoundQueue<byte[]> queue = MyService2.this.que;
            return queue;
        }

        public void clearBuffer() {
            MyService2.this.que.clear();
        }

        public void checkLinkedState(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    if (MyService2.this.isConnected) {
                        MyService2.this.isConnected = MyService2.this.xPrinterDev.GetPortInfo().PortIsOpen();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }
}
