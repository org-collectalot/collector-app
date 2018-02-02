package org.collectalot.collectorapp.security;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/rest/*")
public class RestServiceAccessFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
        // Nothing needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
    	// Process the rest of the filter chain, including the JAX-RS request
    	System.out.println("Before rest req");
        chain.doFilter(request, response);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
