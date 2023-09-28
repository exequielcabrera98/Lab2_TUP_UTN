package Repaso;
import java.io.*;
import java.util.*;

class Main{
    public static void main(String[] args) throws IOException{

        Concesionaria concesionaria = new Concesionaria();
        Scanner scanner = new Scanner(System.in);

        int opcion, continuar;
        String marca, modelo;

        do{
            System.out.println("¿Qué operación desea realizar?");
            System.out.println("1- Agregar vehículo\n2- Eliminar vehículo\n3- Editar precio\n4- Mostrar inventario\n5- Cargar datos\n6- Guardar datos");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> concesionaria.agregarVehiculo();
                case 2 -> {
                    System.out.println("Marca: ");
                    marca = scanner.nextLine();
                    System.out.println("Modelo:");
                    modelo = scanner.nextLine();
                    concesionaria.eliminarVehiculo(marca, modelo);
                }
                case 3 -> {
                    System.out.println("Marca: ");
                    marca = scanner.nextLine();
                    System.out.println("Modelo:");
                    modelo = scanner.nextLine();
                    concesionaria.editarPrecio(marca, modelo);
                }
                case 4 -> concesionaria.mostrarInventario();
                case 5 -> concesionaria.cargar();
                case 6 -> concesionaria.guardar();
            }
            System.out.println("¿Continuar?\n1- Si\n2- No");
            continuar = scanner.nextInt();
        }while (continuar == 1);
        scanner.close();
        concesionaria.guardar();
    }
}

public class Concesionaria implements Serializable{

    List<Vehiculo> inventario;

    Concesionaria() {
        this.inventario = new ArrayList<>();
    }

    void agregarVehiculo() {
        Scanner scanner = new Scanner(System.in);
        String marca, modelo;
        double precio;
        int opcion;

        try {
            System.out.println("Seleccione el tipo de vehículo que desea agregar:\n1- Automóvil\n2- Motocicleta\n3- Utilitario");
            opcion = scanner.nextInt();

            if (opcion < 1 || opcion > 3) {
                System.out.println("La opción ingresada no es válida.");
            } else {
                scanner.nextLine();

                System.out.println("Ingrese la marca del vehículo:");
                marca = scanner.nextLine();

                System.out.println("Ingrese el modelo del vehículo:");
                modelo = scanner.nextLine();

                System.out.println("Ingrese el precio del vehículo (sin impuestos):");
                precio = scanner.nextDouble();

                switch (opcion) {
                    case 1 -> inventario.add(new Automovil(marca, modelo, precio));
                    case 2 -> inventario.add(new Motocicleta(marca, modelo, precio));
                    case 3 -> inventario.add(new Utilitario(marca, modelo, precio));
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número.");
        }
    }

    void eliminarVehiculo(String marca, String modelo){
        inventario.removeIf(vehiculo -> Objects.equals(vehiculo.marca, marca) && Objects.equals(vehiculo.modelo, modelo));
    }

    void editarPrecio(String marca, String modelo){
        Scanner scanner = new Scanner(System.in);
        for(Vehiculo vehiculo:inventario){
            if(Objects.equals(vehiculo.marca, marca) && Objects.equals(vehiculo.modelo, modelo)){
                System.out.println("Precio:");
                vehiculo.setPrecio(scanner.nextDouble());
            }
        }
    }

    void mostrarInventario(){
        System.out.println("       Marca        |       Modelo       |      Precio      |      Impuesto      ");
        System.out.println("---------------------------------------------------------------------------------");

        for(Vehiculo vehiculo:inventario){
            System.out.printf("%-20s %-20s %-20.2f %-20.2f\n",vehiculo.marca, vehiculo.modelo, vehiculo.precio, vehiculo.calcularImpuesto());
        }
    }

    @Override
    public void guardar() {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Exequiel\\Documents\\TUP\\Segundo Cuatrimestre\\Laboratorio de Computción\\inventario.ser");
            ObjectOutputStream salida = new ObjectOutputStream(fileOutputStream);
            salida.writeObject(inventario);
            salida.close();
            fileOutputStream.close();
            System.out.println("Los elementos han sido guardados.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void cargar() {
        try{
            FileInputStream archivoEntrada = new FileInputStream("C:\\Users\\Exequiel\\Documents\\TUP\\Segundo Cuatrimestre\\Laboratorio de Computción\\inventario.ser");
            ObjectInputStream entrada = new ObjectInputStream(archivoEntrada);
            inventario = (ArrayList<Vehiculo>) entrada.readObject();
            entrada.close();
            archivoEntrada.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

abstract class Vehiculo implements Serializable{
    String marca;
    String modelo;
    double precio;

    abstract double calcularImpuesto();
    abstract void mostrarInformacion();

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Automovil extends Vehiculo{

    Automovil(String marca, String modelo, double precio){
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
    }

    @Override
    double calcularImpuesto() {
        return this.precio * 0.3;
    }

    @Override
    void mostrarInformacion() {
        System.out.println("Marca: "+this.marca+
                "\nModelo: "+this.modelo+
                "\nPrecio: "+this.precio+
                "Impuesto: "+this.calcularImpuesto());
    }

    @Override
    public void guardar() {

    }

    @Override
    public void cargar() {

    }
}

class Motocicleta extends Vehiculo{

    Motocicleta(String marca, String modelo, double precio){
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
    }

    @Override
    double calcularImpuesto() {
        return this.precio * 0.25;
    }

    @Override
    void mostrarInformacion() {
        System.out.println("Marca: "+this.marca+
                "\nModelo: "+this.modelo+
                "\nPrecio: "+this.precio+
                "Impuesto: "+this.calcularImpuesto());
    }

    @Override
    public void guardar() {

    }

    @Override
    public void cargar() {

    }
}

class Utilitario extends Vehiculo{

    Utilitario(String marca, String modelo, double precio){
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
    }

    @Override
    double calcularImpuesto() {
        return this.precio * 0.20;
    }

    @Override
    void mostrarInformacion() {
        System.out.println("Marca: "+this.marca+
                "\nModelo: "+this.modelo+
                "\nPrecio: "+this.precio+
                "Impuesto: "+this.calcularImpuesto());
    }

    @Override
    public void guardar() {

    }

    @Override
    public void cargar() {

    }
}

interface Serializable extends java.io.Serializable{
    void guardar();
    void cargar();
}