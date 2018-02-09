package com.g4w18.beans;

import com.g4w18.controllers.BookJpaController;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.g4w18.beans.DropDownSearchBean;
import com.g4w18.beans.SearchBean;

/**
 *
 * @author Salman Haidar
 */
@Named
@SessionScoped
public class SearchBackingBean implements Serializable {

    @Inject
    private BookJpaController bookJpaController;

    private SearchBean searchBean;
    private DropDownSearchBean dropDownSearchBean;
     
    
    public String searchTitle(){
        bookJpaController.findBooksByTitle(searchBean.getSearchTerm());
        
        return "success";
    }
}
