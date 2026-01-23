public class Squadra {
    private String nome;
    private String presidente;
    private Giocatore[] rosa;
    private int contaGiocatori = 0;

    public Squadra(String nome, String presidente) {
        this.nome = nome;
        this.presidente = presidente;
        // Ogni squadra può avere max 30 giocatori
        this.rosa = new Giocatore[30];
    }

    // Aggiunge un giocatore alla rosa (se non si supera il limite)
    public void aggiungiGiocatore(String nome, int numero) {
        if (contaGiocatori < rosa.length) {
            rosa[contaGiocatori] = new Giocatore(nome, numero);
            contaGiocatori++;
        }
    }

    public String getNome() {
        return nome;
    }

    // Restituisce solo i giocatori effettivi (senza null)
    public Giocatore[] getRosaEffettiva() {
        Giocatore[] effettiva = new Giocatore[contaGiocatori];
        for (int i = 0; i < contaGiocatori; i++) {
            effettiva[i] = rosa[i];
        }
        return effettiva;
    }

    // Quando la squadra viene mostrata in una JComboBox, appare il nome
    @Override
    public String toString() {
        return nome;
    }
}
