package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.CityAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDetailServiceImpl implements CityDetailService {

	@Autowired
	private CityDetailDao cityDetailDao;


	@Override
	public List<CityDetail> getCity() {
		return cityDetailDao.findAll();
	}

	@Override
	public CityDetail getCityById(int cityId) {
		CityDetail cityDetail = cityDetailDao.findById(cityId).orElseThrow(()-> new CityNotFoundException(cityId));
		return  cityDetail;
	}

	@Override
	public CityDetail addCity(CityDetail cityDetail) {
		int cityId = cityDetail.getCityId();
		boolean checkCode = cityDetailDao.findById(cityId).isPresent();
		if(checkCode==true){
			throw new CityAlreadyExists(cityId);
		}
		else {
			return cityDetailDao.save(cityDetail);
		}
	}

	@Override
	public void deleteCity() {
		// TODO Auto-generated method stub
		
	}


}
