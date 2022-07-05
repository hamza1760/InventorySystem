package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.WarehouseDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.apache.log4j.*;
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
        if (warehouse.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
            logger.info("getting address from request body");
            int addressId = warehouse.getAddress().getAddressId();
            logger.info("checking if address is present in database with addressId: " + addressId);
            Address address = addressDao.findById(addressId).orElseThrow(() -> {
                logger.info("throwing exception " + NotFoundConstant.ADDRESS_NOT_FOUND.getValue() + " with addressId " + addressId);
                throw new NotFoundException(NotFoundConstant.ADDRESS_NOT_FOUND, addressId);
            });
            logger.info("address found in database");
            logger.info("checking address status");
            if (address.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("address status is deleted");
                logger.info("throwing exception " + NotFoundConstant.ADDRESS_NOT_FOUND.getValue() + " with addressId " + addressId);
                throw new NotFoundException(NotFoundConstant.ADDRESS_NOT_FOUND, addressId);
            }
            int warehouseId = warehouse.getWarehouseId();
            boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
            if (checkWarehouseId) {
                logger.info("throwing exception " + AlreadyExistsConstant.WAREHOUSE_ALREADY_EXISTS.getValue() + " with warehouseId: " + warehouseId);
                throw new AlreadyExists(AlreadyExistsConstant.WAREHOUSE_ALREADY_EXISTS, warehouseId);
            } else {
                Warehouse warehouseInAddress = address.getWarehouse();
                if (warehouseInAddress == null) {
                    logger.info("adding address to warehouse with addressId: " + addressId);
                    warehouse.setAddress(address);
                    logger.info("saving warehouse in database");
                    return warehouseDao.save(warehouse);
                } else {
                    int warehouseIdInAddress = warehouseInAddress.getWarehouseId();
                    logger.info("throwing exception because address already has a warehouse with warehouse id:" + warehouseIdInAddress);
                    throw new DataIntegrityException("address is already assigned to warehouse", warehouseIdInAddress);
                }
            }
        }
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            throw new DataIntegrityException("Cannot add warehouse with status Deleted", warehouse.getWarehouseId());
        } else {
            throw new DataIntegrityException("status not supported", warehouse.getWarehouseId());
        }
    }


    @Override
    public List<Warehouse> getWarehouse() {
        List<Warehouse> warehouses = warehouseDao.findAll();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue());
                throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, 0);
            }
        }
        logger.info("returning warehouse with status active");
        return warehouseDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        }
        logger.info("returning warehouse with status active and id: " + warehouseId);
        return warehouseDao.findByStatusAndWarehouseId(StatusConstant.ACTIVE.getValue(), warehouseId);
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse updateWarehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("updating name of warehouse");
        updateWarehouse.setWarehouseName(warehouse.getWarehouseName());
        logger.info("saving warehouse with updated name in database");
        Warehouse updatedWarehouse = warehouseDao.save(updateWarehouse);
        logger.info("returning warehouse with updated name");
        return updatedWarehouse;
    }


    public Warehouse putInventoryInWarehouse(int warehouseId, int inventoryId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("getting warehouse status");
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            logger.info("checking if inventory exist in database with id: " + inventoryId);
            InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
                logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
            });
            logger.info("getting inventory status");
            if (inventory.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
            } else {
                logger.info("checking if the inventory with id: " + inventoryId + " is not present in any other warehouse");
                Warehouse checkWarehouse = inventory.getWarehouse();
                if (checkWarehouse == null) {
                    logger.info("setting inventory to warehouse");
                    inventory.setWarehouse(warehouse);
                    logger.info("saving inventory in database with warehouse id: " + warehouseId);
                    inventoryDetailDao.save(inventory);
                    return warehouse;
                } else {
                    int warehouseIdInInventory = checkWarehouse.getWarehouseId();
                    logger.info("throwing exception because inventory is already in a warehouse with warehouse id:" + warehouseIdInInventory);
                    throw new DataIntegrityException("this inventory is already in warehouse", warehouseIdInInventory);
                }
            }
        }
    }

    @Override
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("getting warehouse status");
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            Set<InventoryDetail> inventoryDetail = warehouse.getInventory();
            logger.info("checking if inventory exist in warehouse");
            if (inventoryDetail.size() == 0) {
                logger.info("throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " in warehouse with warehouseId: " + warehouseId);
                throw new DataIntegrityException("This warehouse does not have any inventory", warehouseId);
            }
            logger.info("returning itemQuantity in warehouse with warehouseId: " + warehouseId);
            return warehouseDao.getItemQuantityInSingleWarehouse(StatusConstant.ACTIVE.getValue(), warehouseId);
        }
    }

    public List<ItemQuantity> getItemQuantityInAllWarehouses() {
        int found = 0;
        logger.info("getting all warehouses from database");
        List<Warehouse> warehouses = warehouseDao.findAll();
        logger.info("checking if any of the warehouse has inventory");
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
                Set<InventoryDetail> inventory = warehouse.getInventory();
                if (inventory.size() != 0) {
                    logger.info("inventory found");
                    found++;
                }
            } else {
                throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, 0);
            }
        }
        if (found > 0) {
            logger.info("returning itemQuantity in all warehouses");
            return warehouseDao.getItemQuantityAllWarehouses(StatusConstant.ACTIVE.getValue());
        } else {
            logger.info("inventory not found");
            logger.info("throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " because none of the warehouse has inventory");
            throw new DataIntegrityException("None of the warehouses has inventory", 0);
        }
    }


    @Override
    public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("getting warehouse status");
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception " + NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: " + warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            logger.info("getting list of inventories from warehouse");
            Set<InventoryDetail> inventoryDetails = warehouse.getInventory();
            for (InventoryDetail setItemQuantity : inventoryDetails) {
                int inventoryIdInWarehouse = setItemQuantity.getInventoryId();
                logger.info("checking if inventory with inventoryId: " + inventoryId + " exist in warehouse with  warehouseId: " + warehouseId);
                if (inventoryIdInWarehouse == inventoryId) {
                    logger.info("setting In Stock Quantity of item in database");
                    setItemQuantity.setInStock(inventory.getInStock());
                    logger.info("setting Available Quantity of item in database");
                    setItemQuantity.setAvlQty(inventory.getAvlQty());
                    logger.info("setting inventory in warehouse");
                    warehouse.setInventory(setItemQuantity);
                    logger.info("saving inventory in database");
                    inventoryDetailDao.save(setItemQuantity);
                    logger.info("saving warehouse in database");
                    logger.info("returning warehouse with updated inventory");
                    return warehouseDao.save(warehouse);
                }
                logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
            }
            return null;
        }
    }


    @Override
    public void deleteWarehouseById(int warehouseId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId));
        logger.info("getting the list of inventory in warehouse");
        Set<InventoryDetail> inventoryDetailSet = warehouse.getInventory();
        for (InventoryDetail inventory : inventoryDetailSet) {
            logger.info("setting status deleted of all inventory present in warehouse with warehouseId: " + warehouseId);
            inventory.setStatus(StatusConstant.DELETED.getValue());
            logger.info("saving inventory in database");
            inventoryDetailDao.save(inventory);
        }
        logger.info("setting status of warehouse to " + StatusConstant.DELETED.getValue());
        warehouseDao.softDelete(StatusConstant.DELETED.getValue(), warehouseId);
    }
}
