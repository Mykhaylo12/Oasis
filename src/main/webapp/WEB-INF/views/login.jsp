<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style type="text/css"><%@ include file="../styles/login.css" %></style>
<html>
<head>
    <title>Login page</title>
</head>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn second">${errorMsg}</div>
        <form action="/internet_shop_war_exploded/login" method="post">

            <p>Please fill in this form to login into account</p>
            <input type="text" id="login" class="fadeIn second" name="login" placeholder="login">
            <input type="text" id="password" class="fadeIn third" name="psw" placeholder="password">
            <input type="submit" class="fadeIn fourth" value="Log In">
        </form>
    <div class="fadeIn second" >
        <p>Don't have an account?<a href="/internet_shop_war_exploded/registration"> Sign up</a></p>
        <p>Add user with login:"admin", password:"1".<a href="/internet_shop_war_exploded/inject"> Add</a></p>
    </div>
</div>
</div>
</html>
