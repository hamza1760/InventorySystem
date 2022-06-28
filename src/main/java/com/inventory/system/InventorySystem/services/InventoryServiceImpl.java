package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {


    @Autowired
    private InventoryDetailDao inventoryDetailDao;


    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTypeDao itemTypeDao;


    @Override
    public InventoryDetail addInventory(InventoryDetail inventoryDetail) {

        if(inventoryDetail.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
            Item itemInInventory = inventoryDetail.getItem();
            int itemId = itemInInventory.getItemId();
            Item item = itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
            }
            ItemType itemTypeInInventory = inventoryDetail.getItemType();
            int itemTypeId = itemTypeInInventory.getItemTypeId();
            ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId));
            if (itemType.getStatus().equals(StatusConstant.DELETED.getValue())) {
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemTypeId);
            }
            int inventoryId = inventoryDetail.getInventoryId();
            boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
            if (checkInventory) {
                throw new AlreadyExists(AlreadyExistsConstant.INVENTORY_ALREADY_EXISTS, inventoryId);
            } else {
                inventoryDetail.setItem(item);
                inventoryDetail.setItemType(itemTypeInInventory);
                return inventoryDetailDao.save(inventoryDetail);
            }
        }
        if(inventoryDetail.getStatus().equals(StatusConstant.DELETED.getValue())){
            throw new DataIntegrityException("Cannot add inventory with status Deleted",inventoryDetail.getInventoryId());
        }
        else{
            throw new DataIntegrityException("status not supported", inventoryDetail.getInventoryId());
        }


    }

    @Override
    public List<InventoryDetail> getInventory() {
        return inventoryDetailDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public InventoryDetail getInventoryById(int inventoryId) {
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
        return inventoryDetailDao.findByStatusAndInventoryId(StatusConstant.ACTIVE.getValue(), inventoryId);
    }


    @Override
    public InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail, int inventoryId) {
        InventoryDetail setItemQuantity = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
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
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
        inventoryDetailDao.softDelete(StatusConstant.DELETED.getValue(), inventoryId);
    }
}
