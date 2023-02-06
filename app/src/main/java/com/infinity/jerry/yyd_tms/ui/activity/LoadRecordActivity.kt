/*
 * Copyrigh()()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.*
import com.infinity.jerry.yyd_tms.constant.ConstantPool
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.BillListTempShit
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.NewRecordsEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.LoadRecordPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadRecord
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.DateUtil
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import net.posprinter.posprinterface.UiExecute

/**
 * Created by jerry on 2017/6/19.
 */
class LoadRecordActivity : BaseActivity(), IViewLoadRecord {

    override fun jiuCuoSucc() {
    }

    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)

    private var printReciver: ZPrintReceiver? = null
    private var printFilter: IntentFilter? = null
    private var connReceiver: ZBtConnReceiver? = null
    private var connFilter: IntentFilter? = null
    private var factory: ZBtConnFactory? = null


    private var isConnSucc: Boolean = false

    var presenter: LoadRecordPresenter? = null
    var dialog: Dialog? = null
    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        printReciver = ZPrintReceiver(this)
        try {
            mana = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0]
        } catch (e: Exception) {
        }
        if (mana != null) {
            if (mana!!.connState) {
                titleBar.imPlus?.setImageDrawable(ContextCompat.getDrawable(this@LoadRecordActivity, R.mipmap.tms_icon_bluetooth_true))
            } else {
                titleBar.imPlus?.setImageDrawable(ContextCompat.getDrawable(this@LoadRecordActivity, R.mipmap.tms_icon_bluetooth_false))
            }
        } else {
        }
    }

    private val REQUEST_BLUETOOTH_PEMISSION = 64
    private val REQUEST_COARSE_LOCATION = 128

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), REQUEST_BLUETOOTH_PEMISSION)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_COARSE_LOCATION)
        }
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_load_record
    }

    override fun initPresenter() {
        presenter = LoadRecordPresenter.getInstance(this)
    }

    override fun onResume() {
        if (ZShrPrefencs.getInstance().blueToothDeviceName.equals(ConstantPool.BT_XY_NAME)) {
            if (AppUserToken.getInstance().isXy) {
                titleBar.imPlus!!.setImageDrawable(ContextCompat.getDrawable(this@LoadRecordActivity, R.mipmap.tms_icon_bluetooth_true))
            } else {
                titleBar.imPlus!!.setImageDrawable(ContextCompat.getDrawable(this@LoadRecordActivity, R.mipmap.tms_icon_bluetooth_false))
            }
        }
        sendRequest()
        super.onResume()
    }

    override fun initView() {
        titleBar.setTitle("装车历史")
        titleBar.setTitleMode(ZTitlebar.MODE_IMAGE)
        titleBar.imPlus!!.setImageDrawable(ContextCompat.getDrawable(this@LoadRecordActivity, R.mipmap.tms_icon_bluetooth_true))
        titleBar.setOnImageModeListener(object : ZTitlebar.OnImageModeListener {
            override fun onClickImageModel() {
                if (!isConnSucc) factory!!.connToPrinter(this@LoadRecordActivity)
            }

        })
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
    }

    private fun sendRequest() {
        presenter!!.getNewRecordLists()
        dialog!!.show()
    }

    private fun initListView(list: List<NewRecordsEntity>) {
        swipeListView.listView!!.adapter = object : ZCommonAdapter<NewRecordsEntity>(this, list, R.layout.item_load_record) {
            override fun convert(holder: ZViewHolder?, item: NewRecordsEntity?, position: Int) {
                val tvTime = holder!!.getView<TextView>(R.id.tvTime)
                tvTime.text = item!!.date
                val listView = holder.getView<ListView>(R.id.listView)
                listView.adapter = object : ZCommonAdapter<NewRecordsEntity.ContentBean>(this@LoadRecordActivity, item.content, R.layout.item_load_second) {
                    override fun convert(holder: ZViewHolder?, item: NewRecordsEntity.ContentBean?, position: Int) {
                        val tvCarNum = holder!!.getView<TextView>(R.id.tvCarNum)
                        tvCarNum.text = item!!.plateNumber
                        val tvBatch = holder.getView<TextView>(R.id.tvBatchNum)
                        tvBatch.text = "批次号: " + item.batchNumber
                        val tvRecInfo = holder.getView<TextView>(R.id.tvRecInfo)
                        tvRecInfo.text = item.driverName
                        val tvTime = holder.getView<TextView>(R.id.tvTime)
                        tvTime.text = "时间: " + DateUtil.getdateWithStringHS(item.loadingTime)

                        val tvPiao = holder.getView<TextView>(R.id.tvPiao)
                        tvPiao.text = item.count.toString()
                        val tvJian = holder.getView<TextView>(R.id.tvJian)
                        tvJian.text = item.quantity.toString()
                        val tvBuZhuang = holder.getView<TextView>(R.id.tvBuZhuang)
                        tvBuZhuang.setOnClickListener {
                            val intent = Intent()
                            intent.putExtra("batchNumber", item.id.toString())
                            this@LoadRecordActivity.setResult(android.app.Activity.RESULT_OK, intent)
                            this@LoadRecordActivity.finish()
                        }
                        val tvJiuCuo = holder.getView<TextView>(R.id.tvJiuCuo)
                        tvJiuCuo.setOnClickListener {
                            val intent = Intent(this@LoadRecordActivity, LoadJiuCuoActivity::class.java)
                            intent.putExtra("batchNumber", item.id)
                            startActivity(intent)
                        }
                        val tvPrint = holder.getView<TextView>(R.id.tvPrint)
                        tvPrint.setOnClickListener {
                            dialog!!.show()
                            presenter!!.getLoadByBatchIdX(item.id)
                            plate_number = item.plateNumber
                        }

                    }

                }

            }
            //tvPrint.setOnClickListener { showPrintDialog(item) }
        }
        swipeListView.showListView()
    }

    /**
     * 连接状态断开
     */
    private val CONN_STATE_DISCONN = 0x007
    /**
     * 使用打印机指令错误
     */
    private val PRINTER_COMMAND_ERROR = 0x008

    val MESSAGE_UPDATE_PARAMETER = 0x009

    val CONN_MOST_DEVICES = 0x11
    private val CONN_PRINTER = 0x12

    private var mana: DeviceConnFactoryManager? = null

    private var threadPool: ThreadPool = ThreadPool.getInstantiation()

    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                CONN_STATE_DISCONN -> if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] != null) {
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].closePort(0)
                }
                PRINTER_COMMAND_ERROR ->
                    Toasty.error(this@LoadRecordActivity, "错误").show()

                CONN_PRINTER ->
                    Toasty.error(this@LoadRecordActivity, "未连接").show()

                MESSAGE_UPDATE_PARAMETER -> {
                    val strIp = msg.getData().getString("Ip")
                    val strPort = msg.getData().getString("Port")
                    //初始化端口信息
                    DeviceConnFactoryManager.Build()
                            //设置端口连接方式
                            .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.WIFI)
                            //设置端口IP地址
                            .setIp(strIp)
                            //设置端口ID（主要用于连接多设备）
                            .setId(0)
                            //设置连接的热点端口号
                            .setPort(Integer.parseInt(strPort))
                            .build()
                    threadPool = ThreadPool.getInstantiation()
                    threadPool.addTask { DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].openPort() }
                }
                else -> {
                }
            }
        }
    }
    var plate_number: String = ""

    private fun showPrintDialog(entity: List<BillingDrawMain>) {
        entity.forEach {
            it.plate_number = plate_number
        }
        var alert = AlertDialog.Builder(this)
                .setMessage("您确定要打印" + plate_number + "的装车清单吗？")
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    if (ZShrPrefencs.getInstance().netOn == 0) {
                        printLoadCar(entity)
                    } else {
                        if (MainActivity.binder != null) {
                            MainActivity.binder!!.checkLinkedState(object : UiExecute {
                                override fun onsucess() {
                                    Log.e("TAG", "onSucess")
                                    printReciver!!.setLoadPrintEntity(entity, ZPrintReceiver.REQUEST_LOAD)
                                    printReciver!!.sendLoadRecepitX()
                                }

                                override fun onfailed() {
                                    val piaoIp = ZShrPrefencs.getInstance().netESCIp
                                    MainActivity.binder!!.connectNetPort(piaoIp, 9100, object : UiExecute {
                                        override fun onsucess() {
                                            Log.e("TAG", "票据 连接成功 准备打印")
                                            printReciver!!.setLoadPrintEntity(entity, ZPrintReceiver.REQUEST_LOAD)
                                            printReciver!!.sendLoadRecepitX()
                                        }

                                        override fun onfailed() {
                                            Log.e("TAG", "票据 连接失败 ")
                                            Toasty.error(this@LoadRecordActivity, "打印失败，请前往个人中心设置")
                                        }
                                    })
                                }
                            })
                        }
                    }
                    plate_number = ""
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener
                { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    override fun getNewloadByBatchIdSucc(entities: List<BillingDrawMain>) {
        dialog!!.dismiss()
        showPrintDialog(entities)

    }

    override fun onDestroy() {
//        unregisterReceiver(printReciver)
//        unregisterReceiver(connReceiver)
        super.onDestroy()
    }

    private fun printLoadCar(entity: List<BillingDrawMain>) {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].connState) {
            Toasty.error(this, "请先连接打印机").show()
            return
        }
        threadPool = ThreadPool.getInstantiation()
        threadPool.addTask {
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].currentPrinterCommand === PrinterCommand.ESC) {
                printReciver!!.setLoadPrintEntity(entity, ZPrintReceiver.REQUEST_LOAD)
                printReciver!!.sendLoadRecepitX()
            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget()
            }
        }
    }

    override fun loadrecordsuxcc(entities: List<BillListTempShit>) {

    }


    override fun getNewRecordSucc(lists: List<NewRecordsEntity>) {
        dialog!!.dismiss()
        if (lists.isEmpty()) {
            Toasty.info(this, getString(R.string.no_data)).show()
            return
        }
        initListView(lists)
    }

    override fun getRecordListSucc(lists: List<BillingDrawMain>) {
        dialog!!.dismiss()
    }

    override fun getrecordListError() {
        dialog!!.dismiss()

    }

    override fun onNetWorkError() {
        dialog!!.dismiss()
    }

}