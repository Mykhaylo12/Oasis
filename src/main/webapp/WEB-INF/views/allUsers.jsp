<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
Users:
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.userId}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <c:out value="${user.email}"/>
            </td>
            <td>
                <a href="/internet_shop_war_exploded/deleteUser?user_id=${user.userId}">DELETE</a>
            </td>
        </tr>

    </c:forEach>
    <td>
        <a href="/internet_shop_war_exploded/registration">Add new user</a>
    </td>
</table>
<a href="/internet_shop_war_exploded/mainMenu">Main Menu</a>
</body>
</html>
