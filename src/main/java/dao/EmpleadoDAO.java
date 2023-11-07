package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;
import connection.*;
/**
 * Clase que maneja las operaciones de acceso a datos para la entidad Empleado.
 */
public class EmpleadoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	/**
     * Guarda un nuevo empleado en la base de datos.
     *
     * @param empleado El objeto Empleado a ser guardado.
     * @return true si la operación fue exitosa, false de lo contrario.
     * @throws SQLException si hay un error de acceso a la base de datos.
     */
	public boolean guardar(Empleado empleado) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql = "INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear,fecha_actualizar) VALUES(?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, empleado.getNombre());
			statement.setString(2, String.valueOf(empleado.getSexo()));
			statement.setInt(4, empleado.getAnyos());
			statement.setString(5, empleado.getDni());

			estadoOperacion = statement.executeUpdate() > 0;

			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	  /**
     * Edita un empleado existente en la base de datos.
     *
     * @param empleado El objeto Empleado con los datos actualizados.
     * @return true si la operación fue exitosa, false de lo contrario.
     * @throws SQLException si hay un error de acceso a la base de datos.
     */
		 public boolean editar(Empleado empleado) throws SQLException {
		  String sql = null;
		  estadoOperacion = false;
		  connection = obtenerConexion();
		  try {
		   connection.setAutoCommit(false);
		   sql = "UPDATE empleados SET nombre=?, sexo=?, categoria=?, sexo=?, WHERE dni=?";
		   statement = connection.prepareStatement(sql);
		 
		   statement.setString(1, empleado.getNombre());
		   statement.setString(2, String.valueOf(empleado.getSexo()));
		   statement.setInt(3, empleado.getCategoria());
		   statement.setInt(4, empleado.getAnyos());
		   statement.setString(5, empleado.getDni());
		 
		   estadoOperacion = statement.executeUpdate() > 0;
		   connection.commit();
		   statement.close();
		   connection.close();
		 
		  } catch (SQLException e) {
		   connection.rollback();
		   e.printStackTrace();
		  }
		 
		  return estadoOperacion;
		 }
		 /**
		     * Obtiene las nóminas asociadas a un empleado mediante su DNI.
		     *
		     * @param dniEmpleado El DNI del empleado.
		     * @return Una lista de salarios asociados al empleado.
		     */
	 public List<Double> obtenerNominasPorDNI(String dniEmpleado) {
	        List<Double> nominas = new ArrayList<>();
	        
	        try (Connection connection = ConnectionDB.getConnection()) {
	            String sql = "SELECT n.sueldo FROM nominas n INNER JOIN empleados e ON n.dni_empleado = e.dni WHERE e.dni = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, dniEmpleado);
	                
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    while (resultSet.next()) {
	                        double sueldo = resultSet.getDouble("sueldo");
	                        nominas.add(sueldo);
	                        System.out.println("obtenerNominasPorDni success");
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Manejo de errores: Considera un manejo más robusto en producción
	        }

	        return nominas;
	    }
	  /**
	     * Obtiene la lista de todos los empleados en la base de datos.
	     *
	     * @return Una lista de objetos Empleado.
	     * @throws SQLException si hay un error de acceso a la base de datos.
	     */
	public List<Empleado> obtenerEmpleados() throws SQLException {
	    ResultSet resultSet = null;
	    List<Empleado> listaEmpleados = new ArrayList<>();

	    String sql = null;
	    estadoOperacion = false;
	    connection = obtenerConexion();

	    try {
	        sql = "SELECT * FROM empleados";
	        statement = connection.prepareStatement(sql);
	        resultSet = statement.executeQuery();  // Corregir aquí
	        while (resultSet.next()) {
	            Empleado emp = new Empleado(resultSet.getString(2), resultSet.getString(1),
	                    resultSet.getString(3).toCharArray()[0], resultSet.getInt(4), resultSet.getInt(5));

	            listaEmpleados.add(emp);
	        System.out.println("obtenerEmpleados sucess");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Asegúrate de cerrar los recursos en el bloque finally
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (statement != null) {
	            statement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	    System.out.println("listar empleado "+listaEmpleados);
	    return listaEmpleados;
	}
	/**
     * Busca empleados según el criterio y el valor especificados.
     *
     * @param criterio El criterio de búsqueda.
     * @param valor    El valor a buscar.
     * @return Una lista de empleados que coinciden con los criterios de búsqueda.
     * @throws SQLException si hay un error de acceso a la base de datos.
     */
	  public static List<Empleado> buscarEmpleadosPorCriterio(String criterio, String valor) throws SQLException {
	        List<Empleado> empleados = new ArrayList<>();

	        try (Connection connection = ConnectionDB.getConnection()) {
	            String sql = "SELECT * FROM empleados WHERE " + criterio + " = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, valor);
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    while (resultSet.next()) {
	                    	 Empleado empleado = new Empleado(resultSet.getString(1), resultSet.getString(2),
	         	                    resultSet.getString(3).toCharArray()[0], resultSet.getInt(4), resultSet.getInt(5));
	                    	 System.out.println("buscarEmpleadosPorCriterio sucess");
	                        empleados.add(empleado);
	                    }
	                }
	            }
	        }
	        return empleados;
	    }
	  public static double obtenerSalarioDesdeBD(String dni) {
			double salario = 0.0;
	
			// Configura tu conexión a la base de datos aquí usando ConnectionDB
			try (Connection connection = ConnectionDB.getConnection()) {
				// Consulta SQL para obtener el sueldo asociado al DNI de la tabla empleados
				String sql = "SELECT n.sueldo FROM nominas n INNER JOIN empleados e ON n.dni_empleado = e.dni WHERE e.dni = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
					preparedStatement.setString(1, dni);
	
					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						if (resultSet.next()) {
							salario = resultSet.getDouble("sueldo");
						}
						System.out.println(" obtenerSalarioDesdeBD success");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			return salario;
		}
	  /**
	     * Obtiene una conexión a la base de datos.
	     *
	     * @return Una instancia de Connection.
	     * @throws SQLException si hay un error de acceso a la base de datos.
	     */
	  public static List<Double> obtenerNominasDesdeBD(String dni) {
			// Llamar al método de EmpleadoDAO para obtener las nominas
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			System.out.println(" obtenerNominasDesdeBD success");
			return empleadoDAO.obtenerNominasPorDNI(dni);

		}
		/**
	     * Actualiza los salarios de los empleados en la base de datos.
	     */
		public static void actualizarSalario() {

			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			List<Empleado> empleados = null;
			try {
				empleados = empleadoDAO.obtenerEmpleados();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Nomina n = new Nomina();
			for (Empleado empleado : empleados) {
				int nuevoSueldo = n.sueldo(empleado);
				try (Connection connection = ConnectionDB.getConnection()) {
					String sql = "UPDATE nominas SET sueldo = ? WHERE dni = (SELECT dni FROM empleados WHERE dni_empleado = ?)";
					try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
						preparedStatement.setInt(2, nuevoSueldo);
						preparedStatement.setString(1, empleado.getDni());
						preparedStatement.executeUpdate();
						System.out.println(" actualizarSalario success");
					}
				} catch (SQLException e) {
					e.printStackTrace(); // Manejo de errores: Considera un manejo más robusto en producción
				}
			}
		}
		/**
	     * Realiza una búsqueda de empleados según el criterio y el valor especificados.
	     *
	     * @param request  Objeto HttpServletRequest.
	     * @param response Objeto HttpServletResponse.
	     * @return Una lista de empleados que coinciden con los criterios de búsqueda.
	     * @throws ServletException si hay dificultades en la solicitud del servlet.
	     * @throws IOException      si hay un error de E/S en la respuesta del servlet.
	     * @throws SQLException     si hay un error de acceso a la base de datos.
	     */
		public static List<Empleado> realizarBusqueda(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException, SQLException {
		    // Recuperar los parámetros del formulario de búsqueda
		    String criterio = request.getParameter("criterio");
		    String valor = request.getParameter("valor");
		    List<Empleado> empleados = buscarEmpleadosPorCriterio(criterio, valor);

		    // Imprimir información de depuración
		    for (Empleado empleado : empleados) {
		        System.out.println("empleados: " + empleado.toString());
		        System.out.println("lista empleados: " + empleados);
		    }

		    // Establecer los resultados como atributo para la página JSP
		    request.setAttribute("empleados", empleados);

		    // Redirigir a la página JSP de resultados
		    request.getRequestDispatcher("/views/resultadosBusqueda.jsp").forward(request, response);
		    return empleados;
		}
	  
	private Connection obtenerConexion() throws SQLException {
		return ConnectionDB.getConnection();
	}

	public Empleado obtenerEmpleadoPorDni(String dni) {
		// TODO Auto-generated method stub
		return null;
	}
	

}