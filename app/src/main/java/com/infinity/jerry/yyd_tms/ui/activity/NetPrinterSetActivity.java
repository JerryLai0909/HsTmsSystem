/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs;

import net.posprinter.posprinterface.UiExecute;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2018/7/24.
 * You are good enough to do everthing
 */
public class NetPrinterSetActivity extends BaseActivity {

    private ZTitlebar titleBar;
    private EditText editIP;
    private TextView tvStart;
    private TextView tvClear;
    private TextView edIpPos;
    private int isNetOn = 0;

    private TextView tvPiao;
    private TextView tvBiao;

    @Override
    public void initData() {
        isNetOn = ZShrPrefencs.getInstance().getNetOn();
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_printer_set;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar = (ZTitlebar) findViewById(R.id.titleBar);
        tvStart = (TextView) findViewById(R.id.tvStart);
        tvClear = (TextView) findViewById(R.id.tvClear);
        editIP = (EditText) findViewById(R.id.edIp);
        edIpPos = (TextView) findViewById(R.id.edIpPos);
        tvPiao = (TextView) findViewById(R.id.tvPiao);
        tvBiao = (TextView) findViewById(R.id.tvBiao);

        titleBar.setTitle("网口打印设置");
        if (isNetOn == 0) {
            tvStart.setText("点击启用");
            tvStart.setBackgroundColor(ContextCompat.getColor(this, R.color.color_orange));
        } else {
            tvStart.setText("点击停用");
            tvStart.setBackgroundColor(ContextCompat.getColor(this, R.color.level1_gray_dark));
        }

        editIP.setText(ZShrPrefencs.getInstance().getNetESCIp());
        edIpPos.setText(ZShrPrefencs.getInstance().getNetPosIp());

        tvPiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editIP.getText().toString())) {
                    ZShrPrefencs.getInstance().setNetESCIp(editIP.getText().toString());
                    Toasty.success(NetPrinterSetActivity.this, "保存成功").show();
                } else {
                    Toasty.error(NetPrinterSetActivity.this, "请输入ip地址").show();
                }
            }
        });

        tvBiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edIpPos.getText().toString())) {
                    ZShrPrefencs.getInstance().setNetPosIp(edIpPos.getText().toString());
                    Toasty.success(NetPrinterSetActivity.this, "保存成功").show();

                } else {
                    Toasty.error(NetPrinterSetActivity.this, "请输入ip地址").show();
                }
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNetOn = ZShrPrefencs.getInstance().getNetOn();
                if (TextUtils.isEmpty(editIP.getText().toString()) && TextUtils.isEmpty(edIpPos.getText().toString())) {
                    Toasty.info(NetPrinterSetActivity.this, "如需启用请输入至少一个打印机的ip").show();
                    return;
                }
                if (isNetOn == 0) {
                    tvStart.setText("点击停用");
                    tvStart.setBackgroundColor(ContextCompat.getColor(NetPrinterSetActivity.this, R.color.level1_gray_dark));
                    ZShrPrefencs.getInstance().setNetOn(1);
                } else {
                    ZShrPrefencs.getInstance().setNetOn(0);
                    MainActivity.Companion.getBinder().disconnectCurrentPort(new UiExecute() {
                        @Override
                        public void onsucess() {
                            Log.e("TAG", "票据打印机断开成功");
                            ZShrPrefencs.getInstance().setNetESCIp("");
                        }

                        @Override
                        public void onfailed() {
                            Log.e("TAG", "票据打印机断开失败");
                        }
                    });
                    MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                        @Override
                        public void onsucess() {
                            Log.e("TAG", "标签打印机断开成功");
                            ZShrPrefencs.getInstance().setNetPosIp("");
                        }

                        @Override
                        public void onfailed() {
                            Log.e("TAG", "标签打印机断开失败");
                        }
                    });
                    tvStart.setText("点击启用");
                    tvStart.setBackgroundColor(ContextCompat.getColor(NetPrinterSetActivity.this, R.color.color_orange));
                }

            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editIP.setText("");
            }
        });
    }
}
