public class Semaforo {
    private int count;


    public Semaforo(int c) {
        count = c;
    }

    public synchronized void p() throws InterruptedException {
        while(count==0){
            wait();
        }
        count--;
    }

    public synchronized void v(){
        count++;
        notify();
    }

}
