/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model;

import com.infinity.jerry.yyd_tms.mvp.service.BillService;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory;

import rx.Observable;

/**
 * Created by jerry on 2018/7/31.
 * You are good enough to do everthing
 */
public class RecordModel {

    public Observable<ZCommonEntity<Object>> addRecord(String id, String amount, String type) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(BillService.BillServer.class).addRecord(id, Double.valueOf(amount).longValue(), type);
        return observable;
    }


}
