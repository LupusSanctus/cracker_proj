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
import crm.tariffs.domain.Offer;
 
 
@WebServlet(urlPatterns = { "/buyOffer" })

public class BuyOfferServlet extends HttpServlet {
 
    public BuyOfferServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JdbcOperations jdbcOperations = SessionUtils.getStoredConnection(request);

        String errorString = null;
        //List<Offer> list = offerDao.findAll();
        List<Offer> allOffers = null;
        
        // need catch NullPointerEx???
        allOffers = DbInfoUtils.queryAllOffers(jdbcOperations);      
//         if (errorString != null) {
//             response.sendRedirect(request.getContextPath() + "/offers");
//         }
 
        // Redirect to the offer listing page.        
        //else {
            request.setAttribute("allOffers", allOffers);
            request.setAttribute("errorString", errorString);
            
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/fullOffers");
            dispatcher.forward(request, response);
        //}
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
 
}
