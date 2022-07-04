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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl implements WarehouseService {


    static Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);


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
            logger.info("checking if address is present in database with addressId: "+addressId);
            Address address = addressDao.findById(addressId).orElseThrow(() -> {
                logger.info("throwing exception "+ NotFoundConstant.ADDRESS_NOT_FOUND.getValue() +" with addressId "+ addressId );
                throw new NotFoundException(NotFoundConstant.ADDRESS_NOT_FOUND, addressId);
            });
            int warehouseId = warehouse.getWarehouseId();
            boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
            if (checkWarehouseId) {
                logger.info("throwing exception "+AlreadyExistsConstant.WAREHOUSE_ALREADY_EXISTS.getValue() + " with warehouseId: " + warehouseId);
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
        logger.info("returning warehouse with status active");
        return warehouseDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("returning warehouse with status active and id: " + warehouseId);
        return warehouseDao.findByStatusAndWarehouseId(StatusConstant.ACTIVE.getValue(), warehouseId);
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse updateWarehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
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
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("getting warehouse status");
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            logger.info("checking if inventory exist in database with id: " + inventoryId);
            InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
                logger.info("Throwing exception "+NotFoundConstant.INVENTORY_NOT_FOUND.getValue() +" with inventoryId: " +inventoryId);
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
            });
            logger.info("getting inventory status");
            if (inventory.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Throwing exception "+NotFoundConstant.INVENTORY_NOT_FOUND.getValue() +" with inventoryId: " +inventoryId);
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
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("getting warehouse status");
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            Set<InventoryDetail> inventoryDetail = warehouse.getInventory();
            if(inventoryDetail.size()==0){
                logger.info("throwing exception "+NotFoundConstant.INVENTORY_NOT_FOUND.getValue() +" in warehouse with warehouseId: "+warehouseId);
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, 0);
            }
            else {
                logger.info("returning itemQuantity in warehouse with warehouseId: " + warehouseId);
                return warehouseDao.getItemQuantityInSingleWarehouse(warehouseId);
            }
        }
    }

    public List<ItemQuantity> getItemQuantityInAllWarehouse() {
        logger.info("returning itemQuantity in all warehouses");
        return warehouseDao.getItemQuantityAllWarehouses();
    }


    @Override
    public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId) {
        logger.info("checking if warehouse exists in database with id: " + warehouseId);
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> {
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        });
        logger.info("getting warehouse status");
        if (warehouse.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("throwing exception "+NotFoundConstant.WAREHOUSE_NOT_FOUND.getValue() + " with warehouseId: "+warehouseId);
            throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            logger.info("getting list of inventories from warehouse");
            Set<InventoryDetail> inventoryDetails = warehouse.getInventory();
            for (InventoryDetail setItemQuantity : inventoryDetails) {
                int inventoryIdInWarehouse = setItemQuantity.getInventoryId();
                logger.info("checking if inventory with inventoryId: "+inventoryId + " exist in warehouse with  warehouseId: " +warehouseId);
                if (inventoryIdInWarehouse == inventoryId) {
                    setItemQuantity.setInStock(inventory.getInStock());
                    setItemQuantity.setAvlQty(inventory.getAvlQty());
                    warehouse.setInventory(setItemQuantity);
                    inventoryDetailDao.save(setItemQuantity);
                    return warehouseDao.save(warehouse);
                } else {
                    logger.info("Throwing exception "+NotFoundConstant.INVENTORY_NOT_FOUND.getValue() +" with inventoryId: " +inventoryId);
                    throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
                }
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
        logger.info("calling softDelete function from warehouseDao");
        warehouseDao.softDelete(StatusConstant.DELETED.getValue(), warehouseId);
    }
}
