package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired RoleDao roleDao;

    @Autowired
    GlobalMapper globalMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        userDto.setRole(roleDao.findById(1).get());
        User user = globalMapper.UserDtoUser(userDto);
        return globalMapper.UserToUserDto(userDao.save(user));
    }
}
