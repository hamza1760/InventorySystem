package com.inventory.system.InventorySystem.controllers.item.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseItem;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.ItemService;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ItemController {

    static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemTypeService itemTypeService;



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

    @GetMapping("/item/size/")
    public List<ItemSize> getItemSize(){
        List<ItemSize> itemSize = itemService.getAllItemSize();
        return itemSize;
    }

    @GetMapping("/item/size/{itemId}")
    public List<ItemSize> getItemSizeById(@PathVariable int itemId){
        List<ItemSize> itemSize = itemService.getItemSizeById(itemId);
        return itemSize;
    }

    @PostMapping("/item/itemtype/{itemTypeId}")
    public Item addItem(@RequestBody Item item,@PathVariable int itemTypeId) {
        /*adding item to database*/
        itemService.addItem(item,itemTypeId);

        /*mapping item to itemtype*/
        ItemType itemType = itemTypeService.getItemTypeById(itemTypeId);
        itemType.setItems(item);
        itemTypeService.saveItemType(itemType);

        return item;


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







}