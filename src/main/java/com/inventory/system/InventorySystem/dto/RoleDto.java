package com.inventory.system.InventorySystem.dto;
import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.entities.*;

import java.util.*;

public class RoleDto {

    private int roleId;
    private String roleName;
    @JsonIgnore
    private Set<User> user;

    public RoleDto() {
    }

    public RoleDto(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
