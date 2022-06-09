package com.inventory.system.InventorySystem.controllers.product.controller;

import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/* product detail Controller*/
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ProductDetail addProduct(@RequestBody ProductDetail productDetail){

        return productService.addProduct(productDetail);
    }
    @GetMapping("/product")
    public List<ProductDetail> getProduct(){
        return productService.getProduct();
    }

}
