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

import crm.tariffs.utils.SessionUtils;
import crm.tariffs.utils.DbInfoUtils;
import crm.tariffs.domain.Usr;
 
 
@WebServlet(urlPatterns = { "/deleteOffer" })

public class DeleteOfferServlet extends HttpServlet {
 
    public DeleteOfferServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Connection conn = MyUtils.getStoredConnection(request);
        JdbcOperations jdbcOperations = SessionUtils.getStoredConnection(request);
        Integer code = Integer.valueOf(request.getParameter("offerId"));
 
        String errorString = null;
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        //UserAccount userInSession = MyUtils.getLoginedUser(session);                
        Usr user = SessionUtils.getLoginedUser(session);
        
        try {
            DbInfoUtils.deleteOffer(user.getUsrId(), code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }         

        if (errorString != null) {
 
            request.setAttribute("errorString", errorString);
            
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/deleter");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/offers");
        }
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
 
}
