package edu.jhu.mrm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * File: Account.java
 * This file contains the definition of the Account model
 * Specifically designed to be used with MongoDB
 * Author: Cory Drangel and Matthew Kim
 *
 */

@Document(collection = "account")
public class Account {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private String email;

    private Role role;

    private Integer building;

    @Field("apartment_num")
    private Integer apartmentNum;

    public Account() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Integer getBuilding() { return building; }
    public void setBuilding(Integer building) { this.building = building; }

    public Integer getApartmentNum() { return apartmentNum; }
    public void setApartmentNum(Integer apartmentNum) { this.apartmentNum = apartmentNum; }
}
