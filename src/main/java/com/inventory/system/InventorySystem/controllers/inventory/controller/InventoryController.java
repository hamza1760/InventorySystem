package com.inventory.system.InventorySystem.controllers.inventory.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseInventory;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.ItemService;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/* inventory detail Controller*/
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    /*mapping inventory and item*/
    @PostMapping("/inventory/item/{itemId}")
    public InventoryDetail addInventory(@RequestBody InventoryDetail inventoryDetail, @PathVariable int itemId){

        /*setting inventory to particular item*/
        Item item = itemService.getItemById(itemId);
        inventoryDetail.setItemToInventory(item);
        inventoryService.addInventory(inventoryDetail,itemId);
        return inventoryDetail;


    }

    @GetMapping("/inventory")
    public List<InventoryDetail> getInventory(){
        return inventoryService.getInventory();
    }


    @GetMapping("/inventory/{inventoryId}")
    public InventoryDetail getInventoryById(@PathVariable int inventoryId){
        return inventoryService.getInventoryById(inventoryId);
    }

    @PutMapping("/inventory/{inventoryId}")
    public InventoryDetail setItemQuantityInAllWarehouses(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId){
        InventoryDetail updatedItemQuantity = inventoryService.setItemQuantityInAllWarehouses(inventoryDetail,inventoryId);
        return updatedItemQuantity;
    }


    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> deleteinventoryById(@PathVariable int inventoryId){
        inventoryService.deleteInventory(inventoryId);
        return new ResponseEntity<>((new ApiResponseInventory("Inventory Deleted",inventoryId)), HttpStatus.FOUND);
    }


}
