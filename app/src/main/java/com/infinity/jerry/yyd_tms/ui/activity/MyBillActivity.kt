/*
 * Copyrigh()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.bluetooth.DeviceConnFactoryManager
import com.infinity.jerry.yyd_tms.bluetooth.PrinterCommand
import com.infinity.jerry.yyd_tms.bluetooth.ThreadPool
import com.infinity.jerry.yyd_tms.bluetooth.ZPrintReceiver
import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.data.request_entity.MyBillRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.CancelBillPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCancelBill
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.*
import com.infinity.jerry.yyd_tms.utils.DateUtil
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jerry on 2017/6/22.
 */
class MyBillActivity : BaseActivity(), IViewCancelBill {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val spInit: Spinner by bindView<Spinner>(R.id.spInit)
    val spTermi: Spinner by bindView<Spinner>(R.id.spTermi)
    val edConRRName: EditText by bindView<EditText>(R.id.edConRRName)
    val edConRRPhone: EditText by bindView<EditText>(R.id.edConRRPhone)
    val edConEEName: EditText by bindView<EditText>(R.id.edConEEName)
    val edConEEPhone: EditText by bindView<EditText>(R.id.edConEEPhone)
    val edBillNumber: EditText by bindView<EditText>(R.id.edBillNumber)
    val tvClear: TextView by bindView<TextView>(R.id.tvClear)
    val tvSearch: TextView by bindView<TextView>(R.id.tvSearch)
    val coverFrame: FrameLayout by bindView<FrameLayout>(R.id.coverFrame)
    val condition: LinearLayout by bindView<LinearLayout>(R.id.condition)
    val selfArtiNumber: EditText by bindView<EditText>(R.id.edSelfBillNumber)
    val tvBatchPrint: TextView by bindView<TextView>(R.id.tvBatchPrint)
    val imBlueTooth: ImageView by bindView<ImageView>(R.id.imagBle)
    val tvGoSign: TextView by bindView<TextView>(R.id.tvSign)
    private val timePicker: LinearLayout by bindView<LinearLayout>(R.id.timeChoose)
    private val tvTime: TextView by bindView<TextView>(R.id.tvTime)

    val calendar: Calendar = Calendar.getInstance()


    var isCondition: Boolean = false
    private val animDuration: Long = 300
    var initList: List<PointEntity>? = null

    var termiList: List<BillTerimi>? = null
    var requestEntity: MyBillRequest? = null

    var presenter: CancelBillPresenter? = null

    private val EDIT_BILLING_REQUEST: Int = 24
    var dialog: Dialog? = null
    var adapter: ZCommonAdapter<BillingDrawMain>? = null

    private var threadPool: ThreadPool = ThreadPool.getInstantiation()

    private var mana: DeviceConnFactoryManager? = null

    private var printReciver: ZPrintReceiver? = null

    private var isGoSign = false

    override fun initData() {
        printReciver = ZPrintReceiver(this)
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        requestEntity = MyBillRequest()
        requestEntity!!.pageSize = "10"
        requestEntity!!.startTime = DateUtil.getCurrentDataYMD();
        requestEntity!!.endTime = DateUtil.getCurrentDataYMD();
        tvTime.setText(DateUtil.getCurrentDataYMD() + " 到 " + DateUtil.getCurrentDataYMD())
        getMyBillsData()

        if (AppUserToken.getInstance().result != null) {
            if (AppUserToken.getInstance().result.role_uuid == 1L) {
                GiveMeCommonData.getInstance().giveMeChildsPoints(object : CommonPresenter.OnGetChildPointsListener {
                    override fun getChildPointsSucc(childs: MutableList<PointEntity>?) {
                        this@MyBillActivity.initList = childs
                        initInitSpinner()
                    }

                    override fun getChildPointError() = Toasty.error(this@MyBillActivity, getString(R.string.point_get_error)).show()
                })
            }
            if (AppUserToken.getInstance().result.role_uuid == 2L) {
                spInit.isEnabled = false
            }
        }

        GiveMeCommonData.getInstance().giveMeAllTerminal(object : CommonPresenter.OnGetAllTerminalListener {
            override fun getTerminalSucc(list: MutableList<BillTerimi>?) {
                this@MyBillActivity.termiList = list
                initTermiSpinner()
            }

            override fun getTerminalError() = Toasty.error(this@MyBillActivity, getString(R.string.getTermialError)).show()
        })


        try {
            mana = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0]
        } catch (e: Exception) {
        }
        if (mana != null) {
            if (mana!!.connState) {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@MyBillActivity, R.mipmap.tms_icon_bluetooth_true))
            } else {
                imBlueTooth.setImageDrawable(ContextCompat.getDrawable(this@MyBillActivity, R.mipmap.tms_icon_bluetooth_false))
            }
        } else {
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activiyt_mybill_list
    }

    override fun initPresenter() {
        presenter = CancelBillPresenter.getInstance(this)
    }

    private fun initInitSpinner() {
        val strList = ArrayList<String>()
        strList.add(getString(R.string.all))
        initList!!.forEach {
            strList.add(it.point_name)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strList)
        spInit.adapter = adapter
        spInit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                requestEntity!!.initial_station_id = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    requestEntity!!.initial_station_id = null
                } else {
                    requestEntity!!.initial_station_id = initList!![position - 1].uuid.toString()

                }
            }
        }
    }

    private fun initTermiSpinner() {
        val strList = ArrayList<String>()
        strList.add(getString(R.string.all))
        termiList!!.forEach {
            strList.add(it.point_name)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strList)
        spTermi.adapter = adapter
        spTermi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                requestEntity!!.terminal_station_id = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    requestEntity!!.terminal_station_id = null
                } else {
                    requestEntity!!.terminal_station_id = termiList!![position - 1].uuid.toString()
                }
            }
        }
    }

    override fun initView() {
        tvBatchPrint.setBackgroundColor(ContextCompat.getColor(this, R.color.level1_gray_dark))
        tvBatchPrint.isEnabled = false
        titleBar.setTitle("我的运单")
        titleBar.setTitleMode(ZTitlebar.MODE_TEXT)
        titleBar.setTvPlusText(getString(R.string.condition))

        titleBar.setOnTextModeListener(object : ZTitlebar.OnTextModeListener {
            override fun onClickTextMode() {
                if (!isCondition) {
                    startCondition()
                } else {
                    endCondition()
                }
            }
        })
        coverFrame.setOnClickListener {
            endCondition()
        }
        swipeListView.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                requestEntity!!.page = "1"
                getMyBillsData()
            }
        })
        tvClear.setOnClickListener { clearCondition() }
        tvSearch.setOnClickListener {
            isGoSign = false
            requestEntity!!.consignee = getEDStringOrNull(edConEEName)
            requestEntity!!.consignee_phone = getEDStringOrNull(edConEEPhone)
            requestEntity!!.consigner = getEDStringOrNull(edConRRName)
            requestEntity!!.consigner_phone = getEDStringOrNull(edConRRPhone)
            requestEntity!!.article_number = getEDStringOrNull(edBillNumber)
            requestEntity!!.ownArticleNumber = getEDStringOrNull(selfArtiNumber)
            requestEntity!!.page = "1"
            getMyBillsData()

        }
        tvBatchPrint.setOnClickListener {
            val tempList: ArrayList<BillingDrawMain>? = ArrayList()
            if (adapter != null) {
                val temp = adapter!!.dataList
                if (temp != null) {
                    temp.forEach {
                        if (it.driver_name != null && it.driver_name.equals("fuck_yes")) {
                            tempList!!.add(it)
                        }
                    }
                }
            }
            startPrint(tempList)
        }

        tvGoSign.setOnClickListener {
            isGoSign = true
            requestEntity!!.startTime = null
            requestEntity!!.endTime = null
            requestEntity!!.consignee = getEDStringOrNull(edConEEName)
            requestEntity!!.consignee_phone = getEDStringOrNull(edConEEPhone)
            requestEntity!!.consigner = getEDStringOrNull(edConRRName)
            requestEntity!!.consigner_phone = getEDStringOrNull(edConRRPhone)
            requestEntity!!.article_number = getEDStringOrNull(edBillNumber)
            requestEntity!!.ownArticleNumber = getEDStringOrNull(selfArtiNumber)
            requestEntity!!.page = "1"

            var intent = Intent(this, SignActivityX::class.java)
            val bundle = Bundle()
            bundle.putSerializable("bundleEntity", requestEntity)
            intent.putExtra("bundleExtra", bundle)
            startActivity(intent)
        }

        timePicker.setOnClickListener {
            val dialog = DoubleDatePickerDialog(this, DoubleDatePickerDialog.OnDateSetListener { startDatePicker, startYear, startMonthOfYear, startDayOfMonth, endDatePicker, endYear, endMonthOfYear, endDayOfMonth ->
                Logger.i(startYear.toString() + " " + startMonthOfYear + " " + startDayOfMonth + "    "
                        + endYear + "   " + endMonthOfYear + "   " + endDayOfMonth)

                tvTime.setText(startYear.toString() + "-" + (startMonthOfYear + 1) + "-" + startDayOfMonth + " 到 "
                        + endYear + "-" + (endMonthOfYear + 1) + "-" + endDayOfMonth)

                val month = startMonthOfYear + 1
                var monthStr = ""
                if (month < 10) {
                    monthStr = "0" + month.toString()
                } else {
                    monthStr = month.toString()
                }

                val endMonth = endMonthOfYear + 1
                var endMonthStr = ""
                if (endMonth < 10) {
                    endMonthStr = "0" + endMonth.toString()
                } else {
                    endMonthStr = endMonth.toString()
                }

                var startDay = ""
                if (startDayOfMonth < 10) {
                    startDay = "0" + startDayOfMonth.toString()
                } else {
                    startDay = startDayOfMonth.toString()
                }

                var endDay = ""
                if (endDayOfMonth < 10) {
                    endDay = "0" + endDayOfMonth.toString()
                } else {
                    endDay = endDayOfMonth.toString()
                }

                requestEntity!!.startTime = startYear.toString() + "-" + monthStr + "-" + startDay
                requestEntity!!.endTime = endYear.toString() + "-" + endMonthStr + "-" + endDay

                val start = startYear.toString() + "-" + monthStr + "-" + startDay + " 00:00:00"
                val end = endYear.toString() + "-" + endMonthStr + "-" + endDay + " 00:00:00"
                val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                sss = sf.parse(start).time
                eee = sf.parse(end).time

                Log.e("TAG", (eee - sss).toString())

                if (eee - sss > 259200000) {
                    Toasty.info(this@MyBillActivity, "时间筛选不能超过3天").show()
                }


            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }

    var sss = 0L
    var eee = 0L

    var isRequest: Boolean = false

    private fun getMyBillsData() {
        if (isRequest) {
            return
        }
        if (eee - sss > 259200000) {
            Toasty.info(this@MyBillActivity, "时间筛选不能超过3天").show()
            return
        }
        dialog!!.show()
        isRequest = true
        Log.e("TAG", "123 " + requestEntity!!.toString())
        GiveMeCommonData.getInstance().giveMeMyBills(requestEntity!!.startTime, requestEntity!!.endTime, ZGsonUtils.getInstance().getJsonString(requestEntity),
                Integer.parseInt(requestEntity!!.pageSize), Integer.parseInt(requestEntity!!.page), object : CommonPresenter.OnMyBillListener {
            override fun getMyBillSucc(lists: MyBillListEntity?) {
                swipeListView.swipeLayout!!.isRefreshing = false
                if (condition.isShown) {
                    endCondition()
                }
                if (Integer.parseInt(requestEntity!!.page) > 1) {
                    adapter!!.appendListData(lists!!.pageList)
                    swipeListView.showListView()
                } else {
                    initListView(lists!!.pageList)
                }
                requestEntity!!.page = (Integer.parseInt(requestEntity!!.page) + 1).toString()
                isRequest = false
                dialog!!.dismiss()

            }

            override fun getMyBillError() {
                isRequest = false
                swipeListView.swipeLayout!!.isRefreshing = false
                Toasty.error(this@MyBillActivity, getString(R.string.search_error)).show()
                swipeListView.requestNoData()
                dialog!!.dismiss()
            }
        })
    }

    private fun checkIsBatch() {
        var tempNumber = 0
        if (adapter != null) {
            val tt = adapter!!.dataList
            tt.forEach {
                if (it.driver_name != null && it.driver_name.equals("fuck_yes")) {
                    tempNumber++
                }
            }

        }
        if (tempNumber > 1) {
            tvBatchPrint.isEnabled = true
            tvBatchPrint.setBackgroundColor(ContextCompat.getColor(this, R.color.color_blue))
        } else {
            tvBatchPrint.isEnabled = false
            tvBatchPrint.setBackgroundColor(ContextCompat.getColor(this, R.color.level1_gray_dark))
        }
        tvBatchPrint.text = "选中批量打印($tempNumber)"
    }

    private fun initListView(lists: MutableList<BillingDrawMain>?) {
        adapter = object : ZCommonAdapter<BillingDrawMain>(this, lists!!, R.layout.item_billing) {
            override fun convert(holder: ZViewHolder?, item: BillingDrawMain?, position: Int) {
                val tvLLAll = holder!!.getView<LinearLayout>(R.id.llall)

                if (item!!.driver_name != null && item.driver_name.equals("fuck_yes")) {
                    tvLLAll.setBackgroundColor(ContextCompat.getColor(this@MyBillActivity, R.color.level2_gray_light))
                } else {
                    tvLLAll.setBackgroundColor(ContextCompat.getColor(this@MyBillActivity, R.color.color_white))
                }

                val tvNumber = holder!!.getView<TextView>(R.id.blNumber)
                tvNumber.text = item!!.article_number

                val tvTime = holder!!.getView<TextView>(R.id.blTime)
                tvTime.text = DateUtil.getTimetrampWithStringYYMDHM(item!!.invoicedate)

                val tvInit = holder.getView<TextView>(R.id.blInit)
                tvInit.text = getNullablString(item.startStation)

                val tvState = holder.getView<TextView>(R.id.blState)
                tvState.text = getBillState(item.invoice_status.toInt())

                val tvTermi = holder.getView<TextView>(R.id.blTermi)
                if (item.transit_destination != null) {
                    tvTermi.text = getNullablString(item.endStation) + "(" + item.transit_destination + ")"
                } else {
                    tvTermi.text = getNullablString(item.endStation)
                }
                val tvInitInfo = holder.getView<TextView>(R.id.bl_inInfo)
                tvInitInfo.text = getNullablString(item.consigner) + "  " + getNullablString(item.consigner_phone)

                val tvTerInfo = holder.getView<TextView>(R.id.bl_terInfo)
                tvTerInfo.text = getNullablString(item.consignee) + "  " + getNullablString(item.consignee_phone)

                val tvGood = holder.getView<TextView>(R.id.bl_good)
                tvGood.text = getString(R.string.goods) + "  " + getNullablString(item.goods_name)

                val tvPack = holder.getView<TextView>(R.id.bl_pack)
                tvPack.text = getString(R.string.packages) + "  " + getNullablString(item.packaging)

                val tvPayMethod = holder.getView<AutoScaleTextView>(R.id.blPayMethod)
                tvPayMethod.text = "运费: " + getNullStringForDouble(item.freight) + " [" + getNullablString(item.payment_method) + "]"

                val tvFreight = holder.getView<AutoScaleTextView>(R.id.blFreight)
                tvFreight.text = "件数: " + item.quantity + " [" + getNullablString(item.delivery_method) + "]"

                val tvDeliMethod = holder.getView<TextView>(R.id.blDeliMethod)
                tvDeliMethod.text = "备注: " + getNullablString(item.remarks)

                val tvCollectxx = holder.getView<TextView>(R.id.tvCollectxx)
                tvCollectxx.text = "代收: " + getNullablString(item.collection_fee.toString())

                val tvDetail = holder.getView<TextView>(R.id.bl_detail)

                tvDetail.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable("billDetailEntity", item)
                    startActivityForResult(BillDetailActivity::class.java, bundle, 8888)
                }

                val bt_1 = holder.getView<TextView>(R.id.bt_1)
                val bt_2 = holder.getView<TextView>(R.id.bt_2)
                val bt_3 = holder.getView<TextView>(R.id.bt_3)
                val bt_4 = holder.getView<TextView>(R.id.bt_4)

                if (item.update_time != null) {
                    bt_1.visibility = View.VISIBLE
                    bt_1.text = "修改过"
                    bt_1.setTextColor(ContextCompat.getColor(this@MyBillActivity, R.color.color_orange))
                    bt_1.setOnClickListener {
                        Toasty.info(this@MyBillActivity, item.operation_user_name + "于" + item.update_time + "修改过").show()
                    }
                } else {
                    bt_1.visibility = View.INVISIBLE
                }

                if (AppUserToken.getInstance().result.role_uuid == 1L) {
                    bt_3.text = getString(R.string.cancel_billing)
                    bt_3.visibility = View.VISIBLE
                    bt_3.setOnClickListener {
                        showDeleteBillDialog(item.uuid)
                    }
                    bt_4.text = getString(R.string.update_billing)
                    bt_4.visibility = View.VISIBLE
                    bt_4.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putSerializable("billEntity", item)
                        startActivityForResult(DrawBillActivityX::class.java, bundle, EDIT_BILLING_REQUEST)
                    }
                } else {
                    if (item.invoice_status == 1L) {
                        bt_3.text = getString(R.string.cancel_billing)
                        bt_3.visibility = View.VISIBLE
                        bt_3.setOnClickListener {
                            showDeleteBillDialog(item.uuid)
                        }
                        bt_4.text = getString(R.string.update_billing)
                        bt_4.visibility = View.VISIBLE
                        bt_4.setOnClickListener {
                            val bundle = Bundle()
                            bundle.putSerializable("billEntity", item)
                            startActivityForResult(DrawBillActivityX::class.java, bundle, EDIT_BILLING_REQUEST)
                        }
                    } else {
                        bt_3.visibility = View.INVISIBLE
                        bt_4.visibility = View.INVISIBLE
                    }
                }
            }
        }
        swipeListView.listView!!.adapter = adapter
        swipeListView.showListView()
        swipeListView.listView!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                if (view!!.lastVisiblePosition == view.count - 1) {
                    getMyBillsData()
                }
            }

        })
        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            if (lists[position].driver_name == null || !lists[position].driver_name.equals("fuck_yes")) {
                lists[position].driver_name = "fuck_yes"
            } else {
                lists[position].driver_name = "fuck_no"
            }
            adapter!!.notifyDataSetChanged()
            checkIsBatch()
        }
    }

    private fun showDeleteBillDialog(uuid: Long) {
        val alert = AlertDialog.Builder(this)
                .setMessage("您确定要作废该票据吗？")
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    this@MyBillActivity.dialog?.show()
                    presenter!!.cancelBill(uuid)

                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 8888) {
            requestEntity!!.page = "1"
            getMyBillsData()
        }
    }

    private fun startPrint(entity: ArrayList<BillingDrawMain>?) {
        Log.e("TAG", entity!!.toString())

        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].connState) {
            Toasty.error(this, "请先连接打印机").show()
            return
        }
        threadPool = ThreadPool.getInstantiation()
        threadPool.addTask {
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].currentPrinterCommand === PrinterCommand.ESC) {
                printReciver!!.setBuda(9999)
                printReciver!!.batchPrint(entity)
            } else {
                mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget()
            }
        }
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


    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                CONN_STATE_DISCONN -> if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] != null) {
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].closePort(0)
                }
                PRINTER_COMMAND_ERROR ->
                    Toasty.error(this@MyBillActivity, "错误").show()

                CONN_PRINTER ->
                    Toasty.error(this@MyBillActivity, "未连接").show()

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

    private fun clearCondition() {
        requestEntity = MyBillRequest()
        spInit.setSelection(0)
        spTermi.setSelection(0)
        edConRRName.setText("")
        edConRRPhone.setText("")
        edConEEName.setText("")
        edConEEPhone.setText("")
        edBillNumber.setText("")
    }

    //view's animation
    private fun startCondition() {
        isGoSign = false
        coverFrame.visibility = View.VISIBLE
        condition.visibility = View.VISIBLE
        val anim = AnimationUtils.makeInAnimation(this, false)
        anim.duration = animDuration
        condition.animation = anim
        coverFrame.alpha = 0f
        coverFrame.animate().alpha(0.8f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        coverFrame.isEnabled = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        coverFrame.isEnabled = true
                    }
                })

    }

    private fun endCondition() {
        coverFrame.animate().alpha(0f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        coverFrame.isEnabled = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        coverFrame.isEnabled = true
                        coverFrame.visibility = View.GONE
                    }
                })
        val anim = AnimationUtils.makeOutAnimation(this, true)
        anim.duration = animDuration
        condition.animation = anim
        condition.visibility = View.GONE
    }

    override fun cancelBillSucc() {
        dialog?.dismiss()
        Toasty.success(this, getString(R.string.cancel_billing_succ)).show()
        getMyBillsData()
    }

    override fun cancelBillError() {
        dialog?.dismiss()
        Toasty.error(this, getString(R.string.cancel_bill_error)).show()
    }

}