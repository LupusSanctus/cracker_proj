package crm.tariffs.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.servlet.http.HttpSession;

import crm.tariffs.domain.Usr;
import crm.tariffs.domain.Offer;
import crm.tariffs.utils.SessionUtils;
import crm.tariffs.utils.DbInfoUtils;
import crm.tariffs.dao.OfferDao;

 
@WebServlet(urlPatterns = { "/offers" })

public class OfferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public OfferServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JdbcOperations jdbcOperations = SessionUtils.getStoredConnection(request);
        
        String errorString = null;
        List<Offer> list = null;
        Usr user = null;
        Integer usrId = null;
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        //UserAccount userInSession = MyUtils.getLoginedUser(session);                
        user = SessionUtils.getLoginedUser(session);
        
        try {
            usrId = user.getUsrId();
        } catch(NullPointerException ex) {
            
        }
        
        if(usrId == null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/logging");
            dispatcher.forward(request, response);
        }
        
         list = DbInfoUtils.queryOffer(jdbcOperations, usrId);
   
        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", errorString);
        request.setAttribute("offerList", list);
        
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/offer");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
 
}
