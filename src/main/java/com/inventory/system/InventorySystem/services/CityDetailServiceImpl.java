package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.dao.CountryDetailDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.CityAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.CityNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CityDetailServiceImpl implements CityDetailService {

	@Autowired
	private CityDetailDao cityDetailDao;

	@Autowired
	private CountryDetailDao countryDetailDao;


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
	public CityDetail addCity(CityDetail cityDetail , int countryId) {
		CountryDetail countryDetail = countryDetailDao.findById(countryId).orElseThrow(() -> new CountryNotFoundException(countryId));
		String status = countryDetail.getStatus();
		if (status.equals("deleted")) {

			throw new DataIntegrityViolationException("Country is soft deleted");
		} else {
			int cityId = cityDetail.getCityId();
			boolean checkCode = cityDetailDao.findById(cityId).isPresent();
			if (checkCode == true) {
				throw new CityAlreadyExists(cityId);
			} else {
				return cityDetailDao.save(cityDetail);
			}
		}
	}

	@Override
	public void deleteCity(int cityId) {
		CityDetail cityDetail = cityDetailDao.findById(cityId).orElseThrow(()-> new CityNotFoundException(cityId));
		cityDetailDao.delete(cityDetail);
		
	}


}
