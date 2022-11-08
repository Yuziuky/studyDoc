package com.example.service;

import com.example.entity.AccountBalance;
import com.example.repository.AccountBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    @Autowired
    private AccountBalanceRepository balanceRepository;

    public AccountBalance addBalance(long userId, BigDecimal money) throws Exception {
        AccountBalance accountBalance=balanceRepository.findByUserId(userId);
        accountBalance.setBalance(accountBalance.getBalance().add(money));
        AccountBalance result= balanceRepository.save(accountBalance);
        return result;
    }

    public AccountBalance reduceBalance(long userId, BigDecimal money) throws Exception {
        AccountBalance accountBalance=balanceRepository.findByUserId(userId);
        //余额不足处理
        int balance=accountBalance.getBalance().compareTo(money);
        if (balance == -1) return null;
        accountBalance.setBalance(accountBalance.getBalance().subtract(money));

        return balanceRepository.save(accountBalance);
    }
}
