package com.inventory.system.InventorySystem.controllers.product.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseProductDetail;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.services.BrandService;
import com.inventory.system.InventorySystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/* product detail Controller*/
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @PostMapping("/product/brand/{brandId}")
    public ProductDetail addProduct(@RequestBody ProductDetail productDetail,@PathVariable int brandId){

        /*adding product to database*/
        productService.addProduct(productDetail,brandId);



        /*mapping product to brand*/
        BrandDetail brand = brandService.getBrandById(brandId);
          brand.setProduct(productDetail);
         brandService.saveBrand(brand);

          return productDetail;


    }
    @GetMapping("/product")
    public List<ProductDetail> getProduct(){
        return productService.getProduct();


    }
    @GetMapping("/product/{productId}")
    public ProductDetail getProductById(@PathVariable int productId){
        return productService.getProductById(productId);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteproductById(@PathVariable int productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>((new ApiResponseProductDetail("Product Deleted",productId)), HttpStatus.FOUND);
    }

}
