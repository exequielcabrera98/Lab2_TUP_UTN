package Gestion_de_Empleados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Employee implements Impuesto{

    String name;
    int id;
    double base_salary;

    protected abstract double calculateSalary();

    @Override
    public void calcularImpuesto() {}

    public void setName() {
        Scanner scanner = new Scanner(System.in);
        this.name = scanner.next();
    }

    public void setId() {
        Scanner scanner = new Scanner(System.in);
        this.id = scanner.nextInt();
    }

    public void setBase_salary() {
        Scanner scanner = new Scanner(System.in);
        this.base_salary = scanner.nextDouble();
    }

    public double getBase_salary() {
        return base_salary;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

class SalariedEmployee extends Employee implements Impuesto {

    SalariedEmployee(){

    }

    SalariedEmployee(String name, int id, double salary){
        this.name = name;
        this.id = id;
        this.base_salary = salary;
    }
    @Override

    protected double calculateSalary() {
        return base_salary;
    }

    @Override
    public void calcularImpuesto() {
        double impuesto = this.calculateSalary() * 0.20;
        System.out.println("Total impuestos: $"+impuesto+"\nConcepto: Obra social y aportes jubilatorios.");
    }
}

class HourlyEmployee extends Employee implements Impuesto{
    int hours_worked;

    HourlyEmployee(String name, int id, int hours){
        this.name = name;
        this.id = id;
        this.hours_worked = hours;
    }
    @Override
    protected double calculateSalary() {
        int extra_hours;

        if(hours_worked>40){
            extra_hours = hours_worked - 40;

            return hours_worked * 35 + extra_hours * 45;
        }
        return hours_worked * 35;
    }

    @Override
    public void calcularImpuesto() {
        double impuesto = this.calculateSalary() * 0.10;
        System.out.println("Total impuestos: $"+impuesto+"\nConcepto: Monotributo");
    }

    public void setHours_worked() {
        Scanner scanner = new Scanner(System.in);
        this.hours_worked = scanner.nextInt();
    }

    public int getHours_worked() {
        return hours_worked;
    }
}

class CommissionEmployee extends Employee implements Impuesto{
    double sales_made;

    CommissionEmployee(String name, int id, double sales){
        this.name = name;
        this.id = id;
        this.sales_made = sales;
    }

    @Override
    protected double calculateSalary() {
        return sales_made * 0.35;
    }

    @Override
    public void calcularImpuesto() {
        double impuesto;
        if(this.calculateSalary()>700000.00){
            impuesto = this.calculateSalary() * 0.35;
            System.out.println("Total impuestos: $"+impuesto+"\nConcepto: Ganancias");
        }
        else{
            System.out.println("El contribuyente no alcanza el piso de ganancias.");
        }
    }

    public void setSales_made() {
        Scanner scanner = new Scanner(System.in);
        this.sales_made = scanner.nextDouble();
    }

    public double getSales_made() {
        return sales_made;
    }
}

class EmployeeManager {
    List<Employee> staff;

    EmployeeManager(){
        this.staff = new ArrayList<>();
    }

    void addEmployee(){
        Scanner scanner = new Scanner(System.in);
        int choice, id, horas_trabajadas;
        double salario, ventas_realizadas;
        String nombre;

        System.out.println("Seleccione el tipo de empleado aue desea agregar:");
        System.out.println("1- Empleado asalariado\n2- Empleado por hora\n3- Empleado por comisión\n");
        choice = scanner.nextInt();

        System.out.println("Nombre:");
        nombre = scanner.next();
        System.out.println("ID");
        id = scanner.nextInt();

        switch (choice){
            case 1:
                System.out.println("Salario base:");
                salario = scanner.nextDouble();
                staff.add(new SalariedEmployee(nombre,id,salario));
                break;

            case 2:
                System.out.println("Horas trabajadas:");
                horas_trabajadas = scanner.nextInt();
                staff.add(new HourlyEmployee(nombre, id, horas_trabajadas));
                break;

            case 3:
                System.out.println("Ventas realizadas");
                ventas_realizadas = scanner.nextDouble();
                staff.add(new CommissionEmployee(nombre, id, ventas_realizadas));
                break;

            default:
                System.out.println("La opción ingresada no corresponde a una categoría de empleado.");
        }
    }

    void removeEmployee(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ID del empleado que desea eliminar:\n");
        int id = scanner.nextInt();
        staff.removeIf(empleado -> empleado.id == id);
    }

    void editEmployee(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ID del empleado que desea editar:\n");
        int id = scanner.nextInt();

        for(Employee empleado:staff){
            if(empleado.id == id){
                System.out.println("Nombre:");
                empleado.setName();
                System.out.println("ID:");
                empleado.setId();
                if(empleado instanceof HourlyEmployee){
                    System.out.println("Horas trabajadas:");
                    ((HourlyEmployee) empleado).setHours_worked();
                }
                else if(empleado instanceof CommissionEmployee){
                    System.out.println("Ventas realizadas:");
                    ((CommissionEmployee) empleado).setSales_made();
                }
                else {
                    System.out.println("Salario del empleado:");
                    empleado.setBase_salary();
                }
                break;
            }
            else{
                System.out.println("El ID ingresado no corresponde a ningún empleado registrado.");
            }
        }
    }

    void viewEmployees(){
        for(Employee empleado:staff){
            System.out.println("Nombre: "+empleado.getName());
            System.out.println("ID: "+empleado.getId());
            System.out.println("Salario: "+empleado.calculateSalary());
            empleado.calcularImpuesto();
            System.out.println("------------------------------------------------");
        }
    }
}