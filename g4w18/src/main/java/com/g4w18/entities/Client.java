/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Marc-Daniel
 */
@Entity
@Table(name = "client", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByClientId", query = "SELECT c FROM Client c WHERE c.clientId = :clientId")
    , @NamedQuery(name = "Client.findByUsername", query = "SELECT c FROM Client c WHERE c.username = :username")
    , @NamedQuery(name = "Client.findByPassword", query = "SELECT c FROM Client c WHERE c.password = :password")
    , @NamedQuery(name = "Client.findByTitle", query = "SELECT c FROM Client c WHERE c.title = :title")
    , @NamedQuery(name = "Client.findByFirstName", query = "SELECT c FROM Client c WHERE c.firstName = :firstName")
    , @NamedQuery(name = "Client.findByLastName", query = "SELECT c FROM Client c WHERE c.lastName = :lastName")
    , @NamedQuery(name = "Client.findByCompanyName", query = "SELECT c FROM Client c WHERE c.companyName = :companyName")
    , @NamedQuery(name = "Client.findByAddress1", query = "SELECT c FROM Client c WHERE c.address1 = :address1")
    , @NamedQuery(name = "Client.findByAddress2", query = "SELECT c FROM Client c WHERE c.address2 = :address2")
    , @NamedQuery(name = "Client.findByCity", query = "SELECT c FROM Client c WHERE c.city = :city")
    , @NamedQuery(name = "Client.findByProvince", query = "SELECT c FROM Client c WHERE c.province = :province")
    , @NamedQuery(name = "Client.findByCountry", query = "SELECT c FROM Client c WHERE c.country = :country")
    , @NamedQuery(name = "Client.findByPostalCode", query = "SELECT c FROM Client c WHERE c.postalCode = :postalCode")
    , @NamedQuery(name = "Client.findByHomeTelephone", query = "SELECT c FROM Client c WHERE c.homeTelephone = :homeTelephone")
    , @NamedQuery(name = "Client.findByCellphone", query = "SELECT c FROM Client c WHERE c.cellphone = :cellphone")
    , @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email")
    , @NamedQuery(name = "Client.findByIsManager", query = "SELECT c FROM Client c WHERE c.isManager = :isManager")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIENT_ID")
    private Integer clientId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "ADDRESS_1")
    private String address1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "ADDRESS_2")
    private String address2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 2)
    @Column(name = "PROVINCE")
    private String province;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "COUNTRY")
    // Since the website only accomodates Canadians at this time, the country field will be left and defaulted to "Canada"
    private String country = "Canada";
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 6)
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 12)
    @Column(name = "HOME_TELEPHONE")
    private String homeTelephone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 12)
    @Column(name = "CELLPHONE")
    private String cellphone;
    @Pattern(regexp="^[A-Za-z0-9\\._]+@[A-Za-z0-9\\\\._]+\\.[A-Za-z0-9\\\\._]+[^\\.]$", message="{invalidEmail}")
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_MANAGER")
    private boolean isManager;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private List<Review> reviewList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private List<MasterInvoice> masterInvoiceList;

    public Client() {
    }

    public Client(Integer clientId) {
        this.clientId = clientId;
    }

    public Client(Integer clientId, String username, String password, String title, String firstName, String lastName, String companyName, String address1, String address2, String city, String province, String country, String postalCode, String homeTelephone, String cellphone, String email, boolean isManager) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.homeTelephone = homeTelephone;
        this.cellphone = cellphone;
        this.email = email;
        this.isManager = isManager;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<MasterInvoice> getMasterInvoiceList() {
        return masterInvoiceList;
    }

    public void setMasterInvoiceList(List<MasterInvoice> masterInvoiceList) {
        this.masterInvoiceList = masterInvoiceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientId != null ? clientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.Client[ clientId=" + clientId + " ]";
    }
    
}
