/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.*
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.mvp.presenter.BillDrawPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewBillDraw
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import net.posprinter.posprinterface.UiExecute

/**
 * Created by jerry on 2017/6/23.
 */
class BillDetailActivitySign : BaseActivity(), IViewBillDraw {
    override fun drawBillSucc(entity: BillingDrawMain) {

    }

    override fun drawBillError() {
    }

    override fun drawBillBarCodeSucc(barCode: String) {
        if (detialEntity == null) {
            return
        }
        this.detialEntity!!.barCode = barCode
    }

    override fun drawBillBarCodeError(barCode: String) {
    }

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val tvInit: TextView by bindView<TextView>(R.id.tvInit)
    val tvTermi: TextView by bindView<TextView>(R.id.tvTermi)
    val tvDestination: TextView by bindView<TextView>(R.id.tvDestination)
    val tvConsignerInfo: TextView by bindView<TextView>(R.id.consignerInfo)
    val tvConsigneeInfo: TextView by bindView<TextView>(R.id.consigneeInfo)

    val tvFreight: TextView by bindView<AutoScaleTextView>(R.id.tvFreight)
    val tvAdvance: TextView by bindView<AutoScaleTextView>(R.id.tvAdvance)
    val tvCollection: TextView by bindView<AutoScaleTextView>(R.id.tvCollection)
    val tvDeclar: TextView by bindView<AutoScaleTextView>(R.id.tvDeclar)
    val tvValue: TextView by bindView<AutoScaleTextView>(R.id.tvValue)
    val tvDeliverFee: TextView by bindView<AutoScaleTextView>(R.id.tvDeliverFee)
    val tvReceiveFee: TextView by bindView<AutoScaleTextView>(R.id.tvReceiveFee)
    val tvHandlingFee: TextView by bindView<AutoScaleTextView>(R.id.tvHandlingFee)
    val tvLiftForkFee: TextView by bindView<AutoScaleTextView>(R.id.tvLiftForkFee)
    val tvPackinFee: TextView by bindView<TextView>(R.id.tvPackinFee)
    val tvUpstairFee: TextView by bindView<TextView>(R.id.tvUpstairFee)
    val tvReturnFee: TextView by bindView<TextView>(R.id.tvReturnFee)
    val tvUnderChagFee: TextView by bindView<TextView>(R.id.tvUnderChagFee)
    val tvTotalFee: TextView by bindView<TextView>(R.id.tvTotalFee)
    val tvQuantity: TextView by bindView<TextView>(R.id.tvQuantity)

    val tvMethodPay: TextView by bindView<TextView>(R.id.tvMethodPay)
    val tvMethodDeli: TextView by bindView<TextView>(R.id.tvMethodDeli)
    val tvGoodPack: TextView by bindView<TextView>(R.id.tvGoodPack)

    val tvCashPayment: TextView by bindView<TextView>(R.id.tvCashPayment)
    val tvCollePayment: TextView by bindView<TextView>(R.id.tvCollPayment)
    val tvBackPayment: TextView by bindView<TextView>(R.id.tvBackPayment)
    val tvMonthlyPayment: TextView by bindView<TextView>(R.id.tvMonthly)
    val tvDutactPayment: TextView by bindView<TextView>(R.id.tvDutacPayment)

    var detialEntity: BillingDrawMain? = null
    val tvPrint: TextView by bindView<TextView>(R.id.tvPrint)
    val llPrint: LinearLayout by bindView<LinearLayout>(R.id.llPrint)
    val imBlueTooth: ImageView by bindView<ImageView>(R.id.imBlueTooth)

    val tvPrintBiao: TextView by bindView<TextView>(R.id.tvPrintBiao)

    private var printReciver: ZPrintReceiver? = null
    private var factory: ZBtConnFactory? = null
    private var isConnSucc: Boolean = false

    private val REQUEST_BLUETOOTH_PEMISSION = 64
    private val REQUEST_COARSE_LOCATION = 128

    private var threadPool: ThreadPool = ThreadPool.getInstantiation()

    private var mana: DeviceConnFactoryManager? = null

    private val REQUEST_CODE = 0x004

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

    private var presenter: BillDrawPresenter? = null


    override fun initData() {
        detialEntity = intent.extras?.getSerializable("billDetailEntity") as BillingDrawMain?
        if (detialEntity == null) {
            Toasty.info(this, getString(R.string.loading_error)).show()
            return
        }
        presenter = BillDrawPresenter.getInstance(this)
        presenter!!.getBarCode(detialEntity!!.uuid.toString())

        factory = ZBtConnFactory.getInstance()
        factory!!.setOnPrinterStateListener(object : ZBtConnFactory.OnPrinterStateListener {
            override fun stateOn() {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@BillDetailActivitySign, R.mipmap.tms_icon_bluetooth_true))
                isConnSucc = true
            }

            override fun stateOff() {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@BillDetailActivitySign, R.mipmap.tms_icon_bluetooth_false))
                isConnSucc = false
            }
        })
        printReciver = ZPrintReceiver(this)

        try {
            mana = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0]
        } catch (e: Exception) {
        }
        if (mana != null) {
            if (mana!!.connState) {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@BillDetailActivitySign, R.mipmap.tms_icon_bluetooth_true))
            } else {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@BillDetailActivitySign, R.mipmap.tms_icon_bluetooth_false))
            }
        } else {
        }
    }

    override fun onResume() {
        super.onResume()
    }

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
        return R.layout.activity_bill_detail
    }

    override fun initPresenter() {

    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.bill_details))
        tvInit.text = getString(R.string.init_station) + ":   " + getNullablString(detialEntity?.startStation)
        tvTermi.text = getString(R.string.termi_station) + ":   " + getNullablString(detialEntity?.endStation)
        tvDestination.text = getString(R.string.destination) + ":   " + getNullablString(detialEntity?.transit_destination)

        tvConsignerInfo.text = getString(R.string.consigner) + ":  " +
                getNullablString(detialEntity?.consigner) + "   " + getNullablString(detialEntity?.consigner_phone)

        tvConsigneeInfo.text = getString(R.string.consignee) + ":  " +
                getNullablString(detialEntity?.consignee) + "   " + getNullablString(detialEntity?.consignee_phone)

        tvFreight.text = getString(R.string.freight) + " " + getZeroableStringForDouble(detialEntity?.freight)
        tvAdvance.text = getString(R.string.advance) + " " + getZeroableStringForDouble(detialEntity?.advance)
        tvCollection.text = getString(R.string.fee_collection) + " " + getZeroableStringForDouble(detialEntity?.collection_fee)
        tvDeclar.text = "声明价值" + " " + getZeroableStringForDouble(detialEntity?.declared_value)
        tvValue.text = "保价费" + " " + getZeroableStringForDouble(detialEntity?.valuation_fee)
        tvDeliverFee.text = "送货费" + " " + getZeroableStringForDouble(detialEntity?.delivery_fee)
        tvReceiveFee.text = "接货费" + " " + getZeroableStringForDouble(detialEntity?.receiving_fee)
        tvHandlingFee.text = "装卸费" + " " + getZeroableStringForDouble(detialEntity?.handling_fee)
        tvLiftForkFee.text = "叉吊费" + " " + getZeroableStringForDouble(detialEntity?.forklift_fee)
        tvPackinFee.text = "包装费" + " " + getZeroableStringForDouble(detialEntity?.packing_fee)
        tvUpstairFee.text = "上楼费" + " " + getZeroableStringForDouble(detialEntity?.upstair_fee)
        tvReturnFee.text = "回扣" + " " + getZeroableStringForDouble(detialEntity?.return_fee)
        tvUnderChagFee.text = "欠返" + " " + getZeroableStringForDouble(detialEntity?.under_charge_fee)
        tvTotalFee.text = "运费合计" + " " + getZeroableStringForDouble(detialEntity?.total_freight)
        tvQuantity.text = "总 : " + detialEntity?.quantity + " 件"
        tvCashPayment.text = "现付 : " + getZeroableStringForDouble(detialEntity?.cash_payment)
        tvCollePayment.text = "到付 : " + getZeroableStringForDouble(detialEntity?.collect_payment)
        tvBackPayment.text = "回付 : " + getZeroableStringForDouble(detialEntity?.back_payment)
        tvMonthlyPayment.text = "月结 : " + getZeroableStringForDouble(detialEntity?.monthly_payment)
        tvDutactPayment.text = "货款扣 : " + getZeroableStringForDouble(detialEntity?.payment_deduction)
        tvMethodPay.text = "支付方式 : " + getNullablString(detialEntity?.payment_method)
        tvMethodDeli.text = "提货方式 : " + getNullablString(detialEntity?.delivery_method)
        tvGoodPack.text = "货物 : " + getNullablString(detialEntity?.goods_name) + "    包装 : " + getNullablString(detialEntity?.packaging)
        tvPrintBiao.visibility = View.GONE
        tvPrint.setText("打印签收单")
        setListeners()

    }

    private fun startPrint(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].connState) {
            Toasty.error(this, "请先连接打印机").show()
            return
        }
        threadPool = ThreadPool.getInstantiation()
        threadPool.addTask {
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].currentPrinterCommand === PrinterCommand.ESC) {
                printReciver!!.xsetBillPrintEntity(entity, state, number, isSign)
                printReciver!!.setBuda(9999)
                printReciver!!.sendBillRecepit(number)
            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget()
            }
        }
    }

    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                CONN_STATE_DISCONN -> if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] != null) {
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].closePort(0)
                }
                PRINTER_COMMAND_ERROR ->
                    Toasty.error(this@BillDetailActivitySign, "错误").show()

                CONN_PRINTER ->
                    Toasty.error(this@BillDetailActivitySign, "未连接").show()

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


    private fun setListeners() {
        tvPrint.setOnClickListener {
            if (ZShrPrefencs.getInstance().netOn == 0) {
                startPrint(detialEntity!!, ZPrintReceiver.REQUEST_BILL, 1, true)
            } else if (ZShrPrefencs.getInstance().netOn == 1) {
                print2CheckESC(detialEntity!!, ZPrintReceiver.REQUEST_BILL, 1, true)
            }
        }
        tvPrintBiao.setOnClickListener {
            print2CheckPOS(detialEntity!!, ZPrintReceiver.REQUEST_BILL, 1, true)
        }
    }

    private fun print2CheckESC(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        if (MainActivity.binder != null) {
            MainActivity.binder!!.checkLinkedState(object : UiExecute {
                override fun onsucess() {
                    Log.e("TAG", "onSucess")
                    printReciver!!.xsetBillPrintEntity(entity, state, number, isSign)
                    printReciver!!.setBuda(9999)
                    printReciver!!.sendBillRecepit(number)
                }

                override fun onfailed() {
                    print2ConnPiao(entity, state, number, isSign)
                    Log.e("TAG", "onFailed")
                }
            })
        }
    }

    private fun print2CheckPOS(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        if (MainActivity.binderX != null) {
            MainActivity.binderX!!.checkLinkedState(object : UiExecute {
                override fun onsucess() {
                    Log.e("TAG", "onSucessX")
                    printReciver!!.sendPosRecepit(entity)
                }

                override fun onfailed() {
                    Log.e("TAG", "onFailedX")
                    print2ConnBiao(entity, state, number, isSign)
                }
            })
        }
    }

    private fun print2ConnPiao(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        val piaoIp = ZShrPrefencs.getInstance().netESCIp
        MainActivity.binder!!.connectNetPort(piaoIp, 9100, object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "票据 连接成功 准备打印")
                printReciver!!.xsetBillPrintEntity(entity, state, number, isSign)
                printReciver!!.setBuda(9999)
                printReciver!!.sendBillRecepit(number)
            }

            override fun onfailed() {
                Log.e("TAG", "票据 连接失败 ")
            }
        })
    }

    private fun print2ConnBiao(entity: BillingDrawMain, state: Int, number: Int, isSign: Boolean) {
        val biaoIp = ZShrPrefencs.getInstance().netPosIp
        MainActivity.binderX!!.connectNetPort(biaoIp, 9100, object : UiExecute {
            override fun onsucess() {
                Log.e("TAG", "标签 连接成功 准备打印")
                printReciver!!.sendPosRecepit(entity)
            }

            override fun onfailed() {
                Log.e("TAG", "标签 连接失败 ")
            }
        })
    }

    override fun onDestroy() {
//        unregisterReceiver(printReciver)
        super.onDestroy()
    }
}