package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {


    static Logger logger = LoggerFactory.getLogger(ProductTypeService.class);

    @Autowired
    private ProductTypeDao productTypeDao;

    @Autowired
    private BrandDetailDao brandDetailDao;

    @Autowired
    private ItemTypeDao itemTypeDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private InventoryDetailDao inventoryDetailDao;


    @Override
    public List<ProductType> getProductType() {
        return productTypeDao.findByStatus("active");
    }

    @Override
    public ProductType getProductTypeById(int productTypeId) {
        productTypeDao.findById(productTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId));
        logger.info("product fetched");
        return productTypeDao.findByStatusAndProductTypeId("active", productTypeId);
    }

    @Override
    public ProductType addProductType(ProductType productDetail) {
        return productTypeDao.save(productDetail);
    }


    @Override
    public void deleteProductType(int productTypeId) {
        productTypeDao.findById(productTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId));
        productTypeDao.softDelete(productTypeId);
    }
}



