package com.example.service;

import com.example.entity.Commodity;
import com.example.repository.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    public Commodity addCommodity(Commodity commodity){
        Commodity saveResult=commodityRepository.save(commodity);
        return saveResult;
    }

    public List<Commodity> addMultipleCommodity(List<Commodity> commodityList){
        commodityRepository.saveAll(commodityList);
        return null;
    }

    public List<Commodity> getAllCommodity(){
        return commodityRepository.findAll();
    }

    public boolean deleteCommodity(long id){
        return false;
    }
}
