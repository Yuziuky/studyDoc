package com.example.repository;


import com.example.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByCommodityId(long commodityId) throws Exception;

    List<Inventory> findByCommodityIdIn(List<Long> commodityIdList) throws Exception;
}
