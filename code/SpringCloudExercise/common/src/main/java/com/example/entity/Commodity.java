package com.example.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="COMMODITY")
public class Commodity {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="COMMODITY_NAME")
    private String commodityName;
    @Column(name="PRICE")
    private BigDecimal price;
    @Column(name="DESCRIPTION")
    private String description;

    public Commodity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", commodityName='" + commodityName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
