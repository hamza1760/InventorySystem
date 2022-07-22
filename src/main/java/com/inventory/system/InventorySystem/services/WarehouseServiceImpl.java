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
public class WarehouseServiceImpl implements WarehouseService {

    static Logger logger = Logger.getLogger(WarehouseServiceImpl.class);

    /**
     * Implementation of WarehouseDao to work with Warehouse database.
     */
    @Autowired
    private WarehouseDao warehouseDao;

    /**
     * Implementation of AddressDao to work with Address database.
     */
    @Autowired
    private AddressDao addressDao;

    /**
     * Implementation of InventoryDetailDao to work with InventoryDetail database.
     */
    @Autowired
    private InventoryDetailDao inventoryDetailDao;

    /**
     * Implementation of GlobalMapper to map entities to dto and vice versa.
     */
    @Autowired
    private GlobalMapper globalMapper;

    /**
     * To add warehouse in database.
     *
     * @param warehouseDto Object of WarehouseDto.
     * @return The warehouse that is added in database.
     */
    @Override
    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        logger.info("Getting address from request body");
        int addressId = warehouseDto.getAddress().getAddressId();
        logger.info("Checking if address is present in database with addressId: " + addressId);
        Address address = addressDao.findById(addressId).orElseThrow(() -> {
            logger.error("Address not found", new GlobalException(Constants.ADDRESS_NOT_FOUND.getValue(), addressId));
            throw new GlobalException(Constants.ADDRESS_NOT_FOUND.getValue(), addressId);
        });
        logger.info("Address found in database");
        logger.info("Checking address status");
        if (address.getStatus().equals(Constants.DELETED.getValue())) {
            logger.info("Address status is deleted");
            logger.error("Address not found", new GlobalException(Constants.ADDRESS_NOT_FOUND.getValue(), addressId));
            throw new GlobalException(Constants.ADDRESS_NOT_FOUND.getValue(), addressId);
        }
        int warehouseId = warehouseDto.getWarehouseId();
        boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
        if (checkWarehouseId) {
            logger.error("Warehouse already exist in database", new GlobalException(Constants.WAREHOUSE_ALREADY_EXISTS.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_ALREADY_EXISTS.getValue(), warehouseId);
        } else {
            Warehouse warehouseInAddress = address.getWarehouse();
            if (warehouseInAddress == null) {
                logger.info("Adding address to warehouse with addressId: " + addressId);
                warehouseDto.setAddress(globalMapper.addressToAddressDto(address));
                logger.info("Saving warehouse in database");
                Warehouse warehouse = globalMapper.warehouseDtoToWarehouse(warehouseDto);
                return globalMapper.warehouseToWarehouseDto((warehouseDao.save(warehouse)));
            } else {
                int warehouseIdInAddress = warehouseInAddress.getWarehouseId();
                logger.error("Address is already assigned to warehouse", new GlobalException("Address is already assigned to warehouse", warehouseIdInAddress));
                throw new GlobalException("Address is already assigned to warehouse", warehouseIdInAddress);
            }
        }
    }

    /**
     * Get the list of warehouses available in database.
     *
     * @return list of warehouses.
     */
    @Override
    public List<WarehouseDto> getWarehouse() {
        List<Warehouse> warehouses = warehouseDao.findAll();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
                logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), 0));
                throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), 0);
            }
        }
        logger.info("Returning warehouse with status active");
        return warehouseDao.findByStatus(Constants.ACTIVE.getValue()).stream().map(globalMapper::warehouseToWarehouseDto).collect(Collectors.toList());
    }

    /**
     * To get single warehouse based on the id of warehouse.
     *
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return Single warehouse that matches the warehouseId.
     */
    @Override
    public WarehouseDto getWarehouseById(int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        });
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        }
        logger.info("Returning warehouse with status active and id: " + warehouseId);
        return globalMapper.warehouseToWarehouseDto(warehouseDao.findByStatusAndWarehouseId(Constants.ACTIVE.getValue(), warehouseId));
    }

    /**
     * Adding inventory in warehouse
     *
     * @param inventoryDetailsDto Object of InventoryDetailDto to get inventoryId of the inventory to be added in warehouse.
     * @param warehouseId      The id of the warehouse to search warehouse in database.
     * @return Warehouse in which the inventory is added.
     */
    public WarehouseDto putInventoryInWarehouse(Set<InventoryDetailDto> inventoryDetailsDto, int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        });
        logger.info("Getting warehouse status");
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        } else {
            for (InventoryDetailDto inventory1 : inventoryDetailsDto) {
                int inventoryId = inventory1.getInventoryId();
                logger.info("Checking if inventory exist in database with id: " + inventoryId);
                InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
                    logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
                    throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
                });
                logger.info("Getting inventory status");
                if (inventory.getStatus().equals(Constants.DELETED.getValue())) {
                    logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
                    throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
                } else {
                    logger.info("Checking if the inventory with id: " + inventoryId + " is not present in any other warehouse");
                    Warehouse checkWarehouse = inventory.getWarehouse();
                    if (checkWarehouse == null) {
                        logger.info("Setting inventory to warehouse");
                        inventory.setWarehouse(warehouse);
                        logger.info("Saving inventory in database with warehouse id: " + warehouseId);
                        inventoryDetailDao.save(inventory);
                    }
                }
            }
        }
        return globalMapper.warehouseToWarehouseDto((warehouse));
    }

    /**
     * Get the item quantity in all warehouses present in database.
     *
     * @return List of inventories of the item present in all warehouses.
     */
    public List<ItemQuantityDto> getItemQuantityInAllWarehouses() {
        int found = 0;
        logger.info("Getting all warehouses from database");
        List<Warehouse> warehouses = warehouseDao.findAll();
        logger.info("Checking if any of the warehouse has inventory");
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getStatus().equals(Constants.ACTIVE.getValue())) {
                Set<InventoryDetail> inventory = warehouse.getInventory();
                if (inventory.size() != 0) {
                    logger.info("Inventory found");
                    found++;
                }
            } else {
                logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), 0));
                throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), 0);
            }
        }
        if (found > 0) {
            List<ItemQuantity> itemQuantity = warehouseDao.getItemQuantityAllWarehouses(Constants.ACTIVE.getValue());
            if (itemQuantity.size() == 0) {
                logger.error("None of the warehouses has inventory", new GlobalException("None of the warehouses has inventory", 0));
                throw new GlobalException("None of the warehouses has inventory", 0);
            }
            logger.info("Returning itemQuantity in all warehouses");
            return warehouseDao.getItemQuantityAllWarehouses(Constants.ACTIVE.getValue()).stream().map(globalMapper::itemQuantityToItemQuantityDto).collect(Collectors.toList());
        } else {
            logger.info("Inventory not found");
            logger.error("None of the warehouses has inventory", new GlobalException("None of the warehouses has inventory", 0));
            throw new GlobalException("None of the warehouses has inventory", 0);
        }
    }

    /**
     * Get the item quantity in single warehouse.
     *
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return List of inventories of the item present in particular warehouse.
     */
    @Override
    public List<ItemQuantityDto> getItemQuantityInSingleWarehouse(int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        });
        logger.info("Getting warehouse status");
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        } else {
            Set<InventoryDetail> inventoryDetail = warehouse.getInventory();
            logger.info("Checking if inventory exist in warehouse");
            if (inventoryDetail.size() == 0) {
                logger.error("This warehouse does not have any inventory", new GlobalException("This warehouse does not have any inventory", warehouseId));
                throw new GlobalException("This warehouse does not have any inventory", warehouseId);
            }
            List<ItemQuantity> itemQuantity = warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId);
            if (itemQuantity.size() == 0) {
                logger.error("This warehouse does not have any inventory", new GlobalException("This warehouse does not have any inventory", warehouseId));
                throw new GlobalException("This warehouse does not have any inventory", warehouseId);
            }
            logger.info("Returning itemQuantity in warehouse with warehouseId: " + warehouseId);
            return warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId).stream().map(globalMapper::itemQuantityToItemQuantityDto).collect(Collectors.toList());
        }
    }

    /**
     * Update the item quantity in single warehouse.
     *
     * @param inventoryDetailDto   Object of the InventoryDetailDto.
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return Single warehouse with updated inventory of item.
     */
    @Override
    public WarehouseDto setItemQuantityInSingleWarehouse(InventoryDetailDto inventoryDetailDto, int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        });
        logger.info("Getting warehouse status");
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        } else {
            logger.info("Getting list of inventories from warehouse");
            Set<InventoryDetail> inventoryDetails = warehouse.getInventory();
            for (InventoryDetail updateInventory : inventoryDetails) {
                logger.info("Checking if inventory with inventoryId: " + inventoryDetailDto.getInventoryId() + " exist in warehouse with  warehouseId: " + warehouseId);
                if (updateInventory.getInventoryId() == inventoryDetailDto.getInventoryId()) {
                    if (updateInventory.getStatus().equals(Constants.ACTIVE.getValue())) {
                        inventoryDetailDto.setItem(globalMapper.itemToItemDto(updateInventory.getItem()));
                        inventoryDetailDto.setItemType(globalMapper.itemTypeToItemTypeDto(updateInventory.getItemType()));
                        inventoryDetailDto.setWarehouse(globalMapper.warehouseToWarehouseDto(warehouse));
                        updateInventory = globalMapper.inventoryDetailDtoToInventoryDetail(inventoryDetailDto);
                        warehouse.setInventory((updateInventory));
                        logger.info("Saving inventory in database");
                        inventoryDetailDao.save(updateInventory);
                        logger.info("Saving warehouse in database");
                        logger.info("Returning warehouse with updated inventory");
                        return globalMapper.warehouseToWarehouseDto(warehouseDao.save(warehouse));
                    }
                }
            }
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryDetailDto.getInventoryId()));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryDetailDto.getInventoryId());
        }
    }

    /**
     * Delete the particular warehouse from database.
     *
     * @param warehouseId The id of the warehouse to be deleted.
     */
    @Override
    public void deleteWarehouseById(int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId));
            throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), warehouseId);
        });
        logger.info("Getting the list of inventory in warehouse");
        Set<InventoryDetail> inventoryDetailSet = warehouse.getInventory();
        for (InventoryDetail inventory : inventoryDetailSet) {
            logger.info("Setting status deleted of all inventory present in warehouse with warehouseId: " + warehouseId);
            inventory.setStatus(Constants.DELETED.getValue());
            logger.info("Saving inventory in database");
            inventoryDetailDao.save(inventory);
        }
        logger.info("Setting status of warehouse to " + Constants.DELETED.getValue());
        warehouseDao.softDelete(Constants.DELETED.getValue(), warehouseId);
    }
}
