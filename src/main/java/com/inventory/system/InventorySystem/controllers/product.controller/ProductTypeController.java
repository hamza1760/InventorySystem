package com.inventory.system.InventorySystem.controllers.product.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseProductType;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.services.BrandService;
import com.inventory.system.InventorySystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/* product type Controller*/
@RestController
public class ProductTypeController {


    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @PostMapping("/product/brand/{brandId}")
    public ProductType addProductType(@RequestBody ProductType productDetail, @PathVariable int brandId){

        /*adding product to database*//*
        productService.addProductType(productDetail,brandId);



        *//*mapping product to brand*//*
        BrandDetail brand = brandService.getBrandById(brandId);
          brand.setProductType(productDetail);
         brandService.saveBrand(brand);

          return productDetail;*/
        return null;


    }
    @GetMapping("/product")
    public List<ProductType> getProductType(){
        return productService.getProductType();


    }
    @GetMapping("/product/{productTypeId}")
    public ProductType getProductById(@PathVariable int productId){
        return productService.getProductTypeById(productId);
    }

    @DeleteMapping("/product/{productTypeId}")
    public ResponseEntity<?> deleteProductType(@PathVariable int productTypeId){

        productService.deleteProductType(productTypeId);
        return new ResponseEntity<>((new ApiResponseProductType("Product Type Deleted",productTypeId)), HttpStatus.FOUND);
    }

}
