package com.inventory.system.InventorySystem.controllers.item.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;
import com.inventory.system.InventorySystem.services.ItemService;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {

    static Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemTypeService itemTypeService;


    /* Item Controller */
    @GetMapping("/item")
    public List<Item> getItem() {
        logger.info("calling getItem method from controller");
        return itemService.getItem();
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable int itemId) {
        logger.info("calling getItemById method from controller");
        Item item = itemService.getItemById(itemId);
        return new ResponseEntity<>(item, HttpStatus.FOUND);
    }

    @GetMapping("/item/size/")
    public List<ItemSize> getAllItemSize() {
        logger.info("calling getAllItemSize method from controller");
        return itemService.getAllItemSize();
    }

    @GetMapping("/item/size/{itemId}")
    public List<ItemSize> getItemSizeById(@PathVariable int itemId) {
        logger.info("calling getItemSizeById method from controller");
        return itemService.getItemSizeById(itemId);
    }

    @PostMapping("/item/")
    public Item addItem(@Valid @RequestBody Item item) {
        logger.info("calling addItem method from controller");
        return itemService.addItem(item);
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<?> updateItem(@RequestBody Item item, @PathVariable int itemId) {
        logger.info("calling updateItem method from controller");
        Item updatedItem = itemService.updateItem(item, itemId);
        return new ResponseEntity<>(updatedItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> deleteItemById(@PathVariable int itemId) {
        logger.info("calling deleteItemById method from controller");
        itemService.deleteItemById(itemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted successfully ", itemId), HttpStatus.FOUND);
    }
}