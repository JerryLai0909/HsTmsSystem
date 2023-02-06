/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter;

import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.ShouRequestEntity;
import com.infinity.jerry.yyd_tms.mvp.model.DaiShouModel;
import com.infinity.jerry.yyd_tms.mvp.view_interface.IViewDaishou;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jerry on 2018/11/9.
 * You are good enough to do everthing
 */
public class DaiShouPresenter {

    private IViewDaishou iViewDaishou;
    private DaiShouModel daiShouModel;

    private DaiShouPresenter(IViewDaishou iViewDaishou) {
        this.iViewDaishou = iViewDaishou;
        this.daiShouModel = DaiShouModel.getInstance();
    }


    public static DaiShouPresenter getInstance(IViewDaishou iViewDaishou) {
        return new DaiShouPresenter(iViewDaishou);
    }

    public void getDaiShou(String barCode) {
        daiShouModel.getDaishou(barCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<NewListWrap>() {

                    @Override
                    public void onSuccessZ(NewListWrap myBillListEntity) {
                        iViewDaishou.getDaiShouSucc(myBillListEntity);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        iViewDaishou.getError();
                    }
                });
    }

    public void ensureShou(ShouRequestEntity entity) {
        daiShouModel.ensureShou(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<Object>() {
                    @Override
                    public void onSuccessZ(Object o) {
                        iViewDaishou.ensureShouSucc();
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        iViewDaishou.ensureShouSucc();
                    }
                });
    }


    public void getDaiFu(String barCode) {
        daiShouModel.getDaiFu(barCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<NewListWrap>() {

                    @Override
                    public void onSuccessZ(NewListWrap myBillListEntity) {
                        iViewDaishou.getDaiShouSucc(myBillListEntity);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        iViewDaishou.getError();
                    }
                });
    }

    public void ensureFu(ShouRequestEntity entity) {
        daiShouModel.ensureFu(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<Object>() {
                    @Override
                    public void onSuccessZ(Object o) {
                        iViewDaishou.ensureShouSucc();
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        iViewDaishou.ensureShouSucc();
                    }
                });
    }
}
