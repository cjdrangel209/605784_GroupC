package edu.jhu.mrm.dto;

/**
 * File: RegisterAccountDTO.java
 * Data Transfer Object representing the shape for RegisterAccount
 * Author: Cory Drangel and Matthew Kim
 *
 */
public class RegisterAccountDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Integer building;
    private Integer apartmentNum;

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password; 
    }

    public String getFirstName() {
        return firstName; 
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName; 
    }

    public String getLastName() {
        return lastName; 
    }
    public void setLastName(String lastName) {
        this.lastName = lastName; 
    }

    public String getEmail() {
        return email; 
    }
    public void setEmail(String email) {
        this.email = email; 
    }

    public Integer getBuilding() {
        return building; 
    }
    public void setBuilding(Integer building) {
        this.building = building; 
    }

    public Integer getApartmentNum() {
        return apartmentNum; 
    }
    public void setApartmentNum(Integer apartmentNum) {
        this.apartmentNum = apartmentNum; 
    }
}