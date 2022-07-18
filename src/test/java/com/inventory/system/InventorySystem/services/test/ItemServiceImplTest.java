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
import org.mockito.quality.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        List<Item> itemList = Arrays.asList(mockData.getItem());
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
        List<ItemSize> itemSizes = Arrays.asList(mockData.getItemSize());
        List<InventoryDetail> inventoryDetails = Arrays.asList(mockData.getInventoryDetail());
        when(inventoryDetailDao.findAll()).thenReturn(inventoryDetails);
        when(itemDao.getAllItemSize(Constants.ACTIVE.getValue())).thenReturn(itemSizes);
        assertEquals(itemSizes, itemService.getAllItemSize());
    }

    @Test
    public void getItemSizeById() {
        Set<InventoryDetail> inventoryDetails = Set.of(mockData.getInventoryDetail());
        List<ItemSize> itemSizes = Arrays.asList(mockData.getItemSize());
        int id = 1;
        mockData.getItem().setInventory(inventoryDetails);
        List<Item> itemList = Arrays.asList(mockData.getItem());
        itemList.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
            }
        });
        when(itemDao.getItemSizeById(Constants.ACTIVE.getValue(), id)).thenReturn((itemSizes));
        assertEquals(itemSizes, itemService.getItemSizeById(id));
    }

    @Test
    public void testItemNotFoundException() {
        int id = 4;
        List<Item> items = Arrays.asList(mockData.getItem());
        items.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                when(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertThrows(GlobalException.class, () -> itemService.getItemById(id));
    }
}



