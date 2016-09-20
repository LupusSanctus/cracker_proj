package crm.tariffs.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import crm.tariffs.domain.Usr;
import crm.tariffs.utils.DbInfoUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

import crm.tariffs.sql.PostgresqlConfig;
import crm.tariffs.utils.SessionUtils;
import crm.tariffs.utils.DbConnectionUtils;
 

@WebServlet(urlPatterns = { "/loginProcess" })

public class LoginProcesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public LoginProcesServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        String errorString = null;
        String userName = request.getParameter("userName");
        Integer password = null; //= Integer.parseInt(request.getParameter("password"));
        
        try {
            password = Integer.parseInt(request.getParameter("password"));
        } catch (NumberFormatException e) {
            errorString = "Wrong password!";
        }
          
        Usr user = null;
        boolean hasError = false;
 
        if (userName == null || password == null || userName.length() == 0 ||  String.valueOf(password).length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {

            JdbcOperations jdbcOperations = DbConnectionUtils.getJdbcOperations();
            
            try {              
                user = DbInfoUtils.findUser(jdbcOperations, userName, password);
                 
                if (user == null) {
                    hasError = true;
                    errorString = "Invalid user name or password!";
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        
         if (hasError) {
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/logging");
            dispatcher.forward(request, response);
         } else {
            HttpSession session = request.getSession();
            SessionUtils.storeLoginedUser(session, user);
            response.sendRedirect(request.getContextPath() + "/userInfo");
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
 
}
