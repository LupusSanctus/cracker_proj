package crm.tariffs.utils;


import java.sql.Connection;
 
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import crm.tariffs.domain.Usr;


public class SessionUtils {
 
    public static final String attribForConnection = "ATTRIB_CONNECTION";

    public static void storeJdbcConnection(ServletRequest request, JdbcOperations jdbcOperations) {
        request.setAttribute(attribForConnection, jdbcOperations);
    }

    // get the connection by the attribute
    public static JdbcOperations getStoredConnection(ServletRequest request) {
        JdbcOperations jdbcOperations = (JdbcOperations) request.getAttribute(attribForConnection);
        return jdbcOperations;
    } 

    public static void storeLoginedUser(HttpSession session, Usr loginedUser) {

        //JSP can access ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }

    public static Usr getLoginedUser(HttpSession session) {
        Usr loginedUser = (Usr) session.getAttribute("loginedUser");
        return loginedUser;
    }
 
}

