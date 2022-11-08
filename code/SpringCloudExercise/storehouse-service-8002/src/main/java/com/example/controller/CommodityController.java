package com.example.controller;

import com.example.entity.Commodity;
import com.example.repository.CommodityRepository;
import com.example.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @PostMapping("/addCommodity")
    public ResponseEntity<Commodity> addCommodity(Commodity commodity){
        Commodity result=commodityService.addCommodity(commodity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/addMultipleCommodity")
    public ResponseEntity<List<Commodity>> addCommodityList(List<Commodity> commodities){
        List<Commodity> CommodityList=commodityService.addMultipleCommodity(commodities);
        return new ResponseEntity<>(CommodityList, HttpStatus.OK);
    }
    @GetMapping("/CommoditiesMessage")
    public ResponseEntity<List<Commodity>> getCommodityMessage(){
        List<Commodity> commodityList=commodityService.getAllCommodity();
        return new ResponseEntity<>(commodityList,HttpStatus.OK);
    }


}
