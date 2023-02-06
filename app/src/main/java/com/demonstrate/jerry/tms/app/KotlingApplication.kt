/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.app

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.Typeface
import android.os.IBinder
import android.support.multidex.MultiDex
import android.support.v4.content.ContextCompat
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.Bugly
import es.dmoral.toasty.Toasty
import org.litepal.LitePalApplication
import org.litepal.tablemanager.Connector


/**
 * Created by jerry on 2017/6/6.
 */
class KotlingApplication : LitePalApplication() {


    override fun onCreate() {
        super.onCreate()
        initEverything()
    }

    private fun initEverything() {
        app = this
        Connector.getDatabase()
        ZShrPrefencs.registApp(this)
        Logger.addLogAdapter(AndroidLogAdapter())
//        cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this))
        Bugly.init(applicationContext, "6d8ec702b0", false)
//        Toasty.Configs.getInstance()
//                .setErrorColor(ContextCompat.getColor(this, R.color.tms_hasToTv)) // optional
//                .setInfoColor(ContextCompat.getColor(this, R.color.color_darkBlue)) // optional
//                .setSuccessColor(ContextCompat.getColor(this, R.color.color_orange)) // optional
//                .setWarningColor(ContextCompat.getColor(this, R.color.colorPrimary)) // optional
//                .setTextColor(ContextCompat.getColor(this, R.color.color_white)) // optional
//                .tintIcon(true) // optional (apply textColor also to the icon)
//                .setToastTypeface(Typeface.DEFAULT) // optional
//                .setTextSize(14) // optional
//                .apply(); // required
        initPrintService()
    }

    internal inner class PrinterServiceConnection : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Toasty.error(this@KotlingApplication, getString(R.string.jb_printer_error)).show()
        }
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        // you must install multiDex whatever tinker is installed!
//        MultiDex.install(base);
//
//        // 安装tinker
//        Beta.installTinker();
//    }

    private fun initPrintService() {
        //绑定service 并且实例化GpService 去得到应该有的对象
        var jbConn = PrinterServiceConnection()
        //xy打印机
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }

    companion object {
        var app: Context? = null

    }
}