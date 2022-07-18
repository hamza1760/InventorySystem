package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.*;
import com.inventory.system.InventorySystem.mapper.*;
import com.inventory.system.InventorySystem.services.*;
import org.apache.log4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    static Logger logger = Logger.getLogger(ItemServiceImplTest.class);

    MockData mockData = new MockData();
    MockDtoData mockDtoData = new MockDtoData();

    @Mock
    private ItemDao itemDao;

    @Mock
    private ProductTypeDao productTypeDao;

    @Mock
    private BrandDetailDao brandDetailDao;

    @Mock
    private InventoryDetailDao inventoryDetailDao;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void addItem() {
        when(productTypeDao.findById(mockData.getProduct().getProductTypeId())).thenReturn(Optional.of(mockData.getProduct()));
        when(brandDetailDao.findById(mockData.getBrand().getBrandId())).thenReturn(Optional.of(mockData.getBrand()));
        when(globalMapper.itemToItemDto(mockData.getItem())).thenReturn(mockDtoData.getItemDto());
        when(globalMapper.productTypeToProductTypeDto(mockData.getProduct())).thenReturn(mockDtoData.getProductTypeDto());
        when(globalMapper.brandDetailToBrandDetailDto(mockData.getBrand())).thenReturn(mockDtoData.getBrandDetailDto());
        mockDtoData.getItemDto().setProductType(mockDtoData.getProductTypeDto());
        mockDtoData.getItemDto().setBrand(mockDtoData.getBrandDetailDto());
        when(globalMapper.itemDtoItem(mockDtoData.getItemDto())).thenReturn(mockData.getItem());
        when(itemDao.save(mockData.getItem())).thenReturn(mockData.getItem());
        assertEquals(mockDtoData.getItemDto(), itemService.addItem(mockDtoData.getItemDto()));
    }

    @Test
    public void getItem() {
        List<Item> itemList = List.of(mockData.getItem());
        itemList.forEach((i) -> {
            if (Objects.equals(i.getStatus(), Constants.DELETED.getValue())) {
                logger.info("item not found with itemId: " + i.getItemId());
                throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), i.getItemId());
            }
        });
        when(itemDao.findByStatus(Constants.ACTIVE.getValue())).thenReturn(itemList);
        assertEquals(itemList.size(), itemService.getItem().size());
    }

    @Test
    public void getItemById() {
        int id = 1;
        List<Item> items = List.of(mockData.getItem());
        items.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                when(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
                when(globalMapper.itemToItemDto(i)).thenReturn(mockDtoData.getItemDto());
            }
        });
        assertEquals(mockDtoData.getItemDto(), itemService.getItemById(id));
    }

    @Test
    public void deleteItemById() {
        int id = 1;
        List<Item> itemList = List.of(mockData.getItem());
        itemList.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
            }
        });
        itemService.deleteItemById(id);
        verify(itemDao, times(1)).softDelete(Constants.DELETED.getValue(), id);
    }

    @Test
    public void getAllItemSize() {
        List<ItemSize> itemSizes = List.of(mockData.getItemSize());
        List<ItemSizeDto> itemSizesDto = List.of(mockDtoData.getItemSizeDto());
        List<InventoryDetail> inventoryDetails = List.of(mockData.getInventoryDetail());
        when(inventoryDetailDao.findAll()).thenReturn(inventoryDetails);
        when(itemDao.getAllItemSize(Constants.ACTIVE.getValue())).thenReturn(itemSizes);
        System.out.println(List.of(mockData.getItemSize()));
        when(globalMapper.itemSizeToItemSizeDto(mockData.getItemSize())).thenReturn(mockDtoData.getItemSizeDto());
        assertEquals(itemSizesDto, itemService.getAllItemSize());
    }

    @Test
    public void getItemSizeById() {
        Set<InventoryDetail> inventoryDetails = Set.of(mockData.getInventoryDetail());
        List<ItemSize> itemSizes = List.of(mockData.getItemSize());
        itemSizes.forEach((size) -> when(globalMapper.itemSizeToItemSizeDto(size)).thenReturn(mockDtoData.getItemSizeDto()));
        int id = 1;
        List<Item> itemList = List.of(mockData.getItem());
        itemList.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                i.setInventory(inventoryDetails);
            }
        });
        when(itemDao.getItemSizeById(Constants.ACTIVE.getValue(), id)).thenReturn(itemSizes);
        assertEquals(List.of(mockDtoData.getItemSizeDto()), itemService.getItemSizeById(id));
    }

    @Test
    public void testItemNotFoundException() {
        int id = 4;
        List<Item> items = List.of(mockData.getItem());
        items.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                when(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertThrows(GlobalException.class, () -> itemService.getItemById(id));
    }
}



