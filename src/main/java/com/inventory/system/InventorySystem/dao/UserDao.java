package com.inventory.system.InventorySystem.dao;
import com.inventory.system.InventorySystem.entities.*;
import org.springframework.data.jpa.repository.*;

public interface UserDao extends JpaRepository<User, Integer> {

}
