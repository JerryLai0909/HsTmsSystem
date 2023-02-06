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
 * Created by jerry on 2018/7/26.
 * You are good enough to do everthing
 */
public class MyService extends Service {

    private PosPrinterDev xPrinterDev;
    private PosPrinterDev.ReturnMessage mMsg;
    private boolean isConnected = false;
    private RoundQueue<byte[]> que;
    private IBinder myBinder = new MyService.MyBinder();

    public MyService() {
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
            Log.e("TAG", "标签 service open " + ethernetPort + "  " + ethernetIP);
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.Ethernet, ethernetIP, ethernetPort);
                    MyService.this.mMsg = MyService.this.xPrinterDev.Open();
                    if (MyService.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService.this.isConnected = true;
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
                    MyService.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.Bluetooth, bluetoothID);
                    MyService.this.mMsg = MyService.this.xPrinterDev.Open();
                    if (MyService.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService.this.isConnected = true;
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
                    MyService.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.USB, context, usbPathName);
                    MyService.this.mMsg = MyService.this.xPrinterDev.Open();
                    if (MyService.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService.this.isConnected = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void disconnectCurrentPort(UiExecute execute) {
            if (MyService.this.xPrinterDev == null) {
                execute.onsucess();
                return;
            }
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService.this.mMsg = MyService.this.xPrinterDev.Close();
                    if (MyService.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.ClosePortSuccess)) {
                        MyService.this.isConnected = false;
                        if (MyService.this.que != null) {
                            MyService.this.que.clear();
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
                        MyService.this.mMsg = MyService.this.xPrinterDev.Write(data);
                        if (MyService.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                            MyService.this.isConnected = true;
                            return true;
                        }

                        MyService.this.isConnected = false;
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
                            if (MyService.this.xPrinterDev == null) {
                                MyService.this.isConnected = false;
                                return false;
                            }

                            MyService.this.mMsg = MyService.this.xPrinterDev.Write((byte[]) list.get(i));
                        }

                        if (MyService.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                            MyService.this.isConnected = true;
                            return true;
                        }

                        MyService.this.isConnected = false;
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
                    MyService.this.que = MyService.this.getinstaceRoundQueue();
                    byte[] buffer = new byte[4];
                    MyService.this.que.clear();
                    Log.i("TAG", MyService.this.xPrinterDev.Read(buffer).GetErrorCode().toString());

                    for (; MyService.this.xPrinterDev.Read(buffer).GetErrorCode().equals(PosPrinterDev.ErrorCode.ReadDataSuccess); MyService.this.que.addLast(buffer)) {
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException var3) {
                            var3.printStackTrace();
                            return false;
                        }
                    }

                    MyService.this.isConnected = false;
                    return false;
                }
            });
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }

        public RoundQueue<byte[]> readBuffer() {
            new RoundQueue(500);
            RoundQueue<byte[]> queue = MyService.this.que;
            return queue;
        }

        public void clearBuffer() {
            MyService.this.que.clear();
        }

        public void checkLinkedState(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    if (MyService.this.isConnected) {
                        MyService.this.isConnected = MyService.this.xPrinterDev.GetPortInfo().PortIsOpen();
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


