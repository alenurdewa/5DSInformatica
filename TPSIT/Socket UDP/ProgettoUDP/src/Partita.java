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

    public void segnaCasa() {
        golCasa++;
    }

    public void segnaOspite() {
        golOspite++;
    }

    // Restituisce l'intestazione formattata per la GUI
    public String getIntestazione() {
        return " --- " + casa.getNome() + " " + golCasa + " - " + golOspite + " " + ospite.getNome() + " --- \n" +
                "Campo: " + campo + " (" + citta + ") | Ore: " + orario;
    }

    public Squadra getSquadraCasa() {
        return casa;
    }

    public Squadra getSquadraOspite() {
        return ospite;
    }
}