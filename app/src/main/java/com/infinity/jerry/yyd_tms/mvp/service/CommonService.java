/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service;

import com.infinity.jerry.yyd_tms.data.AllPointEntity;
import com.infinity.jerry.yyd_tms.data.BillTerimi;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.data.FirstInfo;
import com.infinity.jerry.yyd_tms.data.MyBillListEntity;
import com.infinity.jerry.yyd_tms.data.PointEntity;
import com.infinity.jerry.yyd_tms.mvp.model.ClientInfo;
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jerry on 2017/6/14.
 */

public class CommonService {

    public interface ClientServer {
        @GET("billing/syn/consigner/{id}")
        Observable<ZCommonEntity<ClientInfo>> getClientInfo(@Path("id") int uuid);

        @GET("billing/paramer/consigner/{id}")
        Observable<ZCommonEntity<FirstInfo>> getFristInfo(@Path("id") int pointId);
    }

    //查询到站
    public interface TerminalServer {
        @POST("billing/searchTerminalStation")
        Observable<ZCommonEntity<List<BillTerimi>>> getTerminal();
    }

    //查询票据详情
    public interface BillDetailServer {
        @FormUrlEncoded
        @POST("billing/selectBillingByUuid")
        Observable<ZCommonEntity<List<BillingDrawMain>>> getBillDetail(@Field("map") String json);
    }

    public interface SuperTerminalServer {
        @POST("billManager/selectAllTerminalStation")
        Observable<ZCommonEntity<List<PointEntity>>> superTerminalSearch();
    }

    //超级票据查询
    public interface SuperBillSearch {
        @FormUrlEncoded
        @POST("billManager/selectBillSuperList")
        Observable<ZCommonEntity<MyBillListEntity>> superBillSearch(@Field("map") String json, @Query("pageSize") int pageSize, @Query("page") int page);
    }

    //我的票据查询
    public interface MyBillSearch {
        @FormUrlEncoded
        @Headers("Content-Type: application/x-www-form-urlencoded")
        @POST("billManager/selectBillList")
        Observable<ZCommonEntity<MyBillListEntity>> myBillSearch(@Field("startTime") String startTime, @Field("endTime") String endTime, @Field("map") String json, @Query("pageSize") int pageSize, @Query("page") int page);
    }

    //总部查询自己的子网点
    public interface ChildPoints {
        @POST("billManager/selectInitialStation")
        Observable<ZCommonEntity<List<PointEntity>>> getChildPoints();
    }

    public interface AllPointsNotSelf {
        @POST("transfer/getPointForNotOwn")
        Observable<ZCommonEntity<List<AllPointEntity>>> getAllPoint();
    }

}
