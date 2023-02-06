/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter;
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.data.StationEditEntity;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by jerry on 2019/3/24.
 * You are good enough to do everthing
 */
public class EditStationActivity extends BaseActivity {

    private ZTitlebar titlebar;
    private ListView listView;
    private LinearLayout llEdit;
    private TextView tvState;
    private EditText edContent;
    private TextView tvEnsure;
    private TextView tvCancel;

    private int editState = 0;
    private long primaryId = -256;

    private List<StationEditEntity> dataList;
    private ZCommonAdapter<StationEditEntity> adapter;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_edit_station;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        titlebar.setTitle("到站分组");
        titlebar.setTitleMode(ZTitlebar.Companion.getMODE_TEXT());
        titlebar.setOnTextModeListener(new ZTitlebar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                editState = 0;
                llEdit.setVisibility(View.VISIBLE);
                tvState.setText("添加分组");
            }
        });
        titlebar.setTvPlusText("添加分组");
        listView = (ListView) findViewById(R.id.listView);
        llEdit = (LinearLayout) findViewById(R.id.llEdit);
        tvState = (TextView) findViewById(R.id.tvState);
        tvEnsure = (TextView) findViewById(R.id.tvEnsure);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        edContent = (EditText) findViewById(R.id.edContent);
        llEdit.setVisibility(View.GONE);
        tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (editState) {
                    case 0:
                        if (!edContent.getText().toString().equals("")) {
                            StationEditEntity entity = new StationEditEntity();
                            entity.setName(edContent.getText().toString().trim());
                            entity.setSort(0);
                            entity.save();
                        }
                        break;
                    case 1://编辑
                        if (primaryId != -256) {
                            if (!edContent.getText().toString().equals("")) {
                                ContentValues values = new ContentValues();
                                values.put("name", edContent.getText().toString().trim());
                                DataSupport.update(StationEditEntity.class, values, primaryId);
                            }
                        }
                        break;
                }
                llEdit.setVisibility(View.GONE);
                edContent.setText("");
                primaryId = -256;
                initListView();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llEdit.setVisibility(View.GONE);
                edContent.setText("");
                primaryId = -256;
            }
        });
        initListView();
    }

    private void initListView() {
        dataList = DataSupport.findAll(StationEditEntity.class);
        if (dataList == null) {
            return;
        }
        adapter = new ZCommonAdapter<StationEditEntity>(this, dataList, R.layout.item_list_station) {
            @Override
            public void convert(ZViewHolder holder, final StationEditEntity item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getName());
                TextView tvEdit = holder.getView(R.id.tvEdit);
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        primaryId = item.getId();
                        editState = 1;
                        llEdit.setVisibility(View.VISIBLE);
                        tvState.setText("编辑分组");
                        edContent.setText(item.getName());
                    }
                });
                TextView tvDelete = holder.getView(R.id.tvDelete);
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        primaryId = item.getId();
                        AlertDialog dialog = new AlertDialog.Builder(EditStationActivity.this)
                                .setTitle("分组信息")
                                .setMessage("您确定要删除该条分组信息吗")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DataSupport.delete(StationEditEntity.class, primaryId);
                                        initListView();
                                    }
                                }).create();
                        dialog.show();
                    }
                });
                TextView tvDo = holder.getView(R.id.tvDo);
                tvDo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        primaryId = item.getId();
                        Intent intent = new Intent(EditStationActivity.this, EditStation2Activity.class);
                        intent.putExtra("primaryId", primaryId);
                        startActivity(intent);
                    }
                });

            }
        };
        listView.setAdapter(adapter);


    }
}
