/*package Excepciones;
import java.util.Scanner;

import java.io.IOException;

public class fueraDeRango {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        try{
            evaluarNum(num);
        }
        catch (Rango e){
            e.printStackTrace();
        }
        catch(fRango)
    }

    public static void evaluarNum(int num){
        if(num<1 || num>100){
            Rango fRango = new Rango("Número fuera de rango");
        }
    }
}

class Rango extends IOException{
     public Rango(){

     }

     public Rango(String mensaje){
         super(mensaje);
     }
}

 */