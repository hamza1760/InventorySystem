package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.*;
import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.*;
import com.inventory.system.InventorySystem.mapper.*;
import org.apache.log4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    MockData mockData = new MockData();
    MockDtoData mockDtoData = new MockDtoData();

    @Test
    public void addWarehouse() {
        when(addressDao.findById(mockData.getAddress().getAddressId())).thenReturn(Optional.of(mockData.getAddress()));
        mockDtoData.getWarehouseDto().setAddress(mockDtoData.getAddressDto());
        when(warehouseDao.save(mockData.getWarehouse())).thenReturn(mockData.getWarehouse());
        when(globalMapper.warehouseToWarehouseDto(mockData.getWarehouse())).thenReturn(mockDtoData.getWarehouseDto());
        when(globalMapper.warehouseDtoToWarehouse(mockDtoData.getWarehouseDto())).thenReturn(mockData.getWarehouse());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseService.addWarehouse(mockDtoData.getWarehouseDto()));
    }

    @Test
    public void getWarehouse() {
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        warehouses.forEach((i) -> {
            if (Objects.equals(i.getStatus(), Constants.DELETED.getValue())) {
                logger.info("warehouse not found with warehouseId: " + i.getWarehouseId());
                throw new GlobalException(Constants.WAREHOUSE_NOT_FOUND.getValue(), i.getWarehouseId());
            }
        });
        when(warehouseDao.findByStatus(Constants.ACTIVE.getValue())).thenReturn(warehouses);
        assertEquals(warehouses.size(), warehouseService.getWarehouse().size());
    }

    @Test
    public void getWarehouseById() {
        int id = 1;
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        warehouses.forEach((i) -> {
            if (id == i.getWarehouseId()) {
                when(warehouseDao.findById(id)).thenReturn(Optional.of(i));
                when(warehouseDao.findByStatusAndWarehouseId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        when(globalMapper.warehouseToWarehouseDto(mockData.getWarehouse())).thenReturn(mockDtoData.getWarehouseDto());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseService.getWarehouseById(id));
    }

    @Test
    public void putInventoryInWarehouse() {
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        int warehouseId = 1;
        int inventoryId = 1;
        warehouses.forEach((war) -> {
            if (warehouseId == war.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(war));
            }
        });
        Set<InventoryDetailDto> inventoryDetailsDto = Set.of(mockDtoData.getInventoryDetailDto());
        inventoryDetailsDto.forEach((inv) -> {
            if (inventoryId == inv.getInventoryId()) {
                when(inventoryDetailDao.findById(inventoryId)).thenReturn(Optional.of(mockData.getInventoryDetail()));
            }
        });
        when(inventoryDetailDao.save(mockData.getInventoryDetail())).thenReturn(mockData.getInventoryDetail());
        when(globalMapper.warehouseToWarehouseDto(mockData.getWarehouse())).thenReturn(mockDtoData.getWarehouseDto());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseService.putInventoryInWarehouse(inventoryDetailsDto, warehouseId));
    }

    @Test
    public void getItemQuantityAllWarehouses() {
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        warehouses.forEach(warehouse -> warehouse.setInventory(mockData.getInventoryDetail()));
        List<ItemQuantityDto> itemQuantityList = List.of(mockDtoData.getItemQuantityDto());
        when(warehouseDao.findAll()).thenReturn(warehouses);
        when(warehouseDao.getItemQuantityAllWarehouses(Constants.ACTIVE.getValue())).thenReturn(List.of(mockData.getItemQuantity()));
        when(globalMapper.itemQuantityToItemQuantityDto(mockData.getItemQuantity())).thenReturn(mockDtoData.getItemQuantityDto());
        assertEquals(itemQuantityList, warehouseService.getItemQuantityInAllWarehouses());
    }

    @Test
    public void getItemQuantityInSingleWarehouse() {
        int warehouseId = 1;
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        List<ItemQuantityDto> itemQuantityList = List.of(mockDtoData.getItemQuantityDto());
        warehouses.forEach((i) -> {
            if (warehouseId == i.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(i));
                i.setInventory(mockData.getInventoryDetail());
            }
        });
        when(warehouseDao.getItemQuantityInSingleWarehouse(Constants.ACTIVE.getValue(), warehouseId)).thenReturn(List.of(mockData.getItemQuantity()));
        when(globalMapper.itemQuantityToItemQuantityDto(mockData.getItemQuantity())).thenReturn(mockDtoData.getItemQuantityDto());
        assertEquals(itemQuantityList, warehouseService.getItemQuantityInSingleWarehouse(warehouseId));
    }

    @Test
    public void setItemQuantityInSingleWarehouse() {
        int warehouseId = 1;
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        warehouses.forEach((i) -> {
            if (warehouseId == i.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(i));
                i.setInventory(mockData.getInventoryDetail());
                when(globalMapper.warehouseToWarehouseDto(i)).thenReturn(mockDtoData.getWarehouseDto());
            }
        });
        when(inventoryDetailDao.save(mockData.getInventoryDetail())).thenReturn(mockData.getInventoryDetail());
        when(warehouseDao.save(mockData.getWarehouse())).thenReturn(mockData.getWarehouse());
        when(globalMapper.inventoryDetailDtoToInventoryDetail(mockDtoData.getInventoryDetailDto())).thenReturn(mockData.getInventoryDetail());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseService.setItemQuantityInSingleWarehouse(mockDtoData.getInventoryDetailDto(), warehouseId));
    }

    @Test
    public void deleteWarehouseById() {
        int id = 1;
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        warehouses.forEach((i) -> {
            if (id == i.getWarehouseId()) {
                when(warehouseDao.findById(id)).thenReturn(Optional.of(i));
                Set<InventoryDetail> inventoryDetails = i.getInventory();
                warehouseService.deleteWarehouseById(id);
                verify(warehouseDao, times(1)).softDelete(Constants.DELETED.getValue(), id);
            }
        });
    }

    @Test
    public void testGlobalException() {
        int warehouseId = 4;
        List<Warehouse> warehouses = List.of(mockData.getWarehouse());
        warehouses.forEach((i) -> {
            if (warehouseId == i.getWarehouseId()) {
                when(warehouseDao.findById(warehouseId)).thenReturn(Optional.of(i));
            }
        });
        assertThrows(GlobalException.class, () -> warehouseService.getWarehouseById(warehouseId));
    }
}


