package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    final String BRAND_NOT_FOUND = "Brand Not Found";
    final String BRAND_ALREADY_EXIST = "Brand Already Exist";

    @Autowired
    private BrandDetailDao brandDetailDao;

    @Override
    public List<BrandDetail> getBrand() {
        return brandDetailDao.findAll();
    }

    @Override
    public BrandDetail getBrandById(int brandId) {
        return brandDetailDao.findById(brandId).orElseThrow(() -> new NotFoundException(BRAND_NOT_FOUND, brandId));
    }

    @Override
    public BrandDetail addBrand(BrandDetail brandDetail) {
        int brandId = brandDetail.getBrandId();
        boolean checkId = brandDetailDao.findById(brandId).isPresent();
        if (checkId) {
            throw new AlreadyExists(BRAND_ALREADY_EXIST, brandId);
        } else {
            return brandDetailDao.save(brandDetail);
        }
    }


    @Override
    public void deleteBrand(int brandId) {
        BrandDetail brandDetail = brandDetailDao.findById(brandId).orElseThrow(() -> new NotFoundException(BRAND_NOT_FOUND, brandId));
        brandDetailDao.delete(brandDetail);
    }
}
