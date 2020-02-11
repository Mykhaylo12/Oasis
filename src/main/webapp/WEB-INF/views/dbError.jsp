<jsp:useBean id="msg" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Data error</title>
</head>
<body>
<h1>Sorry, occurred error while processing data</h1>
<h3>${msg}</h3><br>
<h3><a href="${pageContext.request.contextPath}/mainMenu">Return to Main Page</a></h3>
</body>
</html>
