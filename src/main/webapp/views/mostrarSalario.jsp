<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mostrar Salario</title>
<link rel="stylesheet" href="styles/style.css"></link>
</head>
<body>

    <h1>Salario del Empleado</h1>
    <% List<Double> nominas = (List<Double>)request.getAttribute("nominas"); %>
    <% if (nominas != null && !nominas.isEmpty()) { %>
        <p>El salario del empleado es:</p>
        <ul>
            <% for (Double sueldo : nominas) { %>
                <li>$<%= sueldo %></li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No se encontró información para el DNI proporcionado.</p>
    <% } %>
</body>
<form action="/AppWebNomina/" method="get">
        <input type="submit" value="Volver">
    </form>
</html>
