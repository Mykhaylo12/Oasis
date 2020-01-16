<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11.01.2020
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
Let's create new user
<form action="/internet_shop_war_exploded/registration" method="post">
    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for="Login"><b>Login</b></label>
        <input type="text" placeholder="Enter your login(username)" name="login" required>

        <label for="Name"><b>Name</b></label>
        <input type="text" placeholder="Enter your name" name="name" required>

        <label for="Email"><b>Email</b></label>
        <input type="text" placeholder="Enter your email" name="email" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>

        <label for="psw-repeat"><b>Repeat Password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
        <hr>
        <button type="submit" class="registerbtn">Register</button>

    </div>

    <div class="container signin">

    </div>
</form>
<a href="/internet_shop_war_exploded/mainMenu">Main Menu</a>
</body>
</html>
