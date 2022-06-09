package com.inventory.system.InventorySystem.controllers.inventory.controller;

import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* inventory detail Controller*/
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    /*mapping inventory and item*/
    @PostMapping("/{inventoryId}/inventory/item/{itemId}")
    public InventoryDetail addInventory(@PathVariable int inventoryId, @RequestBody InventoryDetail inventoryDetail, @PathVariable int itemId){

        /*setting inventory id*/
        inventoryDetail.setInventoryId(inventoryId);



        /*setting inventory to particular item*/
        Item item = itemService.getItemById(itemId);
        inventoryDetail.setItemToInventory(item);
        inventoryService.addInventory(inventoryId,inventoryDetail,itemId);

       /* mapping item to inventory*/
         inventoryDetail = inventoryService.getInventoryById(inventoryId);
        item.mapInventorytoItem(inventoryDetail);

        return inventoryDetail;


    }

    @GetMapping("/inventory")
    public List<InventoryDetail> getInventory(){
        return inventoryService.getInventory();
    }


}
