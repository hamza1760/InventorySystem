package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.api.response.*;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.*;
import com.inventory.system.InventorySystem.exceptions.notfound.*;
import com.inventory.system.InventorySystem.services.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/* Exception handling */
@RestControllerAdvice
public class HandlingException {

    static Logger logger = LoggerFactory.getLogger(HandlingException.class);




    @ExceptionHandler(ItemTypeAlreadyExists.class)
    public ResponseEntity<?> itemTypeAlreadyExistsException(ItemTypeAlreadyExists ex) {

        int itemTypeId = ex.id;
        return new ResponseEntity<>(new ApiResponseItemType("ItemType already exists", itemTypeId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductTypeAlreadyExists.class)
    public ResponseEntity<?> productTypeAlreadyExistsException(ProductTypeAlreadyExists ex) {
        int productTypeId = ex.id;
        return new ResponseEntity<>(new ApiResponseProductType("Product already exists", productTypeId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BrandAlreadyExists.class)
    public ResponseEntity<?> brandAlreadyExistsException(BrandAlreadyExists ex) {
        int brandId = ex.id;
        return new ResponseEntity<>(new ApiResponseBrand("Brand already exists", brandId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InventoryAlreadyExists.class)
    public ResponseEntity<?> inventoryAlreadyExistsException(InventoryAlreadyExists ex) {
        int inventoryId = ex.inventoryId;
        return new ResponseEntity<>(new ApiResponseInventory("Inventory already exist",inventoryId),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ItemAlreadyExists.class)
    public ResponseEntity<?> itemAlreadyExistsException(ItemAlreadyExists ex) {
        int itemId = ex.id;
        return new ResponseEntity<>(new ApiResponseItem("Item already exists", itemId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CountryAlreadyExists.class)
    public ResponseEntity<?> countryAlreadyExistsException(CountryAlreadyExists ex){
        int countryId = ex.countryId;
        return  new ResponseEntity<>(new ApiResponseCountry("Country already exists",countryId),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CityAlreadyExists.class)
    public ResponseEntity<?> cityAlreadyExistsException(CityAlreadyExists ex){
        int cityId = ex.cityId;
        return  new ResponseEntity<>(new ApiResponseCity("City already exists",cityId),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AddressAlreadyExists.class)
    public ResponseEntity<?> warehouseAddressAlreadyExistsException(AddressAlreadyExists ex){
        int addressId = ex.addressId;
        return  new ResponseEntity<>(new ApiResponseAddress("WarehouseAddress already exists",addressId),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WarehouseAlreadyExists.class)
    public ResponseEntity<?> warehouseAlreadyExistsException(WarehouseAlreadyExists ex){
        int warehouseId = ex.id;
        return  new ResponseEntity<>(new ApiResponseWarehouse("Warehouse already exists",warehouseId),HttpStatus.CONFLICT);
    }


    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<?> CountryNotFoundException(CountryNotFoundException ex) {

        int countryId = ex.id;
        return new ResponseEntity<>(new ApiResponseCountry("Country not found", countryId), HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<?> CityNotFoundException(CityNotFoundException ex) {

        int cityId = ex.id;
        return new ResponseEntity<>(new ApiResponseCity("City not found", cityId), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> AddressNotFoundException(AddressNotFoundException ex) {

        int addressId = ex.id;
        return new ResponseEntity<>(new ApiResponseAddress("Address not found", addressId), HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<?> warehouseNotFoundException(WarehouseNotFoundException ex) {

        int warehouseId = ex.id;
        return new ResponseEntity<>(new ApiResponseWarehouse("warehouse not found", warehouseId), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<?> BrandNotFoundException(BrandNotFoundException ex) {

        int BrandId = ex.id;
        return new ResponseEntity<>(new ApiResponseBrand("Brand not found", BrandId), HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(ProductTypeNotFoundException.class)
    public ResponseEntity<?> ProductTypeNotFoundException(ProductTypeNotFoundException ex) {

        int productTypeId = ex.id;
        return new ResponseEntity<>(new ApiResponseProductType("Product not found", productTypeId), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ItemTypeNotFoundException.class)
    public ResponseEntity<?> ItemTypeNotFoundException(ItemTypeNotFoundException ex) {

        int itemTypeId = ex.id;
        return new ResponseEntity<>(new ApiResponseItemType("ItemType not found", itemTypeId), HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> itemNotFoundException(ItemNotFoundException ex) {

        int itemId = ex.id;
        return new ResponseEntity<>(new ApiResponseItem("item not found", itemId), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> inventoryNotFoundException(InventoryNotFoundException ex){
        int inventoryId = ex.id;
        return new ResponseEntity<>(new ApiResponseInventory("Inventory not found", inventoryId),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<?> dataIntegrityException(DataIntegrityException ex) {
        String msg = ex.msg;
        int id = ex.id;

        return new ResponseEntity<>(new ApiResponseDataIntegrity(msg,id),HttpStatus.CONFLICT);

    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> InvalidInputException(){

        logger.error("invalid input");
        return new ResponseEntity<>(new ApiResponseInvalidInput("invalid input"),HttpStatus.NO_CONTENT);
    }







}
