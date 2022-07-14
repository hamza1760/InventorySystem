package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.entities.InventoryDetail;

import java.util.Arrays;
import java.util.List;

public class MockData {

    public InventoryDetail getInventoryDetail(){
        return new InventoryDetail(1, "small", 40, 20, 35, 70,
                10, 60, StatusConstant.ACTIVE.getValue());
    }

    public List<InventoryDetail> getInventoryDetails(){
        return Arrays.asList(getInventoryDetail());
    }
}
