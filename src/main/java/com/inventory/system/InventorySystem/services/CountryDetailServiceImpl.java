package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.dao.CountryDetailDao;
import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryDetailServiceImpl implements CountryDetailService {


    @Autowired
    private CountryDetailDao countryDetailDao;

    @Override
    public List<CountryDetail> getCountry() {
        return countryDetailDao.findAll();
    }

    @Override
    public CountryDetail getCountryById(int countryId) {
        countryDetailDao.findById(countryId).orElseThrow(() -> new NotFoundException(NotFoundConstant.COUNTRY_NOT_FOUND, countryId));
        return countryDetailDao.getCountryById(countryId);
    }

    @Override
    public CountryDetail addCountry(CountryDetail countryDetail) {
        int countryId = countryDetail.getCountryId();
        boolean checkCode = countryDetailDao.findById(countryId).isPresent();
        if (checkCode) {
            throw new AlreadyExists(AlreadyExistsConstant.COUNTRY_ALREADY_EXISTS, countryId);
        } else {
            return countryDetailDao.save(countryDetail);
        }
    }

    @Override
    public void deleteCountry(int countryId) {
        countryDetailDao.findById(countryId).orElseThrow(() -> new NotFoundException(NotFoundConstant.COUNTRY_NOT_FOUND, countryId));
        countryDetailDao.softDelete(countryId);
    }
}


