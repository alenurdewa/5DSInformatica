public class SommaArray extends Thread{

    private int indexMin;
    private int indexMax;
    private int[] array;
    private int somma;


    public SommaArray(int indexMin, int indexMax, int[] array) {
        this.indexMin = indexMin;
        this.indexMax = indexMax;
        this.array = array;
        this.somma = 0;
    }

    @Override
    public void run() {
        for(int i = indexMin; i<indexMax; i++){
            somma+=array[i];
        }
    }

    public int getSomma() {
        return somma;
    }
}
