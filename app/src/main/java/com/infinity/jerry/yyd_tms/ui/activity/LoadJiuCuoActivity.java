/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter;
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.data.BillListTempShit;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.data.NewRecordsEntity;
import com.infinity.jerry.yyd_tms.data.TempIdsEntity;
import com.infinity.jerry.yyd_tms.mvp.presenter.LoadRecordPresenter;
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewLoadRecord;
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2018/9/30.
 * You are good enough to do everthing
 */
public class LoadJiuCuoActivity extends BaseActivity implements IViewLoadRecord {

    private ZTitlebar titlebar;
    private ListView listView;
    private TextView tvEnsure;

    private int batchNumber = 0;

    private LoadRecordPresenter presenter;
    private List<BillingDrawMain> dataList;

    private ZCommonAdapter<BillingDrawMain> adapter;

    private Dialog dialog;

    @Override
    public void initData() {
        dialog = LoadingDialog.loadingDialog(this, "正在加载中", true);
        batchNumber = getIntent().getIntExtra("batchNumber", -256);
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_jiucuo;
    }

    @Override
    public void initPresenter() {
        presenter = LoadRecordPresenter.Companion.getInstance(this);
        presenter.getLoadByBatchIdX(batchNumber);
    }

    @Override
    public void initView() {
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        titlebar.setTitle("装车纠错");
        listView = (ListView) findViewById(R.id.listView);
        tvEnsure = (TextView) findViewById(R.id.tvEnsure);
        tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensureJiuCuo();
            }
        });
    }

    private void initListView() {
        if (dataList == null) {
            return;
        }
        adapter = new ZCommonAdapter<BillingDrawMain>(this, dataList, R.layout.item_jiucuo_list) {
            @Override
            public void convert(ZViewHolder holder, BillingDrawMain item, int position) {
                ImageView imChoosed = holder.getView(R.id.imChoosed);
                if (!item.isChoose()) {
                    imChoosed.setImageDrawable(ContextCompat.getDrawable(LoadJiuCuoActivity.this, R.mipmap.icon_radio_circle_off));
                } else {
                    imChoosed.setImageDrawable(ContextCompat.getDrawable(LoadJiuCuoActivity.this, R.mipmap.icon_radio_circle_on));
                }
                TextView tvStation = holder.getView(R.id.tvStation);
                tvStation.setText(item.getStartStation() + " ---- " + item.getEndStation());
                TextView tvInfo = holder.getView(R.id.tvInfo);
                tvInfo.setText(item.getArticle_number());
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataList.get(position).setChoose(!dataList.get(position).isChoose());
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void ensureJiuCuo() {
        dialog.show();
        TempIdsEntity entity = new TempIdsEntity();
        List<Long> list = new ArrayList<>();
        for (BillingDrawMain item : dataList) {
            if (item.isChoose()) {
                list.add(item.getUuid());
            }
        }
        entity.setIds(list);
        presenter.appJiuCuo(entity);
    }

    @Override
    public void getRecordListSucc(@NotNull List<? extends BillingDrawMain> lists) {

    }

    @Override
    public void getrecordListError() {
        dialog.dismiss();
        Toasty.error(this, "请求失败").show();

    }

    @Override
    public void getNewloadByBatchIdSucc(@NotNull List<? extends BillingDrawMain> entities) {
        dialog.dismiss();
        this.dataList = (List<BillingDrawMain>) entities;
        initListView();
    }

    @Override
    public void getNewRecordSucc(@NotNull List<? extends NewRecordsEntity> lists) {

    }

    @Override
    public void onNetWorkError() {
        dialog.dismiss();
        Toasty.error(this, "请求失败").show();
    }

    @Override
    public void jiuCuoSucc() {
        dialog.dismiss();
        this.finish();
    }

    @Override
    public void loadrecordsuxcc(@NotNull List<? extends BillListTempShit> entities) {

    }
}
