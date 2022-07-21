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
public class InventoryServiceImplTest {

    static Logger logger = Logger.getLogger(InventoryServiceImplTest.class);

    @Mock
    private InventoryDetailDao inventoryDetailDao;

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemTypeDao itemTypeDao;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    MockData mockData = new MockData();
    MockDtoData mockDtoData = new MockDtoData();

    @Test
    public void addInventory() {
        when(itemDao.findById(mockData.getItem().getItemId())).thenReturn(Optional.of(mockData.getItem()));
        when(itemTypeDao.findById(mockData.getItemType().getItemTypeId())).thenReturn(Optional.of(mockData.getItemType()));
        when(globalMapper.itemToItemDto(mockData.getItem())).thenReturn(mockDtoData.getItemDto());
        when(globalMapper.itemTypeToItemTypeDto(mockData.getItemType())).thenReturn(mockDtoData.getItemTypeDto());
        when(globalMapper.inventoryDetailToInventoryDetailDto(mockData.getInventoryDetail())).thenReturn(mockDtoData.getInventoryDetailDto());
        when(globalMapper.inventoryDetailDtoToInventoryDetail(mockDtoData.getInventoryDetailDto())).thenReturn(mockData.getInventoryDetail());
        when(inventoryDetailDao.save(mockData.getInventoryDetail())).thenReturn(mockData.getInventoryDetail());
        assertEquals(mockDtoData.getInventoryDetailDto(), inventoryService.addInventory(mockDtoData.getInventoryDetailDto()));
    }

    @Test
    public void getInventory() {
        List<InventoryDetail> inventoryDetails = List.of(mockData.getInventoryDetail());
        inventoryDetails.forEach((i -> {
            if (Objects.equals(i.getStatus(), Constants.DELETED.getValue())) {
                logger.info("inventory not found with inventoryId: " + i.getInventoryId());
                throw new GlobalException(Constants.INVENTORY_NOT_FOUND.getValue(), i.getInventoryId());
            }
        }));
        when(inventoryDetailDao.findByStatus(Constants.ACTIVE.getValue())).thenReturn(inventoryDetails);
        assertEquals(inventoryDetails.size(), inventoryService.getInventory().size());
    }

    @Test
    public void getInventoryById() {
        int id = 1;
        List<InventoryDetailDto> inventoryDetailsDto = List.of(mockDtoData.getInventoryDetailDto());
        inventoryDetailsDto.forEach((i) -> {
            if (id == i.getInventoryId()) {
                when(inventoryDetailDao.findById(id)).thenReturn(Optional.of(mockData.getInventoryDetail()));
                when(inventoryDetailDao.findByStatusAndInventoryId(Constants.ACTIVE.getValue(), id)).thenReturn(mockData.getInventoryDetail());
                when(globalMapper.inventoryDetailToInventoryDetailDto(mockData.getInventoryDetail())).thenReturn(mockDtoData.getInventoryDetailDto());
            }
        });
        assertEquals(mockDtoData.getInventoryDetailDto(), inventoryService.getInventoryById(id));
    }

    @Test
    public void setItemQuantityInAllWarehouses() {
        int id =1;
        when(inventoryDetailDao.findById(mockData.getInventoryDetail().getInventoryId())).thenReturn(Optional.of(mockData.getInventoryDetail()));
        when(inventoryDetailDao.save(mockData.getInventoryDetail())).thenReturn(mockData.getInventoryDetail());
        when(globalMapper.inventoryDetailToInventoryDetailDto(mockData.getInventoryDetail())).thenReturn(mockDtoData.getInventoryDetailDto());
        when(globalMapper.inventoryDetailDtoToInventoryDetail(mockDtoData.getInventoryDetailDto())).thenReturn(mockData.getInventoryDetail());
        assertEquals(mockDtoData.getInventoryDetailDto(), inventoryService.setItemQuantityInAllWarehouses(mockDtoData.getInventoryDetailDto(),id));
    }

    @Test
    public void deleteInventory() {
        int id = 1;
        List<InventoryDetail> inventoryDetails = List.of(mockData.getInventoryDetail());
        inventoryDetails.forEach((i) -> {
            if (id == i.getInventoryId()) {
                when(inventoryDetailDao.findById(id)).thenReturn(Optional.of(i));
            }
        });
        inventoryService.deleteInventoryById(id);
        verify(inventoryDetailDao, times(1)).softDelete(Constants.DELETED.getValue(), id);
    }

    @Test
    public void testInventoryGlobalException() {
        int id = 4;
        List<InventoryDetail> inventoryDetails = List.of(mockData.getInventoryDetail());
        inventoryDetails.forEach((i) -> {
            if (id == i.getInventoryId()) {
                when(inventoryDetailDao.findById(id)).thenReturn(Optional.of(i));
                when(inventoryDetailDao.findByStatusAndInventoryId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertThrows(GlobalException.class, () -> inventoryService.getInventoryById(id));
    }
}
