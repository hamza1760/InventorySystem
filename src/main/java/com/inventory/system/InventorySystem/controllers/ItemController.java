package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.api.response.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.services.*;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
public class ItemController {

    static Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    /* Item Controller */
    @PostMapping("/item/")
    public ItemDto addItem(@Valid @RequestBody ItemDto item) {
        logger.info("Calling addItem method from controller");
        return itemService.addItem(item);
    }

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

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> deleteItemById(@PathVariable int itemId) {
        logger.info("Calling deleteItemById method from controller");
        itemService.deleteItemById(itemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted successfully ", itemId), HttpStatus.FOUND);
    }
}