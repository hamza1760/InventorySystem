package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {


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
        return addressDao.findById(addressId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ADDRESS_NOT_FOUND, addressId));
    }

    @Override
    public Address addAddress(Address address, int cityId) {
        cityDetailDao.findById(cityId).orElseThrow(() -> new NotFoundException(NotFoundConstant.CITY_NOT_FOUND, cityId));
        int addressId = address.getAddressId();
        boolean checkAddressId = addressDao.findById(addressId).isPresent();
        if (checkAddressId) {
            throw new AlreadyExists(AlreadyExistsConstant.ADDRESS_ALREADY_EXISTS, addressId);
        } else {
            return addressDao.save(address);
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        addressDao.findById(addressId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ADDRESS_NOT_FOUND, addressId));
        addressDao.softDelete(StatusConstant.DELETED.getValue(), addressId);
    }
}
