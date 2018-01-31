/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named("register")
public class Registration implements Serializable         
{
    private String username;
    private String password;
    private String title;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String postalCode;
    private String homeTelephone;
    private String cellPhone;
    private String email;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHomeTelephone() {
        return homeTelephone;
    }

    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    
    
    public Collection<SelectItem> getTitleOptions() {
        return titleOptions;
    }
    
    private static Collection<SelectItem> titleOptions;

    static 
    {
        titleOptions = new ArrayList<SelectItem>();
        
        titleOptions.add(new SelectItem(null, "Select a title", "", false, false,
                true));
        
        titleOptions.add(new SelectItem("Mr."));
        titleOptions.add(new SelectItem("Ms."));
        titleOptions.add(new SelectItem("Dr."));
        titleOptions.add(new SelectItem("Sir"));
        titleOptions.add(new SelectItem("Lord"));
        titleOptions.add(new SelectItem("Lady"));
    }
    
    public Collection<SelectItem> getProvinceOptions() {
        return provinceOptions;
    }
    
    private static Collection<SelectItem> provinceOptions;
    
    static 
    {
        provinceOptions = new ArrayList<SelectItem>();
        
        provinceOptions.add(new SelectItem(null, "Select a province", "", false, false,
                true));
        
        provinceOptions.add(new SelectItem("Ontario"));
        provinceOptions.add(new SelectItem("Québec"));
        provinceOptions.add(new SelectItem("British Columbia"));
        provinceOptions.add(new SelectItem("Alberta"));
        provinceOptions.add(new SelectItem("Nova Scotia"));
        provinceOptions.add(new SelectItem("Saskatchewan"));
        provinceOptions.add(new SelectItem("Manitoba"));
        provinceOptions.add(new SelectItem("Newfoundland and Labrador"));
        provinceOptions.add(new SelectItem("New Brunswick"));
        provinceOptions.add(new SelectItem("Prince Edward Island"));
    }
}
