/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter;

import com.infinity.jerry.yyd_tms.mvp.model.RecordModel;
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewRecord;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jerry on 2018/7/31.
 * You are good enough to do everthing
 */
public class RecordPresenter {

    private IViewRecord iViewRecord;
    private RecordModel model;

    public RecordPresenter(IViewRecord iViewRecord) {
        this.iViewRecord = iViewRecord;
        model = new RecordModel();
    }

    public void addBillRecord(String id, String amount,String type) {
        model.addRecord(id, amount,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<Object>() {
                    @Override
                    public void onSuccessZ(Object o) {
                        iViewRecord.addRecordSucc();
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        iViewRecord.addRecordError();

                    }
                });
    }
}
