package org.collectalot.collectorapp.security;
import java.io.IOException;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/rest/*")
public class RestServiceAccessFilter implements Filter {
	public static final String USER_LOGGED_ON = "USER_LOGGED_ON";
	
	@Inject @PreferredUserSessionResolver
	UserSessionResolver userSessionResolver;
	
    @Override
    public void init(FilterConfig config) throws ServletException {
        // Nothing needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
    	request.setAttribute(USER_LOGGED_ON, userSessionResolver.getUserLoggedOn((HttpServletRequest) request));
        chain.doFilter(request, response);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
    }
}
