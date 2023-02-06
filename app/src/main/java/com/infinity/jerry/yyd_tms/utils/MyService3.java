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

import java.util.Arrays;
import java.util.List;

/**
 * Created by jerry on 2018/8/16.
 * You are good enough to do everthing
 */
public class MyService3 extends Service {
    private PosPrinterDev xPrinterDev;
    private PosPrinterDev.ReturnMessage mMsg;
    private boolean isConnected = false;
    private RoundQueue<byte[]> que;
    private IBinder myBinder = new MyService3.MyBinder();

    public MyService3() {
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
        Log.i("TAG", "onCreate");
    }

    public IBinder onBind(Intent intent) {
        Log.i("TAG", "onBind");
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
        private String USBName;
        private android.content.Context Context;
        private PosPrinterDev dev;
        Thread t;

        public MyBinder() {
        }

        public void connectNetPort(final String ethernetIP, final int ethernetPort, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService3.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.Ethernet, ethernetIP, ethernetPort);
                    MyService3.this.mMsg = MyService3.this.xPrinterDev.Open();

                    try {
                        if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                            MyService3.this.isConnected = true;
                            Log.e("connectNetPort", "1");
                            return true;
                        } else {
                            Log.e("connectNetPort", "2");
                            MyService3.this.isConnected = false;
                            return false;
                        }
                    } catch (Exception var2) {
                        var2.printStackTrace();
                        Log.e("connectNetPort", "3");
                        return false;
                    }
                }
            });
            Log.e("connectNetPort", "5");
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }

        public void connectBtPort(final String bluetoothID, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService3.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.Bluetooth, bluetoothID);
                    MyService3.this.mMsg = MyService3.this.xPrinterDev.Open();
                    if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService3.this.isConnected = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void connectUsbPort(final Context context, final String usbPathName, UiExecute execute) {
            this.USBName = usbPathName;
            this.Context = context;
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService3.this.xPrinterDev = new PosPrinterDev(PosPrinterDev.PortType.USB, context, usbPathName);
                    MyService3.this.mMsg = MyService3.this.xPrinterDev.Open();
                    if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.OpenPortSuccess)) {
                        MyService3.this.isConnected = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void disconnectCurrentPort(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService3.this.mMsg = MyService3.this.xPrinterDev.Close();
                    if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.ClosePortSuccess)) {
                        MyService3.this.isConnected = false;
                        if (MyService3.this.que != null) {
                            MyService3.this.que.clear();
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }

        public void write(final byte[] data, UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    if (data != null) {
                        MyService3.this.mMsg = MyService3.this.xPrinterDev.Write(data);
                        if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                            MyService3.this.isConnected = true;
                            return true;
                        }

                        MyService3.this.isConnected = false;
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
                    if (list == null) {
                        return false;
                    } else {
                        for (int i = 0; i < list.size(); ++i) {
                            MyService3.this.mMsg = MyService3.this.xPrinterDev.Write((byte[]) list.get(i));
                        }

                        if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                            MyService3.this.isConnected = true;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            });
            task.execute(new Void[0]);
        }

        public void writeDataByUSB(UiExecute execute, final ProcessData processData) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    List<byte[]> list = processData.processDataBeforeSend();
                    if (list != null) {
                        try {
                            for (int i = 0; i < list.size(); ++i) {
                                MyService3.this.mMsg = MyService3.this.xPrinterDev.Write((byte[]) list.get(i));
                            }

                            if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.WriteDataSuccess)) {
                                MyService3.this.isConnected = true;
                                return true;
                            }

                            MyService3.this.isConnected = false;
                        } catch (NullPointerException var3) {
                            var3.printStackTrace();
                            MyService3.this.isConnected = false;
                            return false;
                        }
                    }

                    return false;
                }
            });
            task.execute(new Void[0]);
        }

        public void acceptdatafromprinter(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService3.this.que = MyService3.this.getinstaceRoundQueue();
                    byte[] buffer = new byte[4];
                    MyService3.this.que.clear();
                    Log.i("TAG", MyService3.this.xPrinterDev.Read(buffer).GetErrorCode().toString());
                    MyService3.this.que.addLast(buffer);
                    Log.i("TAG", "开始读取" + Arrays.toString((byte[]) MyService3.this.que.getLast()));

                    while (MyService3.this.xPrinterDev.Read(buffer).GetErrorCode().equals(PosPrinterDev.ErrorCode.ReadDataSuccess)) {
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException var3) {
                            var3.printStackTrace();
                            return false;
                        }
                    }

                    MyService3.this.isConnected = false;
                    return false;
                }
            });
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }

        public RoundQueue<byte[]> readBuffer() {
            new RoundQueue(500);
            RoundQueue<byte[]> queue = MyService3.this.que;
            return queue;
        }

        public void clearBuffer() {
            MyService3.this.que.clear();
        }

        public void checkLinkedState(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    if (MyService3.this.isConnected) {
                        MyService3.this.isConnected = MyService3.this.xPrinterDev.GetPortInfo().PortIsOpen();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }

        public void disconnetNetPort(UiExecute execute) {
            PosAsynncTask task = new PosAsynncTask(execute, new BackgroundInit() {
                public boolean doinbackground() {
                    MyService3.this.mMsg = MyService3.this.xPrinterDev.Close();
                    if (MyService3.this.mMsg.GetErrorCode().equals(PosPrinterDev.ErrorCode.ClosePortSuccess)) {
                        MyService3.this.isConnected = false;

                        try {
                            MyService3.MyBinder.this.t.stop();
                            MyService3.MyBinder.this.t.destroy();
                        } catch (Exception var2) {
                            var2.printStackTrace();
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            });
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }
    }
}
