package recursion;


public class factoriales {

    public static void main(String[] args) {
        int fact_rec = 5;
        long fact_it = 5;

        //Método recursivo (usa el tipo de dato int).
        System.out.println("El factorial de "+fact_rec+" es: "+factorial(fact_rec));

        //Método iterativo (usa el tipo de dato long).
        System.out.println("El factorial de "+fact_it+" es: "+factorial(fact_it));
    }

    public static int factorial(int num){
        //Caso base
        if(num==1 || num==0){
            return 1;
        }
        //Multiplica al número ingresado por un factor cada vez menor en cada llamada recursiva.
        else{
            return num * factorial(num-1);
        }
    }

    public static long factorial(long num){
        long fact = num-1;

        //Multiplica el número por un factor que se decrementa en cada iteración.
        while(fact>0){
            num *= fact;
            fact -= 1;
        }

        return num;
    }
}
