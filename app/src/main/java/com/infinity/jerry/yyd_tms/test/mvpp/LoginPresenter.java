/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.test.mvpp;

import android.util.Log;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jerry on 2017/7/18.
 */

public class LoginPresenter {

    private IViewLogin iViewLogin;
    private LoginModel model;

    public LoginPresenter(IViewLogin iViewLogin) {//外面activity new了一个 presenter的对象 ，把自己传了进来。因为实现了IViewlogin接口，所以这里的类型是IViewLogin
        this.iViewLogin = iViewLogin;
        this.model = new LoginModel();
    }

    public void loginApp(String name, String pwd) {
        model.loginApp(name, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "请求完成了");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "请求错误了");
                        iViewLogin.loginError();// 这里其实就是activity 自己调用自己实现接口 override 复写的方法 ，下面也一样。

                    }

                    @Override
                    public void onNext(LoginEntity entity) {
                        Log.e("TAG", "请求进行中");
                        iViewLogin.loginSucc(entity);

                    }
                });
    }
}
