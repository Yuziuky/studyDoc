package com.example.controller;

import com.example.entity.AccountBalance;
import com.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/addBalance")
    public ResponseEntity<AccountBalance> increaseBalance(long userId, BigDecimal money) throws Exception {
        AccountBalance accountBalance=paymentService.addBalance(userId,money);
        return new ResponseEntity<>(accountBalance, HttpStatus.OK);
    }

    @RequestMapping("/decreaseBalance")
    public ResponseEntity<AccountBalance> decreaseBalance(long userId, BigDecimal money) throws Exception {
        AccountBalance accountBalance=paymentService.reduceBalance(userId,money);
        return new ResponseEntity<>(accountBalance, HttpStatus.OK);
    }
}
