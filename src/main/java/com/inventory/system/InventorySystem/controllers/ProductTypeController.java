package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* product type Controller*/
@RestController
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping("/product/")
    public ProductType addProductType(@RequestBody ProductType productType) {
        return productTypeService.addProductType(productType);
    }

    @GetMapping("/product")
    public List<ProductType> getProductType() {
        return productTypeService.getProductType();
    }

    @GetMapping("/product/{productTypeId}")
    public ProductType getProductTypeById(@PathVariable int productTypeId) {
        return productTypeService.getProductTypeById(productTypeId);
    }

    @DeleteMapping("/product/{productTypeId}")
    public ResponseEntity<?> deleteProductType(@PathVariable int productTypeId) {
        productTypeService.deleteProductType(productTypeId);
        return new ResponseEntity<>((new ApiResponse("Product Type Deleted", productTypeId)), HttpStatus.FOUND);
    }
}
