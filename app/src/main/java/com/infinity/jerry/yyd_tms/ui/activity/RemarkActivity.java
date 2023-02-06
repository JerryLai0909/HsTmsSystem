package com.infinity.jerry.yyd_tms.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.adapter.ZCommonAdapter;
import com.infinity.jerry.yyd_tms.adapter.ZViewHolder;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.data.database.BillingDrawDataBase;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.utils.DistinctQuery;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;

public class RemarkActivity extends BaseActivity {
    private List<BillingDrawDataBase> all;
    private List<BillingDrawDataBase> sec;
    private ListView listView;
    private ZTitlebar titleBar;
    private ZCommonAdapter<BillingDrawDataBase> adapter;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_remark;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.listView);
        titleBar = (ZTitlebar) findViewById(R.id.titleBar);
        titleBar.setTitle("常用备注");
        DataSupport.findAllAsync(BillingDrawDataBase.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                all = (List<BillingDrawDataBase>) t;
                sec = new ArrayList<>();
                for (BillingDrawDataBase item : all) {
                    if (item.getRemark() != null && !item.getRemark().trim().equals("")) {
                        sec.add(item);
                    }
                }
                initListView();
            }
        });
    }

    private void initListView() {
        adapter = new ZCommonAdapter<BillingDrawDataBase>(this, sec, R.layout.item_list_remark) {
            @Override
            public void convert(ZViewHolder holder, BillingDrawDataBase item, int position) {
                TextView tvRemark = holder.getView(R.id.tvRemark);
                tvRemark.setText(item.getRemark());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("remark", sec.get(position).getRemark());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
