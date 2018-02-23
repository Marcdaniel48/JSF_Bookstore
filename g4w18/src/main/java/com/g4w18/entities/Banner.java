/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

<<<<<<< HEAD
/**
 *
 * @author 1430047
 */
=======
>>>>>>> 0507d7671f35fa61c1c7b3313788726a5dce35e9
@Entity
@Table(name = "banner", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Banner.findAll", query = "SELECT b FROM Banner b")
    , @NamedQuery(name = "Banner.findByBannerId", query = "SELECT b FROM Banner b WHERE b.bannerId = :bannerId")
    , @NamedQuery(name = "Banner.findByBannerName", query = "SELECT b FROM Banner b WHERE b.bannerName = :bannerName")})
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BANNER_ID")
    private Integer bannerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BANNER_NAME")
    private String bannerName;

    public Banner() {
    }

    public Banner(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public Banner(Integer bannerId, String bannerName) {
        this.bannerId = bannerId;
        this.bannerName = bannerName;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bannerId != null ? bannerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banner)) {
            return false;
        }
        Banner other = (Banner) object;
        if ((this.bannerId == null && other.bannerId != null) || (this.bannerId != null && !this.bannerId.equals(other.bannerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.Banner[ bannerId=" + bannerId + " ]";
    }
<<<<<<< HEAD
    
=======

>>>>>>> 0507d7671f35fa61c1c7b3313788726a5dce35e9
}
