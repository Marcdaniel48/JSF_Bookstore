package com.g4w18.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Change,set and get the language  for the website.
 * 
 * @author Salman Haidar
 */
@Named
@SessionScoped
public class LocaleBean implements Serializable {
    
    
    private Locale locale;
    
    @PostConstruct
    public void init()
    {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    public Locale getLocale()
    {
        return locale;
    }
    
    public String getLanguage()
    {
        return locale.getLanguage();
    }
    
    public void setLanguage(String a, String b)
    {
        locale = new Locale(a,b);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}