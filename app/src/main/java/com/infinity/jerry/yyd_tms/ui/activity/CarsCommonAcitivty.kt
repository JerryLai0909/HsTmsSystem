/*
 * Copyrigh()t (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder
import com.infinity.jerry.yyd_tms.base.BaseActivity
import com.infinity.jerry.yyd_tms.data.CarsCommonEntity
import com.infinity.jerry.yyd_tms.mvp.presenter.CarsPresenter
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCars
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView
import es.dmoral.toasty.Toasty
import kotterknife.bindView

/**
 * Created by jerry on 2017/6/18.
 */
class CarsCommonAcitivty : BaseActivity(), IViewCars {


    val titleBar: ZTitlebar by bindView<ZTitlebar>(R.id.titleBar)
    val swipeList: Z_SwipeListView by bindView<Z_SwipeListView>(R.id.swipeListView)
    val btAddCar: TextView by bindView<TextView>(R.id.btAddCars)
    var presenter: CarsPresenter? = null
    val edSearch: EditText by bindView<EditText>(R.id.edSearch)

    var carsList: List<CarsCommonEntity> = ArrayList()
    var adapter: ZCommonAdapter<CarsCommonEntity>? = null

    override fun initData() {

    }

    override fun getLayoutId(): Int {
        SetStatusBarColor()
        return R.layout.activity_common_car
    }

    override fun initPresenter() {
        presenter = CarsPresenter.getInstance(this)
    }

    override fun initView() {
        titleBar.setTitle(getString(R.string.cars_information))
        btAddCar.setOnClickListener {
            startActivity(CarsEditAcitivty::class.java)
        }
        swipeList.setZ_OnRefreshlistener(object : Z_SwipeListView.Z_OnRefreshListener {
            override fun onRefresh() {
                sendRequest()
            }
        })
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && !s.toString().equals("")) {
                    val ss: String = s.toString()
                    carsList.forEach {
                        if (it.plate_number != null) {
                            it.isIs_choosed = it.plate_number.contains(ss)
                        }
                        if (it.driver_name != null) {
                            it.isIs_choosed = it.driver_name.contains(ss)
                        }
                    }
                } else {
                    carsList.forEach {
                        it.isIs_choosed = true
                    }
                }
                if (adapter != null) {
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun onResume() {
        sendRequest()
        super.onResume()
    }

    private fun sendRequest() {
        presenter!!.getCommonCars()
    }

    private fun initListView() {
        adapter = object : ZCommonAdapter<CarsCommonEntity>(this, carsList, R.layout.item_common_cars) {
            override fun convert(holder: ZViewHolder?, item: CarsCommonEntity?, position: Int) {

                val cardView = holder!!.getView<CardView>(R.id.cardView)
                if (item!!.isIs_choosed) {
                    cardView.visibility = View.VISIBLE
                } else {
                    cardView.visibility = View.GONE
                }
                val tvPlateNumber = holder.getView<TextView>(R.id.tvCarNumber)
                tvPlateNumber.text = getNullablString(item.plate_number)
                val tvTime = holder.getView<TextView>(R.id.tvCarTime)
                tvTime.text = getNullablString(item.company_name)
                val tvName = holder.getView<TextView>(R.id.tvCarDriverName)
                tvName.text = getNullablString(item.driver_name)
                val tvPhone = holder.getView<TextView>(R.id.tvCarDriverPhone)
                tvPhone.text = getNullablString(item.driver_phone)
            }
        }

        swipeList.listView!!.adapter = adapter

        swipeList.listView!!.setOnItemClickListener { parent, view, position, id ->
            val bundle = Bundle()
            bundle.putSerializable("carEntity", carsList[position])
            val intent = Intent()
            intent.putExtra("carBundle", bundle)
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }

        swipeList.listView!!.setOnItemLongClickListener { parent, view, position, id ->
            showDeleteDialog(carsList[position])
            return@setOnItemLongClickListener true
        }
    }

    private fun showDeleteDialog(carsCommonEntity: CarsCommonEntity) {
        val alert = AlertDialog.Builder(this)
                .setMessage(getString(R.string.ask_delete_car))
                .setPositiveButton(getString(R.string.yes_do), DialogInterface.OnClickListener { dialog, which ->
                    val entity = CarsCommonEntity()
                    entity.uuid = carsCommonEntity.uuid
                    presenter!!.deleteCommonCar(carsCommonEntity.uuid.toString())
                })
                .setNegativeButton(getString(R.string.no_cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .create()
        alert.show()

    }

    override fun getCarsSucc(cars: List<CarsCommonEntity>) {
        this.carsList = cars
        carsList.forEach {
            it.isIs_choosed = true
        }
        initListView()
        swipeList.swipeLayout!!.isRefreshing = false
    }

    override fun carsRemoteError(type: Int) {
        swipeList.swipeLayout!!.isRefreshing = false
        when (type) {
            IViewCars.CARS_DELETE_ERROR -> Toasty.error(this, getString(R.string.delete_car_error)).show()
            IViewCars.CARS_GET_ERROR -> swipeList.requestNoData()
        }
    }

    override fun deleteCarSucc() {
        Toasty.success(this, getString(R.string.delete_car_succ)).show()
        sendRequest()
    }

    override fun onNetWorkError() {
        swipeList.requestError()
        Toasty.error(this, getString(R.string.netWork_error)).show()
    }
}