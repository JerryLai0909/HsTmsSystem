package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Dialog
import android.view.View
import android.widget.*
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivityWithOutOrientation
import com.infinity.jerry.yyd_tms.data.report.ReportEntity
import com.infinity.jerry.yyd_tms.data.report.ReportPointEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.ReportPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewReport
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog
import com.infinity.jerry.yyd_tms.ui.customview.RepeatableSpinner
import kotterknife.bindView
import java.util.*

/**
 * Created by jerry on 2019/5/28.
 * You are good enough to do everthing
 */
class ReportPointDetailsActivity : BaseActivityWithOutOrientation(), IViewReport {
    override fun getPointListSucc(entity: ReportPointEntity) {
        dealWithData(entity)
        dialog!!.dismiss()
    }

    override fun getPointDetailsSucc(entity: ReportPointEntity) {

    }

    private fun dealWithData(entity: ReportPointEntity) {
        val rankInfo = entity.rankInfo
        val total = entity.totalInfo
        val heji = ReportPointEntity.RankInfoBean()
        heji.billCount = total.billCountTotal
        heji.collection = total.collectionTotal
        heji.netFreight = total.netFreightTotal
        heji.quantity = total.quantityTotal
        heji.totalFreight = total.freightTotal
        heji.pointName = "xxx"
        rankInfo.add(heji)
        if (rankInfo != null && rankInfo.size != 1) {
            initListViews(rankInfo)
        }
    }


    val imBack: ImageView by bindView<ImageView>(R.id.imBack)
    val tvCondition: TextView by bindView<TextView>(R.id.tvTime)
    val spinner: RepeatableSpinner by bindView<RepeatableSpinner>(R.id.spinner)
    val spType: RepeatableSpinner by bindView<RepeatableSpinner>(R.id.typeSp)
    val imRefresh: ImageView by bindView<ImageView>(R.id.imRefresh)
    val leftListView: ListView by bindView<ListView>(R.id.left_listView)
    val rightListView: ListView by bindView<ListView>(R.id.rightListView)
    val tvTitle: TextView by bindView<TextView>(R.id.tvTitle)
    var dialog: Dialog? = null
    var presenter: ReportPresenter? = null
    val mainll: LinearLayout by bindView<LinearLayout>(R.id.main_ll)
    val ca = Calendar.getInstance()
    val mYear = ca.get(Calendar.YEAR)
    val mMonth = ca.get(Calendar.MONTH) + 1
    val mDay = ca.get(Calendar.DAY_OF_MONTH)
    var year: String = ""
    var month: String = ""
    var day: String = ""
    var lastMonth: String = ""
    var timeType = "TODAY"
    var pointId: String? = null
    var typeDian = "SEND"
    var pointName = ""
    var tempxx = ""
    val typexx: TextView by bindView<TextView>(R.id.typexx)

    override fun initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false)
        year = mYear.toString()
        if (mMonth < 10) {
            month = "0" + mMonth.toString()
            lastMonth = "0" + (mMonth - 1).toString()
        } else {
            month = mMonth.toString()
            lastMonth = (mMonth - 1).toString()
        }
        if (mDay < 10) {
            day = "0" + mDay.toString()
        } else {
            day = mDay.toString()
        }


    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_report_point
    }

    override fun initPresenter() {
        presenter = ReportPresenter.getInstance(this)
        pointId = intent.getStringExtra("pointIdx")
        typeDian = intent.getStringExtra("typeDian")
        tempxx = typeDian
        timeType = intent.getStringExtra("timeType")
        pointName = intent.getStringExtra("pointNamex")
        sendRequest()
    }

    override fun initView() {
        imRefresh.visibility = View.GONE
        if (tempxx.equals("SEND")) {
            tvTitle.text = pointName + "-收货网点收货排行"
        } else {
            tvTitle.text = pointName + "-在各个收货网点的收货排行"
        }
        imBack.setOnClickListener { this.finish() }
        tvCondition.text = "关闭"
        tvCondition.setOnClickListener {
            this.finish()

        }
        setListViewOnTouchAndScrollListener(leftListView, rightListView)
        spinner.visibility = View.GONE
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                when (position) {
//                    0 -> {
//                        tvCondition.setText("今天")
//                        timeType = "TODAY"
//                        sendRequest()
//                    }
//                    1 -> {
//                        tvCondition.setText("当月")
//                        timeType = "THISWEEK"
//                        sendRequest()
//                    }
//                    2 -> {
//                        tvCondition.setText("上月")
//                        timeType = "THISMONTH"
//                        sendRequest()
//                    }
//                }
//            }
//        }
//        if (timeType.equals("TODAY")) {
//            spinner.setSelection(0)
//        } else if (timeType.equals("THISWEEK")) {
//            spinner.setSelection(1)
//        } else if (timeType.equals("THISMONTH")) {
//            spinner.setSelection(2)
//        }

        spType.visibility = View.GONE
//        spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                when (position) {
//                    0 -> {
//                        typexx.setText("网点")
//                        typeDian = "SEND"
//                        sendRequest()
//                    }
//                    1 -> {
//                        typexx.setText("到站")
//                        typeDian = "RECEIVE"
//                        sendRequest()
//                    }
//                }
//            }
//        }
//        if (typeDian.equals("SEND")) {
//            spType.setSelection(0)
//        } else if (typeDian.equals("RECEIVE")) {
//            spType.setSelection(1)
//        }
    }


    private fun sendRequest() {
        dialog!!.show()
        presenter!!.getPointDetails(pointId!!, timeType, typeDian)
    }


    private fun initListViews(rankInfo: List<ReportPointEntity.RankInfoBean>) {

        leftListView.adapter = object : ZCommonAdapter<ReportPointEntity.RankInfoBean>(this, rankInfo, R.layout.item_report_left) {
            override fun convert(holder: ZViewHolder?, item: ReportPointEntity.RankInfoBean?, position: Int) {
                val tvText = holder!!.getView<AutoScaleTextView>(R.id.tvText)
                if (item!!.pointName.equals("xxx")) {
                    tvText.text = "合计"
                } else {
                    tvText.text = (position + 1)!!.toString()
                }
            }
        }
        rightListView.adapter = object : ZCommonAdapter<ReportPointEntity.RankInfoBean>(this, rankInfo, R.layout.item_report_list_point) {
            override fun convert(holder: ZViewHolder?, item: ReportPointEntity.RankInfoBean?, position: Int) {
                val wangdian = holder!!.getView<TextView>(R.id.wangdian)
                val jianshu = holder!!.getView<TextView>(R.id.jianshu)
                jianshu.text = item!!.quantity.toString()
                val piaoshu = holder!!.getView<TextView>(R.id.piaoshu)
                piaoshu.text = item.billCount.toString()
                val zongyunfei = holder!!.getView<TextView>(R.id.zongyunfei)
                zongyunfei.text = item.totalFreight.toString()
                val jingyunfei = holder!!.getView<TextView>(R.id.jingyunfei)
                jingyunfei.text = item.netFreight.toString()
                val daishou = holder!!.getView<TextView>(R.id.daishou)
                daishou.text = item.collection.toString()
                val mingxi = holder.getView<TextView>(R.id.mingxi)
                mingxi.text = "明细"
                mingxi.visibility = View.GONE
                if (item.pointName.equals("xxx")) {
//                    mingxi.visibility = View.GONE
                    wangdian.text = ""
                } else {
//                    mingxi.visibility = View.VISIBLE
                    wangdian.text = item!!.pointName
                }
            }
        }

    }

    override fun getBillsSucc(lists: MutableList<ReportEntity>) {

    }

    override fun getBillsError() {
        dialog!!.dismiss()
    }

    override fun onNetWorkError() {
        dialog!!.dismiss()
    }

    fun setListViewOnTouchAndScrollListener(listView1: ListView, listView2: ListView) {
        //设置listview2列表的scroll监听，用于滑动过程中左右不同步时校正
        listView2.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                //如果停止滑动
                if (scrollState == 0 || scrollState == 1) {
                    //获得第一个子view
                    val subView = view.getChildAt(0)

                    if (subView != null) {
                        val top = subView.top
                        val top1 = listView1.getChildAt(0).top
                        val position = view.firstVisiblePosition

                        //如果两个首个显示的子view高度不等
                        if (top != top1) {
                            listView1.setSelectionFromTop(position, top)
                            listView2.setSelectionFromTop(position, top)

                        }
                    }
                }
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                val subView = view.getChildAt(0)
                if (subView != null) {
                    val top = subView.top
                    //      //如果两个首个显示的子view高度不等
                    //                    int top1 = listView1.getChildAt(0).getTop();
                    //                    if (!(top1 - 7 < top && top < top1 + 7)) {
                    listView1.setSelectionFromTop(firstVisibleItem, top)
                    listView2.setSelectionFromTop(firstVisibleItem, top)
                    //                    }
                }
            }
        })

        //设置listview1列表的scroll监听，用于滑动过程中左右不同步时校正
        listView1.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                if (scrollState == 0 || scrollState == 1) {
                    //获得第一个子view
                    val subView = view.getChildAt(0)

                    if (subView != null) {
                        val top = subView.top
                        val top1 = listView2.getChildAt(0).top
                        val position = view.firstVisiblePosition

                        //如果两个首个显示的子view高度不等
                        if (top != top1) {
                            listView1.setSelectionFromTop(position, top)
                            listView2.setSelectionFromTop(position, top)
                        }
                    }
                }
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                val subView = view.getChildAt(0)
                if (subView != null) {
                    val top = subView.top
                    listView1.setSelectionFromTop(firstVisibleItem, top)
                    listView2.setSelectionFromTop(firstVisibleItem, top)

                }
            }
        })
    }

}