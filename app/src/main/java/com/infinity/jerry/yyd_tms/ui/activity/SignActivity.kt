/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.data.request_entity.MyBillRequest
import com.infinity.jerry.yyd_tms.data.request_entity.StringUuid
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.mvp.presenter.SignPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewSign
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import com.infinity.jerry.yyd_tms.utils.DateUtil
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/26.
 */
class SignActivity : BaseActivity(), IViewSign {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeListView: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val tvSignEnsure: TextView by bindView<TextView>(R.id.tvEnsureSign)
    var adapter: ZCommonAdapter<BillingDrawMain>? = null
    var dialog: Dialog? = null
    var lists: List<BillingDrawMain>? = null
    var presenter: SignPresenter? = null
    var isAllChoosed: Boolean = false

    val spInit: Spinner by bindView<Spinner>(R.id.spInit)
    val spTermi: Spinner by bindView<Spinner>(R.id.spTermi)
    val edConRRName: EditText by bindView<EditText>(R.id.edConRRName)
    val edConEEName: EditText by bindView<EditText>(R.id.edConEEName)
    val edBillNumber: EditText by bindView<EditText>(R.id.edBillNumber)
    val tvClear: TextView by bindView<TextView>(R.id.tvClear)
    val tvSearch: TextView by bindView<TextView>(R.id.tvSearch)
    val coverFrame: FrameLayout by bindView<FrameLayout>(R.id.coverFrame)
    val condition: LinearLayout by bindView<LinearLayout>(R.id.condition)


    var isCondition: Boolean = false
    private val animDuration: Long = 300
    var initList: List<PointEntity>? = null

    var termiList: List<BillTerimi>? = null
    var requestEntity: MyBillRequest? = null


    val tvMoney: TextView by bindView<TextView>(R.id.tvMoney)
    val tvCount: TextView by bindView<TextView>(R.id.tvCount)

    override fun initData() {
        requestEntity = MyBillRequest()
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        if (AppUserToken.getInstance().result != null) {
            if (AppUserToken.getInstance().result.role_uuid == 1L) {
                GiveMeCommonData.getInstance().giveMeChildsPoints(object : CommonPresenter.OnGetChildPointsListener {
                    override fun getChildPointsSucc(childs: MutableList<PointEntity>?) {
                        this@SignActivity.initList = childs
                        initInitSpinner()
                    }

                    override fun getChildPointError() = Toasty.error(this@SignActivity, getString(R.string.point_get_error)).show()
                })
            }
            if (AppUserToken.getInstance().result.role_uuid == 2L) {
                spInit.isEnabled = false
            }
        }

        GiveMeCommonData.getInstance().giveMeAllTerminal(object : CommonPresenter.OnGetAllTerminalListener {
            override fun getTerminalSucc(list: MutableList<BillTerimi>?) {
                this@SignActivity.termiList = list
                initTermiSpinner()
//                getMyBillsData()
            }

            override fun getTerminalError() = Toasty.error(this@SignActivity, getString(R.string.getTermialError)).show()
        })

    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_signx
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


    override fun initPresenter() {
        presenter = SignPresenter.getInstance(this)
    }

    private fun sendRequest() {
//        val entity = PageCountEntity()
//        entity.invoice_status = "4"
        presenter!!.getSignbills(ZGsonUtils.getInstance().getJsonString(requestEntity))
//        presenter!!.getSignbills(ZGsonUtils.getInstance().getJsonString(PageCountEntity()))
    }

    override fun onResume() {
        swipeListView.swipeLayout!!.isRefreshing = true
        sendRequest()
        super.onResume()
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.good_sign))
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
                sendRequest()
            }
        })
        tvSignEnsure.setOnClickListener { ensureArr() }

        tvClear.setOnClickListener { clearCondition() }
        tvSearch.setOnClickListener {
            requestEntity!!.consignee = getEDStringOrNull(edConEEName)
            requestEntity!!.consigner = getEDStringOrNull(edConRRName)
            requestEntity!!.waybill_number = getEDStringOrNull(edBillNumber)
            requestEntity!!.page = "1"
            sendRequest()
        }

    }

    private fun startCondition() {
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

    private fun clearCondition() {
        requestEntity = MyBillRequest()
        spInit.setSelection(0)
        spTermi.setSelection(0)
        edConRRName.setText("")
        edConEEName.setText("")
        edBillNumber.setText("")
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

    private fun ensureArr() {
        if (lists != null && lists!!.size > 0) {
            var builder = StringBuilder()
            lists!!.forEach {
                if (it.isChoose) {
                    builder.append(it.uuid.toString() + ",")
                }
            }
            if (builder.length > 0) {
                builder.deleteCharAt(builder.length - 1)
                var request = StringUuid()
                request.uuid = builder.toString()
                presenter!!.ensureSignBill(ZGsonUtils.getInstance().getJsonString(request))
                dialog?.show()
            } else {
                Toasty.info(this, getString(R.string.no_bill_for_arrvial)).show()
            }
        } else {
            Toasty.info(this, getString(R.string.no_bill_for_arrvial)).show()
        }
    }

    private fun initListView() {
        adapter = object : ZCommonAdapter<BillingDrawMain>(this, lists, R.layout.item_arraival_billing) {
            override fun convert(holder: ZViewHolder?, item: BillingDrawMain?, position: Int) {
                val imChoose = holder!!.getView<ImageView>(R.id.imChoose)
                if (item!!.isChoose) {
                    imChoose.setImageDrawable(ContextCompat.getDrawable(this@SignActivity, R.mipmap.tms_icon_choose_c_true))
                } else {
                    imChoose.setImageDrawable(ContextCompat.getDrawable(this@SignActivity, R.mipmap.tms_icon_choose_c_false))
                }
                if (item!!.invoice_status == 4L) {
                    imChoose.visibility = View.VISIBLE
                } else if (item.invoice_status == 5L) {
                    imChoose.visibility = View.GONE
                } else {
                    imChoose.visibility = View.GONE
                }

                val tvTime = holder!!.getView<TextView>(R.id.blTime)
                if (item!!.invoice_date == null) {
                    item!!.invoice_date = ""
                }
                tvTime.text = item.article_number + "  " + DateUtil.getTimetrampWithStringMDHM(item!!.invoice_date)

                val tvState = holder.getView<TextView>(R.id.blState)
                tvState.text = getBillState(item.invoice_status.toInt())

                val tvInit = holder.getView<TextView>(R.id.blInit)
                tvInit.text = getNullablString(item.startStation)

                val tvTermi = holder.getView<TextView>(R.id.blTermi)
                tvTermi.text = getNullablString(item.endStation)

                val tvInitInfo = holder.getView<TextView>(R.id.bl_inInfo)
                tvInitInfo.text = getNullablString(item.consigner) + "  " + getNullablString(item.consigner_phone)

                val tvTerInfo = holder.getView<TextView>(R.id.bl_terInfo)
                tvTerInfo.text = getNullablString(item.consignee) + "  " + getNullablString(item.consignee_phone)

                val tvGood = holder.getView<TextView>(R.id.bl_good)
                tvGood.text = getString(R.string.goods) + "  " + getNullablString(item.goods_name)

                val tvPack = holder.getView<TextView>(R.id.bl_pack)
                tvPack.text = getString(R.string.packages) + "  " + getNullablString(item.packaging)

                val tvPayMethod = holder.getView<TextView>(R.id.blPayMethod)
                tvPayMethod.text = getString(R.string.freight) + ": " + getNullStringForDouble(item.freight) + "元 【" + getNullablString(item.payment_method) + "】"

//                val tvDaishou = holder.getView<TextView>(R.id.tvdaishou)
//                tvDaishou.text = getNullStringForDouble(item.collection_fee)

                val tvFreight = holder.getView<TextView>(R.id.blFreight)
                tvFreight.text = getString(R.string.fee_collection) + ": " + getNullStringForDouble(item.collection_fee) + "元"

                val tvDeliMethod = holder.getView<TextView>(R.id.blDeliMethod)
                tvDeliMethod.text = getString(R.string.method_getGoods) + "  " + getNullablString(item.delivery_method)

                val tvDetail = holder.getView<TextView>(R.id.bl_detail)
                tvDetail.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putSerializable("billDetailEntity", item)
                    startActivity(BillDetailActivitySign::class.java, bundle)
                }

            }
        }
        swipeListView.listView!!.adapter = adapter
        swipeListView.showListView()
        swipeListView.listView!!.setOnItemClickListener { parent, view, position, id ->
            if (this@SignActivity.lists!![position].invoice_status == 5L) {
                return@setOnItemClickListener
            } else this@SignActivity.lists!![position].isChoose = !this@SignActivity.lists!![position].isChoose
            adapter!!.notifyDataSetChanged()
            setTvinfo()
        }
    }

    private fun setTvinfo() {
        if (lists != null) {
            var money = 0.0;
            var count = 0;
            lists!!.forEach {
                if (it.isChoose) {
                    count++
                    if (it.payment_method.equals("到付")) {
                        money += it.total_freight
                    }
                    var temp = it.collection_fee
                    if (temp == null) {
                        temp = 0.0
                    }
                    money += temp
                }
            }
            tvMoney.text = "应收款: " + getZeroableStringForDouble(money) + "元"
            tvCount.text = "共" + count + "单"

        }
    }


    override fun onNetWorkError() {
        dialog?.dismiss()
        swipeListView.requestError()
    }

    override fun getSignBillsSucc(lists: MyBillListEntity) {
        swipeListView.swipeLayout!!.isRefreshing = false
        if (condition.isShown) {
            endCondition()
        }
        if (lists.pageList.isEmpty()) {
            swipeListView.requestNoData()
            return
        }

        if (Integer.parseInt(requestEntity!!.page) > 1) {
            adapter!!.appendListData(lists!!.pageList)
            swipeListView.showListView()
        } else {
            this.lists = lists.pageList
            initListView()
        }
        requestEntity!!.page = (Integer.parseInt(requestEntity!!.page) + 1).toString()

        setTvinfo()
        dialog?.dismiss()
    }


    override fun ensureSignBillsSucc() {
        dialog?.dismiss()
        clearCondition()
        sendRequest()
    }

    override fun remoteSignError(type: Int) {
        dialog?.dismiss()
        swipeListView.swipeLayout!!.isRefreshing = false
        when (type) {
            IViewSign.SIGN_BILLS_ENSURE_ERROR -> Toasty.error(this, getString(R.string.arrvial_ensure_error)).show()
            IViewSign.SIGN_BILLS_GET_ERROR -> swipeListView.requestError()
        }
    }


}