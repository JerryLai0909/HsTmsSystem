/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.z_mvp_utils;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.infinity.jerry.yyd_tms.app.KotlingApplication;
import com.infinity.jerry.yyd_tms.data.AppUserToken;
import com.infinity.jerry.yyd_tms.ui.activity.LoginActivity;

import rx.Subscriber;

/**
 * Created by root on 2/10/17.
 */
public abstract class ZResultSubscriber<T> extends Subscriber<ZCommonEntity<T>> {

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e("subscriber", "custom subscriber onError " + e.toString());
        onErrorZ(e);
    }

    @Override
    public void onNext(ZCommonEntity<T> tZcommonEntity) {
        if (tZcommonEntity.isFlag()) {
            if (tZcommonEntity.getToken() != null && tZcommonEntity.getToken().length() > 0) {
                AppUserToken.getInstance().setToken(tZcommonEntity.getToken());
            }
            onSuccessZ(tZcommonEntity.getData());
        } else if (!tZcommonEntity.isFlag() && tZcommonEntity.getCode() == 204) {
            doLogin();
        } else {
            onErrorZ(new Throwable("Something wrong and show the INFO : " + tZcommonEntity.toString()));
        }
    }

    public abstract void onSuccessZ(T t);

    public abstract void onErrorZ(Throwable throwable);

    private void doLogin() {
        if (KotlingApplication.Companion.getApp() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(KotlingApplication.Companion.getApp(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    KotlingApplication.Companion.getApp().startActivity(intent);
                }
            }, 2000);

        }
    }
}
