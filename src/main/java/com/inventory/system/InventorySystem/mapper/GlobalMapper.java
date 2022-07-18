package com.inventory.system.InventorySystem.mapper;

import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class GlobalMapper {

    @Autowired
    ModelMapper modelMapper;

    public InventoryDetailDto inventoryDetailToInventoryDetailDto(InventoryDetail inventoryDetail) {
        return modelMapper.map(inventoryDetail, InventoryDetailDto.class);
    }

    public InventoryDetail inventoryDetailDtoToInventoryDetail(InventoryDetailDto inventoryDetailDto) {
        return modelMapper.map(inventoryDetailDto, InventoryDetail.class);
    }

    public ItemDto itemToItemDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    public Item itemDtoItem(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    public ProductTypeDto productTypeToProductTypeDto(ProductType productType) {
        return modelMapper.map(productType, ProductTypeDto.class);
    }

    public BrandDetailDto brandDetailToBrandDetailDto(BrandDetail brandDetail) {
        return modelMapper.map(brandDetail, BrandDetailDto.class);
    }

    public ItemSizeDto itemSizeToItemSizeDto(ItemSize itemSize) {
        return modelMapper.map(itemSize, ItemSizeDto.class);
    }

    public ItemTypeDto itemTypeToItemTypeDto(ItemType itemType) {
        return modelMapper.map(itemType, ItemTypeDto.class);
    }

    public WarehouseDto warehouseToWarehouseDto(Warehouse warehouse) {
        return modelMapper.map(warehouse, WarehouseDto.class);
    }

    public Warehouse warehouseDtoToWarehouse(WarehouseDto warehouseDto) {
        return modelMapper.map(warehouseDto, Warehouse.class);
    }

    public AddressDto addressToAddressDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    public ItemQuantityDto itemQuantityToItemQuantityDto(ItemQuantity itemQuantity) {
        return modelMapper.map(itemQuantity, ItemQuantityDto.class);
    }
}
