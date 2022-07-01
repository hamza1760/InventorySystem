package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import com.inventory.system.InventorySystem.services.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    static Logger logger = LoggerFactory.getLogger(ItemServiceImplTest.class);


    @Mock
    private ItemDao itemDao;

    @Mock
    private ProductTypeDao productTypeDao;

    @Mock
    private BrandDetailDao brandDetailDao;


    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void addItem() {
        Item item = new Item(1, "AdidasShoe", StatusConstant.ACTIVE.getValue());
        ProductType productType = new ProductType(StatusConstant.ACTIVE.getValue(), 1, "Shoe");
        BrandDetail brandDetail = new BrandDetail(StatusConstant.ACTIVE.getValue(), 1, "Adidas");
        when(productTypeDao.findById(1)).thenReturn(Optional.of(productType));
        when(brandDetailDao.findById(1)).thenReturn(Optional.of(brandDetail));
        item.setProductType(productType);
        item.setBrand(brandDetail);
        when(itemDao.save(item)).thenReturn(item);
        Item checkItem = itemService.addItem(item);
        assertEquals(item, checkItem);
    }

    @Test
    public void getItem() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, "AdidasShoe", StatusConstant.ACTIVE.getValue()));
        itemList.add(new Item(2, "PumaShoe", StatusConstant.DELETED.getValue()));
        itemList.add(new Item(3, "NikeShoe", StatusConstant.ACTIVE.getValue()));
        itemList.forEach((i) -> {
            if (Objects.equals(i.getStatus(), StatusConstant.DELETED.getValue())) {
                logger.info("item not found with itemId: " + i.getItemId());
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, i.getItemId());

            }


        });
        when(itemDao.findByStatus(StatusConstant.ACTIVE.getValue())).thenReturn(itemList);
        assertEquals(itemList.size(), itemService.getItem().size());

    }

    @Test
    public void getItemById() {
        Item item = new Item(3, "AdidasShoe", StatusConstant.ACTIVE.getValue());
        when(itemDao.findById(item.getItemId())).thenReturn(Optional.of(item));
        when(itemDao.findByStatusAndItemId(item.getStatus(), item.getItemId())).thenReturn(item);
        assertEquals(item, itemService.getItemById(item.getItemId()));
        assertThrows(NotFoundException.class, () -> itemService.getItemById(2));
    }

    @Test
    public void deleteItemById() {
        Item item = new Item(3, "AdidasShoe", StatusConstant.ACTIVE.getValue());
        when(itemDao.findById(3)).thenReturn(Optional.of(item));
        itemService.deleteItemById(3);
        verify(itemDao, times(1)).softDelete(StatusConstant.DELETED.getValue(), item.getItemId());
    }

    @Test
    public void testNotFoundException() {
        Item item = new Item(1, "AdidasShoe", StatusConstant.ACTIVE.getValue());
        when(itemDao.findById(1)).thenReturn(Optional.of(item));
        when(itemDao.findByStatusAndItemId(item.getStatus(), 1)).thenReturn(item);
        assertEquals(item, itemService.getItemById(1));
        assertThrows(NotFoundException.class, () -> itemService.getItemById(3));
    }
}


