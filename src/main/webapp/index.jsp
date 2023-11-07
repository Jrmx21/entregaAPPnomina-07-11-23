<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Inicio Empleados</title>
    <link rel="stylesheet" href="styles/style.css"></link>
</head>
<body>
    <h1>Menu de Opciones Empleados</h1>
    <table>
        <tr>
            <td><a href="empleados?opcion=listar"> Listar empleados</a></td>
        </tr>
        <tr>
            <td><a href="empleados?opcion=actualizar"> Actualizar empleados</a></td>
        </tr>
        </table>
	
	<form action="empleados?opcion=mostrarSalario" method="post">
	<h2>Buscar salario por DNI</h2>
		DNI: <input type="text" name="dni" required> <input
			type="submit" value="Buscar">
	</form>
    <form action="empleados" method="get">
        <h2>Buscar Por criterio</h2>
        <input type="hidden" name="opcion" value="resultadosBusqueda">
        <label>Buscar por:</label>
        <select name="criterio">
            <option value="dni">DNI</option>
            <option value="nombre">Nombre</option>
            <option value="sexo">Sexo</option>
            <option value="categoria">Categoria</option>
            <option value="anyos">Años</option>
        </select>
        <input type="text" name="valor" required>
        <input type="submit" value="Buscar">
    </form>

</body>
</html>
