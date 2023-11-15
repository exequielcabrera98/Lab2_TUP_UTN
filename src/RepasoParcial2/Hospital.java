package RepasoParcial2;

import java.sql.*;
import java.util.Date;

public class Hospital {
    public static void main(String[] args) {


        Doctor doctor3 = new Doctor("Doctor3", 30, "Bioquímico");
        HospitalBD hospital = new HospitalBD();
        hospital.listarPacientes();

    }
}

class HospitalBD{
    public void agregarPaciente(Paciente paciente){
        String consulta = "INSERT INTO pacientes (nombre, edad, historial_medico, doctor, fecha_ingreso) VALUES ('" + paciente.getNombre() + "', " + paciente.getEdad() + ", '" + paciente.getHistorial_medico() + "', " + paciente.getMedicoDeCabecera() + ", '" + paciente.getFecha_de_ingreso() + "')";
        DBHelper.ejecutarConsulta(consulta);
    }

    public void agregarMedico(Doctor doctor){
        String consulta = "INSERT INTO doctores (nombre, edad, especialidad) VALUES ('" + doctor.getNombre() + "', " + doctor.getEdad() + ", '" + doctor.getEspecialidad() + "')";
        DBHelper.ejecutarConsulta(consulta);
    }

    public void eliminarPaciente(String nombre){
        String consulta = "DELETE FROM pacientes WHERE nombre = '" + nombre +"'";
        DBHelper.ejecutarConsulta(consulta);
    }

    public void asignarDoctorCabecera(String nombreDoctor, String nombrePaciente){
        String consulta = "UPDATE pacientes SET doctor = (SELECT id FROM doctores WHERE nombre = '"+nombreDoctor+"') WHERE nombre = '"+nombrePaciente+"'";
        DBHelper.ejecutarConsulta(consulta);
    }

    public void listarPacientes(){
        String consulta = "SELECT * From pacientes";
        ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta);
        listarPaciente(resultado);
    }

    public void listarPacientesEntreDosFechas(Date fecha_desde, Date fecha_hasta){
        String consulta = "SELECT * FROM pacientes WHERE fecha_ingreso BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"'";
        ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta);
        listarPaciente(resultado);
    }

    public void listarPaciente(ResultSet resultado){
        if(resultado != null){
            try{
                System.out.println("Lista de Pacientes:");
                System.out.printf("%-10s %-15s %-5s %-20s %-12s %-10s\n", "ID", "Nombre", "Edad", "Historial Médico", "Fecha Ingreso", "Doctor");

                while(resultado.next()){
                    int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    int edad = resultado.getInt("edad");
                    String historialMedico = resultado.getString("historial_medico");
                    Date fechaIngreso = resultado.getDate("fecha_ingreso");
                    int idDoctor = resultado.getInt("doctor");

                    System.out.printf("%-10d %-15s %-5d %-20s %-12s %-10d\n", id, nombre, edad, historialMedico, fechaIngreso, idDoctor);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}

abstract class Persona{
    protected String nombre;
    protected int edad;
}

class Paciente extends Persona{
    private String historial_medico;
    private String fecha_de_ingreso;
    private int medicoDeCabecera;

    Paciente(String nombre, int edad, String historial, String ingreso, int docCabecera){
        this.nombre = nombre;
        this.edad = edad;
        this.historial_medico = historial;
        this.fecha_de_ingreso = ingreso;
        this.medicoDeCabecera = docCabecera;
    }

    public String getNombre(){
        return nombre;
    }

    public int getEdad(){
        return edad;
    }

    public String getFecha_de_ingreso() {
        return fecha_de_ingreso;
    }

    public String getHistorial_medico() {
        return historial_medico;
    }

    public int getMedicoDeCabecera() {
        return medicoDeCabecera;
    }
}

class Doctor extends Persona{
    private String especialidad;

    Doctor(String nombre, int edad, String especialidad){
        this.nombre = nombre;
        this.edad = edad;
        this.especialidad = especialidad;
    }

    public String getNombre(){
        return nombre;
    }

    public int getEdad(){
        return edad;
    }
    public String getEspecialidad() {
        return especialidad;
    }
}

class DBHelper{
    private static final String url = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String usuario = "root";
    private static final String pass = "";

    //Método para ejecutar una consulta sin devolver resultados
    public static void ejecutarConsulta(String consulta){
        try{
            //Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection(url, usuario, pass);

            //Crear la declaración
            try(PreparedStatement statement = connection.prepareStatement(consulta)){
                //Ejecutar consulta
                statement.executeUpdate();
            }

            //Cerrar la conexión
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Método para ejecutar una consulta y devolver un conjunto de resultados
    public static ResultSet ejecutarConsultaConResultado(String consulta){
        try {
            //Establecer la conexión
            Connection connection = DriverManager.getConnection(url, usuario, pass);

            //Crear la declaración
            PreparedStatement statement = connection.prepareStatement(consulta);

            return statement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}