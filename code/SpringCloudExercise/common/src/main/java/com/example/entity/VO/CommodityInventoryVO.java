package com.example.entity.VO;

public class CommodityInventoryVO {
    private long commodityId;
    private int commodityNum;

    public CommodityInventoryVO() {
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public int getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(int commodityNum) {
        this.commodityNum = commodityNum;
    }

    @Override
    public String toString() {
        return "CommodityInventoryVO{" +
                "commodityId=" + commodityId +
                ", commodityNum=" + commodityNum +
                '}';
    }
}
