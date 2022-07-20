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
public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    MockDtoData mockDtoData = new MockDtoData();

    @Test
    public void addInventory() {
        when(inventoryService.addInventory(mockDtoData.getInventoryDetailDto())).thenReturn(mockDtoData.getInventoryDetailDto());
        assertEquals(mockDtoData.getInventoryDetailDto(), inventoryController.addInventory(mockDtoData.getInventoryDetailDto()));
    }

    @Test
    public void getInventory() {
        when(inventoryService.getInventory()).thenReturn(List.of(mockDtoData.getInventoryDetailDto()));
        assertEquals(1, inventoryController.getInventory().size());
    }

    @Test
    public void getInventoryById() {
        int id = 1;
        when(inventoryService.getInventoryById(id)).thenReturn(mockDtoData.getInventoryDetailDto());
        assertEquals(mockDtoData.getInventoryDetailDto(), inventoryController.getInventoryById(id));
    }

    @Test
    public void setItemQuantityInAllWarehouse() {
        when(inventoryService.setItemQuantityInAllWarehouses(mockDtoData.getInventoryDetailDto())).thenReturn(mockDtoData.getInventoryDetailDto());
        assertEquals(mockDtoData.getInventoryDetailDto(), inventoryController.setItemQuantityInAllWarehouses(mockDtoData.getInventoryDetailDto()));
    }

    @Test
    public void deleteInventoryById() {
        int id = 1;
        assertEquals(new ResponseEntity<>(new ApiResponse(Constants.INVENTORY_DELETED.getValue(), id), HttpStatus.FOUND), inventoryController.deleteInventoryById(id));
        verify(inventoryService, times(1)).deleteInventoryById(id);
    }
}
