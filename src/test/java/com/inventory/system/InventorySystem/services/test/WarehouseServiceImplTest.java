package com.inventory.system.InventorySystem.services.test;


import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.WarehouseDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import com.inventory.system.InventorySystem.services.WarehouseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.log4j.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceImplTest {

    static Logger logger = Logger.getLogger(WarehouseServiceImplTest.class);


    @Mock
    private WarehouseDao warehouseDao;

    @Mock
    private AddressDao addressDao;

    @Mock
    private InventoryDetailDao inventoryDetailDao;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    //warehouse entity
    Warehouse warehouse1 = new Warehouse(1, "PAK", StatusConstant.ACTIVE.getValue());
    Warehouse warehouse2 = new Warehouse(2, "USA", StatusConstant.ACTIVE.getValue());
    Warehouse warehouse3 = new Warehouse(3, "UK", StatusConstant.ACTIVE.getValue());

    //address entity
    Address address = new Address(StatusConstant.ACTIVE.getValue(), 1, 75600, "clifton", "10A");

    //inventory entity
    InventoryDetail inventory1 = new InventoryDetail(1, "small", 40, 20, 35, 70,
            10, 60, StatusConstant.ACTIVE.getValue());

    InventoryDetail inventory2 = new InventoryDetail(2, "medium", 40, 20, 35, 70,
            10, 60, StatusConstant.ACTIVE.getValue());

    InventoryDetail inventory3 = new InventoryDetail(3, "large", 40, 20, 35, 70,
            10, 60, StatusConstant.ACTIVE.getValue());

    //item quantity entity
    ItemQuantity itemQuantity1 = new ItemQuantity(1, "PAK", "Clifton", "Karachi", "Pakistan", 1, "small", 40, 20, "AdidasShoe", 1, "Finished Product", "Shoe", "Adidas");
    ItemQuantity itemQuantity2 = new ItemQuantity(1, "PAK", "Clifton", "Karachi", "Pakistan", 2, "medium", 30, 50, "AdidasShoe", 1, "Finished Product", "Shoe", "Adidas");

    @Test
    public void addWarehouse() {
        when(addressDao.findById(address.getAddressId())).thenReturn(Optional.of(address));
        warehouse1.setAddress(address);
        when(warehouseDao.save(warehouse1)).thenReturn(warehouse1);
        assertEquals(warehouse1, warehouseService.addWarehouse(warehouse1));
    }

    @Test
    public void getWarehouse() {
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        warehouses.forEach((i) -> {
            if (Objects.equals(i.getStatus(), StatusConstant.DELETED.getValue())) {
                logger.info("warehouse not found with warehouseId: " + i.getWarehouseId());
                throw new NotFoundException(NotFoundConstant.WAREHOUSE_NOT_FOUND, i.getWarehouseId());
            }
        });
        when(warehouseDao.findByStatus(StatusConstant.ACTIVE.getValue())).thenReturn(warehouses);
        assertEquals(warehouses.size(), warehouseService.getWarehouse().size());
    }

    @Test
    public void getWarehouseById() {
        int id = 1;
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        warehouses.forEach((i) -> {
            if (id == i.getWarehouseId()) {
                when(warehouseDao.findById(id)).thenReturn(Optional.of(i));
                when(warehouseDao.findByStatusAndWarehouseId(StatusConstant.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertEquals(warehouse1, warehouseService.getWarehouseById(warehouse1));
    }

    @Test
    public void updateWarehouse() {
        Warehouse updateWarehouse = new Warehouse(1, "Pak", StatusConstant.ACTIVE.getValue());
        when(warehouseDao.findById(warehouse1.getWarehouseId())).thenReturn(Optional.of(warehouse1));
        when(warehouseDao.save(warehouse1)).thenReturn(warehouse1);
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(updateWarehouse, warehouse1.getWarehouseId());
        assertEquals(warehouse1.getWarehouseName(), updatedWarehouse.getWarehouseName());
    }

    @Test
    public void deleteWarehouseById() {
        int id = 1;
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        warehouse1.setInventory(inventory1);
        warehouses.forEach((i) -> {
            if (id == i.getWarehouseId()) {
                when(warehouseDao.findById(id)).thenReturn(Optional.of(i));
            }
        });
        when(inventoryDetailDao.save(inventory1)).thenReturn(inventory1);
        warehouseService.deleteWarehouseById(id);
        verify(warehouseDao, times(1)).softDelete(StatusConstant.DELETED.getValue(), id);
    }

    @Test
    public void putInventoryInWarehouse() {
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        int warehouseId = 1;
        int inventoryId = 1;
        warehouses.forEach((war) -> {
            if (warehouseId == war.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(war));
            }
        });
        Set<InventoryDetail> inventoryDetails = Set.of(inventory1);
        inventoryDetails.forEach((inv) -> {
            if (inventoryId == inv.getInventoryId()) {
                when(inventoryDetailDao.findById(inventoryId)).thenReturn(Optional.of(inv));
            }
        });
        when(inventoryDetailDao.save(inventory1)).thenReturn(inventory1);
        assertEquals(warehouse1, warehouseService.putInventoryInWarehouse(inventoryDetails,warehouseId));
    }


    @Test
    public void getItemQuantityInSingleWarehouse() {
        int warehouseId = 1;
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        warehouse1.setInventory(inventory1);
        List<ItemQuantity> itemQuantityList = Arrays.asList(itemQuantity1, itemQuantity2);
        warehouses.forEach((i) -> {
            if (warehouseId == i.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(i));
            }
        });
        when(warehouseDao.getItemQuantityInSingleWarehouse(StatusConstant.ACTIVE.getValue(), warehouseId)).thenReturn(itemQuantityList);
        assertEquals(itemQuantityList, warehouseService.getItemQuantityInSingleWarehouse(warehouseId));
    }

    @Test
    public void getItemQuantityAllWarehouses() {
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        warehouse1.setInventory(inventory1);
        List<ItemQuantity> itemQuantityList = Arrays.asList(itemQuantity1, itemQuantity2);
        when(warehouseDao.findAll()).thenReturn(warehouses);
        when(warehouseDao.getItemQuantityAllWarehouses(StatusConstant.ACTIVE.getValue())).thenReturn(itemQuantityList);
        assertEquals(itemQuantityList, warehouseService.getItemQuantityInAllWarehouses());
    }

    @Test
    public void setItemQuantityInSingleWarehouse() {
        int warehouseId = 1;
        int inventoryId = 1;
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        InventoryDetail updateInventory = new InventoryDetail(1, "small", 100, 50, 35, 70,
                10, 60, StatusConstant.ACTIVE.getValue());
        warehouse1.setInventory(inventory1);
        warehouses.forEach((i) -> {
            if (warehouseId == i.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(i));
            }
        });
        when(inventoryDetailDao.save(inventory1)).thenReturn(inventory1);
        when(warehouseDao.save(warehouse1)).thenReturn(warehouse1);
        assertEquals(warehouse1, warehouseService.setItemQuantityInSingleWarehouse(updateInventory, warehouseId, inventoryId));
    }

    @Test
    public void testNotFoundException() {
        int warehouseId = 4;
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2, warehouse3);
        warehouses.forEach((i) -> {
            if (warehouseId == i.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(i));
            }
        });
        assertThrows(NotFoundException.class, () -> warehouseService.getWarehouseById(warehouse1));
    }
}


