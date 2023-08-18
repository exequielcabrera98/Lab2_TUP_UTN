package recursion;

public class division_con_restas {

    public static void main(String[] args) {
        int dividendo_int=40, divisor_int=4;
        long dividendo=65, divisor=6;

        //Método recursivo (utiliza el tipo de dato int).
        System.out.println("El cociente de "+dividendo_int+"/"+divisor_int+" es: "+division(dividendo_int,divisor_int));
        //Método iterativo (utiliza el tipo de dato long).
        System.out.println("El cociente de "+dividendo+"/"+divisor+" es: "+division(dividendo,divisor));
    }

    public static int division(int dividendo, int divisor){
        //Caso base, esta función no tiene en cuenta el resto de la división.
        if(dividendo < divisor){
            return 0;
        }
        else{
            //Retorna 1 sumado al resultado de la siguiente llamada recursiva.
            return 1 + division(dividendo-divisor, divisor);
        }
    }

    public static long division(long dividendo, long divisor){
        long cociente = 0;

        //Suma 1 al cociente y resta al dividendo el divisor en cada iteración
        //hasta que el dividendo sea menor al divisor.
        while(dividendo >= divisor){
            cociente++;
            dividendo -= divisor;
        }
        return cociente;
    }
}
