package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.api.response.*;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.*;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.WarehouseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/* Exception handling */
@RestControllerAdvice
public class HandlingException {




    @ExceptionHandler(ItemTypeAlreadyExists.class)
    public ResponseEntity<?> itemTypeAlreadyExistsException(ItemTypeAlreadyExists ex) {

        int itemTypeId = ex.id;
        return new ResponseEntity<>(new ApiResponseItemType("ItemType already exists", itemTypeId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductAlreadyExists.class)
    public ResponseEntity<?> productAlreadyExistsException(ProductAlreadyExists ex) {
        int prouctId = ex.id;
        return new ResponseEntity<>(new ApiResponseProductDetail("Product already exists", prouctId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BrandAlreadyExists.class)
    public ResponseEntity<?> brandAlreadyExistsException(BrandAlreadyExists ex) {
        int brandId = ex.id;
        return new ResponseEntity<>(new ApiResponseBrand("Brand already exists", brandId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InventoryAlreadyExists.class)
    public ResponseEntity<?> inventoryAlreadyExistsException(InventoryAlreadyExists ex) {
        int inventoryId = ex.inventoryId;
        int itemId = ex.itemId;
        String itemName = ex.itemName;
        return new ResponseEntity<>(new ApiResponseInventory("Inventory is already in use by the item",inventoryId,itemId,itemName),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ItemAlreadyExists.class)
    public ResponseEntity<?> itemAlreadyExistsException(ItemAlreadyExists ex) {
        int itemId = ex.id;
        return new ResponseEntity<>(new ApiResponseItem("Item already exists", itemId), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CountryAlreadyExists.class)
    public ResponseEntity<?> countryAlreadyExistsException(CountryAlreadyExists ex){
        String countryCode = ex.countryCode;
        return  new ResponseEntity<>(new ApiResponseCountry("Country already exists",countryCode),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CityAlreadyExists.class)
    public ResponseEntity<?> cityAlreadyExistsException(CityAlreadyExists ex){
        String cityCode = ex.cityCode;
        return  new ResponseEntity<>(new ApiResponseCity("City already exists",cityCode),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WarehouseAddressAlreadyExists.class)
    public ResponseEntity<?> warehouseAddressAlreadyExistsException(WarehouseAddressAlreadyExists ex){
        long postalCode = ex.postalCode;
        return  new ResponseEntity<>(new ApiResponseWarehouseAddress("WarehouseAddress already exists",postalCode),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WarehouseAlreadyExists.class)
    public ResponseEntity<?> warehouseAlreadyExistsException(WarehouseAlreadyExists ex){
        int warehouseId = ex.id;
        return  new ResponseEntity<>(new ApiResponseWarehouse("Warehouse already exists",warehouseId),HttpStatus.CONFLICT);
    }



    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<?> warehouseNotFoundException(WarehouseNotFoundException ex) {

        int warehouseId = ex.id;
        return new ResponseEntity<>(new ApiResponseWarehouse("warehouse not exist", warehouseId), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> itemNotFoundException(ItemNotFoundException ex) {

        int itemId = ex.id;
        return new ResponseEntity<>(new ApiResponseItem("item not found", itemId), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> inventoryNotFoundException(InventoryNotFoundException ex){
        int inventoryId = ex.id;
        return new ResponseEntity<>(new ApiResponseInventory("inventory not found", inventoryId),HttpStatus.NOT_FOUND);
    }






}
