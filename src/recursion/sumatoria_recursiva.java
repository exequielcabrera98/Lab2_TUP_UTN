package recursion;
import java.util.Scanner;

public class sumatoria_recursiva {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num;

        System.out.println("Ingrese un número: ");
        num = scanner.nextInt();

        System.out.println("La sumatoria de los números comprendidos entre 1 y "+num+" es: "+sumatoriaRecursiva(num));
    }

    public static int sumatoriaRecursiva(int num){

        //Caso base
        if(num==1){
            return 1;
        }
        //Suma al número ingresado un número cada vez menor en cada llamada recursiva.
        else{
            return num + sumatoriaRecursiva(num-1);
        }
    }
}
