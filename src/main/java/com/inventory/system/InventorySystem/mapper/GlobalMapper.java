package com.inventory.system.InventorySystem.mapper;

import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
