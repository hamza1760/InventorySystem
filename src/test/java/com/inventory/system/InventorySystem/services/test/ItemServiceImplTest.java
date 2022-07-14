package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.GlobalException;
import com.inventory.system.InventorySystem.services.ItemServiceImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    static Logger logger = Logger.getLogger(ItemServiceImplTest.class);

    @Mock
    private ItemDao itemDao;

    @Mock
    private ProductTypeDao productTypeDao;

    @Mock
    private BrandDetailDao brandDetailDao;

    @Mock
    private InventoryDetailDao inventoryDetailDao;

    @InjectMocks
    private ItemServiceImpl itemService;

    //item entity
    Item item1 = new Item(1, "AdidasShoe", Constants.ACTIVE.getValue());
    Item item2 = new Item(2, "PumaShoe", Constants.ACTIVE.getValue());
    Item item3 = new Item(3, "NikeShoe", Constants.ACTIVE.getValue());

    //product type entity
    ProductType productType = new ProductType(Constants.ACTIVE.getValue(), 1, "Shoe");

    //brand entity
    BrandDetail brandDetail = new BrandDetail(Constants.ACTIVE.getValue(), 1, "Adidas");

    //Inventory entity
    InventoryDetail inventory1 = new InventoryDetail(1, "small", 40, 20, 35, 70,
            10, 60, Constants.ACTIVE.getValue());

    InventoryDetail inventory2 = new InventoryDetail(2, "medium", 40, 20, 35, 70,
            10, 60, Constants.ACTIVE.getValue());

    InventoryDetail inventory3 = new InventoryDetail(3, "large", 40, 20, 35, 70,
            10, 60, Constants.ACTIVE.getValue());

    //Item Size entity
    ItemSize itemSize1 = new ItemSize(1, 1, "small", "AdidasShoe", "Finished Product", "Shoe", "Adidas");
    ItemSize itemSize2 = new ItemSize(2, 1, "medium", "AdidasShoe", "Finished Product", "Shoe", "Adidas");
    ItemSize itemSize3 = new ItemSize(3, 1, "large", "AdidasShoe", "Finished Product", "Shoe", "Adidas");

    @Test
    public void addItem() {
        when(productTypeDao.findById(productType.getProductTypeId())).thenReturn(Optional.of(productType));
        when(brandDetailDao.findById(brandDetail.getBrandId())).thenReturn(Optional.of(brandDetail));
        item1.setProductType(productType);
        item1.setBrand(brandDetail);
        when(itemDao.save(item1)).thenReturn(item1);
        assertEquals(item1, itemService.addItem(item1));
    }

    @Test
    public void getItem() {
        List<Item> itemList = Arrays.asList(item1, item2, item3);
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
        int id = 3;
        List<Item> items = Arrays.asList(item1, item2, item3);
        items.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                when(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertEquals(item3, itemService.getItemById(id));
    }

    @Test
    public void updateItem() {
        Item updateItem = new Item(1, "Adidas", Constants.ACTIVE.getValue());
        when(itemDao.findById(item1.getItemId())).thenReturn(Optional.of(item1));
        when(itemDao.save(item1)).thenReturn(item1);
        Item updatedItem = itemService.updateItem(updateItem, item1.getItemId());
        assertEquals(item1.getItemName(), updatedItem.getItemName());
    }

    @Test
    public void deleteItemById() {
        int id = 3;
        List<Item> itemList = Arrays.asList(item1, item2, item3);
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
        List<ItemSize> itemSizes = Arrays.asList(itemSize1, itemSize2, itemSize3);
        List<InventoryDetail> inventoryDetails = Arrays.asList(inventory1, inventory2, inventory3);
        when(inventoryDetailDao.findAll()).thenReturn(inventoryDetails);
        when(itemDao.getAllItemSize(Constants.ACTIVE.getValue())).thenReturn(itemSizes);
        assertEquals(itemSizes, itemService.getAllItemSize());
    }

    @Test
    public void getItemSizeById() {
        Set<InventoryDetail> inventoryDetails = Set.of(inventory1, inventory2, inventory3);
        List<ItemSize> itemSizes = Arrays.asList(itemSize1, itemSize2, itemSize3);
        int id = 1;
        List<Item> itemList = Arrays.asList(item1, item2, item3);
        item1.setInventory(inventoryDetails);
        itemList.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
            }
        });
        when(itemDao.getItemSizeById(Constants.ACTIVE.getValue(), id)).thenReturn((itemSizes));
        assertEquals(itemSizes, itemService.getItemSizeById(id));
    }

    @Test
    public void testItemGlobalException() {
        int id = 4;
        List<Item> items = Arrays.asList(item1, item2, item3);
        items.forEach((i) -> {
            if (id == i.getItemId()) {
                when(itemDao.findById(id)).thenReturn(Optional.of(i));
                when(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertThrows(GlobalException.class, () -> itemService.getItemById(id));
    }
}



