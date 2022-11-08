package com.example.service;

import com.example.entity.Inventory;
import com.example.entity.VO.CommodityInventoryVO;
import com.example.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> increaseCommodity(List<Inventory> commodityInventoryList) throws Exception {
        List<Long> commodityIdList = commodityInventoryList.stream().map(c -> {
            return c.getCommodityId();
        }).collect(Collectors.toList());
        List<Inventory> inventoryList = inventoryRepository.findByCommodityIdIn(commodityIdList);
        List<Inventory> inventories = inventoryList.stream().map(inventory -> {
            Inventory commodityInventoryVO = commodityInventoryList.stream().filter(c -> c.getCommodityId() == inventory.getCommodityId()).collect(Collectors.toList()).get(0);
            inventory.setCount(inventory.getCount() + commodityInventoryVO.getCount());
            return inventory;
        }).collect(Collectors.toList());
        List<Inventory> result = inventoryRepository.saveAll(inventories);
        return result;
    }

    public List<Inventory> decreaseCommodity(List<CommodityInventoryVO> commodityInventoryList) throws Exception {
        List<Long> commodityIdList = commodityInventoryList.stream().map(c -> {
            return c.getCommodityId();
        }).collect(Collectors.toList());
        List<Inventory> inventoryList = inventoryRepository.findByCommodityIdIn(commodityIdList);
        List<Inventory> inventories = inventoryList.stream().map(inventory -> {
            CommodityInventoryVO commodityInventoryVO = commodityInventoryList.stream().filter(c -> c.getCommodityId() == inventory.getCommodityId()).collect(Collectors.toList()).get(0);
            inventory.setCount(inventory.getCount() - commodityInventoryVO.getCommodityNum());
            return inventory;
        }).collect(Collectors.toList());
        List<Inventory> result = inventoryRepository.saveAll(inventories);
        return null;
    }

    public List<Inventory> createInventory(List<Inventory> inventories) throws Exception{
        List<Inventory> inventoryList = inventoryRepository.saveAll(inventories);
        return inventoryList;
    }
}
