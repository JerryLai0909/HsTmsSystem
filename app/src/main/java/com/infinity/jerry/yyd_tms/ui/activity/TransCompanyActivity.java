/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter;
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.data.CooperCompanyEntity;
import com.infinity.jerry.yyd_tms.data.request_entity.CompanyRequest;
import com.infinity.jerry.yyd_tms.mvp.presenter.BoCooperGetPresenter;
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewCooperCompany;
import com.infinity.jerry.yyd_tms.ui.customview.LoadingDialog;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.ui.customview.Z_SwipeListView;
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/6/21.
 */

public class TransCompanyActivity extends BaseActivity implements IViewCooperCompany {

    private ZTitlebar titlebar;
    private Z_SwipeListView swipeListView;
    private EditText edSearch;
    private TextView tvSearch;
    private CompanyRequest requestEntity;
    private BoCooperGetPresenter presenter;

    private Dialog dialog;

    @Override
    public void initData() {
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        swipeListView = (Z_SwipeListView) findViewById(R.id.swipeListView);
        edSearch = (EditText) findViewById(R.id.edCompanyName);
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        requestEntity = new CompanyRequest();
        requestEntity.setPage("1");
        requestEntity.setPageSize("50");
        dialog = LoadingDialog.loadingDialog(this, getString(R.string.loading), false);
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_cooper_company;
    }

    @Override
    public void initPresenter() {
        presenter = BoCooperGetPresenter.Companion.getInstance(this);
    }

    @Override
    public void initView() {
        titlebar.setTitle(getString(R.string.search_company));
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCompany();
            }
        });
        swipeListView.setZ_OnRefreshlistener(new Z_SwipeListView.Z_OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (requestEntity.getCompany_name() == null) {
                    swipeListView.getSwipeLayout().setRefreshing(false);
                } else {
                    sendRequest();
                }
            }
        });
    }

    private void sendRequest() {
        dialog.show();
        presenter.searCompany(ZGsonUtils.getInstance().getJsonString(requestEntity));
    }

    private void searchCompany() {
        if (!TextUtils.isEmpty(getEDString(edSearch))) {
            requestEntity.setCompany_name(getEDString(edSearch));
            sendRequest();
        } else {
            Toasty.info(this, getString(R.string.entry_company_name)).show();
        }
    }

    private void initListView(final CooperCompanyEntity entity) {
        dialog.dismiss();
        swipeListView.getListView().setAdapter(new ZCommonAdapter<CooperCompanyEntity.PageListBean>(this, entity.getPageList(), R.layout.item_cooper_company) {
            @Override
            public void convert(ZViewHolder holder, CooperCompanyEntity.PageListBean item, int position) {
                TextView tvName = holder.getView(R.id.tvCompanyName);
                tvName.setText(item.getCompany_name());
            }
        });
        swipeListView.showListView();

        swipeListView.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("companyEntity", entity.getPageList().get(position));
                Intent intent = new Intent();
                intent.putExtra("commpanyBundle", bundle);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }

    @Override
    public void getCompanySucc(@NotNull CooperCompanyEntity entity) {
        dialog.dismiss();
        initListView(entity);
    }

    @Override
    public void getCompanError() {
        dialog.dismiss();
        swipeListView.requestNoData();
    }

    @Override
    public void onNetWorkError() {
        dialog.dismiss();
        swipeListView.requestError();

    }
}
