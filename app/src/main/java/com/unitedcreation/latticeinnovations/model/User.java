package com.unitedcreation.latticeinnovations.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    private int key;

    private String name;

    private String address;

    private String email;

    private String phone;

    @Ignore
    private String password;

    public User (Integer key, String name, String address, String email, String phone, String password) {

        this.key = key;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;

    }

    public User (Integer key, String name, String address, String email, String phone) {

        this.key = key;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
