package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.dto.BrandDetailDto;
import com.inventory.system.InventorySystem.dto.ItemDto;
import com.inventory.system.InventorySystem.dto.ProductTypeDto;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;
import com.inventory.system.InventorySystem.exceptions.GlobalException;
import com.inventory.system.InventorySystem.mapper.GlobalMapper;
import com.inventory.system.InventorySystem.services.ItemServiceImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    static Logger logger = Logger.getLogger(ItemServiceImplTest.class);

    MockData mockData = new MockData();

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


    ItemDto itemDto = new ItemDto(1, "AdidasShoe", Constants.ACTIVE.getValue());

    ProductTypeDto productTypeDto = new ProductTypeDto(Constants.ACTIVE.getValue(), 1, "Shoe");

    BrandDetailDto brandDetailDto = new BrandDetailDto(Constants.ACTIVE.getValue(), 1, "Adidas");

    @Test
    public void addItem() {
        when(productTypeDao.findById(mockData.getProduct().getProductTypeId())).thenReturn(Optional.of(mockData.getProduct()));
        when(brandDetailDao.findById(mockData.getBrand().getBrandId())).thenReturn(Optional.of(mockData.getBrand()));
        when(globalMapper.itemToItemDto(mockData.getItem())).thenReturn(itemDto);
        when(globalMapper.productTypeToProductTypeDto(mockData.getProduct())).thenReturn(productTypeDto);
        when(globalMapper.brandDetailToBrandDetailDto(mockData.getBrand())).thenReturn(brandDetailDto);
        itemDto.setProductType(productTypeDto);
        itemDto.setBrand(brandDetailDto);
        when(globalMapper.itemDtoItem(itemDto)).thenReturn(mockData.getItem());
        when(itemDao.save(mockData.getItem())).thenReturn(mockData.getItem());
        assertEquals(itemDto, itemService.addItem(itemDto));
    }

    @Test
    public void getItem() {
        List<Item> itemList = Arrays.asList(mockData.getItem());
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
        List<Item> items = Arrays.asList(mockData.getItem());
        items.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                when(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertEquals(mockData.getItem(), itemService.getItemById(id));
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



