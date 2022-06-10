package com.inventory.system.InventorySystem.controllers.item.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseItem;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;



    /* Item Controller */
    @GetMapping("/item")
    public List<Item> getItem() {
        return itemService.getItem();

    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable int itemId) {

        Item item = itemService.getItemById(itemId);
        logger.debug("error1");
        return new ResponseEntity<>(item, HttpStatus.FOUND);

    }

    @PostMapping("/item")
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);

    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<?> updateItem(@RequestBody Item item, @PathVariable int itemId) {
        Item updatedItem=  itemService.updateItem(item, itemId);
        return new ResponseEntity<>(updatedItem, HttpStatus.CREATED);

    }

    @DeleteMapping("/item/{itemId}")

    public ResponseEntity<ApiResponseItem> deleteItemById(@PathVariable int itemId) {

        itemService.deleteItemById(itemId);
        return new ResponseEntity<>(new ApiResponseItem("Item deleted successfully ", itemId), HttpStatus.FOUND);

    }

    /* map inventory to item */


}