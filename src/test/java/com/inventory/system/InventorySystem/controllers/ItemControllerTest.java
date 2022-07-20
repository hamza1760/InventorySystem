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
public class
ItemControllerTest {

    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;

    MockDtoData mockDtoData = new MockDtoData();

    @Test
    public void addItem() {
        when(itemService.addItem(mockDtoData.getItemDto())).thenReturn(mockDtoData.getItemDto());
        assertEquals(mockDtoData.getItemDto(), itemController.addItem(mockDtoData.getItemDto()));
    }

    @Test
    public void getItem() {
        when(itemService.getItem()).thenReturn(List.of(mockDtoData.getItemDto()));
        assertEquals(1, itemController.getItem().size());
    }

    @Test
    public void getItemById() {
        int id = 1;
        when(itemService.getItemById(id)).thenReturn(mockDtoData.getItemDto());
        assertEquals(new ResponseEntity<>(mockDtoData.getItemDto(), HttpStatus.FOUND), itemController.getItemById(id));
    }

    @Test
    public void getAllItemSize() {
        when(itemService.getAllItemSize()).thenReturn(List.of(mockDtoData.getItemSizeDto()));
        assertEquals(1, itemController.getAllItemSize().size());
    }

    @Test
    public void getItemSizeById() {
        int id = 1;
        when(itemService.getItemSizeById(id)).thenReturn(List.of(mockDtoData.getItemSizeDto()));
        assertEquals(1, itemController.getItemSizeById(id).size());
    }

    @Test
    public void deleteItemById() {
        int id = 1;
        assertEquals(new ResponseEntity<>(new ApiResponse(Constants.ITEM_DELETED.getValue(), id), HttpStatus.FOUND), itemController.deleteItemById(id));
        verify(itemService, times(1)).deleteItemById(id);
    }
}
