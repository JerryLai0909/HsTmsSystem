/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.report;

/**
 * Created by jerry on 2017/7/5.
 */

public class ReportEntity {
    private Long uuid;
    private String days;
    private Integer totalBill = 0;
    private Integer quantity = 0;
    private Double cash_payment = 0d;
    private Double collect_payment = 0d;
    private Double default_of_payment = 0d;
    private Double back_payment = 0d;
    private Double monthly_payment = 0d;
    private Double payment_deduction = 0d;
    private Double declared_value = 0d;
    private Double valuation_fee = 0d;
    private Double delivery_fee = 0d;
    private Double advance = 0d;
    private Double receiving_fee = 0d;
    private Double handling_fee = 0d;
    private Double packing_fee = 0d;
    private Double upstair_fee = 0d;
    private Double forklift_fee = 0d;
    private Double return_fee = 0d;
    private Double under_charge_fee = 0d;
    private Double warehousing_fee = 0d;
    private Double collection_fee = 0d;
    private Double freight = 0d;
    private Double total_freight = 0d;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Integer getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Integer totalBill) {
        this.totalBill = totalBill;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "ReportEntity{" +
                "uuid=" + uuid +
                ", days='" + days + '\'' +
                ", totalBill=" + totalBill +
                ", quantity=" + quantity +
                ", cash_payment=" + cash_payment +
                ", collect_payment=" + collect_payment +
                ", default_of_payment=" + default_of_payment +
                ", back_payment=" + back_payment +
                ", monthly_payment=" + monthly_payment +
                ", payment_deduction=" + payment_deduction +
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
                '}';
    }
}
