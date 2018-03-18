/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.filters;

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
@WebFilter(filterName = "IndexFilter", urlPatterns = {"/authenticated/*", "/registration.xhtml", "/login.xhtml"})
public class IndexFilter implements Filter
{
    @Inject
    private ShoppingCart cart;
    
    @Inject
    private LoginController login;
    
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
               
        // If the user is trying to finalize with an empty shopping cart
        if(uri.startsWith("/g4w18/authenticated/") && (cart == null || cart.getShoppingCartBooks().isEmpty()))
        {
            ((HttpServletResponse) response).sendRedirect(contextPath);
        }
        // If the user is trying to access the registration or login pages while logged in
        else if (!uri.startsWith("/g4w18/authenticated/") && login.getLoggedIn()) 
        {
            ((HttpServletResponse) response).sendRedirect(contextPath);
        } 
        else 
        {
            chain.doFilter(request, response);
        }
    }
}
