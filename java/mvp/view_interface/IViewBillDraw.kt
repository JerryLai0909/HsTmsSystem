/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.BillingDrawMain

/**
 * Created by jerry on 2017/6/19.
 */
interface IViewBillDraw {

    fun drawBillSucc(entity: BillingDrawMain)
    fun drawBillError()
    fun drawBillBarCodeSucc(barCode: String)
    fun drawBillBarCodeError(barCode: String)


}