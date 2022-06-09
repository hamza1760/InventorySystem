package com.inventory.system.InventorySystem.controllers.warehouse.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseItem;
import com.inventory.system.InventorySystem.api.response.ApiResponseWarehouse;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.ItemService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private WarehouseService warehouseService;

    /* Warehouse Controller */

    @GetMapping("/warehouse")
    public List<Warehouse> getWarehouse() {

        return warehouseService.getWarehouse();

    }

    @PostMapping("/warehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {

        return warehouseService.addWarehouse(warehouse);
    }

    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable int warehouseId) {

        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity<>(new ApiResponseWarehouse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    /* Put item in warehouse */

    @PutMapping("warehouse/{warehouseId}/item/{itemId}")
    public ResponseEntity<?> addItemIntoWarehouse(@PathVariable int warehouseId, @PathVariable int itemId) {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        ItemDto itemDto = itemService.getItemById(itemId);
        Item item = itemService.itemDtoToItem(itemDto);
        warehouse.putItemIntoWareHouse(item);
        warehouseService.addWarehouse(warehouse);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);

    }

    /* delete item from warehouse */

    @DeleteMapping("warehouse/{warehouseId}/item/{itemId}")
    public ResponseEntity<?> deleteItemFromWarehouse(@PathVariable int warehouseId, @PathVariable int itemId) {

        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        ItemDto itemDto = itemService.getItemById(itemId);
        Item item = itemService.itemDtoToItem(itemDto);
        List<Item> itemList = warehouse.getItem();
        boolean tellME = itemList.contains(item);
        System.out.println(tellME);

        return new ResponseEntity<>(new ApiResponseItem("item from warehouse deleted succesfully", itemId),
                HttpStatus.FOUND);

    }
}
