public class Giocatore {
    private String nome;
    private int numero;

    public Giocatore(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    // Stampa il giocatore come "numero - nome"
    @Override
    public String toString() {
        return numero + " - " + nome;
    }
}
