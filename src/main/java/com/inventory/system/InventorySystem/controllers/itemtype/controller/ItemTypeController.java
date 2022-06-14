package com.inventory.system.InventorySystem.controllers.itemtype.controller;


import com.inventory.system.InventorySystem.api.response.ApiResponseItemType;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import com.inventory.system.InventorySystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/* item type Controller*/
@RestController
public class ItemTypeController {

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private ProductService productService;

    @PostMapping("/itemtype/product/{productId}")
    public ItemType addItemType(@RequestBody ItemType itemType, @PathVariable int productId){

        /*adding itemtype to database*/
        itemTypeService.addItemType(itemType,productId);

        /*mapping itemtype to products*/
        ProductDetail product = productService.getProductById(productId);
        product.setItemType(itemType);
        productService.saveProduct(product);

        return itemType;



    }

    @GetMapping("/itemtype")
    public List<ItemType> getItemType(){
        return itemTypeService.getItemType();

    }

    @GetMapping("/itemtype/{itemTypeId}")
    public ItemType getItemtype(@PathVariable int itemTypeId){
        return itemTypeService.getItemTypeById(itemTypeId);
    }

    @DeleteMapping("/itemtype/{itemTypeId}")
    public ResponseEntity<?> deleteitemtypeById(@PathVariable int itemTypeId){
        itemTypeService.deleteItemType(itemTypeId);
        return new ResponseEntity<>((new ApiResponseItemType("ItemType Deleted",itemTypeId)), HttpStatus.FOUND);
    }
}
