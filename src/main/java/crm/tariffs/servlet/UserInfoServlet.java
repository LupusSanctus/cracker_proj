package crm.tariffs.servlet;


import java.io.IOException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import crm.tariffs.domain.Usr;
import crm.tariffs.utils.SessionUtils;
 
 
@WebServlet(urlPatterns = { "/userInfo" })

public class UserInfoServlet extends HttpServlet {
 
    public UserInfoServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession();
 
        Usr loginedUser = SessionUtils.getLoginedUser(session);
  
        // Not logged in
        if (loginedUser == null) {
       
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
  
        // Store info in request attribute
        request.setAttribute("user", loginedUser);
 
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/userinfo");
        dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
