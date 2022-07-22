package com.inventory.system.InventorySystem.entities;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
@Entity
@Proxy(lazy = false)
public class Role {

    @Id
    private int roleId;
    private String roleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
