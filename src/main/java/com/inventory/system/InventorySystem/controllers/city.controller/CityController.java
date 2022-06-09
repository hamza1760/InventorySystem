package com.inventory.system.InventorySystem.controllers.city.controller;

import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.services.CityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/* city detail Controller*/

@RestController
public class CityController {
    @Autowired
    private CityDetailService cityDetailService;


    @PostMapping("/city")
    public CityDetail addCity(@RequestBody CityDetail cityDetail) {

        return cityDetailService.addCity(cityDetail);
    }

    @GetMapping("/city")
    public List<CityDetail> getCity(){
        return cityDetailService.getCity();
    }
}
