package Repaso;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Hotel {
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        GestorDeHabitaciones gestor = new GestorDeHabitaciones();
        Scanner scanner2 = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        hotel.leerNombre();
        System.out.println("\n");

        while(!salir){

            System.out.println("Seleccione una operación:");
            System.out.println("""
                    1- Ver habitaciones
                    2- Reservar una habitación
                    3- Cancelar una reservación
                    4- Guardar
                    5- Cargar
                    6- Salir""");
            opcion = scanner2.nextInt();

            switch (opcion){
                case 1:
                    gestor.mostrarHabitaciones();
                    break;
                case 2:
                    System.out.println("Ingrese el número de habitación:");
                    gestor.reservar(scanner2.nextInt());
                    break;
                case 3:
                    System.out.println("Ingrese el número de habitación:");
                    gestor.cancelarReserva(scanner2.nextInt());
                    break;
                case 4:
                    gestor.guardar();
                    break;
                case 5:
                    gestor.cargar();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("La opción ingresada no es válida");
            }
        }
        scanner2.close();
    }

    void leerNombre(){
        try{
            FileReader entrada;
            entrada = new FileReader("C:\\Users\\Exequiel\\Desktop\\Hotel\\Nombre_hotel.txt");

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


abstract class Persona implements MostrarInformacion{
    String nombre;
    String localidad;
    @Override
    public void mostrar() {

    }
}

class GestorDeHabitaciones implements Serializable {
    ArrayList<Habitacion> habitaciones = new ArrayList<>();

    GestorDeHabitaciones(){
        habitaciones.add(new Habitacion(101, 3));
        habitaciones.add(new Habitacion(102, 3));
        habitaciones.add(new Habitacion(103, 3));
        habitaciones.add(new Habitacion(104, 3));
        habitaciones.add(new Habitacion(105, 3));
    }

    void mostrarHabitaciones(){
        System.out.println("Nro de habitación |Cantidad de camas |Estado      ");
        System.out.println("------------------------------------------------");
        for(Habitacion habitacion:habitaciones){
            String estado;

            if(habitacion.reservada){
                estado = "Reservada";
            }
            else{
                estado = "Disponible";
            }

            System.out.printf("%-18d %-18d %-12s\n",habitacion.nroHabitacion, habitacion.cantCamas, estado);
            habitacion.mostrar();
            System.out.println("------------------------------------------------");
        }
    }

    void reservar(int num){
        for(Habitacion habitacion:habitaciones){
            if(habitacion.nroHabitacion == num){
                if(!habitacion.reservada){
                    habitacion.reservada = true;
                    habitacion.IngresarHuespedes();
                    break;
                }
                else{
                    System.out.println("Lo lamento, esta habitación ya esta reservada.");
                    break;
                }
            }
        }
    }

    void cancelarReserva(int num){
        for(Habitacion habitacion:habitaciones){
            if(habitacion.nroHabitacion == num){
                if(habitacion.reservada){
                    habitacion.reservada = false;
                    habitacion.huespedes.clear();
                }
                else{
                    System.out.println("Esta habitación no esta reservada.");
                }
            }
        }
    }

    void guardar(){
        try{
            FileOutputStream archivoSalida = new FileOutputStream("C:\\Users\\Exequiel\\Desktop\\Hotel\\Habitaciones.ser");
            ObjectOutputStream salida = new ObjectOutputStream(archivoSalida);
            salida.writeObject(habitaciones);
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
            FileInputStream archivoEntrada = new FileInputStream("C:\\Users\\Exequiel\\Desktop\\Hotel\\Habitaciones.ser");
            ObjectInputStream entrada = new ObjectInputStream(archivoEntrada);
            habitaciones = (ArrayList<Habitacion>) entrada.readObject();
            entrada.close();
            archivoEntrada.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Habitacion implements MostrarInformacion, Serializable{

    int nroHabitacion;
    int cantCamas;
    boolean reservada;
    ArrayList<Huesped> huespedes = new ArrayList<>();

    Habitacion(int nroHabitacion, int cantCamas){
        this.nroHabitacion = nroHabitacion;
        this.cantCamas = cantCamas;
        this.reservada = false;
    }

    void IngresarHuespedes(){
        Scanner s = new Scanner(System.in);
        int cantHuespedes;
        String nombre, localidad;

        System.out.println("Ingrese la cantidad de huéspedes:");
        cantHuespedes = s.nextInt();
        s.nextLine();

        if(cantHuespedes <= this.cantCamas){
            for(int i = 0;i < cantHuespedes; i++){
                System.out.println("Nombre:");
                nombre = s.nextLine();
                System.out.println("Localidad:");
                localidad = s.nextLine();

                huespedes.add(new Huesped(nombre, localidad));
            }
        }
        else{
            System.out.println("La cantidad de huéspedes es mayor a la cantidad de camas.");
        }
    }


    @Override
    public void mostrar() {
        System.out.println("Habitación N°" + this.nroHabitacion);

        if(this.reservada){

            for(Huesped huesped:huespedes){
                huesped.mostrar();
            }
        }
        else{
            System.out.println("La habitación está vacía.");
        }
    }
}

class Huesped extends Persona implements MostrarInformacion, Serializable {


    Huesped(String nombre,  String localidad){
        this.nombre = nombre;
        this.localidad = localidad;
    }
    @Override
    public void mostrar() {
        System.out.println(this.nombre+"\t"+this.localidad);
    }
}

interface MostrarInformacion{
    void mostrar();
}