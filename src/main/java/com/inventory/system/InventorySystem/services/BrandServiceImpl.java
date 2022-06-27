package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private BrandDetailDao brandDetailDao;

    @Override
    public List<BrandDetail> getBrand() {
        return brandDetailDao.findAll();
    }

    @Override
    public BrandDetail getBrandById(int brandId) {
        return brandDetailDao.findById(brandId).orElseThrow(() -> new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId));
    }

    @Override
    public BrandDetail addBrand(BrandDetail brandDetail) {
        int brandId = brandDetail.getBrandId();
        boolean checkId = brandDetailDao.findById(brandId).isPresent();
        if (checkId) {
            throw new AlreadyExists(AlreadyExistsConstant.BRAND_ALREADY_EXISTS, brandId);
        } else {
            return brandDetailDao.save(brandDetail);
        }
    }


    @Override
    public void deleteBrand(int brandId) {
        BrandDetail brandDetail = brandDetailDao.findById(brandId).orElseThrow(() -> new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId));
        brandDetailDao.delete(brandDetail);
    }
}
