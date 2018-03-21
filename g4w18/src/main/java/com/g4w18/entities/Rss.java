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

/**
 *
 * @author 1331680
 */
@Entity
@Table(name = "rss", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Rss.findAll", query = "SELECT r FROM Rss r")
    , @NamedQuery(name = "Rss.findByRssId", query = "SELECT r FROM Rss r WHERE r.rssId = :rssId")
    , @NamedQuery(name = "Rss.findByRssLink", query = "SELECT r FROM Rss r WHERE r.rssLink = :rssLink")
    , @NamedQuery(name = "Rss.findByIsActive", query = "SELECT r FROM Rss r WHERE r.isActive = :isActive")})
public class Rss implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RSS_ID")
    private Integer rssId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RSS_LINK")
    private String rssLink;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    public Rss() {
    }

    public Rss(Integer rssId) {
        this.rssId = rssId;
    }

    public Rss(Integer rssId, String rssLink, boolean isActive) {
        this.rssId = rssId;
        this.rssLink = rssLink;
        this.isActive = isActive;
    }

    public Integer getRssId() {
        return rssId;
    }

    public void setRssId(Integer rssId) {
        this.rssId = rssId;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rssId != null ? rssId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rss)) {
            return false;
        }
        Rss other = (Rss) object;
        if ((this.rssId == null && other.rssId != null) || (this.rssId != null && !this.rssId.equals(other.rssId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.Rss[ rssId=" + rssId + " ]";
    }
    
}
