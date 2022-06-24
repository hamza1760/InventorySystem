package com.inventory.system.InventorySystem.services.test;


import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.services.ProductTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductTypeServiceImplTest {



    @Mock
    private ProductTypeDao productTypeDao;

    @InjectMocks
    private ProductTypeServiceImpl productTypeService;


    @Test
    public void getProductTypeTest() {

        List<ProductType> productTypeList = new ArrayList<ProductType>();

        productTypeList.add(new ProductType("active", 1, "shoe"));
        productTypeList.add(new ProductType("active", 2, "shirt"));
        productTypeList.add(new ProductType("active", 3, "phone"));

        when(productTypeDao.findByStatus("active")).thenReturn(productTypeList);

        List<ProductType> productTypes = productTypeService.getProductType();

        assertEquals(3, productTypes.size());


    }

    @Test
	public void softDeleteTest(){

		ProductType productType = new ProductType("active",1,"shoe");


       when(productTypeDao.findByStatusAndProductTypeId("active",1)).thenReturn(productType);

        productTypeService.deleteProductType(productType.getProductTypeId());

        String status = productType.getStatus();

        assertEquals("deleted",status);





	}


}
