package com.inventory.system.InventorySystem.controllers.city.controller;

import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.services.CityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/city/{cityId}")
    public CityDetail getCityById(@PathVariable int cityId){
        return cityDetailService.getCityById(cityId);
    }


}
