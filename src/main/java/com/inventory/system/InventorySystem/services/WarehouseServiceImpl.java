package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.WarehouseDao;
import com.inventory.system.InventorySystem.dto.InventoryDetailDto;
import com.inventory.system.InventorySystem.dto.ItemQuantityDto;
import com.inventory.system.InventorySystem.dto.WarehouseDto;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.GlobalException;
import com.inventory.system.InventorySystem.mapper.GlobalMapper;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    static Logger logger = Logger.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private InventoryDetailDao inventoryDetailDao;

    @Autowired
    private GlobalMapper globalMapper;

    @Override
    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        if (warehouseDto.getStatus().equals(Constants.ACTIVE.getValue())) {
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
        if (warehouseDto.getStatus().equals(Constants.DELETED.getValue())) {
            throw new GlobalException("Cannot add warehouse with status Deleted", warehouseDto.getWarehouseId());
        } else {
            throw new GlobalException("Status not supported", warehouseDto.getWarehouseId());
        }
    }

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

    @Override
    public WarehouseDto getWarehouseById(Warehouse getWarehouse) {
        int warehouseId = getWarehouse.getWarehouseId();
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

    public WarehouseDto putInventoryInWarehouse(Set<InventoryDetailDto> inventoryDetails, int warehouseId) {
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
            for (InventoryDetailDto inventory1 : inventoryDetails) {
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
                logger.error("his warehouse does not have any inventory", new GlobalException("This warehouse does not have any inventory", warehouseId));
                throw new GlobalException("This warehouse does not have any inventory", warehouseId);
            }
            List<ItemQuantity> itemQuantity = warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId);
            if (itemQuantity.size() == 0) {
                logger.error("his warehouse does not have any inventory", new GlobalException("This warehouse does not have any inventory", warehouseId));
                throw new GlobalException("This warehouse does not have any inventory", warehouseId);
            }
            logger.info("Returning itemQuantity in warehouse with warehouseId: " + warehouseId);
            return warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId).stream().map(globalMapper::itemQuantityToItemQuantityDto).collect(Collectors.toList());
        }
    }

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

    @Override
    public WarehouseDto setItemQuantityInSingleWarehouse(InventoryDetailDto inventory, int warehouseId,
                                                         int inventoryId) {
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
            for (InventoryDetail setItemQuantity : inventoryDetails) {
                int inventoryIdInWarehouse = setItemQuantity.getInventoryId();
                logger.info("Checking if inventory with inventoryId: " + inventoryId + " exist in warehouse with  warehouseId: " + warehouseId);
                if (inventoryIdInWarehouse == inventoryId) {
                    logger.info("Setting In Stock Quantity of item in database");
                    setItemQuantity.setInStock(inventory.getInStock());
                    logger.info("Setting Available Quantity of item in database");
                    setItemQuantity.setAvlQty(inventory.getAvlQty());
                    logger.info("Setting inventory in warehouse");
                    warehouse.setInventory((setItemQuantity));
                    logger.info("Saving inventory in database");
                    inventoryDetailDao.save(setItemQuantity);
                    logger.info("Saving warehouse in database");
                    logger.info("Returning warehouse with updated inventory");
                    return globalMapper.warehouseToWarehouseDto(warehouseDao.save(warehouse));
                }
            }
            logger.error("Inventory not found", new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId));
            throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), inventoryId);
        }
    }

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
