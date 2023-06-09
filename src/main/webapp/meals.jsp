<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<table border="1" style="border-collapse: collapse;">
    <caption>Meals</caption>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

<%--    <%--%>
<%--        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");--%>
<%--    %>--%>
<%--    <jsp:useBean id="formatter" scope="request" type=""/>--%>
    <c:forEach var="meal" items="${requestScope.meals}">

        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${meal.excess ? 'red' : 'green'};">
            <td>${meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))}</td>
<%--            <td>${meal.dateTime.format(formatter)}</td>--%>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
