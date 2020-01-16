<jsp:useBean id="bucket" scope="request" type="mate.academy.internetshop.model.Bucket"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items in bucket</title>
</head>
<body>
Bucket:
<table border="1">
    <tr>
        <th>Item id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete item from bucket</th>
    </tr>
    <c:forEach var="item" items="${bucket.items}">
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
                <a href="${pageContext.request.contextPath}/servlet/deleteItemFromBucket?bucket_id=${bucket.bucketId}&itemId=${item.itemId}">DELETE</a>
            </td>
        </tr>

    </c:forEach>

</table>
<a href="${pageContext.request.contextPath}/servlet/completeOrder?bucket_id=${bucket.bucketId}">Checkout</a>
<a href="${pageContext.request.contextPath}/servlet/allItems">Add more items</a>
<a href="${pageContext.request.contextPath}/mainMenu">Main Menu</a>
</body>
</html>
