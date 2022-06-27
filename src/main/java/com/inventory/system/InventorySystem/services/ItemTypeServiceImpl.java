package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {


    @Autowired
    private ItemTypeDao itemTypeDao;

    @Autowired
    private ProductTypeDao productTypeDao;


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
        return itemTypeDao.save(itemType);
    }


    @Override
    public void deleteItemType(int itemTypeId) {
        itemTypeDao.findById(itemTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId));
        itemTypeDao.softDelete(StatusConstant.DELETED.getValue(), itemTypeId);
    }
}
