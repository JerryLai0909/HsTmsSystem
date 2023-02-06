/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jerry on 2017/6/19.
 */

public class BillingDrawMain implements Serializable, Parcelable {

    private String barCode;

    private String bar_code;

    @Override
    public String toString() {
        return "BillingDrawMain{" +
                "barCode='" + barCode + '\'' +
                ", bar_code='" + bar_code + '\'' +
                ", company_name='" + company_name + '\'' +
                ", isChoose=" + isChoose +
                ", uuid=" + uuid +
                ", owner=" + owner +
                ", is_unusual=" + is_unusual +
                ", waybill_number='" + waybill_number + '\'' +
                ", initial_station_id=" + initial_station_id +
                ", initial_station_name='" + initial_station_name + '\'' +
                ", terminal_station_name='" + terminal_station_name + '\'' +
                ", terminal_station_id=" + terminal_station_id +
                ", invoice_date='" + invoice_date + '\'' +
                ", consigner_uuid=" + consigner_uuid +
                ", consigner='" + consigner + '\'' +
                ", consigner_phone='" + consigner_phone + '\'' +
                ", consigner_addr='" + consigner_addr + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consignee_phone='" + consignee_phone + '\'' +
                ", consignee_addr='" + consignee_addr + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", quantity=" + quantity +
                ", packaging='" + packaging + '\'' +
                ", weight='" + weight + '\'' +
                ", volume='" + volume + '\'' +
                ", delivery_method='" + delivery_method + '\'' +
                ", number_of_copies=" + number_of_copies +
                ", drawer_uuid=" + drawer_uuid +
                ", drawer_name='" + drawer_name + '\'' +
                ", invoice_status=" + invoice_status +
                ", article_number='" + article_number + '\'' +
                ", transit_destination='" + transit_destination + '\'' +
                ", loading_batches_id=" + loading_batches_id +
                ", source='" + source + '\'' +
                ", remarks='" + remarks + '\'' +
                ", billing_uuid=" + billing_uuid +
                ", declared_value=" + declared_value +
                ", valuation_fee=" + valuation_fee +
                ", delivery_fee=" + delivery_fee +
                ", advance=" + advance +
                ", receiving_fee=" + receiving_fee +
                ", handling_fee=" + handling_fee +
                ", packing_fee=" + packing_fee +
                ", upstair_fee=" + upstair_fee +
                ", forklift_fee=" + forklift_fee +
                ", return_fee=" + return_fee +
                ", under_charge_fee=" + under_charge_fee +
                ", warehousing_fee=" + warehousing_fee +
                ", collection_fee=" + collection_fee +
                ", freight=" + freight +
                ", total_freight=" + total_freight +
                ", total_freight_receipts='" + total_freight_receipts + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", cash_payment=" + cash_payment +
                ", collect_payment=" + collect_payment +
                ", default_of_payment=" + default_of_payment +
                ", back_payment=" + back_payment +
                ", monthly_payment=" + monthly_payment +
                ", payment_deduction=" + payment_deduction +
                ", startStation='" + startStation + '\'' +
                ", endStation='" + endStation + '\'' +
                ", user_name='" + user_name + '\'' +
                ", totalBill=" + totalBill +
                ", invoicedate='" + invoicedate + '\'' +
                ", point_owner_phone_o='" + point_owner_phone_o + '\'' +
                ", batch_number='" + batch_number + '\'' +
                ", plate_number='" + plate_number + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_phone='" + driver_phone + '\'' +
                ", billing_fee_uuid=" + billing_fee_uuid +
                ", logistics_notice='" + logistics_notice + '\'' +
                ", loading_time='" + loading_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", operation_user_name='" + operation_user_name + '\'' +
                ", operation_user_uuid=" + operation_user_uuid +
                ", input_article_number='" + input_article_number + '\'' +
                ", startPoint_phone='" + startPoint_phone + '\'' +
                '}';
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private String company_name;
    private boolean isChoose = false;
    private Long uuid;
    private Long owner;
    private Integer is_unusual;
    private String waybill_number;
    private Long initial_station_id;
    private String initial_station_name;
    private String terminal_station_name;
    private Long terminal_station_id;
    private String invoice_date;
    private Long consigner_uuid;
    private String consigner;
    private String consigner_phone;
    private String consigner_addr;
    private String consignee;
    private String consignee_phone;
    private String consignee_addr;
    private String goods_name;
    private Long quantity;
    private String packaging;
    private String weight;
    private String volume;
    private String delivery_method;
    private Long number_of_copies;
    private Long drawer_uuid;
    private String drawer_name;
    private Long invoice_status;
    private String article_number;
    private String transit_destination;
    private Long loading_batches_id;
    private String source;
    private String remarks;

    private Long billing_uuid;
    private Double declared_value;
    private Double valuation_fee;
    private Double delivery_fee;
    private Double advance;
    private Double receiving_fee;
    private Double handling_fee;
    private Double packing_fee;
    private Double upstair_fee;
    private Double forklift_fee;
    private Double return_fee;
    private Double under_charge_fee;
    private Double warehousing_fee;
    private Double collection_fee;
    private Double freight;
    private Double total_freight;
    private String total_freight_receipts;
    private String payment_method;
    private Double cash_payment;
    private Double collect_payment;
    private Double default_of_payment;
    private Double back_payment;
    private Double monthly_payment;
    private Double payment_deduction;

    private String startStation;
    private String endStation;
    private String user_name;

    private Integer totalBill;
    private String invoicedate;
    private String point_owner_phone_o;

    private String batch_number;
    private String plate_number;
    private String driver_name;
    private String driver_phone;
    private Long billing_fee_uuid;

    private String logistics_notice;

    private String loading_time;

    private String update_time;

    private String operation_user_name;

    private Long operation_user_uuid;

    private String input_article_number;

    public String getInput_article_number() {
        return input_article_number;
    }

    public void setInput_article_number(String input_article_number) {
        this.input_article_number = input_article_number;
    }

    public String getOperation_user_name() {
        return operation_user_name;
    }

    public void setOperation_user_name(String operation_user_name) {
        this.operation_user_name = operation_user_name;
    }

    public Long getOperation_user_uuid() {
        return operation_user_uuid;
    }

    public void setOperation_user_uuid(Long operation_user_uuid) {
        this.operation_user_uuid = operation_user_uuid;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getLoading_time() {
        return loading_time;
    }

    public void setLoading_time(String loading_time) {
        this.loading_time = loading_time;
    }

    public String getLogistics_notice() {
        return logistics_notice;
    }

    public void setLogistics_notice(String logistics_notice) {
        this.logistics_notice = logistics_notice;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    private String startPoint_phone;


    public String getStartPoint_phone() {
        return startPoint_phone;
    }

    public void setStartPoint_phone(String startPoint_phone) {
        this.startPoint_phone = startPoint_phone;
    }

    public Long getBilling_fee_uuid() {
        return billing_fee_uuid;
    }

    public void setBilling_fee_uuid(Long billing_fee_uuid) {
        this.billing_fee_uuid = billing_fee_uuid;
    }

    public static Creator<BillingDrawMain> getCREATOR() {
        return CREATOR;
    }

    public String getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(String batch_number) {
        this.batch_number = batch_number;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public Integer getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Integer totalBill) {
        this.totalBill = totalBill;
    }

    public String getPoint_owner_phone_o() {
        return point_owner_phone_o;
    }

    public void setPoint_owner_phone_o(String point_owner_phone_o) {
        this.point_owner_phone_o = point_owner_phone_o;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Integer getIs_unusual() {
        return is_unusual;
    }

    public void setIs_unusual(Integer is_unusual) {
        this.is_unusual = is_unusual;
    }

    public String getInitial_station_name() {
        return initial_station_name;
    }

    public void setInitial_station_name(String initial_station_name) {
        this.initial_station_name = initial_station_name;
    }

    public String getTerminal_station_name() {
        return terminal_station_name;
    }

    public void setTerminal_station_name(String terminal_station_name) {
        this.terminal_station_name = terminal_station_name;
    }

    public String getDrawer_name() {
        return drawer_name;
    }

    public void setDrawer_name(String drawer_name) {
        this.drawer_name = drawer_name;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public Long getInitial_station_id() {
        return initial_station_id;
    }

    public void setInitial_station_id(Long initial_station_id) {
        this.initial_station_id = initial_station_id;
    }

    public Long getTerminal_station_id() {
        return terminal_station_id;
    }

    public void setTerminal_station_id(Long terminal_station_id) {
        this.terminal_station_id = terminal_station_id;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public Long getConsigner_uuid() {
        return consigner_uuid;
    }

    public void setConsigner_uuid(Long consigner_uuid) {
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

    public String getConsigner_addr() {
        return consigner_addr;
    }

    public void setConsigner_addr(String consigner_addr) {
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

    public String getConsignee_addr() {
        return consignee_addr;
    }

    public void setConsignee_addr(String consignee_addr) {
        this.consignee_addr = consignee_addr;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public Long getNumber_of_copies() {
        return number_of_copies;
    }

    public void setNumber_of_copies(Long number_of_copies) {
        this.number_of_copies = number_of_copies;
    }

    public Long getDrawer_uuid() {
        return drawer_uuid;
    }

    public void setDrawer_uuid(Long drawer_uuid) {
        this.drawer_uuid = drawer_uuid;
    }

    public Long getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(Long invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getArticle_number() {
        return article_number;
    }

    public void setArticle_number(String article_number) {
        this.article_number = article_number;
    }

    public String getTransit_destination() {
        return transit_destination;
    }

    public void setTransit_destination(String transit_destination) {
        this.transit_destination = transit_destination;
    }

    public Long getLoading_batches_id() {
        return loading_batches_id;
    }

    public void setLoading_batches_id(Long loading_batches_id) {
        this.loading_batches_id = loading_batches_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getBilling_uuid() {
        return billing_uuid;
    }

    public void setBilling_uuid(Long billing_uuid) {
        this.billing_uuid = billing_uuid;
    }

    public Double getDeclared_value() {
        return declared_value;
    }

    public void setDeclared_value(Double declared_value) {
        this.declared_value = declared_value;
    }

    public Double getValuation_fee() {
        return valuation_fee;
    }

    public void setValuation_fee(Double valuation_fee) {
        this.valuation_fee = valuation_fee;
    }

    public Double getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Double delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getReceiving_fee() {
        return receiving_fee;
    }

    public void setReceiving_fee(Double receiving_fee) {
        this.receiving_fee = receiving_fee;
    }

    public Double getHandling_fee() {
        return handling_fee;
    }

    public void setHandling_fee(Double handling_fee) {
        this.handling_fee = handling_fee;
    }

    public Double getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(Double packing_fee) {
        this.packing_fee = packing_fee;
    }

    public Double getUpstair_fee() {
        return upstair_fee;
    }

    public void setUpstair_fee(Double upstair_fee) {
        this.upstair_fee = upstair_fee;
    }

    public Double getForklift_fee() {
        return forklift_fee;
    }

    public void setForklift_fee(Double forklift_fee) {
        this.forklift_fee = forklift_fee;
    }

    public Double getReturn_fee() {
        return return_fee;
    }

    public void setReturn_fee(Double return_fee) {
        this.return_fee = return_fee;
    }

    public Double getUnder_charge_fee() {
        return under_charge_fee;
    }

    public void setUnder_charge_fee(Double under_charge_fee) {
        this.under_charge_fee = under_charge_fee;
    }

    public Double getWarehousing_fee() {
        return warehousing_fee;
    }

    public void setWarehousing_fee(Double warehousing_fee) {
        this.warehousing_fee = warehousing_fee;
    }

    public Double getCollection_fee() {
        return collection_fee;
    }

    public void setCollection_fee(Double collection_fee) {
        this.collection_fee = collection_fee;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getTotal_freight() {
        return total_freight;
    }

    public void setTotal_freight(Double total_freight) {
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

    public Double getCash_payment() {
        return cash_payment;
    }

    public void setCash_payment(Double cash_payment) {
        this.cash_payment = cash_payment;
    }

    public Double getCollect_payment() {
        return collect_payment;
    }

    public void setCollect_payment(Double collect_payment) {
        this.collect_payment = collect_payment;
    }

    public Double getDefault_of_payment() {
        return default_of_payment;
    }

    public void setDefault_of_payment(Double default_of_payment) {
        this.default_of_payment = default_of_payment;
    }

    public Double getBack_payment() {
        return back_payment;
    }

    public void setBack_payment(Double back_payment) {
        this.back_payment = back_payment;
    }

    public Double getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(Double monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public Double getPayment_deduction() {
        return payment_deduction;
    }

    public void setPayment_deduction(Double payment_deduction) {
        this.payment_deduction = payment_deduction;
    }


    public BillingDrawMain() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
        dest.writeValue(this.uuid);
        dest.writeValue(this.owner);
        dest.writeValue(this.is_unusual);
        dest.writeString(this.waybill_number);
        dest.writeValue(this.initial_station_id);
        dest.writeString(this.initial_station_name);
        dest.writeString(this.terminal_station_name);
        dest.writeValue(this.terminal_station_id);
        dest.writeString(this.invoice_date);
        dest.writeValue(this.consigner_uuid);
        dest.writeString(this.consigner);
        dest.writeString(this.consigner_phone);
        dest.writeString(this.consigner_addr);
        dest.writeString(this.consignee);
        dest.writeString(this.consignee_phone);
        dest.writeString(this.consignee_addr);
        dest.writeString(this.goods_name);
        dest.writeValue(this.quantity);
        dest.writeString(this.packaging);
        dest.writeString(this.weight);
        dest.writeString(this.volume);
        dest.writeString(this.delivery_method);
        dest.writeValue(this.number_of_copies);
        dest.writeValue(this.drawer_uuid);
        dest.writeString(this.drawer_name);
        dest.writeValue(this.invoice_status);
        dest.writeString(this.article_number);
        dest.writeString(this.transit_destination);
        dest.writeValue(this.loading_batches_id);
        dest.writeString(this.source);
        dest.writeString(this.remarks);
        dest.writeValue(this.billing_uuid);
        dest.writeValue(this.declared_value);
        dest.writeValue(this.valuation_fee);
        dest.writeValue(this.delivery_fee);
        dest.writeValue(this.advance);
        dest.writeValue(this.receiving_fee);
        dest.writeValue(this.handling_fee);
        dest.writeValue(this.packing_fee);
        dest.writeValue(this.upstair_fee);
        dest.writeValue(this.forklift_fee);
        dest.writeValue(this.return_fee);
        dest.writeValue(this.under_charge_fee);
        dest.writeValue(this.warehousing_fee);
        dest.writeValue(this.collection_fee);
        dest.writeValue(this.freight);
        dest.writeValue(this.total_freight);
        dest.writeString(this.total_freight_receipts);
        dest.writeString(this.payment_method);
        dest.writeValue(this.cash_payment);
        dest.writeValue(this.collect_payment);
        dest.writeValue(this.default_of_payment);
        dest.writeValue(this.back_payment);
        dest.writeValue(this.monthly_payment);
        dest.writeValue(this.payment_deduction);
        dest.writeString(this.startStation);
        dest.writeString(this.endStation);
        dest.writeString(this.user_name);
        dest.writeValue(this.totalBill);
        dest.writeString(this.invoicedate);
        dest.writeString(this.point_owner_phone_o);
        dest.writeString(this.batch_number);
        dest.writeString(this.plate_number);
        dest.writeString(this.driver_name);
        dest.writeString(this.driver_phone);
        dest.writeValue(this.billing_fee_uuid);
        dest.writeString(this.logistics_notice);
        dest.writeString(this.loading_time);
        dest.writeString(this.update_time);
        dest.writeString(this.operation_user_name);
        dest.writeValue(this.operation_user_uuid);
        dest.writeString(this.input_article_number);
        dest.writeString(this.startPoint_phone);
    }

    protected BillingDrawMain(Parcel in) {
        this.isChoose = in.readByte() != 0;
        this.uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.owner = (Long) in.readValue(Long.class.getClassLoader());
        this.is_unusual = (Integer) in.readValue(Integer.class.getClassLoader());
        this.waybill_number = in.readString();
        this.initial_station_id = (Long) in.readValue(Long.class.getClassLoader());
        this.initial_station_name = in.readString();
        this.terminal_station_name = in.readString();
        this.terminal_station_id = (Long) in.readValue(Long.class.getClassLoader());
        this.invoice_date = in.readString();
        this.consigner_uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.consigner = in.readString();
        this.consigner_phone = in.readString();
        this.consigner_addr = in.readString();
        this.consignee = in.readString();
        this.consignee_phone = in.readString();
        this.consignee_addr = in.readString();
        this.goods_name = in.readString();
        this.quantity = (Long) in.readValue(Long.class.getClassLoader());
        this.packaging = in.readString();
        this.weight = in.readString();
        this.volume = in.readString();
        this.delivery_method = in.readString();
        this.number_of_copies = (Long) in.readValue(Long.class.getClassLoader());
        this.drawer_uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.drawer_name = in.readString();
        this.invoice_status = (Long) in.readValue(Long.class.getClassLoader());
        this.article_number = in.readString();
        this.transit_destination = in.readString();
        this.loading_batches_id = (Long) in.readValue(Long.class.getClassLoader());
        this.source = in.readString();
        this.remarks = in.readString();
        this.billing_uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.declared_value = (Double) in.readValue(Double.class.getClassLoader());
        this.valuation_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.delivery_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.advance = (Double) in.readValue(Double.class.getClassLoader());
        this.receiving_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.handling_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.packing_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.upstair_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.forklift_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.return_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.under_charge_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.warehousing_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.collection_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.freight = (Double) in.readValue(Double.class.getClassLoader());
        this.total_freight = (Double) in.readValue(Double.class.getClassLoader());
        this.total_freight_receipts = in.readString();
        this.payment_method = in.readString();
        this.cash_payment = (Double) in.readValue(Double.class.getClassLoader());
        this.collect_payment = (Double) in.readValue(Double.class.getClassLoader());
        this.default_of_payment = (Double) in.readValue(Double.class.getClassLoader());
        this.back_payment = (Double) in.readValue(Double.class.getClassLoader());
        this.monthly_payment = (Double) in.readValue(Double.class.getClassLoader());
        this.payment_deduction = (Double) in.readValue(Double.class.getClassLoader());
        this.startStation = in.readString();
        this.endStation = in.readString();
        this.user_name = in.readString();
        this.totalBill = (Integer) in.readValue(Integer.class.getClassLoader());
        this.invoicedate = in.readString();
        this.point_owner_phone_o = in.readString();
        this.batch_number = in.readString();
        this.plate_number = in.readString();
        this.driver_name = in.readString();
        this.driver_phone = in.readString();
        this.billing_fee_uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.logistics_notice = in.readString();
        this.loading_time = in.readString();
        this.update_time = in.readString();
        this.operation_user_name = in.readString();
        this.operation_user_uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.input_article_number = in.readString();
        this.startPoint_phone = in.readString();
    }

    public static final Creator<BillingDrawMain> CREATOR = new Creator<BillingDrawMain>() {
        @Override
        public BillingDrawMain createFromParcel(Parcel source) {
            return new BillingDrawMain(source);
        }

        @Override
        public BillingDrawMain[] newArray(int size) {
            return new BillingDrawMain[size];
        }
    };
}
