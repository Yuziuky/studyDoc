package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name="INVENTORY")
public class Inventory {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="COUNT")
    private int count;
    @Column(name="COMMODITY_ID")
    private long commodityId;

    public Inventory() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", count=" + count +
                ", commodityId=" + commodityId +
                '}';
    }
}
