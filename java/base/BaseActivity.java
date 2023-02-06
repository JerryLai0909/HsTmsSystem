/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.app.AppManager;
import com.infinity.jerry.yyd_tms.utils.StatusBarSetting;
import com.infinity.jerry.yyd_tms.utils.ZDoubleFormat;


/**
 * Created by jerry on 2017/6/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    private int count;//记录开启进度条的情况 只能开一个

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        // 默认着色状态栏
        mContext = this;
        this.initData();
        this.initPresenter();
        this.initView();

    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract void initData();

    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarSetting.setColor(this, getResources().getColor(R.color.color_darkOrange));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarSetting.setColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarSetting.setTranslucent(this);
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    protected String getEDString(EditText editText) {
        String result = editText.getText().toString().trim();
        return result;
    }

    protected String getEDStringOrNull(EditText editText) {
        String result = editText.getText().toString().trim();
        return !result.equals("") ? result : null;
    }

    protected String getNullablString(String string) {
        return string == null ? "" : string;
    }

    protected String getZeroableStringForDouble(Double sth) {
        if (sth == null || sth == 0D) {
            return "0";
        }
        return ZDoubleFormat.zFormat(String.valueOf(sth));
    }

    protected String getNullStringForDouble(Double sth) {
        if (sth == null || sth == 0D) {
            return "";
        }
        return ZDoubleFormat.zFormat(String.valueOf(sth));
    }

    protected String getZeroableStringForLong(Long sth) {
        if (sth == null || sth == 0L) {
            return "0";
        }
        return String.valueOf(sth);
    }

    protected double getDoubleNotNull(Double sth) {
        if (sth != null) {
            return sth;

        }
        return 0.0;
    }

    protected String getBillState(int state) {
        if (state == 1) {
            return "已开票";
        }
        if (state == 2) {
            return "已装车";
        }
        if (state == 3) {
            return "运输中";
        }
        if (state == 4) {
            return "已到货";
        }
        if (state == 5) {
            return "已签收";
        }
        if (state == 6) {
            return "已中转";
        }
        if (state == 9) {
            return "已作废";
        }
        return "未知状态";
    }

    protected void closeTheKeyBorad() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}


