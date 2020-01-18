<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 14.01.2020
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<style  type="text/css"><%@include file="/WEB-INF/styles/main.css"%></style>--%>
<%--<style  type="text/css"><%@include file="/WEB-INF/styles/util.css"%></style>--%>
<html>
<head>
    <title>Login page</title>
</head>
<body>
Hello login page
</body>
<div>${errorMsg}</div>
<form action="/internet_shop_war_exploded/login" method="post">
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this form to login into account.</p>
        <hr>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter your login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>

        <hr>
        <button type="submit" class="registerbtn">Login</button>

    </div>

    <div class="container signin">
        <p>Don't have an account?<a href="/internet_shop_war_exploded/registration">Sign in</a></p>
        <p>For testing only: <a href="/internet_shop_war_exploded/inject">add User(login: user, password:1) and Admin(login: admin, password:1)</a></p>
    </div>
</form>
</body>
</html>
