public class Partita {
    private Squadra casa;
    private Squadra ospite;
    private String citta;
    private String campo;
    private String orario;
    private int golCasa = 0;
    private int golOspite = 0;

    public Partita(Squadra casa, Squadra ospite, String citta, String campo, String orario) {
        this.casa = casa;
        this.ospite = ospite;
        this.citta = citta;
        this.campo = campo;
        this.orario = orario;
    }

    // Aumenta il punteggio della squadra di casa
    public void segnaCasa() {
        golCasa++;
    }

    // Aumenta il punteggio della squadra ospite
    public void segnaOspite() {
        golOspite++;
    }

    // Restituisce l'intestazione della partita (es. Inter 1 - 0 Milan | Stadio ...)
    public String getIntestazione() {
        return casa.getNome() + " " + golCasa + " - " + golOspite + " " + ospite.getNome() +
                " | " + campo + " (" + citta + ")";
    }

    public Squadra getSquadraCasa() {
        return casa;
    }

    public Squadra getSquadraOspite() {
        return ospite;
    }
}
