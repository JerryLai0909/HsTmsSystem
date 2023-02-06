/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewBillEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.TempDaiShouEntity
import com.infinity.jerry.yyd_tms.data.request_entity.MyBillRequest
import com.infinity.jerry.yyd_tms.mvp.presenter.CancelBillPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCancelBill
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.DateUtil
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/22.
 */
class ShouBillActivity : BaseActivity(), IViewCancelBill {

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

    val llRemote: LinearLayout by bindView<LinearLayout>(R.id.llRemote)

    var isCondition: Boolean = false
    private val animDuration: Long = 300
    var initList: List<PointEntity>? = null

    var termiList: List<BillTerimi>? = null
    var requestEntity: MyBillRequest? = null

    var presenter: CancelBillPresenter? = null

    private val EDIT_BILLING_REQUEST: Int = 24
    var dialog: Dialog? = null
    var adapter: ZCommonAdapter<BillingDrawMain>? = null

    private var isGoSign = false

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        requestEntity = MyBillRequest()
        getMyBillsData()

        if (AppUserToken.getInstance().result != null) {
            if (AppUserToken.getInstance().result.role_uuid == 1L) {
                GiveMeCommonData.getInstance().giveMeChildsPoints(object : CommonPresenter.OnGetChildPointsListener {
                    override fun getChildPointsSucc(childs: MutableList<PointEntity>?) {
                        this@ShouBillActivity.initList = childs
                        initInitSpinner()
                    }

                    override fun getChildPointError() = Toasty.error(this@ShouBillActivity, getString(R.string.point_get_error)).show()
                })
            }
            if (AppUserToken.getInstance().result.role_uuid == 2L) {
                spInit.isEnabled = false
            }
        }

        GiveMeCommonData.getInstance().giveMeAllTerminal(object : CommonPresenter.OnGetAllTerminalListener {
            override fun getTerminalSucc(list: MutableList<BillTerimi>?) {
                this@ShouBillActivity.termiList = list
                initTermiSpinner()
            }

            override fun getTerminalError() = Toasty.error(this@ShouBillActivity, getString(R.string.getTermialError)).show()
        })

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
        imBlueTooth.visibility = View.GONE
        tvBatchPrint.text = "确定"
        titleBar.setTitle("运单搜索")
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

            val dataList: ArrayList<NewBillEntity>? = ArrayList()

            tempList!!.forEach {
                val entity = NewBillEntity()
                entity.articleNumber = it.article_number
                entity.id = it.uuid
                entity.consignee = it.consignee
                entity.consigneePhone = it.consignee_phone
                entity.consigner = it.consigner
                entity.consignerPhone = it.consigner_phone
                entity.collectionFee = it.collection_fee

                dataList!!.add(entity)
            }

            val intent = Intent()
            val tempEntity = TempDaiShouEntity()
            tempEntity.dataList = dataList
            val bundle = Bundle()
            bundle.putSerializable("bundleEntity", tempEntity)
            intent.putExtra("bundle", bundle)
            this@ShouBillActivity.setResult(1, intent)
            this@ShouBillActivity.finish()
        }
    }

    var isRequest: Boolean = false

    private fun getMyBillsData() {
        if (isRequest) {
            return
        }
        isRequest = true
        Log.e("TAG", "123 " + ZGsonUtils.getInstance().getJsonString(requestEntity!!.toString()))
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
            }

            override fun getMyBillError() {
                isRequest = false
                swipeListView.swipeLayout!!.isRefreshing = false
                Toasty.error(this@ShouBillActivity, getString(R.string.search_error)).show()
                swipeListView.requestNoData()
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

        tvBatchPrint.text = "确定选中($tempNumber)条运单"
    }

    private fun initListView(lists: MutableList<BillingDrawMain>?) {
        adapter = object : ZCommonAdapter<BillingDrawMain>(this, lists!!, R.layout.item_billing) {
            override fun convert(holder: ZViewHolder?, item: BillingDrawMain?, position: Int) {
                val tvLLAll = holder!!.getView<LinearLayout>(R.id.llall)

                if (item!!.driver_name != null && item.driver_name.equals("fuck_yes")) {
                    tvLLAll.setBackgroundColor(ContextCompat.getColor(this@ShouBillActivity, R.color.level2_gray_light))
                } else {
                    tvLLAll.setBackgroundColor(ContextCompat.getColor(this@ShouBillActivity, R.color.color_white))
                }

                val tvNumber = holder!!.getView<TextView>(R.id.blNumber)
                tvNumber.text = item!!.article_number

                val tvTime = holder!!.getView<TextView>(R.id.blTime)
                tvTime.text = DateUtil.getTimetrampWithStringMDHM(item!!.invoicedate)

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
                tvDetail.visibility = View.GONE

                val llStatus = holder.getView<LinearLayout>(R.id.llStatus)
                llStatus.visibility = View.GONE
                val llDetail = holder.getView<LinearLayout>(R.id.llDetail)
                llDetail.visibility = View.GONE

                val bt_1 = holder.getView<TextView>(R.id.bt_1)
                bt_1.visibility = View.GONE
                val bt_2 = holder.getView<TextView>(R.id.bt_2)
                bt_2.visibility = View.GONE
                val bt_3 = holder.getView<TextView>(R.id.bt_3)
                bt_3.visibility = View.GONE
                val bt_4 = holder.getView<TextView>(R.id.bt_4)
                bt_4.visibility = View.GONE
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
                    this@ShouBillActivity.dialog?.show()
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