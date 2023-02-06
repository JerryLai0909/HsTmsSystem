/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
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

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2018/10/23.
 * You are good enough to do everthing
 */
public class ScanActivity extends CaptureActivity implements IViewDaishou {

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
            Log.e("TAG", "收款 " + temp);
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
                    tvInfo.setText("无需重复扫描");
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
        titlebar.setTitle("扫码收款");

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
                ScanActivity.this.setResult(1, intent);
                ScanActivity.this.finish();
            }
        });
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
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
        capture.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    private void sendRequest(String barCode) {
        isScan = false;
        presenter.getDaiShou(barCode);
    }

    @Override
    public void getDaiShouSucc(NewListWrap result) {
        vibrate();
//        ZMediaSoundHelper helper = ZMediaSoundHelper.getInstance(this);
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = getAssets().openFd("scan_succ.mp3");
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        dataString.add(lastString);
        if (result.getContent().size() == 0) {
            tvInfo.setText("没有找到票据信息");
        } else {
            dataList.add(result.getContent().get(0));
            Log.e("TAG", result.toString());
            tvInfo.setText("订单" + result.getContent().get(0).getBarCode() + "添加成功");
        }
        isScan = true;
    }

    @Override
    public void getError() {
        tvInfo.setText("票据不符合收款条件");
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
        ScanActivity.this.setResult(1, intent);
        ScanActivity.this.finish();
        super.onBackPressed();
    }
}
