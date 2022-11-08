package com.example.service;

import com.example.entity.Commodity;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.repository.CommodityRepository;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommodityRepository commodityRepository;

    public Order createOrder(Order order){
        order.setStatus(1);
        order.setCreateTime(new Date());
        List<OrderItem> orderItemList= order.getOrderItemList();
        List<Long> commodityIds=orderItemList.stream().map(item->{return item.getCommodityId();}).collect(Collectors.toList());
        List<Commodity> commodityList = commodityRepository.findAllById(commodityIds);
        List<BigDecimal> amountList =  orderItemList.stream().map(item->{
            Commodity commodity=commodityList.stream().filter(goods -> goods.getId()==item.getCommodityId()).collect(Collectors.toList()).get(0);

            return commodity.getPrice().multiply(new BigDecimal(item.getCount()));
        }).collect(Collectors.toList());
        final BigDecimal[] amount = {new BigDecimal("0")};

        amountList.stream().forEach(item->{
            amount[0] = amount[0].add(item);
            System.out.println("amount:"+ amount[0]);
        });
        order.setAmount(amount[0]);
        Order newOrder=orderRepository.save(order);
        return newOrder;
    }
}
