<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
Items:
<table border="1">
    <tr>
        <th>Item id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete item</th>
        <th>Add to bucket</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.itemId}"/>
            </td>
            <td>
                <c:out value="${item.name}"/>
            </td>
            <td>
                <c:out value="${item.price}"/>
            </td>
            <td>
                <a href="/internet_shop_war_exploded/deleteItem?item_id=${item.itemId}">DELETE</a>
            </td>
            <td>
                <a href="/internet_shop_war_exploded/addToBucket?item_id=${item.itemId}">Add to bucket</a>
            </td>
        </tr>

    </c:forEach>
    <td>
        <a href="/internet_shop_war_exploded/additem">Add new item</a>
    </td>
</table>
<a href="${pageContext.request.contextPath}/mainMenu">Main Menu</a>
</body>
</html>
