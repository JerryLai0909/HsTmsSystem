/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.z_mvp_utils;

import android.util.Log;

import com.infinity.jerry.yyd_tms.data.request_entity.RateSpecialEntity;

import rx.Subscriber;

/**
 * Created by jerry on 2017/7/28.
 */

public abstract class ZRateSubscriber extends Subscriber<RateSpecialEntity> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("subscriber", "custom subscriber onError " + e.toString());
        onErrorZ(e);
    }

    @Override
    public void onNext(RateSpecialEntity entity) {
        if (entity.isFlag()) {
            onSuccessZ(entity.getData());
        }else{
            onError(new Throwable("rate wrong"));
        }
    }

    public abstract void onSuccessZ(double t);

    public abstract void onErrorZ(Throwable throwable);
}
