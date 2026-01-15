import java.util.ArrayList;
import java.util.List;

public class Campionato {
    private List<Squadra> squadre;

    public Campionato() {
        squadre = new ArrayList<>();

        // Squadra 1
        Squadra inter = new Squadra("Inter", "Marotta");
        inter.aggiungiGiocatore("Lautaro Martinez", 10);
        inter.aggiungiGiocatore("Nicolò Barella", 23);
        inter.aggiungiGiocatore("Marcus Thuram", 9);
        inter.aggiungiGiocatore("Hakan Calhanoglu", 20);

        // Squadra 2
        Squadra milan = new Squadra("Milan", "Cardinale");
        milan.aggiungiGiocatore("Rafael Leao", 10);
        milan.aggiungiGiocatore("Christian Pulisic", 11);
        milan.aggiungiGiocatore("Theo Hernandez", 19);
        milan.aggiungiGiocatore("Mike Maignan", 16);

        // Squadra 3
        Squadra juve = new Squadra("Juventus", "Ferrero");
        juve.aggiungiGiocatore("Dusan Vlahovic", 9);
        juve.aggiungiGiocatore("Federico Chiesa", 7);
        juve.aggiungiGiocatore("Gleison Bremer", 3);

        squadre.add(inter);
        squadre.add(milan);
        squadre.add(juve);
    }

    public List<Squadra> getSquadre() {
        return squadre;
    }
}