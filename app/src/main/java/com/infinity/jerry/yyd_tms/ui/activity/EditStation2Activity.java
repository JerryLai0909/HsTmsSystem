/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter;
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.data.BillTerimi;
import com.infinity.jerry.yyd_tms.data.StationEditEntity;
import com.infinity.jerry.yyd_tms.data.StationToSmallEntity;
import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.GiveMeCommonData;
import com.infinity.jerry.yyd_tms.ui.customview.AutoScaleTextView;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;

import org.litepal.crud.DataSupport;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2019/3/24.
 * You are good enough to do everthing
 */
public class EditStation2Activity extends BaseActivity {

    private long primaryId;

    private ZTitlebar titlebar;
    private TextView tvName;
    private GridView gridView;

    private StationEditEntity entity;
    private ZCommonAdapter<BillTerimi> adapter;

    private List<StationToSmallEntity> relaList;


    @Override
    public void initData() {
        primaryId = getIntent().getLongExtra("primaryId", -256);
        entity = DataSupport.where("id = ?", String.valueOf(primaryId)).findLast(StationEditEntity.class);
        relaList = DataSupport.where("stationEditEntityId = ?", String.valueOf(primaryId)).find(StationToSmallEntity.class);

        GiveMeCommonData.getInstance().giveMeAllTerminal(new CommonPresenter.OnGetAllTerminalListener() {
            @Override
            public void getTerminalSucc(List<BillTerimi> o) {
                initGridView(o);
            }

            @Override
            public void getTerminalError() {
                Toasty.error(EditStation2Activity.this, "获取到站失败，请检查网络").show();
            }
        });
    }


    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_edit_station_2;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        titlebar.setTitle("添加到站");
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText("您正在为 " + entity.getName() + " 此路线添加到站信息");
        gridView = (GridView) findViewById(R.id.gridView);

    }

    private void initGridView(final List<BillTerimi> dataList) {
        for (BillTerimi item : dataList) {
            for (StationToSmallEntity small : relaList) {
                if (item.getUuid() == small.getSmallId()) {
                    item.setChoosed(true);
                }
            }
        }

        adapter = new ZCommonAdapter<BillTerimi>(this, dataList, R.layout.item_draw_lines2) {
            @Override
            public void convert(ZViewHolder holder, BillTerimi item, int position) {
                AutoScaleTextView tvLinesName = holder.getView(R.id.tvLinesName);
                tvLinesName.setText(item.getPoint_name());
                ImageView imCooper = holder.getView(R.id.im_core);
                if (item.isChoosed()) {
                    imCooper.setVisibility(View.VISIBLE);
                } else {
                    imCooper.setVisibility(View.GONE);
                }
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean flag = dataList.get(position).isChoosed();
                if (flag) {//标记取消
                    dataList.get(position).setChoosed(false);
                    DataSupport.deleteAll(StationToSmallEntity.class, "stationEditEntityId = ? AND smallId = ?", String.valueOf(primaryId), String.valueOf(dataList.get(position).getUuid()));
                } else {
                    StationToSmallEntity entity = new StationToSmallEntity();
                    entity.setStationEditEntityId(primaryId);
                    entity.setName(dataList.get(position).getPoint_name());
                    entity.setSmallId(dataList.get(position).getUuid());
                    entity.setLogistics_uuid(dataList.get(position).getLogistics_uuid());
                    entity.save();
                    dataList.get(position).setChoosed(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
