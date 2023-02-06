/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils

import com.infinity.jerry.yyd_tms.data.database.BillingDrawDataBase

import org.litepal.crud.DataSupport
import org.litepal.crud.callback.FindCallback
import org.litepal.crud.callback.FindMultiCallback

/**
 * Created by jerry on 2018/5/27.
 * You are good enough to do everthing
 */

class Test {


    private fun sss() {
        DataSupport.findAsync(BillingDrawDataBase::class.java, 1).listen(object : FindCallback {
            override fun <T> onFinish(t: T) {
                val allSongs = t as List<BillingDrawDataBase>
            }
        })

        DataSupport.where().findAsync(BillingDrawDataBase::class.java).listen(object : FindMultiCallback {
            override fun <T> onFinish(t: List<T>) {

            }
        })

        DataSupport.findAllAsync(BillingDrawDataBase::class.java, 1).listen(object : FindMultiCallback {
            override fun <T> onFinish(t: List<T>) {

            }
        })

    }
}
