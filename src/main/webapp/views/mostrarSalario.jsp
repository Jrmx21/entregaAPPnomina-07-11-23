<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Mostrar Salario</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>

    <h1>Salario del Empleado</h1>
    <c:set var="nominas" value="${requestScope.nominas}" />
    <c:if test="${not empty nominas}">
        <p>El salario del empleado es:</p>
        <ul>
            <c:forEach var="sueldo" items="${nominas}">
                <li>$<c:out value="${sueldo}" /></li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty nominas}">
        <p>No se encontró información para el DNI proporcionado.</p>
    </c:if>

    <form action="/AppWebNomina/" method="get">
        <input type="submit" value="Volver">
    </form>
</body>
</html>
