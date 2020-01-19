<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style type="text/css"><%@ include file="../styles/login.css" %></style>
<html>
<head>
    <title>Main menu</title>
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn second" >
            <h5>Internet Shop Main Menu</h5>
        <p><a href="/internet_shop_war_exploded/servlet/addItem">Add new item</a></p>
        <p><a href="/internet_shop_war_exploded/servlet/allUsers">Show all users</a></p>
        <p> <a href="/internet_shop_war_exploded/servlet/allItems">Show all items</a></p>
        <p> <a href="/internet_shop_war_exploded/servlet/bucketController">Show bucket</a></p>
        <p> <a href="${pageContext.request.contextPath}/servlet/orders">Show order</a></p>
            <br>
        <p> <a href="${pageContext.request.contextPath}/logout">Logout</a></p>
</div>
</div>
</div>
</body>
</html>
