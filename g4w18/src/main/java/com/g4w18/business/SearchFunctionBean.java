package com.g4w18.business;


import com.g4w18.beans.SearchBean;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.g4w18.Books;
import javax.persistence.EntityManager;

/**
 *
 * @author Salman Haidar
 */
@Named("searchFunction")
@SessionScoped
public class SearchFunctionBean implements Serializable{
    
    @Inject
    private SearchBean searchBean;
    
    public SearchFunctionBean(){
        super();
    }
    
    public String searchTitle(){
        String query="";
        return query;
        
    }
}
