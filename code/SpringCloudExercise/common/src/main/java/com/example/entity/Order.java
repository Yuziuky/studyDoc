package com.example.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ORDERS")
public class Order {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="Amount")
    private BigDecimal amount;
    @Column(name="CREATE_TIME")
    private Date createTime;

    //1--未支付，2--
    @Column(name="STATUS")
    private int status;

    @OneToMany(mappedBy = "order",cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
    private List<OrderItem> orderItemList;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", status=" + status +
                ", orderItemList=" + orderItemList +
                '}';
    }
}
