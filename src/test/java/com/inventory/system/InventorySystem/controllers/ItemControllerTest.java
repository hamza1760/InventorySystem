package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.*;
import com.inventory.system.InventorySystem.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;

    MockData mockData = new MockData();
    MockDtoData mockDtoData = new MockDtoData();

    @Test
    public void getItem() {
        when(itemService.getItem()).thenReturn(List.of(mockDtoData.getItemDto()));
        Assertions.assertEquals(1, itemController.getItem().size());
    }
}
