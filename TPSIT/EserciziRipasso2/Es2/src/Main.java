public class Main {
    public static void main(String[] args) {
        System.out.println(" ");

        int[] array = {1,2,3,4,5,6,7,8,9,10};

        SommaArray somma1 = new SommaArray(0,array.length/2,array);
        SommaArray somma2 = new SommaArray(array.length/2,(array.length),array);

        somma1.start();
        somma2.start();
        try {
            somma1.join();
            somma2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }





        int somma = somma1.getSomma()+ somma2.getSomma();
        System.out.println("Somma finale = "+ somma);

        System.out.println("Risultato della prima metà con range di index "+ 0+"-"+(array.length/2-1)+" = "+ somma1.getSomma());
        System.out.println("Risultato della seconda metà con range di index "+ (array.length/2)+"-"+(array.length-1)+" = "+ somma2.getSomma());

    }

}