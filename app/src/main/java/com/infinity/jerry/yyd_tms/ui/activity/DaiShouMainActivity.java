/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;

/**
 * Created by jerry on 2018/10/23.
 * You are good enough to do everthing
 */
public class DaiShouMainActivity extends BaseActivity {

    private LinearLayout llShou;
    private LinearLayout llFu;
    private LinearLayout llBiao;
    private ZTitlebar titlebar;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_daishou_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        titlebar.setTitle("代收管理");
        llShou = (LinearLayout) findViewById(R.id.llShou);
        llFu = (LinearLayout) findViewById(R.id.llFu);
        llBiao = (LinearLayout) findViewById(R.id.llBiao);
        llBiao.setVisibility(View.GONE);
        llShou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaiShouMainActivity.this, DaiShouShouActivity.class);
                startActivity(intent);
            }
        });

        llFu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaiShouMainActivity.this, DaiShouFuActivity.class);
                startActivity(intent);
            }
        });

        llBiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
