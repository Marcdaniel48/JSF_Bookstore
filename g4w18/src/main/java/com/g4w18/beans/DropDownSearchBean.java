package com.g4w18.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 1339644
 */
@ManagedBean
@ViewScoped
public class DropDownSearchBean {
    
    private Map<String,Map<String,String>> data = new HashMap<String,Map<String,String>>();
    private String searchOption;
    private Map<String,String> searchOptions;
    
    @PostConstruct
    public void init(){
        searchOptions = new HashMap<String,String>();
        
        searchOptions.put("Book","Book");
        searchOptions.put("Title","Title");
        searchOptions.put("Author","Author");
        searchOptions.put("ISBN","ISBN");
        searchOptions.put("Publisher","Publisher");
    }
    
    public String getSearchOption(){
        return searchOption;
    }
    
    public void setSearchOption(){
        this.searchOption=searchOption;
    }
    
    public Map<String,String> getSearchOptions(){
        return searchOptions;
    }
}
