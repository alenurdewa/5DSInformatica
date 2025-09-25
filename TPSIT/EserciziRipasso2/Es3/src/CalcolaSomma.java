public class CalcolaSomma extends Thread{

    private int idDipendente;
    private float[][] matriceDipendenti;
    private float sommaStipendi;

    public CalcolaSomma(int idDipendente, float[][] matriceDipendenti) {
        this.idDipendente = idDipendente;
        this.matriceDipendenti = matriceDipendenti;
        sommaStipendi=0;
    }

    @Override
    public void run() {

        for (int i =0; i< matriceDipendenti.length; i++){

            for (int j=0; j<matriceDipendenti[0].length;j++){
                if (j==idDipendente){
                    sommaStipendi+=matriceDipendenti[i][j];
                }
            }
        }

    }

    public float getSommaStipendi() {
        return sommaStipendi;
    }
}
