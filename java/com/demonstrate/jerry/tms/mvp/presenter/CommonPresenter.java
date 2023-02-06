/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.presenter;

import com.infinity.jerry.yyd_tms.data.AllPointEntity;
import com.infinity.jerry.yyd_tms.data.BillTerimi;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.data.FirstInfo;
import com.infinity.jerry.yyd_tms.data.MyBillListEntity;
import com.infinity.jerry.yyd_tms.data.PointEntity;
import com.infinity.jerry.yyd_tms.data.request_entity.StringUuid;
import com.infinity.jerry.yyd_tms.mvp.model.ClientInfo;
import com.infinity.jerry.yyd_tms.mvp.model.CommonModel;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZResultSubscriber;
import com.infinity.jerry.yyd_tms.utils.ZGsonUtils;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jerry on 2017/6/14.
 */

public class CommonPresenter {

    private CommonModel model;
    public static CommonPresenter presenter = null;

    private CommonPresenter() {
        model = CommonModel.getInstance();
    }

    public static CommonPresenter getInstance() {
        if (presenter == null) {
            presenter = new CommonPresenter();
        }
        return presenter;
    }

    public void getFirstClientInfo(final int pointId, final FirstClientListener listener) {
        model.getFirstinfo(pointId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<FirstInfo>() {
                    @Override
                    public void onSuccessZ(FirstInfo clientInfo) {
                        listener.getFirstSucc(clientInfo);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getFirstError();
                    }
                });
    }

    public void getAllClientInfo(final int uuid, final ClientInfoListener listener) {
        model.getClientInfo(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<ClientInfo>() {
                    @Override
                    public void onSuccessZ(ClientInfo clientInfo) {
                        listener.getClientSucc(clientInfo);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getCLientError();
                    }
                });
    }

    public void getAllTerminal(final OnGetAllTerminalListener listener) {
        model.getTerminal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<BillTerimi>>() {
                    @Override
                    public void onSuccessZ(List<BillTerimi> o) {
                        listener.getTerminalSucc(o);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getTerminalError();
                    }
                });
    }

    public void getBillDetail(Long uuid, final OnGetBillDetailListener listener) {
        StringUuid request = new StringUuid();
        request.setUuid(String.valueOf(uuid));
        model.getDetial(ZGsonUtils.getInstance().getJsonString(request))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<BillingDrawMain>>() {

                    @Override
                    public void onSuccessZ(List<BillingDrawMain> billingDrawMains) {
                        listener.getBillDetailSucc(billingDrawMains);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getBillDetailError();
                    }
                });
    }

    public void getSuperTerminal(final OnSuperTerminalListener listener) {
        model.getSuperTerminal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<PointEntity>>() {
                    @Override
                    public void onSuccessZ(List<PointEntity> pointEntities) {
                        listener.getSuperTermiSucc(pointEntities);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getSuperTermiError();
                    }
                });

    }

    //超级运单查询接口  需要一个全新的subscriber
    public void getSuperBills(String json, int pageSize, int page, final OnSuperBillListener listener) {
        model.getSuperBills(json, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<MyBillListEntity>() {
                    @Override
                    public void onSuccessZ(MyBillListEntity entity) {
                        listener.getSuperBillSucc(entity);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getSuperBillError();
                    }
                });
    }

    public void getMyBills(String startTime, String endTime, String json, int pageSize, int page, final OnMyBillListener listener) {
        model.getMyBills(startTime, endTime, json, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<MyBillListEntity>() {
                    @Override
                    public void onSuccessZ(MyBillListEntity objects) {
                        listener.getMyBillSucc(objects);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getMyBillError();
                    }
                });
    }

    //查询子网点
    public void getChildPoints(final OnGetChildPointsListener listener) {
        model.getChildPoints()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<PointEntity>>() {
                    @Override
                    public void onSuccessZ(List<PointEntity> pointEntities) {
                        listener.getChildPointsSucc(pointEntities);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getChildPointError();
                    }
                });

    }

    //查询除开自己所有点
    public void getAllPoint(final OnAllPointSearchListener listener) {
        model.getAllPoint()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<AllPointEntity>>() {
                    @Override
                    public void onSuccessZ(List<AllPointEntity> allPointEntities) {
                        listener.getAllPointsSucc(allPointEntities);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        listener.getAllPointsError();
                    }
                });
    }

    public interface FirstClientListener {
        void getFirstSucc(FirstInfo info);

        void getFirstError();
    }

    public interface ClientInfoListener {
        void getClientSucc(ClientInfo clientInfo);

        void getCLientError();
    }

    public interface OnGetAllTerminalListener {
        void getTerminalSucc(List<BillTerimi> o);

        void getTerminalError();
    }

    public interface OnGetBillDetailListener {
        void getBillDetailSucc(List<BillingDrawMain> bills);

        void getBillDetailError();
    }

    public interface OnSuperTerminalListener {
        void getSuperTermiSucc(List<PointEntity> lists);

        void getSuperTermiError();
    }

    public interface OnSuperBillListener {
        void getSuperBillSucc(MyBillListEntity bills);

        void getSuperBillError();
    }

    public interface OnMyBillListener {
        void getMyBillSucc(MyBillListEntity lists);

        void getMyBillError();
    }

    public interface OnGetChildPointsListener {
        void getChildPointsSucc(List<PointEntity> childs);

        void getChildPointError();
    }

    public interface OnAllPointSearchListener {
        void getAllPointsSucc(List<AllPointEntity> lists);

        void getAllPointsError();
    }
}
