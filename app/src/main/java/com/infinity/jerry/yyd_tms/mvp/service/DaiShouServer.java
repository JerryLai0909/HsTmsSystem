/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service;

import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewListWrap;
import com.infinity.jerry.yyd_tms.data.new_tms_entity.ShouRequestEntity;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jerry on 2018/11/9.
 * You are good enough to do everthing
 */
public class DaiShouServer {

    public interface DaiShouService {
        @GET("express/collect/receivables/{barCode}")
        Observable<ZCommonEntity<NewListWrap>> getShou(@Path("barCode") String barCode);

        @PUT("express/collect/receivables")
        Observable<ZCommonEntity<Object>> ensureShou(@Body ShouRequestEntity entity);

        @GET("express/collect/paid/{barCode}")
        Observable<ZCommonEntity<NewListWrap>> getFu(@Path("barCode") String barCode);

        @PUT("express/collect/paid")
        Observable<ZCommonEntity<Object>> ensureFu(@Body ShouRequestEntity entity);
    }
}
