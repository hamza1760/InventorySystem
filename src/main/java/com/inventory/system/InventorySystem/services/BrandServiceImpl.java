package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.BrandAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.BrandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		BrandDetail brandDetail = brandDetailDao.findById(brandId).orElseThrow(()-> new BrandNotFoundException(brandId));
		return brandDetail;
	}

	@Override
	public BrandDetail addBrand(BrandDetail brandDetail) {
		int brandId= brandDetail.getBrandId();
		boolean checkId = brandDetailDao.findById(brandId).isPresent();
		if(checkId==true){
			throw new BrandAlreadyExists(brandId);
		}
		else {
			return brandDetailDao.save(brandDetail);
		}
	}

	@Override
	public void deleteBrand(int brandId) {
		BrandDetail brandDetail = brandDetailDao.findById(brandId).orElseThrow(()-> new BrandNotFoundException(brandId));
		brandDetailDao.delete(brandDetail);

		
	}

}
