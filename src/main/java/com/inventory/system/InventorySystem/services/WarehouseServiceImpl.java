package com.inventory.system.InventorySystem.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    final String ADDRESS_NOT_FOUND = "Address Not Found";

    final String WAREHOUSE_NOT_FOUND = "Warehouse Not Found";
    final String WAREHOUSE_ALREADY_EXIST = "Warehouse Already Exist";

    final String INVENTORY_NOT_FOUND = "Inventory Not Found";


    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private InventoryDetailDao inventoryDetailDao;


    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        Address warehouseAddress = warehouse.getAddress();
        int addressId = warehouseAddress.getAddressId();
        addressDao.findById(addressId).orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND, addressId));
        int warehouseId = warehouse.getWarehouseId();
        boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
        if (checkWarehouseId) {
            throw new AlreadyExists(WAREHOUSE_ALREADY_EXIST, warehouseId);
        } else {
            Address address = addressDao.getReferenceById(addressId);
            Warehouse warehouseInAddress = address.getWarehouse();
            if (warehouseInAddress == null) {
                warehouse.setAddress(address);
                return warehouseDao.save(warehouse);
            } else {
                int warehouseIdInAddress = warehouseInAddress.getWarehouseId();
                throw new DataIntegrityException("address is already assigned to warehouse", warehouseIdInAddress);
            }
        }
    }


    @Override
    public List<Warehouse> getWarehouse() {
        return warehouseDao.findByStatus("active");
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId));
        return warehouseDao.findByStatusAndWarehouseId("active", warehouseId);
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {
        Warehouse updateWarehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId));
        updateWarehouse.setWarehouseName(warehouse.getWarehouseName());
        Warehouse updatedWarehouse = warehouseDao.save(updateWarehouse);
        return updatedWarehouse;
    }


    public Warehouse putInventoryInWarehouse(int warehouseId, int inventoryId) {
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId));
        String warehouseStatus = warehouse.getStatus();
        if (warehouseStatus.contains("deleted")) {
            throw new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new NotFoundException(INVENTORY_NOT_FOUND, inventoryId));
            String inventoryStatus = inventory.getStatus();
            if (inventoryStatus.contains("deleted")) {
                throw new NotFoundException(INVENTORY_NOT_FOUND, inventoryId);
            } else {
                Warehouse checkWarehouse = inventory.getWarehouse();
                if (checkWarehouse == null) {
                    inventory.setWarehouse(warehouse);
                    inventoryDetailDao.save(inventory);
                    return warehouse;
                } else {
                    int warehouseIdInInventory = checkWarehouse.getWarehouseId();
                    throw new DataIntegrityException("this inventory is already in warehouse", warehouseIdInInventory);
                }
            }
        }
    }

    @Override
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId) {
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId));
        if (warehouse.getStatus().contains("deleted")) {
            throw new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            return warehouseDao.getItemQuantityInSingleWarehouse(warehouseId);
        }
    }

    public List<ItemQuantity> getItemQuantityInAllWarehouse() {
        return warehouseDao.getItemQuantityAllWarehouses();
    }


    @Override
    public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId) {
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId));
        if (warehouse.getStatus().equals("deleted")) {
            throw new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId);
        } else {
            Set<InventoryDetail> inventoryDetails = warehouse.getInventory();
            for (InventoryDetail setItemQuantity : inventoryDetails) {
                int inventoryIdInWarehouse = setItemQuantity.getInventoryId();
                if (inventoryIdInWarehouse == inventoryId) {
                    setItemQuantity.setInStock(inventory.getInStock());
                    setItemQuantity.setAvlQty(inventory.getAvlQty());
                    warehouse.setInventory(setItemQuantity);
                    inventoryDetailDao.save(setItemQuantity);
                    return warehouseDao.save(warehouse);
                } else {
                    throw new NotFoundException(INVENTORY_NOT_FOUND, inventoryId);
                }
            }
            return null;
        }
    }


    @Override
    public void deleteWarehouse(int warehouseId) {
        Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(() -> new NotFoundException(WAREHOUSE_NOT_FOUND, warehouseId));
        Set<InventoryDetail> inventoryDetailSet = warehouse.getInventory();
        for (InventoryDetail inventory : inventoryDetailSet) {
            inventory.setStatus("deleted");
            inventoryDetailDao.save(inventory);
        }
        warehouseDao.softDelete(warehouseId);
    }
}
