package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.dto.ItemDto;
import com.inventory.system.InventorySystem.dto.ItemSizeDto;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.services.ItemService;
import org.apache.log4j.Logger;
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

    /* Item Controller */
    @GetMapping("/item")
    public List<ItemDto> getItem() {
        logger.info("Calling getItem method from controller");
        return itemService.getItem();
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable int itemId) {
        logger.info("Calling getItemById method from controller");
        ItemDto item = itemService.getItemById(itemId);
        return new ResponseEntity<>(item, HttpStatus.FOUND);
    }

    @GetMapping("/item/size/")
    public List<ItemSizeDto> getAllItemSize() {
        logger.info("Calling getAllItemSize method from controller");
        return itemService.getAllItemSize();
    }

    @GetMapping("/item/size/{itemId}")
    public List<ItemSizeDto> getItemSizeById(@PathVariable int itemId) {
        logger.info("Calling getItemSizeById method from controller");
        return itemService.getItemSizeById(itemId);
    }

    @PostMapping("/item/")
    public ItemDto addItem(@Valid @RequestBody ItemDto item) {
        logger.info("Calling addItem method from controller");
        return itemService.addItem(item);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> deleteItemById(@PathVariable int itemId) {
        logger.info("Calling deleteItemById method from controller");
        itemService.deleteItemById(itemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted successfully ", itemId), HttpStatus.FOUND);
    }
}