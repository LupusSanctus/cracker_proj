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
import org.springframework.jdbc.core.JdbcOperations;
import javax.servlet.http.HttpSession;
import java.util.*;

import crm.tariffs.utils.SessionUtils;
import crm.tariffs.utils.DbInfoUtils;
import crm.tariffs.domain.Usr;
 
 
@WebServlet(urlPatterns = { "/buyChosen" })

public class BuyChosenOfferServlet extends HttpServlet {
 
    public BuyChosenOfferServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JdbcOperations jdbcOperations = SessionUtils.getStoredConnection(request);
        Integer code = Integer.valueOf(request.getParameter("offerId"));
        //Integer code = Integer.parseInt(request.getParameter("offerId"));
 
        String errorString = null;

        Usr user = null;
        Integer usrId = null;
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();      
        user = SessionUtils.getLoginedUser(session);
        
        try {
            usrId = user.getUsrId();
        } catch(NullPointerException ex) {
            
        }
        
        if(usrId == null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/logging");
            dispatcher.forward(request, response);
        }
        DbInfoUtils.createOffer(jdbcOperations, usrId, code);      

        if (errorString != null) {
            response.sendRedirect(request.getContextPath() + "/offers");
        }
    
        else {            
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/offers");
            dispatcher.forward(request, response);
        }
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
 
}
