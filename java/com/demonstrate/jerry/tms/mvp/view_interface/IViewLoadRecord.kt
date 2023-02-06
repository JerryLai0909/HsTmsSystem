/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.BillListTempShit
import com.infinity.jerry.yyd_tms.data.BillingDrawMain
import com.infinity.jerry.yyd_tms.data.NewRecordsEntity

/**
 * Created by jerry on 2017/7/10.
 */
interface IViewLoadRecord : IViewNetState {
    fun getRecordListSucc(lists: List<BillingDrawMain>)
    fun getrecordListError()
    fun getNewloadByBatchIdSucc(entities: List<BillingDrawMain>)
    fun loadrecordsuxcc(entities:List<BillListTempShit>)
    fun getNewRecordSucc(lists: List<NewRecordsEntity>)
    fun jiuCuoSucc()

}