/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.infinity.jerry.yyd_tms.constant.ConstantPool;

public class ZShrPrefencs {

    private static ZShrPrefencs zShrPrefencs = null;
    private Context context;//This is the context from only application context;

    private SharedPreferences userShare;
    private SharedPreferences.Editor userEditor;


    private ZShrPrefencs(Context context) {
        this.context = context;
        userShare = context.getSharedPreferences(ConstantPool.USER_SHARE, Context.MODE_PRIVATE);
        userEditor = userShare.edit();
    }

    public static ZShrPrefencs registApp(Context context) {
        if (zShrPrefencs == null) {
            zShrPrefencs = new ZShrPrefencs(context);
        }
        return zShrPrefencs;
    }

    public static ZShrPrefencs getInstance() {

        return zShrPrefencs;

    }

    public void clearData() {
        userEditor.clear();
        userEditor.apply();
        Log.e("TAG", "SharePreferences 清除所有Token数据");
    }

    //登录成功之后需要修改这两个参数
    public void setNameAndPwd(String name, String pwd) {
        userEditor.putBoolean("is_login", true);
        userEditor.putString("zUserName", name);
        userEditor.putString("zUserPwd", pwd);
        userEditor.apply();
    }

    public void setCopyCount(int number) {
        userEditor.putInt("zUserCopyNumber", number);
        userEditor.apply();
    }

    public int getCopyCount() {
        return userShare.getInt("zUserCopyNumber", 0);
    }

    public String[] getNameAndPwd() {
        String[] arr = {userShare.getString("zUserName", "ZGtt"), userShare.getString("zUserPwd", "LoveGtt")};
        return arr;
    }

    public void saveBlueToothDeviceName(String string) {
        userEditor.putString("zBlueDeviceName", string);
        userEditor.apply();
    }

    public void saveBlueToothDeviceName2(String string) {
        userEditor.putString("zBlueDeviceName2", string);
        userEditor.apply();
    }

    public String getBlueToothDeviceName() {
        return userShare.getString("zBlueDeviceName", ConstantPool.BT_NO_DEVICE);
    }

    public String getBlueToothDeviceName2() {
        return userShare.getString("zBlueDeviceName2", ConstantPool.BT_NO_DEVICE);
    }

    public void saveBlueToothMac(String string) {
        userEditor.putString("zBlueMacAddress", string);
        userEditor.apply();
    }

    public void saveBlueToothMac2(String string) {
        userEditor.putString("zBlueMacAddress2", string);
        userEditor.apply();
    }

    public String getBlueToothMac() {
        return userShare.getString("zBlueMacAddress", ConstantPool.BT_NO_DEVICE);
    }

    public String getBlueToothMac2() {
        return userShare.getString("zBlueMacAddress2", ConstantPool.BT_NO_DEVICE);
    }

    public boolean isOpenPrinter() {
        return userShare.getBoolean("openPrinter", false);
    }

    public void setOpenPrinter(boolean flag) {
        userEditor.putBoolean("openPrinter", flag);
        userEditor.apply();
    }

    public void setPayMethod(String string) {
        userEditor.putString("payMethod", string);
        userEditor.apply();
    }

    public String getPayMethod() {
        return userShare.getString("payMethod", "现付");
    }

    public void setDeliMethod(String string) {
        userEditor.putString("deliMethod", string);
        userEditor.apply();
    }

    public String getDeliMethod() {
        return userShare.getString("deliMethod", "自提");

    }

    public void setNetESCIp(String ip) {
        userEditor.putString("escIp", ip);
        userEditor.apply();
    }

    public String getNetESCIp() {
        return userShare.getString("escIp", "");
    }

    public void setNetPosIp(String ip) {
        userEditor.putString("posIp", ip);
        userEditor.apply();
    }

    public String getNetPosIp() {
        return userShare.getString("posIp", "");
    }

    public void setNetOn(int on) {
        userEditor.putInt("netOn", on);
        userEditor.apply();
    }

    public int getNetOn() {
        return userShare.getInt("netOn", 0);
    }


    public void setIsPriceDetail(int flag) {//0 不开 1 开
        userEditor.putInt("isPriceOn", flag);
        userEditor.apply();
    }

    public int getPriceDetail() {
        return userShare.getInt("isPriceOn", 1);
    }

    public void setSpaceCount(int flag) {
        userEditor.putInt("spaceCount", flag);
        userEditor.apply();
    }

    public int getSpaceCount() {
        return userShare.getInt("spaceCount", 0);
    }

    public void setHintParams(int flag) {
        userEditor.putInt("specialHint", flag);
        userEditor.apply();
    }

    public int getHintParams() {
        return userShare.getInt("specialHint", 0);
    }

    public void setPriceSize(int flag) {
        userEditor.putInt("priceSize", flag);
        userEditor.apply();
    }

    public int getPriceSize() { //0小1大
        return userShare.getInt("priceSize", 0);
    }

    public void setIsBarCode(int flag) {
        userEditor.putInt("barCode", flag);
        userEditor.apply();
    }

    public int getIsBarCode() {
        return userShare.getInt("barCode", 1);
    }

    public void setTimeDistr(int time) {
        userEditor.putInt("timeDis", time);
        userEditor.apply();
    }

    public int getTimeDistr() {
        return userShare.getInt("timeDis", 0);
    }


    public void setSendSize(int flag) {
        userEditor.putInt("sendSize", flag);
        userEditor.apply();
    }

    public int getSendSize() {
        return userShare.getInt("sendSize", 0);
    }

    public void setReciveSize(int flag) {
        userEditor.putInt("reviceSize", flag);
        userEditor.apply();
    }

    public int getReciveSize() {
        return userShare.getInt("reviceSize", 0);
    }

    public void setNumberSize(int flag) {
        userEditor.putInt("numberSize", flag);
        userEditor.apply();
    }

    public int getNumberSize() {
        return userShare.getInt("numberSize", 0);
    }

    public void setPosMax(int number) {
        userEditor.putInt("posMax", number);
        userEditor.apply();
    }

    public int getPosMax() {
        return userShare.getInt("posMax", 0);
    }


    public void setBiaoGeshi(int type) {
        userEditor.putInt("biaoType", type);
        userEditor.apply();
    }

    public int getBiaogeshi() {
        return userShare.getInt("biaoType", 0);
    }


    public void setBigNumber(int type) {
        userEditor.putInt("bigNumber", type);
        userEditor.apply();
    }

    public int getBigNumber() {
        return userShare.getInt("bigNumber", 0);
    }

    public void setBigDianFu(int type) {
        userEditor.putInt("bigDianFu", type);
        userEditor.apply();
    }

    public int getBigDianFu() {
        return userShare.getInt("bigDianFu", 0);
    }


}
