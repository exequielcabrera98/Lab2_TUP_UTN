package Gestion_de_Empleados;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeManager gestor = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);
        int iniciar, opcion;

        do{
            System.out.println("¿Qué operación desea realizar?");
            System.out.println("1- Agregar empleado\n2- Eliminar empleado\n3- Editar empleado\n4- Listar empleados\n");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> gestor.addEmployee();
                case 2 -> gestor.removeEmployee();
                case 3 -> gestor.editEmployee();
                case 4 -> gestor.viewEmployees();
                default -> System.out.println("La opción ingresada no es válida.");
            }

            System.out.println("¿Realizar otra operación?\n1- Si\n2- No");
            iniciar = scanner.nextInt();
        }while (iniciar == 1);
        scanner.close();
    }
}
