/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import com.google.gson.Gson;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.data.NewBillDrawEntity;

/**
 * Created by root on 2/17/17.
 */

public class ZGsonUtils {

    private static ZGsonUtils zGsonUtils = null;
    private Gson gson;

    private ZGsonUtils() {
        gson = new Gson();
    }

    public static ZGsonUtils getInstance() {
        if (zGsonUtils == null) {
            zGsonUtils = new ZGsonUtils();
        }

        return zGsonUtils;
    }

    public String getJsonString(Object o) {
        return gson.toJson(o);
    }


    public static BillingDrawMain convertBillDraw(NewBillDrawEntity entity) {
        BillingDrawMain item = new BillingDrawMain();
        item.setUuid(entity.getId());
//        item.setOwner(entity.getOwner());
        item.setIs_unusual(entity.getIsUnusual());
        item.setWaybill_number(entity.getWaybillNumber());
//        item.setInitial_station_id(entity.getInitialStationId());
//        item.setTerminal_station_id(entity.getTerminalStationId());
//        item.setConsigner_uuid(entity.getConsignerUuid());
        item.setConsigner(entity.getConsigner());
        item.setConsigner_phone(entity.getConsignerPhone());
//        item.setConsigner_addr(entity.getConsignerAddr());
        item.setConsignee(entity.getConsignee());
        item.setConsignee_phone(entity.getConsigneePhone());
//        item.setConsignee_addr(entity.getConsigneeAddr());
        item.setGoods_name(entity.getGoodsName());
        item.setQuantity(entity.getQuantity());
        item.setPackaging(entity.getPackaging());
//        item.setWeight(entity.getWeight());
//        item.setVolume(entity.getVolume());
//        item.setDelivery_method(entity.getDeliveryMethod());
        item.setNumber_of_copies(entity.getNumberOfCopies());
//        item.setDrawer_uuid(entity.getDrawerUuid());
        item.setInvoice_date(entity.getInvoiceDate());
        item.setArticle_number(entity.getArticleNumber());
//        item.setTransit_destination(entity.getTransitDestination());
//        item.setLoading_batches_id(entity.getLoadingBatchesId());
//        item.setSource(entity.getSource());
        item.setRemarks(entity.getRemarks());
        item.setInitial_station_name(entity.getStartStation());
        item.setTerminal_station_name(entity.getEndStation());
//        item.setCompany_name(entity.getCompanyName);
//        item.setDrawer_name(entity.getDrawName());
//        item.setBilling_uuid(entity.getBillingUuid());
        item.setDeclared_value(entity.getDeclaredValue());
        item.setValuation_fee(entity.getValuationFee());
        item.setDelivery_fee(entity.getDeliveryFee());
        item.setAdvance(entity.getAdvance());
//        item.setReceiving_fee(entity.getRecevingFee());
//        item.setHandling_fee(entity.getHandlingFee());
        item.setPackaging(entity.getPackaging());
//        item.setUpstair_fee(entity.getUpstairFee());
//        item.setForklift_fee(entity.getForklistFee());
//        item.setUnder_charge_fee(entity.getUnderChargeFee());
//        item.setWarehousing_fee(entity.getWarehousingFee());
//        item.setCollection_fee(entity.getCollection());
        item.setFreight(entity.getFreight());
        item.setTotal_freight(entity.getTotalFreight());
//        item.setTotal_freight_receipts(entity.getTotalFreightReceipts());
        item.setPayment_method(entity.getPaymentMethod());
        item.setCash_payment(entity.getCashPayment());
        item.setCollect_payment(entity.getCollectPayment());
//        item.setDefault_of_payment(entity.getDefaultOfPayment());
//        item.setBack_payment(entity.getBackPayment());
//        item.setMonthly_payment(entity.getMonthlyPayment());
//        item.setPayment_deduction(entity.getPaymentDeduction());
//        item.setStartStation(entity.getStartStation());
//        item.setEndStation(entity.getEndStation());
//        item.setUser_name(entity.getUserName());
//        item.getTotalBill(entity.getTotalBill());
//        item.setPoint_owner_phone_o(entity.getPointOwnerPhoneO());
//        item.setBatch_number(entity.getBatchNumber);
//        item.setPlate_number(entity.getPlateNumber);
//        item.setDriver_phone(entity.getDriverPhone());
//        item.setBilling_fee_uuid(entity.getBillingFeeUuid());
//        item.setLogistics_notice(entity.getLogisticsNotice());
//        item.setLoading_time(entity.getLoadingTime());
//        item.setOperation_user_name(entity.getOperationUserName());
//        item.setUpdate_time(entity.getUpdateTime());
//        item.setInvoicedate(entity.getInvoiceDate());
//        item.setDriver_name(entity.getDriverName());
//        item.setOperation_user_uuid(entity.getOperationUserUuid());
        item.setInput_article_number(entity.getInputArticleNumber());

        return item;

    }


}
