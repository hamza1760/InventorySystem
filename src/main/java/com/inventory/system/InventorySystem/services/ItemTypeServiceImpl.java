package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {


    @Autowired
    private ItemTypeDao itemTypeDao;



    @Override
    public List<ItemType> getItemType() {
        return itemTypeDao.findByStatus("active");
    }

    @Override
    public ItemType getItemTypeById(int itemTypeId) {
        itemTypeDao.findById(itemTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId));
        return itemTypeDao.findByStatusAndItemTypeId("active", itemTypeId);
    }

    @Override
    public ItemType addItemType(ItemType itemType) {
        int itemTypeId = itemType.getItemTypeId();
        boolean checkItemType = itemTypeDao.findById(itemTypeId).isPresent();
        if (checkItemType) {
            throw new AlreadyExists(AlreadyExistsConstant.ITEM_TYPE_ALREADY_EXISTS,itemTypeId);
        }
            if (itemType.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
                return itemTypeDao.save(itemType);
            }
            if (itemType.getStatus().equals(StatusConstant.DELETED.getValue())) {
                throw new DataIntegrityException("Cannot add itemType with status Deleted", itemType.getItemTypeId());
            } else {
                throw new DataIntegrityException("Status not supported", itemType.getItemTypeId());
            }
        }



    @Override
    public void deleteItemType(int itemTypeId) {
        itemTypeDao.findById(itemTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId));
        itemTypeDao.softDelete(StatusConstant.DELETED.getValue(), itemTypeId);
    }
}
