package Primer_parcial;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Hospital {
    public static void main(String[] args) {
        Gestion gestion = new Gestion();
        Hospital hospital = new Hospital();
        Scanner scanner = new Scanner(System.in);
        hospital.datosDeContacto();
        int opcion;
        boolean salir = false;

        while(!salir){

            System.out.println("Seleccione una operación:");
            System.out.println("""
                    1- Listar doctores
                    2- Registrar nuevo paciente
                    3- Actualizar información del paciente
                    4- Consultar historial del paciente
                    5- Nuevo historial para un paciente
                    6- Guardar historial de pacientes
                    7- Cargar historial de pacientes
                    8- Salir""");
            opcion = scanner.nextInt();

            switch (opcion){
                case 1:
                    gestion.listarDoctores();
                    break;
                case 2:
                    gestion.registrarPaciente();
                    break;
                case 3:
                    gestion.editarInformacion();
                    break;
                case 4:
                    gestion.verHistorial();
                    break;
                case 5:
                    gestion.cargarEvento();
                    break;
                case 6:
                    gestion.guardar();
                    break;
                case 7:
                    gestion.cargar();
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("La opción ingresada no es válida");
                    break;
            }
        }
        gestion.guardar();
        scanner.close();
    }

    public void datosDeContacto(){
        try{
            FileReader entrada;
            entrada = new FileReader("C:\\Users\\Exequiel\\Desktop\\Hospital\\Datos_de_contacto.txt");

            int caracter = entrada.read();
            char letra = (char)caracter;

            while(caracter != -1){
                System.out.print(letra);
                caracter = entrada.read();
                letra = (char)caracter;
            }
            entrada.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Gestion implements Serializable{
    ArrayList<Doctor> doctores = new ArrayList<>();
    ArrayList<Paciente> pacientes = new ArrayList<>();

    Gestion(){
        doctores.add(new Doctor("Juan Ramirez", 23478999, "12/6/1978", "Pediatra"));
        doctores.add(new Doctor("Marcela Sosa", 25978334, "30/4/1980", "Cirujana"));
        doctores.add(new Doctor("Alfio Smith", 27943334, "17/7/1980", "Cardiólogo"));
    }

    void listarDoctores(){
        System.out.println("Doctor/a          |DNI         |Especialidad      ");
        System.out.println("--------------------------------------------------");
        for(Doctor doctor:doctores){

            System.out.printf("%-18s %-12d %-18s\n",doctor.getNombre(),doctor.getDni(),doctor.getEspecialidad());
            System.out.println("------------------------------------------------");
        }
    }

    void registrarPaciente(){
        Scanner scanner = new Scanner(System.in);
        String nombre, telefono, fecha;
        int dni, grupo;

        System.out.println("Ingrese el nombre de paciente:");
        nombre = scanner.nextLine();
        System.out.println("DNI:");
        dni = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Fecha de nacimiento:");
        fecha = scanner.nextLine();
        System.out.println("Teléfono:");
        telefono = scanner.nextLine();
        System.out.println("Grupo Sanguíneo: (1- A,  2- B,  3- 0)");
        grupo = scanner.nextInt();

        pacientes.add(new Paciente(nombre,dni,fecha,telefono,grupo));
    }

    void editarInformacion(){
        Scanner scanner = new Scanner(System.in);
        int dni;
        System.out.println("Ingrese el DNI del paciente:");
        dni = scanner.nextInt();
        scanner.nextLine();

        for(Paciente paciente:pacientes){
            if(paciente.dni == dni){
                System.out.println("Nombre:");
                paciente.setNombre(scanner.nextLine());
                System.out.println("Fecha de nacimiento:");
                paciente.setFecha_nacimiento(scanner.nextLine());
                System.out.println("Teléfono:");
                paciente.setTelefono(scanner.nextLine());
                System.out.println("Grupo sanguíneo:");
                paciente.setGrupo_sanguineo(scanner.nextInt());
            }
        }
    }

    void cargarEvento(){
        Scanner scanner = new Scanner(System.in);
        int dni;
        System.out.println("Ingrese el DNI del paciente:");
        dni = scanner.nextInt();

        for (Paciente paciente:pacientes){
            if(paciente.dni == dni){
                paciente.cargarEvento();
            }
        }
    }

    void verHistorial(){
        Scanner scanner = new Scanner(System.in);
        int dni;
        System.out.println("Ingrese el DNI del paciente:");
        dni = scanner.nextInt();

        for (Paciente paciente:pacientes){
            if(paciente.dni == dni){
                paciente.verHistorialDeEventos();
            }
        }
    }

    void guardar(){
        try{
            FileOutputStream archivoSalida = new FileOutputStream("C:\\Users\\Exequiel\\Desktop\\Hospital\\Pacientes.ser");
            ObjectOutputStream salida = new ObjectOutputStream(archivoSalida);
            salida.writeObject(pacientes);
            salida.close();
            archivoSalida.close();
            System.out.println("Los datos han sido guardados.");
        }
        catch (Exception  e){
            e.printStackTrace();
        }
    }

    void cargar(){
        try{
            FileInputStream archivoEntrada = new FileInputStream("C:\\Users\\Exequiel\\Desktop\\Hospital\\Pacientes.ser");
            ObjectInputStream entrada = new ObjectInputStream(archivoEntrada);
            pacientes = (ArrayList<Paciente>) entrada.readObject();
            entrada.close();
            archivoEntrada.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

abstract class Persona implements Serializable{
    protected String nombre;
    protected int dni;
    protected String fecha_nacimiento;

    public String getNombre() {
        return nombre;
    }

    public int getDni() {
        return dni;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
}

class Doctor extends Persona implements Serializable{
    private String especialidad;

    Doctor(String nombre, int dni, String fecha,  String especialidad){
        this.nombre = nombre;
        this.dni = dni;
        this.fecha_nacimiento = fecha;
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}

class Paciente extends Persona implements Informacion, Serializable{

    ArrayList<String> historial = new ArrayList<>();
    private String telefono;
    private int grupo_sanguineo;

    Paciente(String nombre, int dni, String fecha, String telefono, int grupo){
        this.nombre = nombre;
        this.dni = dni;
        this.fecha_nacimiento = fecha;
        this.telefono = telefono;
        this.grupo_sanguineo =grupo;
    }

    @Override
    public void verHistorialDeEventos() {

        System.out.println("Historial médico de "+this.nombre);
        for(String evento:historial){
            System.out.println(evento);
        }
    }

    void cargarEvento(){
        Scanner scanner = new Scanner(System.in);
        String fecha, evento;
        System.out.println("Fecha:");
        fecha = scanner.nextLine();
        System.out.println("Evento:");
        evento = scanner.nextLine();

        historial.add(fecha + " - "+evento+"\n");
    }

    public void setGrupo_sanguineo(int grupo_sanguineo) {
        this.grupo_sanguineo = grupo_sanguineo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getGrupo_sanguineo() {
        return grupo_sanguineo;
    }
}

interface Informacion{
    void verHistorialDeEventos();
}