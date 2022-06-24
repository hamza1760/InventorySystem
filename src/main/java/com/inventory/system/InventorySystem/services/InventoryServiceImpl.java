package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {

    final String ITEM_NOT_FOUND = "Item Not Found";

    final String ITEM_TYPE_NOT_FOUND = "Item Type Not Found";

    final String INVENTORY_NOT_FOUND = "Inventory Not Found";
    final String INVENTORY_ALREADY_EXIST = "Inventory Already Exist";

    @Autowired
    private InventoryDetailDao inventoryDetailDao;


    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTypeDao itemTypeDao;


    @Override
    public InventoryDetail addInventory(InventoryDetail inventoryDetail) {
        Item itemInInventory = inventoryDetail.getItem();
        int itemId = itemInInventory.getItemId();
        Item item = itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND, itemId));
        String itemStatus = item.getStatus();
        if (itemStatus.contains("deleted")) {
            throw new NotFoundException(ITEM_NOT_FOUND, itemId);
        }
        ItemType itemTypeInInventory = inventoryDetail.getItemType();
        int itemTypeId = itemTypeInInventory.getItemTypeId();
        ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(() -> new NotFoundException(ITEM_TYPE_NOT_FOUND, itemTypeId));
        String itemTypeStatus = itemType.getStatus();
        if (itemTypeStatus.contains("deleted")) {
            throw new NotFoundException(ITEM_TYPE_NOT_FOUND, itemTypeId);
        }
        int inventoryId = inventoryDetail.getInventoryId();
        boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
        if (checkInventory) {
            throw new AlreadyExists(INVENTORY_ALREADY_EXIST, inventoryId);
        } else {
            inventoryDetail.setItem(item);
            inventoryDetail.setItemType(itemTypeInInventory);
        }
        return inventoryDetailDao.save(inventoryDetail);
    }

    @Override
    public List<InventoryDetail> getInventory() {
        return inventoryDetailDao.findByStatus("active");
    }

    @Override
    public InventoryDetail getInventoryById(int inventoryId) {
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(INVENTORY_NOT_FOUND, inventoryId));
        return inventoryDetailDao.findByStatusAndInventoryId("active", inventoryId);
    }


    @Override
    public InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail, int inventoryId) {
        InventoryDetail setItemQuantity = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(INVENTORY_NOT_FOUND, inventoryId));
        setItemQuantity.setAvlQty(inventoryDetail.getAvlQty());
        setItemQuantity.setInStock(inventoryDetail.getInStock());
        return inventoryDetailDao.save(setItemQuantity);
    }


    @Override
    public InventoryDetail updateInventoryById() {
        return null;
    }


    @Override
    public void deleteInventory(int inventoryId) {
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(INVENTORY_NOT_FOUND, inventoryId));
        inventoryDetailDao.softDelete(inventoryId);
    }
}
