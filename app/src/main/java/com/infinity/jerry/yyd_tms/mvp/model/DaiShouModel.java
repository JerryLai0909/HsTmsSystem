/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model;

import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.ShouRequestEntity;
import com.infinity.jerry.yyd_tms.mvp.service.DaiShouServer;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory;

import rx.Observable;


/**
 * Created by jerry on 2018/11/9.
 * You are good enough to do everthing
 */
public class DaiShouModel {

    private DaiShouModel() {

    }

    public static DaiShouModel getInstance() {
        return new DaiShouModel();
    }

    public Observable<ZCommonEntity<NewListWrap>> getDaishou(String barCode) {
        return ZServiceFactory.getInstance().createService(DaiShouServer.DaiShouService.class).getShou(barCode);
    }

    public Observable<ZCommonEntity<Object>> ensureShou(ShouRequestEntity entity) {
        return ZServiceFactory.getInstance().createService(DaiShouServer.DaiShouService.class).ensureShou(entity);
    }


    public Observable<ZCommonEntity<NewListWrap>> getDaiFu(String barCode) {
        return ZServiceFactory.getInstance().createService(DaiShouServer.DaiShouService.class).getFu(barCode);
    }

    public Observable<ZCommonEntity<Object>> ensureFu(ShouRequestEntity entity) {
        return ZServiceFactory.getInstance().createService(DaiShouServer.DaiShouService.class).ensureFu(entity);
    }
}
