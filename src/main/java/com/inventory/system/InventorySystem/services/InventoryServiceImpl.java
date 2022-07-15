package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.dto.InventoryDetailDto;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.GlobalException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    static Logger logger = Logger.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryDetailDao inventoryDetailDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTypeDao itemTypeDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventoryDetailDto addInventory(InventoryDetail inventoryDetail) {
        if (inventoryDetail.getStatus().equals(Constants.ACTIVE.getValue())) {
            logger.info("Getting item from request body");
            int itemId = inventoryDetail.getItem().getItemId();
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
            int itemTypeId = inventoryDetail.getItemType().getItemTypeId();
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
            int inventoryId = inventoryDetail.getInventoryId();
            logger.info("Checking if inventory is already present in database with inventoryId: " + inventoryId);
            boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
            if (checkInventory) {
                logger.info("Inventory found in database");
                logger.error("Inventory already exists", new GlobalException(Constants.INVENTORY_ALREADY_EXISTS.getValue(), inventoryId));
                throw new GlobalException(Constants.INVENTORY_ALREADY_EXISTS.getValue(), inventoryId);
            } else {
                logger.info("Setting item to inventory");
                inventoryDetail.setItem(item);
                logger.info("Setting itemType to inventory");
                inventoryDetail.setItemType(itemType);
                logger.info("Saving inventory in database with inventoryId: " + inventoryId + " itemId: " + itemId + " itemTypeId: " + itemTypeId);
                return inventoryDetailToInventoryDetailDto(inventoryDetailDao.save(inventoryDetail));
            }
        }
        if (inventoryDetail.getStatus().equals(Constants.DELETED.getValue())) {
            throw new GlobalException("Cannot add inventory with status Deleted", inventoryDetail.getInventoryId());
        } else {
            throw new GlobalException("status not supported", inventoryDetail.getInventoryId());
        }
    }

    @Override
    public List<InventoryDetailDto> getInventory() {
        List<InventoryDetail> inventoryDetail = inventoryDetailDao.findAll();
        for (InventoryDetail inventory : inventoryDetail) {
            if (inventory.getStatus().equals(Constants.DELETED.getValue())) {
                logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), 0));
                throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), 0);
            }
        }
        logger.info("Returning list of inventories from database");
        return inventoryDetailDao.findByStatus(Constants.ACTIVE.getValue()).stream().map(this::inventoryDetailToInventoryDetailDto).collect(Collectors.toList());
    }

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
        return inventoryDetailToInventoryDetailDto(inventoryDetailDao.findByStatusAndInventoryId(Constants.ACTIVE.getValue(), inventoryId));
    }

    @Override
    public InventoryDetailDto setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail, int inventoryId) {
        logger.info("Checking if the inventory is present in database with inventoryId: " + inventoryId);
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
        });
        return inventoryDetailToInventoryDetailDto(inventoryDetailDao.save(inventoryDetail));
    }

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

    public InventoryDetailDto inventoryDetailToInventoryDetailDto(InventoryDetail inventoryDetail) {
        return modelMapper.map(inventoryDetail, InventoryDetailDto.class);
    }
}
