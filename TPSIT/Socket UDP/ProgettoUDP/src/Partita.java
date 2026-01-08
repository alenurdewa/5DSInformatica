public class Partita {
    private String squadraCasa;
    private String squadraOspite;
    private String citta;
    private String campo;
    private String orario;
    private int punteggioCasa;
    private int punteggioOspite;

    public Partita(String casa, String ospite, String citta, String campo, String orario) {
        this.squadraCasa = casa;
        this.squadraOspite = ospite;
        this.citta = citta;
        this.campo = campo;
        this.orario = orario;
        this.punteggioCasa = 0;
        this.punteggioOspite = 0;
    }

    public void aggiungiGolCasa() { this.punteggioCasa++; }
    public void aggiungiGolOspite() { this.punteggioOspite++; }

    // Stringa formattata per l'intestazione
    public String getHeader() {
        return "\n=========================================\n" +
                "      " + squadraCasa + " " + punteggioCasa + " - " + punteggioOspite + " " + squadraOspite + "\n" +
                "-----------------------------------------\n" +
                "   " + orario + " | " + campo + " (" + citta + ")\n" +
                "=========================================\n";
    }

    public String getScoreString() {
        return squadraCasa + " " + punteggioCasa + " - " + punteggioOspite + " " + squadraOspite;
    }
}