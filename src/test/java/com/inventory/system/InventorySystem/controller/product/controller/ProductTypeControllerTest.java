package com.inventory.system.InventorySystem.controller.product.controller;


import com.inventory.system.InventorySystem.controllers.product.controller.ProductTypeController;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.services.ProductTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductTypeControllerTest {


    @InjectMocks
    private ProductTypeController productTypeController;

    @Mock
    private ProductTypeService productTypeService;

    @Test
    public void getProductTypeTest(){

        //give
        List<ProductType> productTypes = new ArrayList<ProductType>();
        productTypes.add(new ProductType("active",1,"shoe"));
        productTypes.add(new ProductType("active",2,"shirt"));
        productTypes.add(new ProductType("active",3,"phone"));

        //when then
        when(productTypeService.getProductType()).thenReturn(productTypes);

        List<ProductType> product2 = productTypeController.getProductType();
        assertEquals(3,product2.size());

    }
}
