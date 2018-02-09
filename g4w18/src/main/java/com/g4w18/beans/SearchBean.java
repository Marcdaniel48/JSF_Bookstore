package com.g4w18.beans;

import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Salman Haidar
 */

@Named("searchBean")
@RequestScoped
public class SearchBean implements Serializable{
    private String searchTerm;


public SearchBean(){
    super();
}

public void setSearchTerm(String searchTerm){
    this.searchTerm = searchTerm;
}

public String getSearchTerm(){
    return searchTerm;
}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.searchTerm);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchBean other = (SearchBean) obj;
        if (!Objects.equals(this.searchTerm, other.searchTerm)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SearchBean{" + "searchTerm=" + searchTerm + '}';
    }

}

