package com.g4w18.filters;

import com.g4w18.customcontrollers.CustomClientController;
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
 * Filter that checks to see if the user is trying to access certain pages that he shouldn't be allowed to access right now, and redirects the user to the index page.
 * The user shouldn't be able to:
 *  - access any checkout pages with an empty shopping cart.
 *  - access the registration and login pages while logged in.
 *  - access any management pages while not logged in with a manager account.
 * 
 * @author Marc-Daniel
 */
@WebFilter(filterName = "IndexFilter", urlPatterns = {"/checkout/*", "/management/*", "/registration.xhtml", "/login.xhtml"})
public class IndexFilter implements Filter
{
    // Used to check if the shopping cart is empty.
    @Inject
    private ShoppingCart cart;
    
    // Used to check if the user is logged in and if the user logged-in user is a manager.
    @Inject
    private LoginController login;
    
    // Used to check if the user is logged in with a correct username and password combination.
    @Inject
    private CustomClientController clientJpaController;
    
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
     * Redirects the user to the index page of the bookstore site, if they're trying to view pages they shouldn't access right now.
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
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String uri = ((HttpServletRequest)request).getRequestURI();
        
        // If the user is trying to finalize with an empty shopping cart, then redirect to index
        if(uri.startsWith("/g4w18/checkout/") && (cart == null || cart.getShoppingCartBooks().isEmpty()))
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
