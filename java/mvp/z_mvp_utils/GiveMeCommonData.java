/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.z_mvp_utils;

import com.infinity.jerry.yyd_tms.mvp.presenter.CommonPresenter;

/**
 * Created by jerry on 2017/6/14.
 */

public class GiveMeCommonData {

    private CommonPresenter presenter;

    private GiveMeCommonData() {
        presenter = CommonPresenter.getInstance();
    }

    public static GiveMeCommonData getInstance() {
        return new GiveMeCommonData();
    }

    public void giveMeAllTerminal(CommonPresenter.OnGetAllTerminalListener listener) {
        presenter.getAllTerminal(listener);
    }

    public void giveMeBillDetail(Long uuid, CommonPresenter.OnGetBillDetailListener listener) {
        presenter.getBillDetail(uuid, listener);
    }

    public void giveMeSuperTerminal(CommonPresenter.OnSuperTerminalListener listener) {
        presenter.getSuperTerminal(listener);
    }

    public void giveMeSuperBills(String json, int pageSize, int page, CommonPresenter.OnSuperBillListener listener) {
        presenter.getSuperBills(json, pageSize, page, listener);
    }

    public void giveMeMyBills(String startTime, String endTime, String json, int pageSize, int page, CommonPresenter.OnMyBillListener listener) {
        presenter.getMyBills(startTime, endTime, json, pageSize, page, listener);
    }

    public void giveMeChildsPoints(CommonPresenter.OnGetChildPointsListener listener) {
        presenter.getChildPoints(listener);
    }

    public void giveMeAllPoints(CommonPresenter.OnAllPointSearchListener listener) {
        presenter.getAllPoint(listener);
    }

}
