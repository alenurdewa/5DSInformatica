public class Forza4 {
    private char[][] tabella;
    private final char player1 = 'r';
    private final char player2 = 'b';
    private char pAttuale;
    private int turno;
    private char winner;

    public Forza4() {
        //creo la tabella e la inizializzo
        tabella = new char[7][6];
        inizializzaTabella();
        //inizializzo il contatore dei turni
        turno = 0;

        //inizializzo il giocatore attuale e il vincitore
        pAttuale = 'n';
        winner = 'n';
    }

    private void inizializzaTabella(){
        //rempio la tabella di caratteri n, usati per indicare la casella vuota (null)
        for (int i = 0; i<tabella.length;i++){
            for (int j=0; j<tabella[i].length; j++){
                tabella[i][j] = 'n';
            }
        }
    }

    public boolean giocaTurno(int base) {
        //aumento il contatore del turno
        turno++;
        //variabile che dice se il turno è andato a buon fine
        boolean res = true;

        //determino il giocatore attuale
        if (turno%2==0) {
            pAttuale = player1;
        } else {
            pAttuale = player2;
        }

        //controllo che la base inserita sia accettabile
        if (base < 0 || base >= tabella.length) {
            //se non lo è torno indietro di un turno, così si rifarà, e dico che il turno non è andato a buon fine
            turno --;
            res = false;
        } else {
            //se la base è accettabile controllo dove, nella colonna, mettere il dischetto
            boolean isEmpty = true;
            int contatore = 0;
            //ciclo finchè trovo caselle vuote e sono dentro i limiti della tabella
            while (isEmpty && contatore<=tabella[base].length){
                //se il contatore va oltre al limite della tabella allora tutta la colonna è libera
                if (contatore == (tabella[base].length)) {
                    //metto il dischetto nella prima casella dal fondo
                    contatore --;
                    tabella[base][contatore] = pAttuale;
                    isEmpty = false;
                } else {
                    //controllo casella per casella se sono libere
                    if (tabella[base][contatore] != 'n') {
                        //in caso non lo sia mi segno la casella libera subito sopra
                        contatore--;

                        //se la posizione segnata è accettabile inserisco il dischetto
                        if (contatore >= 0) {
                            tabella[base][contatore] = pAttuale;
                            isEmpty = false;
                        } else {
                            //altrimenti vorrà dire che la colonna è completamente piena
                            isEmpty = false;
                        }
                    }
                }
                contatore+=1;
            }

            //controllo se il contatore sia 0: in caso lo sia tutta la colonna è piena e il turno non è andato a buon fine
            contatore --;
            if (contatore == -1){
                turno --;
                res = false;
            }

            //se il turno è andato a buon fine determino il vincitore
            if (res) {
                checkWin(base, contatore);
            }
        }

        return res;
    }

    //controlla tutti i possibili casi di vittoria
    public void checkWin(int base, int altezza){
        if (checkVertical(base, altezza)) {
            winner = pAttuale;
        }

        if (checkOrizzontale(base, altezza)) {
            winner = pAttuale;
        }

        if (checkDiagionale(base, altezza)) {
            winner = pAttuale;
        }

        if (checkPareggio()) {
            //in caso di pareggio il "giocatore vincitore" è p, che simboleggia il pareggio
            winner = 'p';
        }
    }

    private boolean checkPareggio() {
        boolean pareggio = true;

        int i = 0;
        int j;
        //per controllare il pareggio ciclo tutta la tabella; se trovo una casella impostata a n e quindi non usata
        //allora  si può ancora giocare, altrimenti se tutto è occupato è pareggio
        while (i < tabella.length && pareggio) {
            j = 0;

            while (i < tabella[i].length  && pareggio) {
                if (tabella[i][j] == 'n') {
                    pareggio = false;
                }

                j++;
            }

            i++;
        }

        return pareggio;
    }

    //controllo la vincita verticale nella colonna dell'inserimento andando dall'altezza del dischetto inserito in giù
    private boolean checkVertical(int base, int altezza) {
        boolean w = false;

        int ctr = 0;
        int i = altezza;
        boolean interrompi = false;

        while (i < tabella[0].length && !interrompi) {
            if (tabella[base][i] == pAttuale) {
                ctr ++;
            } else {
                interrompi = true;
            }

            i++;
        }

        if (ctr >= 4 ) {
            w = true;
        }

        return w;
    }

    //controllo se nella altre colonne, alla stessa altezza del dischetto appena inserito, ce ne sono altri
    //che determinano la vittoria
    private boolean checkOrizzontale(int base, int altezza) {
        boolean w = false;

        //ciclo prima in un senso e poi nell altro
        int ctr = 0;
        int i = base;
        boolean interrompi = false;

        while (i < tabella.length && !interrompi) {
            if (tabella[i][altezza] == pAttuale) {
                ctr ++;
            } else {
                interrompi = true;
            }
            i++;
        }

        i = base - 1;
        interrompi = false;

        while (i >= 0 && !interrompi) {
            if (tabella[i][altezza] == pAttuale) {
                ctr ++;
            } else {
                interrompi = true;
            }
            i--;
        }

        if (ctr >= 4) {
            w = true;
        }

        return w;
    }

    public char getpAttuale() {
        return pAttuale;
    }

    //controllo diagonale di vittoria
    private boolean checkDiagionale(int base, int altezza) {
        boolean w = false;
        int a;
        boolean esci = false;

        int ctr = 0;
        a = altezza;
        int i = base;
        //controllo metà di una diagonale segnandomi quanti dischetti trovo
        while (i < tabella.length && a < tabella[i].length && !esci) {
            if (tabella[i][a] == pAttuale) {
                ctr ++;
                a ++;
            } else {
                esci = true;
            }
            i++;
        }
        a = altezza - 1;
        i = base - 1;
        //controllo l'altra metà
        while (i >= 0 && a >= 0 && !esci) {
            if (tabella[i][a] == pAttuale) {
                a--;
                ctr ++;
            } else {
                esci = true;
            }
            i--;
        }

        //controllo se ha vinto
        if (ctr >= 4) {
            w = true;
        } else {
            esci = false;
            ctr = 0;
            a = altezza - 1;
            i = base + 1;
            //controllo una metà dell'altra diagonale
            while (i < tabella.length && a >= 0 && !esci) {
                if (tabella[i][a] == pAttuale) {
                    a--;
                    ctr ++;
                } else {
                    esci = true;
                }
                i++;
            }
            a = altezza;
            i = base;
            //controllo l'altra metà
            while (i >= 0 && a < tabella[i].length && !esci) {
                if (tabella[i][a] == pAttuale) {
                    a++;
                    ctr ++;
                } else {
                    esci = true;
                }
                i--;
            }

            //controllo se ha vinto
            if (ctr >= 4) {
                w = true;
            }
        }

        return w;
    }

    @Override
    public String toString() {
        String output = "";

        //stampo la tabella usando colori: rosso per r e blu per b
        for (int i=0; i<tabella[0].length; i++){
            for (int j=0; j<tabella.length; j++){
                //la prima stringa con il \ indica il colore, la seconda lo resetta così il resto è del colore normale
                if (tabella[j][i] == 'b') {
                    output += "\u001B[34m" + tabella[j][i] + "\u001B[0m";
                } else if (tabella[j][i] == 'r') {
                    output += "\u001B[31m" + tabella[j][i] + "\u001B[0m";
                } else {
                    output += (tabella[j][i]);
                }
                output += "|";
            }
            output += "\n";
        }
        return output;
    }

    public String getWinner() {
        //uso la stessa logica del toString, rosso per r e blu per b
        String out;
        if (winner == 'b') {
            out = "\u001B[34m" + winner + "\u001B[0m";
        } else if (winner == 'r') {
            out = "\u001B[31m" + winner + "\u001B[0m";
        } else {
            out = winner + "";
        }
        return out;
    }
}
