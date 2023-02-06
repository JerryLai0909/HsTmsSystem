/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.IBinder
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.util.Log
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.app.AppManager
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.IBtConnBack
import com.infinity.jerry.yyd_tms.bluetooth.ZBtConnFactory
import com.infinity.jerry.yyd_tms.bluetooth.ZBtConnReceiver
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.ui.fragment.FinanceFragment
import com.infinity.jerry.yyd_tms.ui.fragment.HomeFragment
import com.infinity.jerry.yyd_tms.ui.fragment.MineFragment
import com.infinity.jerry.yyd_tms.utils.MyService3
import com.infinity.jerry.yyd_tms.utils.MyService4
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import es.dmoral.toasty.Toasty
import net.posprinter.posprinterface.IMyBinder
import net.posprinter.posprinterface.UiExecute

class MainActivity : BaseActivity() {

    private var fragmentManager: FragmentManager? = null
    private var homeFragment: HomeFragment? = null
    private var financeFragment: FinanceFragment? = null
    private var mineFragment: MineFragment? = null
    private var factory: ZBtConnFactory? = null
    private var connFilter: IntentFilter? = null

    private val REQUEST_BLUETOOTH_PEMISSION = 64
    private val REQUEST_COARSE_LOCATION = 128
    private var connReceiver: ZBtConnReceiver? = null

    var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("TAG", "票据打印机service 绑定成功")
            binder = iBinder as IMyBinder
            if (ZShrPrefencs.getInstance().netOn == 1) {
                if (ZShrPrefencs.getInstance().netESCIp != "") {
//                    connectPiao()
                }
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
        }
    }
    var connX: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("TAG", "标签打印机service 绑定成功")
            binderX = iBinder as IMyBinder
            if (ZShrPrefencs.getInstance().netOn == 1) {
                if (ZShrPrefencs.getInstance().netPosIp != "") {
//                    connectBiao()
                }
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
        }
    }

    companion object {
        var binder: IMyBinder? = null
        var binderX: IMyBinder? = null
    }

    override fun initData() {
        fragmentManager = supportFragmentManager
        homeFragment = HomeFragment()
        factory = ZBtConnFactory.getInstance()

        val intent = Intent(this, MyService3::class.java)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)

        val intent2 = Intent(this, MyService4::class.java)
        bindService(intent2, connX, Context.BIND_AUTO_CREATE)

        connReceiver = ZBtConnReceiver(object : IBtConnBack {
            override fun btConnSucc() {

            }

            override fun btConnError() {
            }
        })
        connFilter = IntentFilter()
        connFilter!!.addAction(BluetoothDevice.ACTION_FOUND)
        connFilter!!.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        connFilter!!.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        connFilter!!.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        connFilter!!.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        connFilter!!.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
        connFilter!!.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        connFilter!!.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(connReceiver, connFilter)
        factory!!.connToPrinter(this)
        checkPermission()
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_main
    }

    override fun initPresenter() {

    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), REQUEST_BLUETOOTH_PEMISSION)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_COARSE_LOCATION)
        }
    }

    override fun initView() {
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        addFirstFragment()

        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
        val colors = intArrayOf(resources.getColor(R.color.color_darkGray), getResources().getColor(R.color.color_orange))
        val csl = ColorStateList(states, colors)
        navigation!!.itemTextColor = csl
        navigation!!.itemIconTintList = csl
    }

    private fun addFirstFragment() {
        val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
        transaction.add(R.id.content, homeFragment)
        transaction.show(homeFragment)
        transaction.commit()
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
        if (isAddThisFragment) {
            transaction.add(R.id.content, fragment)
            isAddThisFragment = false
        }
        hideAllFragment()
        transaction.show(fragment)
        transaction.commit()
    }

    private var flag: Boolean = false
    override fun onStop() {
        if (factory != null) {
            factory!!.stopSearchBt()
        }
        if (!flag) {
            unregisterReceiver(connReceiver)
            flag = true
        }
        super.onStop()
    }

    private fun hideAllFragment() {
        val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
        if (homeFragment != null) {
            transaction.hide(homeFragment)
        }
        if (financeFragment != null) {
            transaction.hide(financeFragment)
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment)
        }
        transaction.commit()
    }

    var fragmentState: Int = 1;
    var isAddThisFragment: Boolean = false

    val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (fragmentState != 1) {
                    if (homeFragment == null) {
                        homeFragment = HomeFragment()
                        isAddThisFragment = true
                    } else {
                        isAddThisFragment = false
                    }
                    changeFragment(homeFragment!!)
                    fragmentState = 1
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.navigation_dashboard -> {
                if (AppUserToken.getInstance().result.role_uuid == 2L) {
                    Toasty.info(this@MainActivity, getString(R.string.not_boss)).show()
                    return@OnNavigationItemSelectedListener true
                } else {
                    if (fragmentState != 2) {
                        if (financeFragment == null) {
                            financeFragment = FinanceFragment()
                            isAddThisFragment = true
                        } else {
                            isAddThisFragment = false
                        }
                        changeFragment(financeFragment!!)
                        fragmentState = 2
                        return@OnNavigationItemSelectedListener true
                    }
                }
            }
            R.id.navigation_notifications -> {
                if (fragmentState != 3) {
                    if (mineFragment == null) {
                        mineFragment = MineFragment()
                        isAddThisFragment = true
                    } else {
                        isAddThisFragment = false
                    }
                    changeFragment(mineFragment!!)
                    fragmentState = 3
                    return@OnNavigationItemSelectedListener true
                }
            }
        }
        false
    }

    override fun onDestroy() {
        unbindService(conn)
        unbindService(connX)
        super.onDestroy()
    }

    private fun connectPiao() {
        if (binder == null) {
            return
        }
        var ipAddress = ZShrPrefencs.getInstance().netESCIp
        MainActivity.binder!!.connectNetPort(ipAddress, 9100, object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "票据打印机连接成功")

            }

            override fun onfailed() {
                Log.e("TAG", "票据打印机连接失败")
            }
        })
    }

    private fun connectBiao() {
        if (binder == null) {
            return
        }
        var ipAddress = ZShrPrefencs.getInstance().netPosIp
        MainActivity.binderX!!.connectNetPort(ipAddress, 9100, object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "标签打印机连接成功")
            }

            override fun onfailed() {
                Toasty.success(this@MainActivity, "连接失败X").show()
                Log.e("TAG", "标签打印机连接失败")
            }
        })

    }

    private fun disConnectPiao() {
        binder!!.disconnectCurrentPort(object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "断开成功")
            }

            override fun onfailed() {
                Log.e("TAG", "断开失败")
            }
        })
    }


    private var backTime: Long = 0

    override fun onBackPressed() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - backTime > 2000) {//如果两次按键时间间隔大于800毫秒，则不退出
            backTime = secondTime//更新firstTime
            Toasty.info(this, "再按一次退出程序").show()
        } else {
            ZBtConnFactory.getInstance().resetBt()
            AppManager.getAppManager().AppExit(this, true)
        }
    }
}
