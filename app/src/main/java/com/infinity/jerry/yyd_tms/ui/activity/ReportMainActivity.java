package com.infinity.jerry.yyd_tms.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;

/**
 * Created by jerry on 2019/5/28.
 * You are good enough to do everthing
 */
public class ReportMainActivity extends BaseActivity {

    TextView tvData;
    TextView tvPaihang;

    ZTitlebar titlebar;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_report_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvData = (TextView) findViewById(R.id.tvData);
        tvPaihang = (TextView) findViewById(R.id.tvPaihang);
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        titlebar.setTitle("数据列表");

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportMainActivity.this, ReportMonthActivity.class);
                startActivity(intent);
            }
        });

        tvPaihang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportMainActivity.this, ReportPointActivity.class);
                startActivity(intent);
            }
        });
    }
}
