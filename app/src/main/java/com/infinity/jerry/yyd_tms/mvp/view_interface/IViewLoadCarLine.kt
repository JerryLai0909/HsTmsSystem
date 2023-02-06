/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.LoadCarLineEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.LoadListEntity
import com.infinity.jerry.yyd_tms.data.new_tms_entity.NewBillEntity

/**
 * Created by jerry on 2017/6/19.
 */
interface IViewLoadCarLine : IViewNetState {

    fun getLoadLinesSucc(entity: List<LoadCarLineEntity>)
    fun getLoadLinesError()
    fun getLoadListSucc(entity: List<LoadListEntity>)
    fun getEndPointSucc(dataList: List<NewBillEntity>, item: LoadListEntity)
    fun buZhuangSucc()



}