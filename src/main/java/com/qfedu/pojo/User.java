package com.qfedu.pojo;

import java.util.List;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private List<Role> roles;

    private List<Permission> perms;

    public List<Permission> getPerms() {
        return perms;
    }

    public void setPerms(List<Permission> perms) {
        this.perms = perms;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", roles=" + roles +
                ", perms=" + perms +
                '}';
    }
}