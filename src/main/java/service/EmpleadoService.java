package service;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import model.*;
import dao.*;

//Service
public class EmpleadoService {
 private EmpleadoDAO empleadoDAO;

 public EmpleadoService() {
     this.empleadoDAO = new EmpleadoDAO();
 }

 public List<Empleado> obtenerTodosLosEmpleados() {
     try {
         return empleadoDAO.obtenerEmpleados();
     } catch (SQLException e) {
         e.printStackTrace();
         return Collections.emptyList();
     }
 }
 
 public Empleado obtenerEmpleadoPorDni(String dni) {
     return empleadoDAO.obtenerEmpleadoPorDni(dni);
 }

 public void guardarEmpleado(Empleado empleado) {
     try {
         empleadoDAO.guardar(empleado);
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 public void editarEmpleado(Empleado empleado) {
     try {
         empleadoDAO.editar(empleado);
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
}
