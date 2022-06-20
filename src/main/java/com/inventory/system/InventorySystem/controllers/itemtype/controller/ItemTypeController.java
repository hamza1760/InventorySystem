package com.inventory.system.InventorySystem.controllers.itemtype.controller;


import com.inventory.system.InventorySystem.api.response.ApiResponseItemType;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import com.inventory.system.InventorySystem.services.ProductTypeService;
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
    private ProductTypeService productTypeService;

    @PostMapping("/itemtype/")
    public ItemType addItemType(@RequestBody ItemType itemType){

        return itemTypeService.addItemType(itemType);



    }

    @GetMapping("/itemtype")
    public List<ItemType> getItemType(){
        return itemTypeService.getItemType();

    }

    @GetMapping("/itemtype/{itemTypeId}")
    public ItemType getItemTypeById(@PathVariable int itemTypeId){
        return itemTypeService.getItemTypeById(itemTypeId);
    }

    @DeleteMapping("/itemtype/{itemTypeId}")
    public ResponseEntity<?> deleteitemtypeById(@PathVariable int itemTypeId){
        itemTypeService.deleteItemType(itemTypeId);
        return new ResponseEntity<>((new ApiResponseItemType("ItemType Deleted",itemTypeId)), HttpStatus.FOUND);
    }
}
