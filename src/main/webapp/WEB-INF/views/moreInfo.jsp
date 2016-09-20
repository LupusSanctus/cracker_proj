<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Offer List</title>
 </head>
 <body>
 
    <jsp:include page="/mainMenu"></jsp:include>
 
    <h3>Offer List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>Attributes</th>
          <th>Description</th>
          <th>Values</th>
       </tr>
     <c:forEach var="map" items="${moreInfoOffer}">
        <tr>
            <td><c:out value="${map['attr_name']}"/>
            <td><c:out value="${map['description']}"/>
            <td><c:out value="${map['attr_value']}"/>
        </tr>
    </c:forEach>

    </table>
    
 </body>
</html>
