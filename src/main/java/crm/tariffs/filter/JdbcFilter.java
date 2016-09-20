package crm.tariffs.filter;


import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import org.springframework.jdbc.core.JdbcOperations;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
 
import crm.tariffs.utils.SessionUtils;
import crm.tariffs.utils.DbConnectionUtils;
 
 
@WebFilter(filterName = "jdbcfilter", urlPatterns = { "/*" })

public class JdbcFilter implements Filter {
 
   public JdbcFilter() {
   }
 
   @Override
   public void init(FilterConfig fConfig) throws ServletException {
 
   }
 
   @Override
   public void destroy() {
 
   }
 
 
   // true - if a servlet (where db connection is needed) is requested
   // false - else
   private boolean isRequestedForServlet(HttpServletRequest request) {
       System.out.println("Filter");

       // Servlet pattern: /servlet_path/*

       String servletPath = request.getServletPath();
       // /*
       String pathInfo = request.getPathInfo();
 
       String urlPattern = servletPath;
 
       if (pathInfo != null) {
           // /servlet_path/*
           urlPattern = servletPath + "/*";
       }
 
       // Key: servlet title
       // Value: servlet registration
       Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
               .getServletRegistrations();
 
 
       // check if request matches one of the existed servlets
       Collection<? extends ServletRegistration> values = servletRegistrations.values();
       for (ServletRegistration sr : values) {
           Collection<String> mappings = sr.getMappings();
           if (mappings.contains(urlPattern)) {
               return true;
           }
       }
       return false;
   }
 
   @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest servletRequest = (HttpServletRequest) request;
 
 
        // Get connection only for requests where it`s really needed (ex.: request to the LoginProcServlet)
        if (this.isRequestedForServlet(servletRequest)) {
    
            System.out.println("Connection now is available for: " + servletRequest.getServletPath());
            JdbcOperations jdbcOperations = DbConnectionUtils.getJdbcOperations();
            SessionUtils.storeJdbcConnection(request, jdbcOperations);
            // go to the next filter or target
            chain.doFilter(request, response);
        }
        else {       
            chain.doFilter(request, response);
        }
    
    }
 
}
