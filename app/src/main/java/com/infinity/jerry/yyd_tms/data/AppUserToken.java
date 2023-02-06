/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.util.List;

/**
 * Created by root on 2/15/17.
 */
public class AppUserToken {

    private static AppUserToken tokenInstance = null;
    private boolean isXy = false;
    private boolean isLogin = false;
    private LoginResult result;
    private String token;
    private List<Declaration> declars;
    private Double appRate ;

    public Double getAppRate() {
        return appRate;
    }

    public void setAppRate(Double appRate) {
        this.appRate = appRate;
    }

    public List<Declaration> getDeclars() {
        return declars;
    }

    public void setDeclars(List<Declaration> declars) {
        this.declars = declars;
    }

    public boolean isXy() {
        return isXy;
    }

    public void setXy(boolean xy) {
        isXy = xy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private AppUserToken() {

    }

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

    public static AppUserToken getInstance() {
        if (tokenInstance == null) {
            tokenInstance = new AppUserToken();
        }
        return tokenInstance;
    }

    public static AppUserToken getTokenInstance() {
        return tokenInstance;
    }

    public static void setTokenInstance(AppUserToken tokenInstance) {
        AppUserToken.tokenInstance = tokenInstance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public String toString() {
        return "AppUserToken{" +
                "isLogin=" + isLogin +
                ", result=" + result +
                '}';
    }
}
