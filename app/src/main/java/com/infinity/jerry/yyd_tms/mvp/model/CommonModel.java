/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model;

import com.infinity.jerry.yyd_tms.data.AllPointEntity;
import com.infinity.jerry.yyd_tms.data.BillTerimi;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.data.FirstInfo;
import com.infinity.jerry.yyd_tms.data.MyBillListEntity;
import com.infinity.jerry.yyd_tms.data.PointEntity;
import com.infinity.jerry.yyd_tms.mvp.service.CommonService;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory;

import java.util.List;

import rx.Observable;

/**
 * Created by jerry on 2017/6/14.
 */

public class CommonModel {

    private static CommonModel model = null;

    private CommonModel() {

    }

    public static CommonModel getInstance() {
        if (model == null) {
            model = new CommonModel();
        }
        return model;

    }

    public Observable<ZCommonEntity<FirstInfo>> getFirstinfo(int pointId) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.ClientServer.class)
                .getFristInfo(pointId);
        return observable;
    }


    public Observable<ZCommonEntity<ClientInfo>> getClientInfo(int uuid) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.ClientServer.class)
                .getClientInfo(uuid);
        return observable;
    }

    public Observable<ZCommonEntity<List<BillTerimi>>> getTerminal() {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.TerminalServer.class)
                .getTerminal();
        return observable;
    }

    public Observable<ZCommonEntity<List<BillingDrawMain>>> getDetial(String json) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.BillDetailServer.class)
                .getBillDetail(json);
        return observable;
    }

    public Observable<ZCommonEntity<List<PointEntity>>> getSuperTerminal() {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.SuperTerminalServer.class)
                .superTerminalSearch();
        return observable;
    }

    public Observable<ZCommonEntity<MyBillListEntity>> getSuperBills(String json, int pageSize, int page) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.SuperBillSearch.class)
                .superBillSearch(json, pageSize, page);
        return observable;
    }

    public Observable<ZCommonEntity<MyBillListEntity>> getMyBills(String startTime, String endTime, String json, int pageSize, int page) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.MyBillSearch.class)
                .myBillSearch(startTime, endTime, json,pageSize, page);
        return observable;
    }

    public Observable<ZCommonEntity<List<PointEntity>>> getChildPoints() {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.ChildPoints.class)
                .getChildPoints();
        return observable;
    }

    public Observable<ZCommonEntity<List<AllPointEntity>>> getAllPoint() {
        Observable observable = ZServiceFactory.getInstance()
                .createService(CommonService.AllPointsNotSelf.class)
                .getAllPoint();
        return observable;
    }

}
