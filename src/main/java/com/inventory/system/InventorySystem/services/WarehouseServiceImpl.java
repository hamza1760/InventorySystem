package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.WarehouseDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    static Logger logger = Logger.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private InventoryDetailDao inventoryDetailDao;

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        if (warehouse.getStatus().equals(Constants.ACTIVE.getValue())) {
            logger.info("Getting address from request body");
            int addressId = warehouse.getAddress().getAddressId();
            logger.info("Checking if address is present in database with addressId: " + addressId);
            Address address = addressDao.findById(addressId).orElseThrow(() -> {
                logger.error("Address not found", new NotFoundException(Constants.ADDRESS_NOT_FOUND, addressId));
                throw new NotFoundException(Constants.ADDRESS_NOT_FOUND, addressId);
            });
            logger.info("Address found in database");
            logger.info("Checking address status");
            if (address.getStatus().equals(Constants.DELETED.getValue())) {
                logger.info("Address status is deleted");
                logger.error("Address not found", new NotFoundException(Constants.ADDRESS_NOT_FOUND, addressId));
                throw new NotFoundException(Constants.ADDRESS_NOT_FOUND, addressId);
            }
            int warehouseId = warehouse.getWarehouseId();
            boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
            if (checkWarehouseId) {
                logger.error("Warehouse already exist in database", new AlreadyExists(Constants.WAREHOUSE_ALREADY_EXISTS, warehouseId));
                throw new AlreadyExists(Constants.WAREHOUSE_ALREADY_EXISTS, warehouseId);
            } else {
                Warehouse warehouseInAddress = address.getWarehouse();
                if (warehouseInAddress == null) {
                    logger.info("Adding address to warehouse with addressId: " + addressId);
                    warehouse.setAddress(address);
                    logger.info("Saving warehouse in database");
                    return warehouseDao.save(warehouse);
                } else {
                    int warehouseIdInAddress = warehouseInAddress.getWarehouseId();
                    logger.error("Address is already assigned to warehouse", new DataIntegrityException("Address is already assigned to warehouse", warehouseIdInAddress));
                    throw new DataIntegrityException("Address is already assigned to warehouse", warehouseIdInAddress);
                }
            }
        }
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            throw new DataIntegrityException("Cannot add warehouse with status Deleted", warehouse.getWarehouseId());
        } else {
            throw new DataIntegrityException("Status not supported", warehouse.getWarehouseId());
        }
    }

    @Override
    public List<Warehouse> getWarehouse() {
        List<Warehouse> warehouses = warehouseDao.findAll();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
                logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, 0));
                throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, 0);
            }
        }
        logger.info("Returning warehouse with status active");
        return warehouseDao.findByStatus(Constants.ACTIVE.getValue());
    }

    @Override
    public Warehouse getWarehouseById(Warehouse getWarehouse) {
        int warehouseId = getWarehouse.getWarehouseId();
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        }
        logger.info("Returning warehouse with status active and id: " + warehouseId);
        return warehouseDao.findByStatusAndWarehouseId(Constants.ACTIVE.getValue(), warehouseId);
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse updateWarehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("Updating name of warehouse");
        updateWarehouse.setWarehouseName(warehouse.getWarehouseName());
        logger.info("Saving warehouse with updated name in database");
        Warehouse updatedWarehouse = warehouseDao.save(updateWarehouse);
        logger.info("Returning warehouse with updated name");
        return updatedWarehouse;
    }

    public Warehouse putInventoryInWarehouse(Set<InventoryDetail> inventoryDetails, int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("Getting warehouse status");
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            for (InventoryDetail inventory1 : inventoryDetails) {
                int inventoryId = inventory1.getInventoryId();
                logger.info("Checking if inventory exist in database with id: " + inventoryId);
                InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
                    logger.error("Inventory not found", new NotFoundException(Constants.INVENTORY_NOT_FOUND, inventoryId));
                    throw new NotFoundException(Constants.INVENTORY_NOT_FOUND, inventoryId);
                });
                logger.info("Getting inventory status");
                if (inventory.getStatus().equals(Constants.DELETED.getValue())) {
                    logger.error("Inventory not found", new NotFoundException(Constants.INVENTORY_NOT_FOUND, inventoryId));
                    throw new NotFoundException(Constants.INVENTORY_NOT_FOUND, inventoryId);
                } else {
                    logger.info("Checking if the inventory with id: " + inventoryId + " is not present in any other warehouse");
                    Warehouse checkWarehouse = inventory.getWarehouse();
                    if (checkWarehouse == null) {
                        logger.info("Setting inventory to warehouse");
                        inventory.setWarehouse(warehouse);
                        logger.info("Saving inventory in database with warehouse id: " + warehouseId);
                        inventoryDetailDao.save(inventory);
                    } else {
                        int warehouseIdInInventory = checkWarehouse.getWarehouseId();
                        logger.error("This inventory is already in warehouse", new DataIntegrityException("The inventory with inventory id: " + inventoryId + " is already in warehouse", warehouseIdInInventory));
                        throw new DataIntegrityException("The inventory with inventory id: " + inventoryId + " is already in warehouse", warehouseIdInInventory);
                    }
                }
            }
        }
        return warehouse;
    }

    @Override
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("Getting warehouse status");
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            Set<InventoryDetail> inventoryDetail = warehouse.getInventory();
            logger.info("Checking if inventory exist in warehouse");
            if (inventoryDetail.size() == 0) {
                logger.error("his warehouse does not have any inventory", new DataIntegrityException("This warehouse does not have any inventory", warehouseId));
                throw new DataIntegrityException("This warehouse does not have any inventory", warehouseId);
            }
            List<ItemQuantity> itemQuantity = warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId);
            if (itemQuantity.size() == 0) {
                logger.error("his warehouse does not have any inventory", new DataIntegrityException("This warehouse does not have any inventory", warehouseId));
                throw new DataIntegrityException("This warehouse does not have any inventory", warehouseId);
            }
            logger.info("Returning itemQuantity in warehouse with warehouseId: " + warehouseId);
            return warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId);
        }
    }

    public List<ItemQuantity> getItemQuantityInAllWarehouses() {
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
                logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, 0));
                throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, 0);
            }
        }
        if (found > 0) {
            List<ItemQuantity> itemQuantity = warehouseDao.getItemQuantityAllWarehouses(Constants.ACTIVE.getValue());
            if (itemQuantity.size() == 0) {
                logger.error("None of the warehouses has inventory", new DataIntegrityException("None of the warehouses has inventory", 0));
                throw new DataIntegrityException("None of the warehouses has inventory", 0);
            }
            logger.info("Returning itemQuantity in all warehouses");
            return warehouseDao.getItemQuantityAllWarehouses(Constants.ACTIVE.getValue());
        } else {
            logger.info("Inventory not found");
            logger.error("None of the warehouses has inventory", new DataIntegrityException("None of the warehouses has inventory", 0));
            throw new DataIntegrityException("None of the warehouses has inventory", 0);
        }
    }

    @Override
    public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId,
                                                      int inventoryId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("Getting warehouse status");
        if (warehouse.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
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
                    return warehouseDao.save(warehouse);
                }
                logger.error("Inventory not found", new NotFoundException(Constants.INVENTORY_NOT_FOUND, inventoryId));
                throw new NotFoundException(Constants.INVENTORY_NOT_FOUND, inventoryId);
            }
            return null;
        }
    }

    @Override
    public void deleteWarehouseById(int warehouseId) {
        logger.info("Checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.error("Warehouse not found", new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId));
            throw new NotFoundException(Constants.WAREHOUSE_NOT_FOUND, warehouseId);
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
