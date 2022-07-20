package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.*;
import com.inventory.system.InventorySystem.mapper.*;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class InventoryServiceImpl implements InventoryService {

    static Logger logger = Logger.getLogger(InventoryServiceImpl.class);

    /**
     * Implementation of InventoryDetailDao to work with inventory database.
     */
    @Autowired
    private InventoryDetailDao inventoryDetailDao;

    /**
     * Implementation of ItemDao to work with item database.
     */
    @Autowired
    private ItemDao itemDao;

    /**
     * implementation of ItemTypeDao to work with item type database.
     */
    @Autowired
    private ItemTypeDao itemTypeDao;

    /**
     * Implementation of GlobalMapper to map entities to dto and vice versa.
     */
    @Autowired
    GlobalMapper globalMapper;

    /**
     * To add inventory in database.
     *
     * @param inventoryDetailDto The object of the InventoryDetailDto.
     * @return inventory that is added to database.
     */
    @Override
    public InventoryDetailDto addInventory(InventoryDetailDto inventoryDetailDto) {
            logger.info("Getting item from request body");
            int itemId = inventoryDetailDto.getItem().getItemId();
            logger.info("Checking if item exists in database with itemId: " + itemId);
            Item item = itemDao.findById(itemId).orElseThrow(() -> {
                logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
                throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
            });
            logger.info("Item found in database");
            logger.info("Checking item status");
            if (item.getStatus().equals(Constants.DELETED.getValue())) {
                logger.info("Item status is deleted");
                logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
                throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
            }
            logger.info("Getting itemTypeId from request body");
            int itemTypeId = inventoryDetailDto.getItemType().getItemTypeId();
            logger.info("Checking if itemType exists in database with itemTypeId: " + itemTypeId);
            ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(() -> {
                logger.error("Item Type not found ", new GlobalException(Constants.ITEM_TYPE_NOT_FOUND.getValue(), itemTypeId));
                throw new GlobalException(Constants.ITEM_TYPE_NOT_FOUND.getValue(), itemTypeId);
            });
            logger.info("Item Type found in database");
            logger.info("Checking itemType status");
            if (itemType.getStatus().equals(Constants.DELETED.getValue())) {
                logger.info("Item Type status is deleted");
                logger.error("Item Type not found ", new GlobalException(Constants.ITEM_TYPE_NOT_FOUND.getValue(), itemTypeId));
                throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemTypeId);
            }
            logger.info("Getting inventoryId from request body");
            int inventoryId = inventoryDetailDto.getInventoryId();
            logger.info("Checking if inventory is already present in database with inventoryId: " + inventoryId);
            boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
            if (checkInventory) {
                logger.info("Inventory found in database");
                logger.error("Inventory already exists", new GlobalException(Constants.INVENTORY_ALREADY_EXISTS.getValue(), inventoryId));
                throw new GlobalException(Constants.INVENTORY_ALREADY_EXISTS.getValue(), inventoryId);
            } else {
                logger.info("Setting item to inventory");
                inventoryDetailDto.setItem(globalMapper.itemToItemDto(item));
                logger.info("Setting itemType to inventory");
                inventoryDetailDto.setItemType(globalMapper.itemTypeToItemTypeDto(itemType));
                logger.info("Saving inventory in database with inventoryId: " + inventoryId + " itemId: " + itemId + " itemTypeId: " + itemTypeId);
                InventoryDetail inventoryDetail = globalMapper.inventoryDetailDtoToInventoryDetail(inventoryDetailDto);
                return globalMapper.inventoryDetailToInventoryDetailDto(inventoryDetailDao.save(inventoryDetail));
            }
    }

    /**
     * Get the list of all inventories available in database.
     *
     * @return list of inventories.
     */
    @Override
    public List<InventoryDetailDto> getInventory() {
        int deleted = 0;
        List<InventoryDetail> inventoryDetail = inventoryDetailDao.findAll();
        for (InventoryDetail inventory : inventoryDetail) {
            if (inventory.getStatus().equals(Constants.DELETED.getValue())) {
                deleted++;
            }
            if (deleted == inventoryDetail.size()) {
                logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), 0));
                throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), 0);
            }
        }
        logger.info("Returning list of inventories from database");
        return inventoryDetailDao.findByStatus(Constants.ACTIVE.getValue()).stream().map(globalMapper::inventoryDetailToInventoryDetailDto).collect(Collectors.toList());
    }

    /**
     * To get single inventory based on the id of inventory
     *
     * @param inventoryId The id of the inventory to search inventory in database
     * @return Single inventory that matches the inventoryId
     */
    @Override
    public InventoryDetailDto getInventoryById(int inventoryId) {
        logger.info("Checking if the inventory is present in database with inventoryId: " + inventoryId);
        InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
        });
        if (inventory.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
        }
        logger.info("Returning inventory with inventoryId: " + inventoryId);
        return globalMapper.inventoryDetailToInventoryDetailDto(inventoryDetailDao.findByStatusAndInventoryId(Constants.ACTIVE.getValue(), inventoryId));
    }

    /**
     * To update quantity of the item in all warehouses.
     *
     * @param inventoryDetailDto The object of the InventoryDetailDto.
     * @return Single inventory that is updated.
     */
    @Override
    public InventoryDetailDto setItemQuantityInAllWarehouses(InventoryDetailDto inventoryDetailDto) {
        int inventoryId = inventoryDetailDto.getInventoryId();
        logger.info("Checking if the inventory is present in database with inventoryId: " + inventoryId);
        InventoryDetail inventoryDetail = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
        });
        if (inventoryDetail.getStatus().equals(Constants.ACTIVE.getValue())) {
            logger.info("setting item");
            inventoryDetailDto.setItem(globalMapper.inventoryDetailToInventoryDetailDto(inventoryDetail).getItem());
            logger.info("setting itemType");
            inventoryDetailDto.setItemType(globalMapper.inventoryDetailToInventoryDetailDto(inventoryDetail).getItemType());
            logger.info("setting warehouse");
            inventoryDetailDto.setWarehouse(globalMapper.inventoryDetailToInventoryDetailDto(inventoryDetail).getWarehouse());
            logger.info("updating inventory");
            InventoryDetail updatedInventory = globalMapper.inventoryDetailDtoToInventoryDetail(inventoryDetailDto);
            logger.info("returning updated inventory");
            return globalMapper.inventoryDetailToInventoryDetailDto(inventoryDetailDao.save(updatedInventory));
        }
        logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
        throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
    }

    /**
     * Delete the particular inventory.
     *
     * @param inventoryId The id of the inventory to be deleted.
     */
    @Override
    public void deleteInventoryById(int inventoryId) {
        logger.info("checking if the inventory is present in database with inventoryId: " + inventoryId);
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
        });
        logger.info("Setting status of inventory to " + Constants.DELETED.getValue());
        inventoryDetailDao.softDelete(Constants.DELETED.getValue(), inventoryId);
    }
}
