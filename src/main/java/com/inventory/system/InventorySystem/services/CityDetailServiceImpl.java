package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.dao.CountryDetailDao;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityDetailServiceImpl implements CityDetailService {

    final String CITY_NOT_FOUND = "City Not Found";
    final String CITY_ALREADY_EXIST = "City Already Exist";

    final String COUNTRY_NOT_FOUND = "Country Not Found";

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
        return cityDetailDao.findById(cityId).orElseThrow(() -> new NotFoundException(CITY_NOT_FOUND, cityId));
    }


    @Override
    public CityDetail addCity(CityDetail cityDetail, int countryId) {
        CountryDetail countryDetail = countryDetailDao.findById(countryId).orElseThrow(() -> new NotFoundException(COUNTRY_NOT_FOUND, countryId));
        String status = countryDetail.getStatus();
        if (status.equals("deleted")) {
            throw new DataIntegrityViolationException("Country is soft deleted");
        } else {
            int cityId = cityDetail.getCityId();
            boolean checkCode = cityDetailDao.findById(cityId).isPresent();
            if (checkCode) {
                throw new AlreadyExists(CITY_ALREADY_EXIST, cityId);
            } else {
                return cityDetailDao.save(cityDetail);
            }
        }
    }

    @Override
    public void deleteCity(int cityId) {
        CityDetail cityDetail = cityDetailDao.findById(cityId).orElseThrow(() -> new NotFoundException(CITY_NOT_FOUND, cityId));
        cityDetailDao.delete(cityDetail);
    }
}
