/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.service

import com.infinity.jerry.yyd_tms.data.*
import com.infinity.jerry.yyd_tms.data.request_entity.RateSpecialEntity
import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZCommonEntity
import retrofit2.http.*
import rx.Observable

/**
 * Created by jerry on 2017/6/15.
 * 增删改查 增和改 区别 uuid
 */

class PointSetService {
    //声明相关
    interface GetDeclarction {
        @POST("declaraction/selectDeclaractionList")
        fun getDeclar(): Observable<ZCommonEntity<List<Declaration>>>
    }

    interface DeleteDeclars {
        @FormUrlEncoded
        @POST("declaraction/deleteDeclaraction")
        fun deleteDeclar(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }

    interface EditDeclars {
        @POST("declaraction/addDeclaraction")
        @FormUrlEncoded
        fun editDeclars(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }

    //车辆相关
    interface GetCommonCars {
        @POST("driverManager/selectDriverList")
        fun getCommonCars(): Observable<ZCommonEntity<List<CarsCommonEntity>>>
    }

    interface EditCommonCars {
        @FormUrlEncoded
        @POST("driverManager/addOrUpdateDriver")
        fun editCommonCars(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }

    interface DeleteCommonCars {
        @FormUrlEncoded
        @POST("driverManager/deleteDriver")
        fun deleteCommonCars(@Field("uuid") json: String): Observable<ZCommonEntity<Any>>
    }


    //别名管理
    interface GetCommonAlias {
        @POST("")
        fun getCommonAlias(): Observable<ZCommonEntity<List<AliasEntity>>>
    }

    interface EditCommonAlias {
        @POST("")
        fun editCommonAlias(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }

    interface DeleteCommonAlias {
        @POST("")
        fun deleteCOmmonAlias(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }

    //网点人员管理
    interface PointAndManServer {
        @FormUrlEncoded
        @POST("pointManager/selectPointAndPointUser")
        fun getPointAndMan(@Field("map") json: String): Observable<ZCommonEntity<BoPointAndManEntity>>

        @FormUrlEncoded
        @POST("pointManager/selectPoint")
        fun getPointDetails(@Field("map") json: String): Observable<ZCommonEntity<BoPointDetail>>

        @FormUrlEncoded
        @POST("pointManager/addOrUpdatePoint")
        fun updatePointInfo(@Header("token") token: String, @Field("map") json: String): Observable<ZCommonEntity<Any>>

        @FormUrlEncoded
        @POST("pointManager/selectPointUser")
        fun getAllMan(@Header("token") token: String, @Field("map") json: String): Observable<ZCommonEntity<List<BoManDetial>>>

        @FormUrlEncoded
        @POST("pointManager/addOrUpdatePointUser")
        fun updatePointMan(@Header("token") token: String, @Field("map") json: String): Observable<ZCommonEntity<Any>>

        @FormUrlEncoded
        @POST("pointManager/deletePointUser")
        fun deletePointMan(@Header("token") token: String, @Field("map") json: String): Observable<ZCommonEntity<Any>>
    }


    interface CooperateServer {
        @FormUrlEncoded
        @POST("partners/selectPartnersList")
        fun getCooperates(@Field("map") json: String): Observable<ZCommonEntity<CooperEntity>>

        @FormUrlEncoded
        @POST("partners/updatePartnersStatus")
        fun updateCooper(@Field("map") json: String): Observable<ZCommonEntity<Any>>

        @FormUrlEncoded
        @POST("partners/selectCompanyList")
        fun searchCompany(@Field("map") json: String): Observable<ZCommonEntity<CooperCompanyEntity>>

        @FormUrlEncoded
        @POST("partners/selectPointList")
        fun searchPoint(@Field("map") json: String): Observable<ZCommonEntity<CooperPointEntity>>

        @FormUrlEncoded
        @POST("partners/addPartners")
        fun addCooper(@Field("map") json: String): Observable<ZCommonEntity<Any>>
    }


    interface FeeRateServer{
        @GET("billing/InsuredRate/{id}")
        fun getRate(@Path("id") uuid :Long) : Observable<RateSpecialEntity>

        @FormUrlEncoded
        @POST("billing/SaveInsuredRate")
        fun setRate(@Field("map") json :String) :Observable<ZCommonEntity<Any>>

    }


}