package com.example.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="ACCOUNT_BALANCE")
public class AccountBalance {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="BALANCE")
    private BigDecimal balance;
    @Column(name="USER_ID")
    private long userId;

    public AccountBalance() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        userId = userId;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "id=" + id +
                ", balance=" + balance +
                ", UserId=" + userId +
                '}';
    }
}
