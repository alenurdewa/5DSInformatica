import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int numeroDipendenti = 5;
        int numeroMesi = 3;

        float[][] matrix = new float[3][5];


        for (int i = 0; i<numeroMesi; i++) {

            for (int j = 0; j < numeroDipendenti; j++){
                System.out.println("Inserire lo stipendio del "+(i+1)+ " mese dell'anno del "+ (j+1) + " dipendente");
                float stipendio = input.nextInt();
                while (stipendio<=0){
                    System.out.println("Lo stipendio deve essere positivo");
                    stipendio = input.nextInt();
                }
                matrix[i][j] = stipendio;
            }
        }


        System.out.println("Scegli l'id del dipendente da 1 a 5");

        int idDipendenteScelto = input.nextInt();
        while (idDipendenteScelto<1 || idDipendenteScelto >5){
            System.out.println("Il numero inserito non è valido");
            idDipendenteScelto = input.nextInt();
        }

        CalcolaSomma calcolaSomma = new CalcolaSomma(idDipendenteScelto-1, matrix);
        calcolaSomma.start();
        try {
            calcolaSomma.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("La somma degli stipendi del dipendente "+idDipendenteScelto+ " è " + calcolaSomma.getSommaStipendi());


    }


}