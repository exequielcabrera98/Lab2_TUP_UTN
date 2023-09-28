package Repaso;
/*Escribe un programa en Java que realice las siguientes operaciones con un `ArrayList` de números enteros:
Crea un `ArrayList` llamado `miLista` e inicialízalo con 5 números enteros de tu elección.
Lee un número entero `n` desde el teclado. Luego, agrega este número al final de `miLista`.
Lee otro número entero `posicion` desde el teclado. Luego, muestra el elemento en la posición `posicion` de `miLista`.
Lee un número entero `indice` desde el teclado. Luego, elimina el elemento en la posición `indice` de `miLista`.
Muestra el contenido actual de `miLista` después de todas las operaciones.
Asegúrate de manejar los posibles errores.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> lista = new ArrayList<>(Arrays.asList(78, 23, 54, 12, 19));
        int op, continuar = 1;

        while(continuar == 1){
            System.out.println("Seleccione la operación:\n1- Añadir\n2- Ver Elemento\n3- Eliminar\n4- Ver lista\n");
            op = scanner.nextInt();

            switch (op) {
                case 1 -> agregarNumero(lista);
                case 2 -> mostrarElemento(lista);
                case 3 -> eliminarElemento(lista);
                case 4 -> mostrarLista(lista);
                default -> System.out.println("La opción ingresada no es válida:");
            }

            System.out.println("¿Desea continuar?\n1- Si\n2- No\n");
            continuar = scanner.nextInt();
        }
    }

    public static void agregarNumero(ArrayList<Integer> lista){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número a agregar:");
        lista.add(scanner.nextInt());
        scanner.close();
    }

    public static void mostrarElemento(ArrayList<Integer> lista){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la posición:");
        System.out.println(lista.get(scanner.nextInt()-1));
        scanner.close();
    }

    public static void eliminarElemento(ArrayList<Integer> lista){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el índice del elemento a eliminar:");
        lista.remove(scanner.nextInt());
        scanner.close();
    }

    public static void mostrarLista(ArrayList<Integer> lista){
        System.out.println(lista.toString());
    }
}
