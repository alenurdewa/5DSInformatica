public class Semaforo {


    private int count;

    public Semaforo(int count) {
        this.count = count;
    }

    public synchronized void p(){
        while (count == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        count--;
    }

    public synchronized void v(){
        count++;
        notifyAll();
    }
}
