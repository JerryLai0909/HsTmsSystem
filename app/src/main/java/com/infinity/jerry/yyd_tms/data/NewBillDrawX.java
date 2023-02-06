/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.util.List;

/**
 * Created by jerry on 2018/10/5.
 * You are good enough to do everthing
 */
public class NewBillDrawX {

    private String endStation;
    private List<DataBean> data;

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int uuid;
        private long waybill_number;
        private int initial_station_id;
        private int terminal_station_id;
        private int owner;
        private String invoice_date;
        private Object consigner_uuid;
        private String consigner;
        private String consigner_phone;
        private Object consigner_addr;
        private String consignee;
        private String consignee_phone;
        private Object consignee_addr;
        private String goods_name;
        private int quantity;
        private Object packaging;
        private Object weight;
        private Object volume;
        private String delivery_method;
        private Object input_article_number;
        private Object number_of_copies;
        private int drawer_uuid;
        private int invoice_status;
        private String article_number;
        private Object transit_destination;
        private long loading_batches_id;
        private Object source;
        private int is_unusual;
        private Object update_time;
        private Object operation_user_uuid;
        private Object receipts_delivery_fee;
        private Object receipts_total_freight;
        private Object receipts_collection_fee;
        private String remarks;
        private String invoicedate;
        private int billing_fee_uuid;
        private int declared_value;
        private int valuation_fee;
        private int delivery_fee;
        private int advance;
        private int receiving_fee;
        private int handling_fee;
        private int packing_fee;
        private int upstair_fee;
        private int forklift_fee;
        private int return_fee;
        private int under_charge_fee;
        private int warehousing_fee;
        private int collection_fee;
        private int freight;
        private int total_freight;
        private String total_freight_receipts;
        private String payment_method;
        private int cash_payment;
        private int collect_payment;
        private int default_of_payment;
        private int back_payment;
        private int monthly_payment;
        private int payment_deduction;
        private String startStation;
        private String startPoint_phone;
        private Object startPoint_addr;
        private Object point_owner_phone_o;
        private String endStation;
        private Object point_addr;
        private String user_name;
        private Object batch_number;
        private String company_name;
        private Object logistics_notice;
        private Object operation_user_name;
        private Object billing_remark;
        private String collect_status;
        private String payment_status;
        private String barCode;

        public int getUuid() {
            return uuid;
        }

        public void setUuid(int uuid) {
            this.uuid = uuid;
        }

        public long getWaybill_number() {
            return waybill_number;
        }

        public void setWaybill_number(long waybill_number) {
            this.waybill_number = waybill_number;
        }

        public int getInitial_station_id() {
            return initial_station_id;
        }

        public void setInitial_station_id(int initial_station_id) {
            this.initial_station_id = initial_station_id;
        }

        public int getTerminal_station_id() {
            return terminal_station_id;
        }

        public void setTerminal_station_id(int terminal_station_id) {
            this.terminal_station_id = terminal_station_id;
        }

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
        }

        public String getInvoice_date() {
            return invoice_date;
        }

        public void setInvoice_date(String invoice_date) {
            this.invoice_date = invoice_date;
        }

        public Object getConsigner_uuid() {
            return consigner_uuid;
        }

        public void setConsigner_uuid(Object consigner_uuid) {
            this.consigner_uuid = consigner_uuid;
        }

        public String getConsigner() {
            return consigner;
        }

        public void setConsigner(String consigner) {
            this.consigner = consigner;
        }

        public String getConsigner_phone() {
            return consigner_phone;
        }

        public void setConsigner_phone(String consigner_phone) {
            this.consigner_phone = consigner_phone;
        }

        public Object getConsigner_addr() {
            return consigner_addr;
        }

        public void setConsigner_addr(Object consigner_addr) {
            this.consigner_addr = consigner_addr;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getConsignee_phone() {
            return consignee_phone;
        }

        public void setConsignee_phone(String consignee_phone) {
            this.consignee_phone = consignee_phone;
        }

        public Object getConsignee_addr() {
            return consignee_addr;
        }

        public void setConsignee_addr(Object consignee_addr) {
            this.consignee_addr = consignee_addr;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Object getPackaging() {
            return packaging;
        }

        public void setPackaging(Object packaging) {
            this.packaging = packaging;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getVolume() {
            return volume;
        }

        public void setVolume(Object volume) {
            this.volume = volume;
        }

        public String getDelivery_method() {
            return delivery_method;
        }

        public void setDelivery_method(String delivery_method) {
            this.delivery_method = delivery_method;
        }

        public Object getInput_article_number() {
            return input_article_number;
        }

        public void setInput_article_number(Object input_article_number) {
            this.input_article_number = input_article_number;
        }

        public Object getNumber_of_copies() {
            return number_of_copies;
        }

        public void setNumber_of_copies(Object number_of_copies) {
            this.number_of_copies = number_of_copies;
        }

        public int getDrawer_uuid() {
            return drawer_uuid;
        }

        public void setDrawer_uuid(int drawer_uuid) {
            this.drawer_uuid = drawer_uuid;
        }

        public int getInvoice_status() {
            return invoice_status;
        }

        public void setInvoice_status(int invoice_status) {
            this.invoice_status = invoice_status;
        }

        public String getArticle_number() {
            return article_number;
        }

        public void setArticle_number(String article_number) {
            this.article_number = article_number;
        }

        public Object getTransit_destination() {
            return transit_destination;
        }

        public void setTransit_destination(Object transit_destination) {
            this.transit_destination = transit_destination;
        }

        public long getLoading_batches_id() {
            return loading_batches_id;
        }

        public void setLoading_batches_id(long loading_batches_id) {
            this.loading_batches_id = loading_batches_id;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public int getIs_unusual() {
            return is_unusual;
        }

        public void setIs_unusual(int is_unusual) {
            this.is_unusual = is_unusual;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getOperation_user_uuid() {
            return operation_user_uuid;
        }

        public void setOperation_user_uuid(Object operation_user_uuid) {
            this.operation_user_uuid = operation_user_uuid;
        }

        public Object getReceipts_delivery_fee() {
            return receipts_delivery_fee;
        }

        public void setReceipts_delivery_fee(Object receipts_delivery_fee) {
            this.receipts_delivery_fee = receipts_delivery_fee;
        }

        public Object getReceipts_total_freight() {
            return receipts_total_freight;
        }

        public void setReceipts_total_freight(Object receipts_total_freight) {
            this.receipts_total_freight = receipts_total_freight;
        }

        public Object getReceipts_collection_fee() {
            return receipts_collection_fee;
        }

        public void setReceipts_collection_fee(Object receipts_collection_fee) {
            this.receipts_collection_fee = receipts_collection_fee;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getInvoicedate() {
            return invoicedate;
        }

        public void setInvoicedate(String invoicedate) {
            this.invoicedate = invoicedate;
        }

        public int getBilling_fee_uuid() {
            return billing_fee_uuid;
        }

        public void setBilling_fee_uuid(int billing_fee_uuid) {
            this.billing_fee_uuid = billing_fee_uuid;
        }

        public int getDeclared_value() {
            return declared_value;
        }

        public void setDeclared_value(int declared_value) {
            this.declared_value = declared_value;
        }

        public int getValuation_fee() {
            return valuation_fee;
        }

        public void setValuation_fee(int valuation_fee) {
            this.valuation_fee = valuation_fee;
        }

        public int getDelivery_fee() {
            return delivery_fee;
        }

        public void setDelivery_fee(int delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        public int getAdvance() {
            return advance;
        }

        public void setAdvance(int advance) {
            this.advance = advance;
        }

        public int getReceiving_fee() {
            return receiving_fee;
        }

        public void setReceiving_fee(int receiving_fee) {
            this.receiving_fee = receiving_fee;
        }

        public int getHandling_fee() {
            return handling_fee;
        }

        public void setHandling_fee(int handling_fee) {
            this.handling_fee = handling_fee;
        }

        public int getPacking_fee() {
            return packing_fee;
        }

        public void setPacking_fee(int packing_fee) {
            this.packing_fee = packing_fee;
        }

        public int getUpstair_fee() {
            return upstair_fee;
        }

        public void setUpstair_fee(int upstair_fee) {
            this.upstair_fee = upstair_fee;
        }

        public int getForklift_fee() {
            return forklift_fee;
        }

        public void setForklift_fee(int forklift_fee) {
            this.forklift_fee = forklift_fee;
        }

        public int getReturn_fee() {
            return return_fee;
        }

        public void setReturn_fee(int return_fee) {
            this.return_fee = return_fee;
        }

        public int getUnder_charge_fee() {
            return under_charge_fee;
        }

        public void setUnder_charge_fee(int under_charge_fee) {
            this.under_charge_fee = under_charge_fee;
        }

        public int getWarehousing_fee() {
            return warehousing_fee;
        }

        public void setWarehousing_fee(int warehousing_fee) {
            this.warehousing_fee = warehousing_fee;
        }

        public int getCollection_fee() {
            return collection_fee;
        }

        public void setCollection_fee(int collection_fee) {
            this.collection_fee = collection_fee;
        }

        public int getFreight() {
            return freight;
        }

        public void setFreight(int freight) {
            this.freight = freight;
        }

        public int getTotal_freight() {
            return total_freight;
        }

        public void setTotal_freight(int total_freight) {
            this.total_freight = total_freight;
        }

        public String getTotal_freight_receipts() {
            return total_freight_receipts;
        }

        public void setTotal_freight_receipts(String total_freight_receipts) {
            this.total_freight_receipts = total_freight_receipts;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public int getCash_payment() {
            return cash_payment;
        }

        public void setCash_payment(int cash_payment) {
            this.cash_payment = cash_payment;
        }

        public int getCollect_payment() {
            return collect_payment;
        }

        public void setCollect_payment(int collect_payment) {
            this.collect_payment = collect_payment;
        }

        public int getDefault_of_payment() {
            return default_of_payment;
        }

        public void setDefault_of_payment(int default_of_payment) {
            this.default_of_payment = default_of_payment;
        }

        public int getBack_payment() {
            return back_payment;
        }

        public void setBack_payment(int back_payment) {
            this.back_payment = back_payment;
        }

        public int getMonthly_payment() {
            return monthly_payment;
        }

        public void setMonthly_payment(int monthly_payment) {
            this.monthly_payment = monthly_payment;
        }

        public int getPayment_deduction() {
            return payment_deduction;
        }

        public void setPayment_deduction(int payment_deduction) {
            this.payment_deduction = payment_deduction;
        }

        public String getStartStation() {
            return startStation;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
        }

        public String getStartPoint_phone() {
            return startPoint_phone;
        }

        public void setStartPoint_phone(String startPoint_phone) {
            this.startPoint_phone = startPoint_phone;
        }

        public Object getStartPoint_addr() {
            return startPoint_addr;
        }

        public void setStartPoint_addr(Object startPoint_addr) {
            this.startPoint_addr = startPoint_addr;
        }

        public Object getPoint_owner_phone_o() {
            return point_owner_phone_o;
        }

        public void setPoint_owner_phone_o(Object point_owner_phone_o) {
            this.point_owner_phone_o = point_owner_phone_o;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public Object getPoint_addr() {
            return point_addr;
        }

        public void setPoint_addr(Object point_addr) {
            this.point_addr = point_addr;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public Object getBatch_number() {
            return batch_number;
        }

        public void setBatch_number(Object batch_number) {
            this.batch_number = batch_number;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public Object getLogistics_notice() {
            return logistics_notice;
        }

        public void setLogistics_notice(Object logistics_notice) {
            this.logistics_notice = logistics_notice;
        }

        public Object getOperation_user_name() {
            return operation_user_name;
        }

        public void setOperation_user_name(Object operation_user_name) {
            this.operation_user_name = operation_user_name;
        }

        public Object getBilling_remark() {
            return billing_remark;
        }

        public void setBilling_remark(Object billing_remark) {
            this.billing_remark = billing_remark;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }
    }
}
