package com.example.controller;

import com.example.entity.Inventory;
import com.example.entity.VO.CommodityInventoryVO;
import com.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    //获取当前库存
    public ResponseEntity<Inventory> getCommodityInventory(Long CommodityId) {
        return null;
    }

    //TODO
    //vo没有作用，直接使用inventory
    //增加库存,返回当前库存值
    @PostMapping("/addCommodityNum")
    public ResponseEntity<List<Inventory>> increaseInventory(@RequestBody List<Inventory> commodityInventoryList) throws Exception {
        List<Inventory> inventoryList = inventoryService.increaseCommodity(commodityInventoryList);
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

    //减少库存，库存不够报错（不够报错操作放到购买环节--order上）
    @RequestMapping("/decreaseCommodityNum")
    public ResponseEntity<List<Inventory>> decreaseInventory(List<CommodityInventoryVO> commodityInventoryVOList) throws Exception {
        List<Inventory> inventoryList = inventoryService.decreaseCommodity(commodityInventoryVOList);
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

    @PostMapping("/newCommodityNum")
    public ResponseEntity<List<Inventory>> createInventory(@RequestBody List<Inventory> Inventories) throws Exception {
        System.out.println(Inventories.toString());
        List<Inventory> inventoryList = inventoryService.createInventory(Inventories);
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

}
