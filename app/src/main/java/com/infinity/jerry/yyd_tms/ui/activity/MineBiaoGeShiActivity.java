/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs;

/**
 * Created by jerry on 2019/1/14.
 * You are good enough to do everthing
 */
public class MineBiaoGeShiActivity extends BaseActivity {

    private ZTitlebar zTitlebar;
    private LinearLayout llNormal;
    private LinearLayout llSimple;
    private LinearLayout llBigCompany;

    private ZShrPrefencs zShrPrefencs;
    private int style = 0;

    private ImageView imNormal;
    private ImageView imSimple;
    private ImageView imBigCompany;

    @Override
    public void initData() {
        zShrPrefencs = ZShrPrefencs.getInstance();
        style = zShrPrefencs.getBiaogeshi();
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.acitivity_biao_geshi;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        zTitlebar = (ZTitlebar) findViewById(R.id.titleBar);
        zTitlebar.setTitle("标签打印模板");

        llNormal = (LinearLayout) findViewById(R.id.llNormal);
        llSimple = (LinearLayout) findViewById(R.id.llSimple);
        llBigCompany = (LinearLayout) findViewById(R.id.llBigCompany);

        imNormal = (ImageView) findViewById(R.id.imNormal);
        imSimple = (ImageView) findViewById(R.id.imSimple);
        imBigCompany = (ImageView) findViewById(R.id.imBigCompany);

        switch (style) {
            case 0:
                imNormal.setImageDrawable(ContextCompat.getDrawable(MineBiaoGeShiActivity.this, R.mipmap.tms_icon_choose_c_true));
                break;
            case 1:
                imSimple.setImageDrawable(ContextCompat.getDrawable(MineBiaoGeShiActivity.this, R.mipmap.tms_icon_choose_c_true));
                break;
            case 2:
                imBigCompany.setImageDrawable(ContextCompat.getDrawable(MineBiaoGeShiActivity.this, R.mipmap.tms_icon_choose_c_true));
        }


        llNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEverything();
                zShrPrefencs.setBiaoGeshi(0);
                imNormal.setImageDrawable(ContextCompat.getDrawable(MineBiaoGeShiActivity.this, R.mipmap.tms_icon_choose_c_true));

            }
        });

        llSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEverything();
                zShrPrefencs.setBiaoGeshi(1);
                imSimple.setImageDrawable(ContextCompat.getDrawable(MineBiaoGeShiActivity.this, R.mipmap.tms_icon_choose_c_true));
            }
        });

        llBigCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEverything();
                zShrPrefencs.setBiaoGeshi(2);
                imBigCompany.setImageDrawable(ContextCompat.getDrawable(MineBiaoGeShiActivity.this, R.mipmap.tms_icon_choose_c_true));
            }
        });
    }

    private void clearEverything() {
        imNormal.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.tms_icon_choose_c_false));
        imSimple.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.tms_icon_choose_c_false));
        imBigCompany.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.tms_icon_choose_c_false));
    }
}
