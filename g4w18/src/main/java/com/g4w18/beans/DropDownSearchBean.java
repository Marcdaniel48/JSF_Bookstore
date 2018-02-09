package com.g4w18.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 1339644
 */
@ManagedBean(name="drop")
@SessionScoped
public class DropDownSearchBean implements Serializable{ 
    
    
   
    private String searchOption;
    
    private static Map<String,Object> searchOptions;
    static{
        searchOptions = new LinkedHashMap<String,Object>();
        
        searchOptions.put("Book","Book");
        searchOptions.put("Title","Title");
        searchOptions.put("Author","Author");
        searchOptions.put("ISBN","ISBN");
        searchOptions.put("Publisher","Publisher");
    }
    
    public Map<String,Object> getSearchOptionValue() {
		return searchOptions;
	}
    
    public String getSearchOption()
    {
        return searchOption;
    }
    
    public void setSearchOption(String searchOption)
    {
        this.searchOption = searchOption;
    }
}

