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
 
    <h3>Full offer List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>Id</th>
          <th>Name</th>
          <th colspan="2">Action</th>
       </tr>
       <c:forEach items="${allOffers}" var="offer" >
          <tr>
             <td>${offer.offerId}</td>
             <td>${offer.offerName}</td>

             <c:if test="${offer.offerId != 0}">
                <td><a href="buyChosen?offerId=${offer.offerId}">Buy</a></td>
                <td><a href="moreInfo?offerId=${offer.offerId}">More info</a></td>
             </c:if>
            
          </tr>
       </c:forEach>
    </table>
  
 </body>
</html>
