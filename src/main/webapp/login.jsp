<%@ page language="java" contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <title>Login Page</title>
    </head> 
    <body>
        <h3>Compiler Validation</h3>
        <form action="loginServlet" method="post"> Please enter your username
            <input type="text" name="usrname"/>
            <br> Please enter your password
            <input type="text" name="pw"/>
            <input type="submit" value="submit"> 
        </form> 
    </body> 
</html>
