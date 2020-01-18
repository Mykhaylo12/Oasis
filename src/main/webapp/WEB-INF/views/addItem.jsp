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
    <title>Add item</title>
</head>
<body>
<form action="/internet_shop_war_exploded/servlet/addItem" method="post">
    <div class="container">
        <h1>Please add item</h1>
        <p>Please fill in this form to add item.</p>
        <hr>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter name of item" name="name" required>

        <label for="price"><b>Price</b></label>
        <input type="text" placeholder="Enter price of item" name="price" required>

        <hr>
        <button type="submit" class="registerbtn">Add item</button>

    </div>
    <div class="container signin">

    </div>
</form>
<a href="/internet_shop_war_exploded/mainMenu">Main Menu</a>
</body>
</html>
