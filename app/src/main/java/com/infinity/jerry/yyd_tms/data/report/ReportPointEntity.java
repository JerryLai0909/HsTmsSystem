package com.infinity.jerry.yyd_tms.data.report;

import java.util.List;

/**
 * Created by jerry on 2019/5/28.
 * You are good enough to do everthing
 */
public class ReportPointEntity {



    private TotalInfoBean totalInfo;
    private List<RankInfoBean> rankInfo;

    public TotalInfoBean getTotalInfo() {
        return totalInfo;
    }

    public void setTotalInfo(TotalInfoBean totalInfo) {
        this.totalInfo = totalInfo;
    }

    public List<RankInfoBean> getRankInfo() {
        return rankInfo;
    }

    public void setRankInfo(List<RankInfoBean> rankInfo) {
        this.rankInfo = rankInfo;
    }

    public static class TotalInfoBean {

        private int quantityTotal;
        private int billCountTotal;
        private int freightTotal;
        private int netFreightTotal;
        private int collectionTotal;

        public int getQuantityTotal() {
            return quantityTotal;
        }

        public void setQuantityTotal(int quantityTotal) {
            this.quantityTotal = quantityTotal;
        }

        public int getBillCountTotal() {
            return billCountTotal;
        }

        public void setBillCountTotal(int billCountTotal) {
            this.billCountTotal = billCountTotal;
        }

        public int getFreightTotal() {
            return freightTotal;
        }

        public void setFreightTotal(int freightTotal) {
            this.freightTotal = freightTotal;
        }

        public int getNetFreightTotal() {
            return netFreightTotal;
        }

        public void setNetFreightTotal(int netFreightTotal) {
            this.netFreightTotal = netFreightTotal;
        }

        public int getCollectionTotal() {
            return collectionTotal;
        }

        public void setCollectionTotal(int collectionTotal) {
            this.collectionTotal = collectionTotal;
        }
    }

    public static class RankInfoBean {
        @Override
        public String toString() {
            return "RankInfoBean{" +
                    "totalFreight=" + totalFreight +
                    ", netFreight=" + netFreight +
                    ", collection=" + collection +
                    ", billCount=" + billCount +
                    ", quantity=" + quantity +
                    ", pointRank=" + pointRank +
                    ", pointName='" + pointName + '\'' +
                    ", pointId='" + pointId + '\'' +
                    '}';
        }

        private int totalFreight;
        private int netFreight;
        private int collection;
        private int billCount;
        private int quantity;
        private int pointRank;
        private String pointName;
        private String pointId;

        public int getTotalFreight() {
            return totalFreight;
        }

        public void setTotalFreight(int totalFreight) {
            this.totalFreight = totalFreight;
        }

        public int getNetFreight() {
            return netFreight;
        }

        public void setNetFreight(int netFreight) {
            this.netFreight = netFreight;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public int getBillCount() {
            return billCount;
        }

        public void setBillCount(int billCount) {
            this.billCount = billCount;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPointRank() {
            return pointRank;
        }

        public void setPointRank(int pointRank) {
            this.pointRank = pointRank;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }
    }

    @Override
    public String toString() {
        return "ReportPointEntity{" +
                "totalInfo=" + totalInfo +
                ", rankInfo=" + rankInfo +
                '}';
    }
}
