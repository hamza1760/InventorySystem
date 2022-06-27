package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
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
        return cityDetailDao.findById(cityId).orElseThrow(() -> new NotFoundException(NotFoundConstant.CITY_NOT_FOUND, cityId));
    }


    @Override
    public CityDetail addCity(CityDetail cityDetail, int countryId) {
        CountryDetail countryDetail = countryDetailDao.findById(countryId).orElseThrow(() -> new NotFoundException(NotFoundConstant.COUNTRY_NOT_FOUND, countryId));
        String status = countryDetail.getStatus();
        if (status.equals("deleted")) {
            throw new DataIntegrityViolationException("Country is soft deleted");
        } else {
            int cityId = cityDetail.getCityId();
            boolean checkCode = cityDetailDao.findById(cityId).isPresent();
            if (checkCode) {
                throw new AlreadyExists(AlreadyExistsConstant.CITY_ALREADY_EXISTS, cityId);
            } else {
                return cityDetailDao.save(cityDetail);
            }
        }
    }

    @Override
    public void deleteCity(int cityId) {
        CityDetail cityDetail = cityDetailDao.findById(cityId).orElseThrow(() -> new NotFoundException(NotFoundConstant.CITY_NOT_FOUND, cityId));
        cityDetailDao.delete(cityDetail);
    }
}
