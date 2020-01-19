<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style type="text/css"><%@ include file="../styles/login.css" %></style>
<html>
<head>
    <title>Registration</title>
</head>
<div class="wrapper fadeInDown">
    <div id="formContent">
<form action="/internet_shop_war_exploded/registration" method="post">
        <h3>Registration</h3>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <input type="text" placeholder="login (username)" class="fadeIn second" name="login" required>

        <input type="text" placeholder="name" class="fadeIn second" name="name" required>

        <input type="text" placeholder="email" class="fadeIn second" name="email" required>

        <input type="text" placeholder="password" class="fadeIn second" name="psw" required>

        <button type="submit" class="fadeIn fourth">Sign up</button>
</form>
<a href="/internet_shop_war_exploded/login">Login</a>
</div>
</div>
</html>
