/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data

/**
 * Created by jerry on 2017/6/7.
 */
object DirtyData {

    var mainData: List<String> = listOf<String>("开票", "装车", "到货", "签收", "我的运单", "运单查询", "快运代收", "报表", "异常", "中转")
    var mainData2: List<String> = listOf<String>("开票", "装车", "到货", "签收", "我的运单", "运单查询", "报表", "异常", "中转")

    var searchFeeList: MutableList<SearchFee> = mutableListOf(
            SearchFee("null", "全部"),
            SearchFee("freight", "运费"),
            SearchFee("collection_fee", "代收货款"),
            SearchFee("valuation_fee", "保价费"),
            SearchFee("delivery_fee", "送货费"),
            SearchFee("advance", "垫付费"),
            SearchFee("receiving_fee", "接货费"),
            SearchFee("handling_fee", "装卸费"),
            SearchFee("receiving_fee", "包装费"),
            SearchFee("upstair_fee", "上楼费"),
            SearchFee("forklift_fee", "叉吊费"),
            SearchFee("return_fee", "回扣"),
            SearchFee("under_charge_fee", "欠返"))

    var searchStateList: MutableList<SearchState> = mutableListOf(
            SearchState(0, "全部"),
            SearchState(1, "已开票"),
            SearchState(2, "已装车"),
            SearchState(3, "运输中"),
            SearchState(4, "已到货"),
            SearchState(5, "已签收"),
            SearchState(6, "已中转"),
            SearchState(9, "已作废"))

    var searchPayMethod: MutableList<SearchPayMethod> = mutableListOf(
            SearchPayMethod(0, "全部"),
            SearchPayMethod(1, "现付"),
            SearchPayMethod(2, "到付"),
            SearchPayMethod(3, "欠付"),
            SearchPayMethod(4, "回付"),
            SearchPayMethod(5, "月结"),
            SearchPayMethod(6, "货款扣"),
            SearchPayMethod(7, "多笔付"))

}