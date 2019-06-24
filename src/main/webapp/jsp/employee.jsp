<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Menu displayed to the customer once they log in -->
<html>
<!-- Displays customer name, shows current accounts they have -->
<h1><b>Employee Menu - Welcome, ${firstName}</b></h1>
<b>Current accounts</b><br>
<body>
<c:forEach items="${accounts}" var="account">
    <c:out value="Type: ${account.type}, Balance: ${account.balance}, AccountID: ${account.accountID}"/><br>
</c:forEach>
</body>

<!-- Withdraw/Deposit money, text field for value/account, buttons for withdraw/deposit -->
<form method="post" action="updateEmployeeAccount">
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
<form method="post" action="manageEmployeeAccounts">
    <h2><b>Open Account</b></h2>
    <input type="submit" name="accountOperation" value="Open Checking Account">
    <input type="submit" name="accountOperation" value="Open Savings Account">
    <br><br>
    <h2><b>Close account</b></h2>
    <input type="submit" name="accountOperation" value="Close Checking Account">
    <input type="submit" name="accountOperation" value="Close Savings Account">
    <br><br>
</form>

<!-- Open account, buttons for new Checking/Savings acc -->
<form method="post" action="manageOtherAccounts">
    <h2><b>Open/Close Customer Account</b></h2>
    Customer Login ID:<input type="text" name="customerLogin" required/><br>
    <input type="submit" name="accountOperation" value="Open Checking Account">
    <input type="submit" name="accountOperation" value="Open Savings Account"><br>
    <input type="submit" name="accountOperation" value="Close Checking Account">
    <input type="submit" name="accountOperation" value="Close Savings Account">
    <br><br>
</form>


<!-- Displays all transactions for Employee -->
<form method="get" action="employeeTransactions">
    <h2><b>Show Personal Transactions</b></h2>
    <input type="submit" value="Show Transactions"/>
</form>

<!-- Displays all transactions for Customer -->
<form method="get" action="otherTransactions">
    <h2><b>Show Customer Transactions</b></h2>
    Customer Login ID:<input type="text" name="customerLogin" required/><br>
    <input type="submit" value="Show Transactions"/>
</form>



<!-- Updates user information, ALL fields required -->
<form method="post" action="updateEmployeeInfo">
    <h2><b>Update Personal Info</b></h2>
    First Name: <input type="text" name="first" required/>
    Last Name: <input type="text" name="last" required/>
    New Login: <input type="text" name="login" required/>
    Password: <input type="password" name="pass" required/>
    <input type="submit" name="button" value="Update User Info"/>
</form>

<!-- Updates Customer information, ALL fields required -->
<form method="post" action="updateInfoForCustomer">
    <h2><b>Update Customer Info</b></h2>
    Customer Login: <input type="text" name="customer" required/>
    First Name: <input type="text" name="first" required/>
    Last Name: <input type="text" name="last" required/>
    New Login: <input type="text" name="login" required/>
    Password: <input type="password" name="pass" required/>
    <input type="submit" name="button" value="Update Customer Info"/>
</form>

<!-- Allows Employee to create a new customer with all information specified -->
<form method="post" action="createCustomer">
    <h2><b>Create new Customer</b></h2>
    First Name: <input type="text" name="first" required/>
    Last Name: <input type="text" name="last" required/>
    Login: <input type="text" name="login" required/>
    Password: <input type="password" name="pass" required/>
    <input type="submit" name="button" value="Create Customer"/>
</form>

<!-- Allows Employee to remove a customer based on specified login name -->
<form method="post" action="removeCustomer">
    <h2><b>Remove existing Customer</b></h2>
    Customer Login: <input type="text" name="customer" required/>
    <input type="submit" name="button" value="Remove Customer"/>
</form>

<!-- Allows user to transfer money between accounts, or to another user -->
<form method="post" action="employeeTransfer">
    <h2><b>Transfer Funds</b></h2>
    Personal Transfer<br>
    Enter amount to transfer: <input type="text" name="amount" required/><br>
    <input type="submit" name="operation" value="Transfer To Checking"/>
    <input type="submit" name="operation" value="Transfer To Savings"/><br>
    Enter user to transfer to: <input type="text" name="transferUser"/><br>
    <input type="submit" name="operation" value="Transfer To User"/>
</form>

<!-- Logout Button-->
<form action="login" method="get">
    <input type="submit" name="button" value="Logout"/>
</form>
</html>
