package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.CityAlreadyExists;
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
	public CityDetail getCityById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CityDetail addCity(CityDetail cityDetail) {
		String cityCode = cityDetail.getCityCode();
		boolean checkCode = cityDetailDao.findById(cityCode).isPresent();
		if(checkCode==true){
			throw new CityAlreadyExists(cityCode);
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
