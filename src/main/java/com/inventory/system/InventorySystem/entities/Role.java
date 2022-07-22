package com.inventory.system.InventorySystem.entities;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Proxy(lazy = false)
public class Role {

    @Id
    private int roleId;
    private String roleName;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "role")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(int roleId, String roleName) {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
