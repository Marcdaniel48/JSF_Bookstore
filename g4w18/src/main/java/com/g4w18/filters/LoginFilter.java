/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.filters;

import com.g4w18.customcontrollers.LoginController;
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
 * Filter that checks to see if the user, who's not logged in, is trying to access pages that require login, and redirects the user to the login page.
 * 
 * @author Marc-Daniel
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/management/*", "/checkout/*"})
public class LoginFilter implements Filter
{
    // Used to check if the current user is logged in.
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

    /**
     * If the user is trying to access any checkout or management pages while not logged in, redirect the user to the login page.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        if (login == null || !login.getLoggedIn()) 
        {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        } 
        else 
        {
            chain.doFilter(request, response);
        }
    }
}
