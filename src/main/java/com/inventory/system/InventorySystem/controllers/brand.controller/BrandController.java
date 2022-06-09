package com.inventory.system.InventorySystem.controllers.brand.controller;

import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/* brand detail Controller*/

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/brand")
    public BrandDetail addBrand(@RequestBody BrandDetail brandDetail){

        return brandService.addBrand(brandDetail);
    }

    @GetMapping("/brand")
    public List<BrandDetail> getBrand(){
        return brandService.getBrand();
    }



}
