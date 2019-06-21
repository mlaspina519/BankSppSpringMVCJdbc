<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Prints out a list of all transactions the currently logged in user has -->
<html>
<h1><b>Transactions For ${firstName}</b></h1>

<body>
<c:forEach items="${transactions}" var="transaction">
    <c:out value="Account: ${transaction.accountID}, Type: ${transaction.transactionType}, Amount: ${transaction.amount}"/><br>
</c:forEach>
</body>

<c:choose>
    <c:when test="${callingUserType == 'Customer'}">
        <form method="get" action="customer">
            <input type="submit" value="Back To Menu"/>
        </form>
    </c:when>
    <c:otherwise>
        <form method="get" action="employee">
            <input type="submit" value="Back To Menu"/>
        </form>
    </c:otherwise>
</c:choose>

</html>
