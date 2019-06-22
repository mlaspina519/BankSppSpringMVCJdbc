<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
	<form method="post" action="login">
		<label for = "Login">
			Login:<input type="text" name="user" id="Login" required/><br/>
		</label>

		<label for = "Password">
			Password:<input type="password" name="pass" id="Password" required/><br/>
		</label>

		<input type="submit" value="login" />
	</form>
</html>