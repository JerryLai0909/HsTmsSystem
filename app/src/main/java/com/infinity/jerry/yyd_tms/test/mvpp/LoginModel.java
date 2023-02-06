/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.test.mvpp;

import com.infinity.jerry.yyd_tms.mvp.z_mvp_utils.ZServiceFactory;

import rx.Observable;

/**
 * Created by jerry on 2017/7/18.
 */

public class LoginModel {

    public Observable<LoginEntity> loginApp(String name, String pwd) {
        Observable observable = ZServiceFactory.getInstance()
                .createService(LoginServer.Login.class)
                .loginApp(name, pwd);
        return observable;
    }

    public void shit() {
  
    }
}
