package com.inventory.system.InventorySystem.controllers.brand.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* brand detail Controller*/

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/brand")
    public BrandDetail addBrand(@RequestBody BrandDetail brandDetail) {
        return brandService.addBrand(brandDetail);
    }

    @GetMapping("/brand")
    public List<BrandDetail> getBrand() {
        return brandService.getBrand();
    }

    @GetMapping("/brand/{brandId}")
    public BrandDetail getBrandById(@PathVariable int brandId) {
        return brandService.getBrandById(brandId);
    }

    @DeleteMapping("/brand/{brandId}")
    public ResponseEntity<?> deleteBrandById(@PathVariable int brandId) {
        brandService.deleteBrand(brandId);
        return new ResponseEntity<>((new ApiResponse("Brand Deleted", brandId)), HttpStatus.FOUND);
    }
}


