package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.BrandAlreadyExists;
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
	public BrandDetail getBrandById() {
		// TODO Auto-generated method stub
		return null;
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
	public void deleteBrand() {
		// TODO Auto-generated method stub
		
	}

}
