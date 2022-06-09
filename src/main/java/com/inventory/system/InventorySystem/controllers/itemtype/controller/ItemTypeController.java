package com.inventory.system.InventorySystem.controllers.itemtype.controller;

import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/* item type Controller*/
@RestController
public class ItemTypeController {

    @Autowired
    private ItemTypeService itemTypeService;

    @PostMapping("/itemtype")
    public ItemType addItemType(@RequestBody ItemType itemType){

        return itemTypeService.addItemType(itemType);
    }

    @GetMapping("/itemtype")
    public List<ItemType> getItemType(){
        return itemTypeService.getItemType();

    }
}
