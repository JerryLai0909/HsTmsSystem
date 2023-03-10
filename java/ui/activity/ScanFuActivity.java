/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewBillEntity;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.TempDaiShouEntity;
import com.infinity.jerry.yyd_tms.mvp.presenter.DaiShouPresenter;
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewDaishou;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.utils.StatusBarSetting;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2018/10/23.
 * You are good enough to do everthing
 */
public class ScanFuActivity extends CaptureActivity implements IViewDaishou {


    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private DaiShouPresenter presenter;

    private boolean isScan = true;

    private List<NewBillEntity> dataList;

    private TextView tvInfo;
    private ZTitlebar titlebar;
    private TextView tvInsure;

    private List<String> dataString = new ArrayList<>();
    private String lastString = "";

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            String temp = result.getText();
            Log.e("TAG", "?????? " + temp);
            if (isScan && temp.length() > 20) {
                boolean flag = true;
                for (String s : dataString) {
                    if (s.equals(temp)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    sendRequest(result.getText());
                    lastString = temp;
                } else {
                    tvInfo.setText("??????????????????");
                }

            }
        }


        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarSetting.setColor(this, getResources().getColor(R.color.color_darkOrange));
        setContentView(R.layout.activity_scan);
        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        titlebar.setTitle("????????????");
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        barcodeScannerView.decodeContinuous(callback);
        presenter = DaiShouPresenter.getInstance(this);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInsure = (TextView) findViewById(R.id.tvInsure);
        dataList = new ArrayList<>();

        tvInsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                TempDaiShouEntity tempEntity = new TempDaiShouEntity();
                tempEntity.setDataList(dataList);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleEntity", tempEntity);
                intent.putExtra("bundle", bundle);
                ScanFuActivity.this.setResult(1, intent);
                ScanFuActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    private void sendRequest(String barCode) {
        isScan = false;
        presenter.getDaiFu(barCode);
    }

    @Override
    public void getDaiShouSucc(NewListWrap result) {
        dataString.add(lastString);
        if (result.getContent().size() == 0) {
            tvInfo.setText("????????????????????????");
        } else {
            dataList.add(result.getContent().get(0));
            Log.e("TAG", result.toString());
            tvInfo.setText("??????" + result.getContent().get(0).getBarCode() + "????????????");
        }
        isScan = true;

    }

    @Override
    public void getError() {
        tvInfo.setText("???????????????????????????");
        isScan = true;
    }

    @Override
    public void ensureShouSucc() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        TempDaiShouEntity tempEntity = new TempDaiShouEntity();
        tempEntity.setDataList(dataList);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleEntity", tempEntity);
        intent.putExtra("bundle", bundle);
        ScanFuActivity.this.setResult(1, intent);
        ScanFuActivity.this.finish();
        super.onBackPressed();
    }
}
