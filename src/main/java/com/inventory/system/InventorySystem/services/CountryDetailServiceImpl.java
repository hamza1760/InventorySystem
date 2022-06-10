package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.CountryDetailDao;
import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.CountryAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryDetailServiceImpl implements CountryDetailService{

	@Autowired
	private CountryDetailDao countryDetailDao;

	@Override
	public List<CountryDetail> getCountry() {
		return countryDetailDao.findAll();

	}

	@Override
	public CountryDetail getCountryById(int countryId) {
		CountryDetail countryDetail = countryDetailDao.findById(countryId).orElseThrow(()->new CountryNotFoundException(countryId));
		return countryDetail;
	}

	@Override
	public CountryDetail addCountry(CountryDetail countryDetail) {
		int countryId = countryDetail.getCountryId();
		boolean checkCode = countryDetailDao.findById(countryId).isPresent();
		if(checkCode==true){
			throw new CountryAlreadyExists(countryId);
		}
		else {
			return countryDetailDao.save(countryDetail);
		}

	}

	@Override
	public void deleteCountry() {
		// TODO Auto-generated method stub
		
	}

}


