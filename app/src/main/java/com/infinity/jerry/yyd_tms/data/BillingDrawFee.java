/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2017/6/15.
 */

public class BillingDrawFee {
    long uuid;
    long billing_uuid;
    double declared_value;
    double valuation_fee;
    double delivery_fee;
    double advance;
    double receiving_fee;
    double handling_fee;
    double packing_fee;
    double upstair_fee;
    double forklift_fee;
    double return_fee;
    double under_charge_fee;
    double warehousing_fee;
    double collection_fee;
    double freight;
    double total_freight;
    double total_freight_receipts;
    String payment_method;
    double cash_payment;
    double collect_payment;
    double default_of_payment;
    double back_payment;
    double monthly_payment;
    double payment_deduction;
}
