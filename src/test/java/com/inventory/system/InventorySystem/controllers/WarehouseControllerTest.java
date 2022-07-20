package com.inventory.system.InventorySystem.controllers;
import com.inventory.system.InventorySystem.*;
import com.inventory.system.InventorySystem.apiresponse.*;
import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarehouseControllerTest {

    @Mock
    private WarehouseService warehouseService;

    @InjectMocks
    private WarehouseController warehouseController;

    MockDtoData mockDtoData = new MockDtoData();

    @Test
    void addWarehouse() {
        when(warehouseService.addWarehouse(mockDtoData.getWarehouseDto())).thenReturn(mockDtoData.getWarehouseDto());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseController.addWarehouse(mockDtoData.getWarehouseDto()));
    }

    @Test
    void getWarehouse() {
        when(warehouseService.getWarehouse()).thenReturn(List.of(mockDtoData.getWarehouseDto()));
        assertEquals(1, warehouseController.getWarehouse().size());
    }

    @Test
    void getWarehouseById() {
        int id = 1;
        when(warehouseService.getWarehouseById(id)).thenReturn(mockDtoData.getWarehouseDto());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseController.getWarehouseById(id));
    }

    @Test
    void putInventoryInWarehouse() {
        int id = 1;
        when(warehouseService.putInventoryInWarehouse(Set.of(mockDtoData.getInventoryDetailDto()), id)).thenReturn(mockDtoData.getWarehouseDto());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseController.putInventoryInWarehouse(Set.of(mockDtoData.getInventoryDetailDto()), id));
    }

    @Test
    void getItemQuantityInAllWarehouses() {
        when(warehouseService.getItemQuantityInAllWarehouses()).thenReturn(List.of(mockDtoData.getItemQuantityDto()));
        assertEquals(1, warehouseController.getItemQuantityInAllWarehouses().size());
    }

    @Test
    void getItemQuantityInSingleWarehouse() {
        int id = 1;
        when(warehouseService.getItemQuantityInSingleWarehouse(id)).thenReturn(List.of(mockDtoData.getItemQuantityDto()));
        assertEquals(1, warehouseController.getItemQuantityInSingleWarehouse(id).size());
    }

    @Test
    void setItemQuantityInSingleWarehouse() {
        int id = 1;
        when(warehouseService.setItemQuantityInSingleWarehouse(mockDtoData.getInventoryDetailDto(), id)).thenReturn(mockDtoData.getWarehouseDto());
        assertEquals(mockDtoData.getWarehouseDto(), warehouseController.setItemQuantityInSingleWarehouse(mockDtoData.getInventoryDetailDto(), id));
    }

    @Test
    void deleteWarehouseById() {
        int id = 1;
        assertEquals(new ResponseEntity<>(new ApiResponse(Constants.WAREHOUSE_DELETED.getValue(), id), HttpStatus.FOUND), warehouseController.deleteWarehouseById(id));
        verify(warehouseService, times(1)).deleteWarehouseById(id);
    }
}