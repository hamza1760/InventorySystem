package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.apiresponse.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/signup")
    public UserDto addUser(@RequestBody UserDto userDto) {
       return userService.addUser(userDto);
    }


}
