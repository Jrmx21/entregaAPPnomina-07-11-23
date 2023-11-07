package controller;

import java.io.IOException;
import model.*;
import dao.*;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class EmpleadoServlet
 * 
 * Administra peticiones para la tabla de empleados.
 */
@WebServlet(description = "Administra peticiones para la tabla", urlPatterns = "/empleados")
public class EmpleadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final char E = 0;
	/**
     * Maneja el método HTTP GET.
     *
     * @param request  Objeto HttpServletRequest.
     * @param response Objeto HttpServletResponse.
     * @throws ServletException si hay dificultades en la solicitud del servlet.
     * @throws IOException      si hay un error de E/S en la respuesta del servlet.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		try {
			System.out.println("empleados?" + EmpleadoDAO.buscarEmpleadosPorCriterio("dni", "20099558L"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String opcion = request.getParameter("opcion");

		if (opcion.equals("resultadosBusqueda")) {

			try {
				List<Empleado> empleados = EmpleadoDAO.realizarBusqueda(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (opcion.equals("listar")) {
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			List<Empleado> lista = new ArrayList<>();
			try {
				lista = empleadoDAO.obtenerEmpleados();
				for (Empleado empleado : lista) {
					System.out.println(empleado);
				}
				request.setAttribute("lista", lista);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", "Error al obtener empleados: " + e.getMessage());
			}

			System.out.println("Usted a presionado la opcion listar");
		}

		else if (opcion.equals("meditar")) {
			List<Empleado> empleados = new ArrayList<>();
			Empleado empleado = new Empleado("", "", E);
			String dni = request.getParameter("dni");
			System.out.println("Editar dni: " + empleado.dni);
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			
			try {
				empleados = empleadoDAO.obtenerEmpleados();
				System.out.println(empleados);
				request.setAttribute("empleados", empleados);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException error) {
				// TODO Auto-generated catch block
				error.printStackTrace();
			}
		}else if (opcion.equals("guardar")) {
			
			   try {
				   EmpleadoDAO empleadoDAO = new EmpleadoDAO();
				   Empleado empleado = new Empleado(request.getParameter("nombre"),request.getParameter("dni"),
					        request.getParameter("sexo").toCharArray()[0], Integer.parseInt(request.getParameter("categoria")),
					        Integer.parseInt(request.getParameter("anyos")));
			    empleadoDAO.guardar(empleado);
			    System.out.println("Registro guardado satisfactoriamente...");
			    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
			    requestDispatcher.forward(request, response);
			 
			   } catch (SQLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			   }
			  } else if (opcion.equals("editar")) {
		            List<Empleado> empleados = new ArrayList<>();
		            Empleado empleado = null; // Cambio: Inicializar empleado como null
		            String dni = request.getParameter("dni");
		            System.out.println("Editar dni: " + dni);

		            try {
		                // Cambio: Obtener el empleado por DNI
		                empleados = EmpleadoDAO.buscarEmpleadosPorCriterio("dni", dni);
		                if (!empleados.isEmpty()) {
		                    empleado = empleados.get(0);
		                    request.setAttribute("empleado", empleado); // Cambio: Añadir el empleado a los atributos de la solicitud
		                }
		            } catch (SQLException error) {
		                error.printStackTrace();
		            }

			  }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opcion = request.getParameter("opcion");
		// Recuperar el DNI del formulario
		String dni = request.getParameter("dni");

		// Llamar a un método para obtener las nominas desde la base de datos
		List<Double> nominas = EmpleadoDAO.obtenerNominasDesdeBD(dni);
		// Establecer las nominas como un atributo para la página JSP
		request.setAttribute("nominas", nominas);

		// Redirigir a la página JSP de visualización
		if(opcion.equals("mostrarSalario")) {
			
			//TENDRAS QUE GUARDAR EN UNA VARIABLE
			double salario = EmpleadoDAO.obtenerSalarioDesdeBD(dni);
			
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/mostrarSalario.jsp");
			dispatcher.forward(request, response);
			
			
		}
		else if (opcion.equals("listar")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listar.jsp");
			dispatcher.forward(request, response);
			
		}
	}
}