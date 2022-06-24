package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    final String ADDRESS_NOT_FOUND = "Address Not Found";
    final String ADDRESS_ALREADY_EXIST = "Address Already Exist";

    final String CITY_NOT_FOUND = "City Not Found";


    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CityDetailDao cityDetailDao;

    @Override
    public List<Address> getAddress() {
        return addressDao.findAll();
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressDao.findById(addressId).orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND, addressId));
    }

    @Override
    public Address addAddress(Address address, int cityId) {
        CityDetail cityDetail = cityDetailDao.findById(cityId).orElseThrow(() -> new NotFoundException(CITY_NOT_FOUND, cityId));
        int addressId = address.getAddressId();
        boolean checkPostalCode = addressDao.findById(addressId).isPresent();
        if (checkPostalCode) {
            throw new AlreadyExists(ADDRESS_ALREADY_EXIST, addressId);
        } else {
            return addressDao.save(address);
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        Address address = addressDao.findById(addressId).orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND, addressId));
        addressDao.delete(address);
    }
}
