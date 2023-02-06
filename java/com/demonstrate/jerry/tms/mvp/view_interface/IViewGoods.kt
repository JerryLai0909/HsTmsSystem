/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.CommonGoods

/**
 * Created by jerry on 2017/6/15.
 */
interface IViewGoods : IViewNetState {


    fun getGoodsSucc(goods: List<CommonGoods>)
    fun addGoodsSucc()
    fun deleteGoodSucc()

    fun remoteGoodError(type: Int)


    companion object {
        val GOOD_GET_ERROR: Int = 32
        val GOOD_DELETE_ERROR: Int = 30
        val GOOD_ADD_ERROR: Int = 64
    }

}