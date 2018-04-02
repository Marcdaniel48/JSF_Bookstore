/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.filters;

import com.g4w18.customcontrollers.CustomClientJpaController;
import com.g4w18.customcontrollers.LoginController;
import com.g4w18.customcontrollers.ShoppingCart;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marc-Daniel
 */
@WebFilter(filterName = "IndexFilter", urlPatterns = {"/authenticated/*", "/management/*", "/registration.xhtml", "/login.xhtml"})
public class IndexFilter implements Filter
{
    @Inject
    private ShoppingCart cart;
    
    @Inject
    private LoginController login;
    
    @Inject
    private CustomClientJpaController clientJpaController;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException 
    {
        //
    }

    @Override
    public void destroy() 
    {
        //
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String uri = ((HttpServletRequest)request).getRequestURI();
               System.out.println("database database" + login.getLoggedIn());
        // If the user is trying to finalize with an empty shopping cart, then redirect to index
        if(uri.startsWith("/g4w18/authenticated/") && (cart == null || cart.getShoppingCartBooks().isEmpty()))
        {
            ((HttpServletResponse) response).sendRedirect(contextPath);
        }
        // If the user is trying to access the registration or login pages while logged in, then redirect to index
        else if ((uri.startsWith("/g4w18/registration") || uri.startsWith("/g4w18/login")) && login.getLoggedIn()) 
        {
            ((HttpServletResponse) response).sendRedirect(contextPath);
        } 
        // If the user, who's logged in, is trying to access a manager's page, but is not a manager, then redirect to index
        // OR if the user is not logged in, then redirect as well.
        else if(uri.startsWith("/g4w18/management/"))
        {
            if(login.getLoggedIn())
            {
                if(!clientJpaController.findClientByUsername(login.getUsername()).getIsManager())
                    ((HttpServletResponse) response).sendRedirect(contextPath);
            }
            else if(!login.getLoggedIn())
            {
                ((HttpServletResponse) response).sendRedirect(contextPath);
            }
        }
            
        chain.doFilter(request, response);
    }
}
