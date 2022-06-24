package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.CountryDetailDao;
import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryDetailServiceImpl implements CountryDetailService{

	final String COUNTRY_NOT_FOUND = "Country Not Found";
	final String COUNTRY_ALREADY_EXIST = "Country Already Exist";

	@Autowired
	private CountryDetailDao countryDetailDao;

	@Override
	public List<CountryDetail> getCountry() {
		return countryDetailDao.findAll();

	}

	@Override
	public CountryDetail getCountryById(int countryId) {
		countryDetailDao.findById(countryId).orElseThrow(()->new NotFoundException(COUNTRY_NOT_FOUND,countryId));
		return countryDetailDao.getCountryById(countryId);
	}

	@Override
	public CountryDetail addCountry(CountryDetail countryDetail) {
		int countryId = countryDetail.getCountryId();
		boolean checkCode = countryDetailDao.findById(countryId).isPresent();
		if(checkCode==true){
			throw new AlreadyExists(COUNTRY_ALREADY_EXIST,countryId);
		}
		else {
			return countryDetailDao.save(countryDetail);
		}

	}

	@Override
	public void deleteCountry(int countryId) {
		countryDetailDao.findById(countryId).orElseThrow(()-> new NotFoundException(COUNTRY_NOT_FOUND,countryId));
		countryDetailDao.softDelete(countryId);
		
	}



}


