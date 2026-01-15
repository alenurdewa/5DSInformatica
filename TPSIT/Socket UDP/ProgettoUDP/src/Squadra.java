import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Squadra implements Serializable {
    private String nome;
    private String presidente;
    private List<Giocatore> rosa;

    public Squadra(String nome, String presidente) {
        this.nome = nome;
        this.presidente = presidente;
        this.rosa = new ArrayList<>();
    }

    public void aggiungiGiocatore(String nome, int numero) {
        rosa.add(new Giocatore(nome, numero));
    }

    public String getNome() {
        return nome;
    }

    public List<Giocatore> getRosa() {
        return rosa;
    }

    @Override
    public String toString() {
        return nome;
    }
}