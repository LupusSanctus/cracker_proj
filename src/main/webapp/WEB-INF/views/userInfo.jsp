<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>User Info</title>
 </head>
 <body>

    <jsp:include page="/mainMenu"></jsp:include>
 
    <h3>Hello: ${user.usrName}</h3>
 
    User Name: <b>${user.usrName}</b>
 
 </body>
</html>
