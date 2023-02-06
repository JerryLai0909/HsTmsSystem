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

public class BillingDrawVo {

    long uuid;
    String waybill_number;
    String initial_station_id;
    String terminal_station_id;
    String invoice_date;
    long consigner_uuid;
    String consigner;
    String consigner_phone;
    String consigner_addr;
    String consignee;
    String consignee_phone;
    String consignee_addr;
    String goods_name;
    long quantity;
    String packaging;
    String weight;
    String volume;
    String delivery_method;
    long number_of_copies;
    long drawer_uuid;
    long invoice_status;
    String article_number;
    String transit_destination;
    long loading_batches_id;
    String source;
    String remarks;
}
