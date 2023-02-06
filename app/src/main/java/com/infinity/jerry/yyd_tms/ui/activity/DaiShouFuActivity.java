/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter;
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewBillEntity;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.ShouRequestEntity;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.TempDaiShouEntity;
import com.infinity.jerry.yyd_tms.mvp.presenter.DaiShouPresenter;
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewDaishou;
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2018/10/23.
 * You are good enough to do everthing
 */
public class DaiShouFuActivity extends BaseActivity implements IViewDaishou {

    private ZTitlebar titlebar;
    private LinearLayout llSearch;
    private ListView listView;
    private TextView tvEnsure;
    private TextView tvTotal;
    private TextView tvCount;
    private EditText edSao;

    private DaiShouPresenter presenter;
    private boolean isScan = true;

    private Dialog dialog;

    private int total = 0;
    private int count = 0;

    private List<NewBillEntity> dataList;
    private ZCommonAdapter<NewBillEntity> adapter;


    @Override
    public void initData() {
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.update), false);
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_daishou_shou;
    }

    @Override
    public void initPresenter() {
        presenter = DaiShouPresenter.getInstance(this);
    }

    @Override
    public void initView() {
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaiShouFuActivity.this, ShouBillActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        tvEnsure = (TextView) findViewById(R.id.tvEnsure);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvCount = (TextView) findViewById(R.id.tvCount);
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        edSao = (EditText) findViewById(R.id.edSao);

        titlebar.setTitle("扫码付款");
        titlebar.setTitleMode(ZTitlebar.Companion.getMODE_IMAGE());
        titlebar.setImPlusDrawable(ContextCompat.getDrawable(this, R.mipmap.tms_qrcode));
        titlebar.setOnImageModeListener(new ZTitlebar.OnImageModeListener() {
            @Override
            public void onClickImageModel() {
                IntentIntegrator integrator = new IntentIntegrator(DaiShouFuActivity.this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator.setOrientationLocked(true);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setCaptureActivity(ScanFuActivity.class); //设置打开摄像头的Activity
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


        edSao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ss = s.toString();
                if (ss.length() == 24) {
                    if (isScan) {
                        sendRequest(ss);
                    }
                }


            }
        });


        dataList = new ArrayList<>();
        tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Long> ids = new ArrayList<>();
                for (NewBillEntity item : dataList) {
                    ids.add(item.getId());
                }
                ShouRequestEntity entity = new ShouRequestEntity();
                entity.setIds(ids);
                presenter.ensureFu(entity);
            }
        });
        initListView();
    }

    private void sendRequest(String barCode) {
        dialog.show();
        isScan = false;
        presenter.getDaiFu(barCode);
    }

    private void initListView() {
        adapter = new ZCommonAdapter<NewBillEntity>(this, dataList, R.layout.item_list_daishou) {
            @Override
            public void convert(ZViewHolder holder, NewBillEntity item, final int position) {
                TextView tvNumber = holder.getView(R.id.tvNumber);
                tvNumber.setText(item.getArticleNumber());
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getConsignee());
                TextView tvMoney = holder.getView(R.id.tvMoney);
                tvMoney.setText(StringUtils.getZeroableStringForDouble(item.getCollectionFee()));
                TextView tvRemove = holder.getView(R.id.tvRemove);
                tvRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataList.remove(position);
                        adapter.notifyDataSetChanged();
                        countInfo();
                    }
                });

            }
        };
        listView.setAdapter(adapter);
        countInfo();
    }

    private void countInfo() {
        total = 0;
        count = 0;
        for (NewBillEntity item : dataList) {
            total += item.getCollectionFee();
            count++;
        }
        tvTotal.setText("代收合计: " + total + "元");
        tvCount.setText("共" + count + "单");
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getBundleExtra("bundle");
            if (bundle != null) {
                TempDaiShouEntity entity = (TempDaiShouEntity) bundle.getSerializable("bundleEntity");
                if (entity != null) {
                    List<NewBillEntity> list = entity.getDataList();
                    for (NewBillEntity item : list) {
                        boolean flag = false;
                        for (NewBillEntity temp : this.dataList) {
                            if (item.getArticleNumber().equals(temp.getArticleNumber())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            dataList.add(item);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    countInfo();
                }
            }
        } else {
            Log.e("TAG", "data 为空");
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
    }

    @Override
    public void getDaiShouSucc(NewListWrap result) {
        dialog.dismiss();
        if (result.getContent().size() == 0) {
            Toasty.info(this, "没有找到票据信息").show();
        } else {
            NewBillEntity item = result.getContent().get(0);
            boolean flag = false;
            for (NewBillEntity temp : this.dataList) {
                if (item.getArticleNumber().equals(temp.getArticleNumber())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                dataList.add(result.getContent().get(0));
                vibrate();
            } else {
                Toasty.info(this, "无需重复添加").show();
            }
            Log.e("TAG", result.toString());
        }
        isScan = true;
        edSao.setText("");
        adapter.notifyDataSetChanged();
        countInfo();
    }

    @Override
    public void getError() {
        dialog.dismiss();
        Toasty.info(this, "票据不符合付款条件").show();
        isScan = true;
        edSao.setText("");
    }

    @Override
    public void ensureShouSucc() {
        Toasty.success(this, "付款成功").show();
        dataList.clear();
        adapter.notifyDataSetChanged();
        tvTotal.setText("代收合计: " + 0 + "元");
        tvCount.setText("共" + 0 + "单");
        countInfo();
    }
}
