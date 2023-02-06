/*
 * Copyright (c) 201()7. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.AppUserToken
import com.infinity.jerry.yyd_tms.data.FirstInfo
import com.infinity.jerry.yyd_tms.data.database.BillingDrawDataBase
import com.infinity.jerry.yyd_tms.mvp.model.ClientInfo
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import es.dmoral.toasty.Toasty
import kotterknife.bindView
import org.litepal.crud.DataSupport

/**
 * Created by jerry on 2017/6/27.
 */
class MineAiHintActivity : BaseActivity() {

    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val edSearch: EditText by bindView<EditText>(R.id.edSearch)
    val listView: ListView by bindView<ListView>(R.id.listView)
    val tvSearch: TextView by bindView<TextView>(R.id.tvSearch)
    val tvUpdate: TextView by bindView<TextView>(R.id.tvUpdateInfo)
    val tvFirstInfo: TextView by bindView<TextView>(R.id.tvFirstInfo)

    var adapter: ZCommonAdapter<BillingDrawDataBase>? = null

    var dialog: Dialog? = null

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, "正在加载中", false)
    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_ai_hint
    }

    override fun initPresenter() {
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.ai_hint))
        tvSearch.setOnClickListener { ensureSearch() }
        tvUpdate.setOnClickListener {
            updateInfo()
        }
        tvFirstInfo.setOnLongClickListener {
            firstInfo()
            return@setOnLongClickListener true
        }
        tvFirstInfo.setOnClickListener {
            Toasty.success(this@MineAiHintActivity, "本按钮需长按").show()
        }
    }

    private fun firstInfo() {
        dialog!!.show()

        CommonPresenter.getInstance().getFirstClientInfo(AppUserToken.getInstance().result.company_uuid.toInt(), object : CommonPresenter.FirstClientListener {
            override fun getFirstSucc(info: FirstInfo?) {
                val data: ArrayList<BillingDrawDataBase> = ArrayList()

                info!!.content.forEach {
                    val temp = BillingDrawDataBase()
                    temp!!.sendName = it.content.consigner
                    temp!!.sendPhone = it.content.consigner_phone
                    temp!!.reciveName = it.content.consignee
                    temp!!.recivePhone = it.content.consignee_phone
                    data.add(temp)
                }
                DataSupport.deleteAll(BillingDrawDataBase::class.java)
                DataSupport.saveAll(data)
                Toasty.success(this@MineAiHintActivity, "数据同步成功").show()
                dialog!!.dismiss()
            }

            override fun getFirstError() {
                Toasty.error(this@MineAiHintActivity, "数据同步失败，请重试").show()
                dialog!!.dismiss()
            }
        })
    }

    private fun updateInfo() {
        dialog!!.show()
        CommonPresenter.getInstance().getAllClientInfo(AppUserToken.getInstance().result.company_uuid.toInt(), object : CommonPresenter.ClientInfoListener {
            override fun getClientSucc(clientInfo: ClientInfo?) {
                dialog!!.dismiss()
                DataSupport.deleteAll(BillingDrawDataBase::class.java)
                val data: ArrayList<BillingDrawDataBase> = ArrayList()

                clientInfo!!.consignee.forEach {
                    val temp = BillingDrawDataBase()
                    temp!!.reciveName = it.consignee
                    temp!!.recivePhone = it.consignee_phone
                    data!!.add(temp)
                }

                clientInfo!!.consigner.forEach {
                    val temp = BillingDrawDataBase()
                    temp!!.sendName = it.consigner
                    temp!!.sendPhone = it.consigner_phone
                    data!!.add(temp)
                }
                DataSupport.saveAll(data)
                Toasty.success(this@MineAiHintActivity, "数据同步成功").show()

            }

            override fun getCLientError() {
                Toasty.error(this@MineAiHintActivity, "数据同步失败，请重试").show()
                dialog!!.dismiss()
            }

        })

    }

    private fun ensureSearch() {
        var string = getEDString(edSearch)
        var lists = DataSupport.where("sendName LIKE ? OR reciveName LIKE ?", "%" + string + "%", "%" + string + "%").find(BillingDrawDataBase::class.java)
        if (lists != null && lists.size > 0) {
            initListView(lists)
        } else {
            Toasty.info(this, getString(R.string.no_answer_for)).show()
        }
    }

    private fun initListView(lists: MutableList<BillingDrawDataBase>) {
        adapter = object : ZCommonAdapter<BillingDrawDataBase>(this, lists, R.layout.item_ai_hint) {
            override fun convert(holder: ZViewHolder?, item: BillingDrawDataBase?, position: Int) {
                val sendName = item!!.sendName
                val sendPhone = item.sendPhone
                val reciveName = item.reciveName
                val recivePhone = item.recivePhone
                val tvSendName = holder!!.getView<TextView>(R.id.sendName)
                val tvSendPhone = holder.getView<TextView>(R.id.sendPhone)
                val sendState = holder.getView<TextView>(R.id.sendState)
                if (sendName == null || sendName.isEmpty()) {
                    tvSendName.text = reciveName
                    tvSendPhone.text = recivePhone
                    sendState.text = "收货人"
                } else {
                    tvSendName.text = sendName
                    tvSendPhone.text = sendPhone
                    sendState.text = "发货人"
                }
            }
        }
        listView.adapter = adapter
        listView.setOnItemLongClickListener { parent, view, position, id ->
            showDeleteDialog(lists, position)
            return@setOnItemLongClickListener false
        }
    }

    private fun showDeleteDialog(lists: MutableList<BillingDrawDataBase>, position: Int) {
        var alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.ai_delete_hint))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    DataSupport.delete(BillingDrawDataBase::class.java, lists[position].id.toLong());
                    lists!!.removeAt(position)
                    adapter!!.notifyDataSetChanged()
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show();
    }
}