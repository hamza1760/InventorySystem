package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import com.inventory.system.InventorySystem.services.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    static Logger logger = LoggerFactory.getLogger(InventoryServiceImplTest.class);

    @Mock
    private InventoryDetailDao inventoryDetailDao;

    @Mock
    private ItemDao itemDao;

    @Mock
    private ItemTypeDao itemTypeDao;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    //inventory entity
    InventoryDetail inventory1 = new InventoryDetail(1, "small", 40, 20, 35, 70,
            10, 60, StatusConstant.ACTIVE.getValue());

    InventoryDetail inventory2 = new InventoryDetail(2, "medium", 40, 20, 35, 70,
            10, 60, StatusConstant.ACTIVE.getValue());

    InventoryDetail inventory3 = new InventoryDetail(3, "large", 40, 20, 35, 70,
            10, 60, StatusConstant.ACTIVE.getValue());

    //item entity
    Item item = new Item(1, "AdidasShoe", StatusConstant.ACTIVE.getValue());

    //itemType entity
    ItemType itemType = new ItemType(StatusConstant.ACTIVE.getValue(), 3, "Finished Product");


    @Test
    public void addInventory() {
        when(itemDao.findById(item.getItemId())).thenReturn(Optional.of(item));
        when(itemTypeDao.findById(itemType.getItemTypeId())).thenReturn(Optional.of(itemType));
        inventory1.setItem(item);
        inventory1.setItemType(itemType);
        when(inventoryDetailDao.save(inventory1)).thenReturn(inventory1);
        assertEquals(inventory1, inventoryService.addInventory(inventory1));
    }

    @Test
    public void getInventory() {
        List<InventoryDetail> inventoryDetails = Arrays.asList(inventory1, inventory2, inventory3);
        inventoryDetails.forEach((i -> {
            if (Objects.equals(i.getStatus(), StatusConstant.DELETED.getValue())) {
                logger.info("inventory not found with inventoryId: "+i.getInventoryId());
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, i.getInventoryId());
            }
        }));
        when(inventoryDetailDao.findByStatus(StatusConstant.ACTIVE.getValue())).thenReturn(inventoryDetails);
        assertEquals(inventoryDetails.size(), inventoryService.getInventory().size());
    }

    @Test
    public void getInventoryById() {
        int id = 4;
        List<InventoryDetail> inventoryDetails = Arrays.asList(inventory1, inventory2, inventory3);
        inventoryDetails.forEach((i) -> {
            if (id == i.getInventoryId()) {
                when(inventoryDetailDao.findById(id)).thenReturn(Optional.of(i));
                when(inventoryDetailDao.findByStatusAndInventoryId(StatusConstant.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertEquals(inventory3, inventoryService.getInventoryById(id));
    }

    @Test
    public void setItemQuantityInAllWarehouse() {
        InventoryDetail updateInventory = new InventoryDetail(1, "small", 50, 100, 35, 70,
                10, 60, StatusConstant.ACTIVE.getValue());
        when(inventoryDetailDao.findById(inventory1.getInventoryId())).thenReturn(Optional.of(inventory1));
        inventory1.setInStock(updateInventory.getInStock());
        inventory1.setAvlQty(updateInventory.getAvlQty());
        when(inventoryDetailDao.save(inventory1)).thenReturn(inventory1);
        assertEquals(inventory1, inventoryService.setItemQuantityInAllWarehouses(inventory1, inventory1.getInventoryId()));
    }

    @Test
    public void deleteInventory() {
        int id = 3;
        List<InventoryDetail> inventoryDetails = Arrays.asList(inventory1, inventory2, inventory3);
        inventoryDetails.forEach((i)->{
            if(id==i.getInventoryId()){
                when(inventoryDetailDao.findById(id)).thenReturn(Optional.of(i));
            }
        });

        inventoryService.deleteInventoryById(id);
        verify(inventoryDetailDao, times(1)).softDelete(StatusConstant.DELETED.getValue(), id);
    }

    @Test
    public void testInventoryNotFoundException() {
        int id = 4;
        List<InventoryDetail> inventoryDetails = Arrays.asList(inventory1, inventory2, inventory3);
        inventoryDetails.forEach((i) -> {
            if (id == i.getInventoryId()) {
                when(inventoryDetailDao.findById(id)).thenReturn(Optional.of(i));
                when(inventoryDetailDao.findByStatusAndInventoryId(StatusConstant.ACTIVE.getValue(), id)).thenReturn(i);
            }
        });
        assertThrows(NotFoundException.class, () -> inventoryService.getInventoryById(id));
    }
}
