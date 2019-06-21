<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Menu displayed to the customer once they log in -->
<html>
<!-- Displays customer name, shows current accounts they have -->
<h1><b>Customer Menu - Welcome, ${firstName}</b></h1>
<b>Current accounts</b><br>
<body>
<c:forEach items="${accounts}" var="account">
    <c:out value="Type: ${account.type}, Balance: ${account.balance}, AccountID: ${account.accountID}"/><br>
</c:forEach>
</body>

<!-- Withdraw/Deposit money, text field for value/account, buttons for withdraw/deposit -->
<form method="post" action="updateCustomerAccount">
    <h2><b>Withdraw/Deposit money</b></h2>
    Amount (Dollars): <input type="text" name="amount" required>
    <br><b>Checking Account</b>
    <input type="submit" name="account" value="Deposit Into Checking">
    <input type="submit" name="account" value="Withdraw From Checking">
    <br><b>Savings Account</b>
    <input type="submit" name="account" value="Deposit Into Savings">
    <input type="submit" name="account" value="Withdraw From Savings">
    <br><br>
</form>

<!-- Open account, buttons for new Checking/Savings acc -->
<form method="post" action="manageCustomerAccounts">
    <h2><b>Open Account</b></h2>
    <input type="submit" name="accountOperation" value="Open Checking Account">
    <input type="submit" name="accountOperation" value="Open Savings Account">
    <br><br>
    <h2><b>Close account</b></h2>
    <input type="submit" name="accountOperation" value="Close Checking Account">
    <input type="submit" name="accountOperation" value="Close Savings Account">
    <br><br>
</form>

<!-- Displays all transactions for currently logged in user -->
<form method="get" action="customerTransactions">
    <h2><b>Show Transactions</b></h2>
    <input type="submit" name="button" value="Transactions"/>
</form>

<!-- Updates user information, ALL fields required -->
<form method="post" action="updateCustomerInfo">
    <h2><b>Update User Info</b></h2>
    First Name: <input type="text" name="first" required/>
    Last Name: <input type="text" name="last" required/>
    Login: <input type="text" name="login" required/>
    Password: <input type="password" name="pass" required/>
    <input type="submit" name="button" value="Update User Info"/>
</form>

<!-- Allows user to transfer money between accounts, or to another user -->
<form method="post" action="customerTransfer">
    <h2><b>Transfer Funds</b></h2>
    Personal Transfer<br>
    Enter amount to transfer: <input type="text" name="amount" required/><br>
    <input type="submit" name="operation" value="Transfer To Checking"/>
    <input type="submit" name="operation" value="Transfer To Savings"/><br>
    Enter user to transfer to: <input type="text" name="transferUser"/><br>
    <input type="submit" name="operation" value="Transfer To User"/>
</form>

<!-- Logout Button-->
<form action="login" method ="get">
    <input type="submit" name="button" value="Logout"/>
</form>
</html>
