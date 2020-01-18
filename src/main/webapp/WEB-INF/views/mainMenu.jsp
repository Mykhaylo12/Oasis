<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11.01.2020
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        .vertical-menu {
            width: 200px;
        }

        .vertical-menu a {
            background-color: #eee;
            color: black;
            display: block;
            padding: 12px;
            text-decoration: none;
        }

        .vertical-menu a:hover {
            background-color: #ccc;
        }

        .vertical-menu a.active {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>

<h1>Internet Shop Main Menu</h1>

<div class="vertical-menu">
    <a href="/internet_shop_war_exploded/registration">Registration</a>
    <a href="/internet_shop_war_exploded/servlet/addItem">Add new item</a>
    <a href="/internet_shop_war_exploded/servlet/allUsers">Show all users</a>
    <a href="/internet_shop_war_exploded/servlet/allItems">Show all items</a>
    <a href="/internet_shop_war_exploded/servlet/bucketController">Show bucket</a>
    <a href="${pageContext.request.contextPath}/servlet/orders">Show order</a>
    <br>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
