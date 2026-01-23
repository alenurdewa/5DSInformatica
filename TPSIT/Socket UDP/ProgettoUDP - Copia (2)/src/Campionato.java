public class Campionato {

    private Squadra[] squadre;

    public Campionato() {
        squadre = new Squadra[20];

        // 1) Atalanta
        Squadra atalanta = new Squadra("Atalanta", "Antonio Percassi");
        atalanta.aggiungiGiocatore("Odilon Kossounou", 3);
        atalanta.aggiungiGiocatore("Isak Hien", 4);
        atalanta.aggiungiGiocatore("Mitchel Bakker", 5);
        atalanta.aggiungiGiocatore("Yunus Musah", 6);
        atalanta.aggiungiGiocatore("Kamaldeen Sulemana", 7);
        atalanta.aggiungiGiocatore("Mario Pašalić", 8);
        atalanta.aggiungiGiocatore("Gianluca Scamacca", 9);
        atalanta.aggiungiGiocatore("Lazar Samardžić", 10);
        atalanta.aggiungiGiocatore("Ademola Lookman", 11);
        atalanta.aggiungiGiocatore("Éderson", 13);

        squadre[0] = atalanta;

        // 2) Milan
        Squadra milan = new Squadra("AC Milan", "Paolo Scaroni");
        milan.aggiungiGiocatore("Pietro Terracciano", 1);
        milan.aggiungiGiocatore("Mike Maignan", 16);
        milan.aggiungiGiocatore("Lorenzo Torriani", 96);
        milan.aggiungiGiocatore("Pervis Estupiñán", 2);
        milan.aggiungiGiocatore("Koni De Winter", 5);
        milan.aggiungiGiocatore("Fikayo Tomori", 23);
        milan.aggiungiGiocatore("Zachary Athekame", 24);
        milan.aggiungiGiocatore("David Odogu", 27);
        milan.aggiungiGiocatore("Strahinja Pavlović", 31);
        milan.aggiungiGiocatore("Davide Bartesaghi", 33);
        milan.aggiungiGiocatore("Matteo Gabbia", 46);
        milan.aggiungiGiocatore("Samuele Ricci", 4);
        milan.aggiungiGiocatore("Ruben Loftus-Cheek", 8);
        milan.aggiungiGiocatore("Christian Pulisic", 11);
        milan.aggiungiGiocatore("Adrien Rabiot", 12);
        milan.aggiungiGiocatore("Luka Modrić", 14);
        milan.aggiungiGiocatore("Youssouf Fofana", 19);
        milan.aggiungiGiocatore("Ardon Jashari", 30);
        milan.aggiungiGiocatore("Alexis Saelemaekers", 56);
        squadre[1] = milan;

        // 3) Juventus
        Squadra juve = new Squadra("Juventus", "Gianluca Ferrero");
        juve.aggiungiGiocatore("Mattia Perin", 1);
        juve.aggiungiGiocatore("Michele Di Gregorio", 16);
        juve.aggiungiGiocatore("Carlo Pinsoglio", 23);
        juve.aggiungiGiocatore("Gleison Bremer", 3);
        juve.aggiungiGiocatore("Federico Gatti", 4);
        juve.aggiungiGiocatore("Lloyd Kelly", 6);
        juve.aggiungiGiocatore("Pierre Kalulu", 15);
        juve.aggiungiGiocatore("Daniele Rugani", 24);
        juve.aggiungiGiocatore("Juan Cabal", 32);
        juve.aggiungiGiocatore("Manuel Locatelli", 5);
        juve.aggiungiGiocatore("Teun Koopmeiners", 8);
        juve.aggiungiGiocatore("Filip Kostić", 18);
        juve.aggiungiGiocatore("Fabio Miretti", 21);
        juve.aggiungiGiocatore("Weston McKennie", 22);
        juve.aggiungiGiocatore("João Mário", 25);
        juve.aggiungiGiocatore("Andrea Cambiaso", 27);
        juve.aggiungiGiocatore("Francisco Conceição", 7);
        juve.aggiungiGiocatore("Dušan Vlahović", 9);
        juve.aggiungiGiocatore("Kenan Yıldız", 10);
        juve.aggiungiGiocatore("Edon Zhegrova", 11);
        squadre[2] = juve;

        // 4) Inter
        Squadra inter = new Squadra("Inter", "Giuseppe Marotta");
        inter.aggiungiGiocatore("Yann Sommer", 1);
        inter.aggiungiGiocatore("Denzel Dumfries", 2);
        inter.aggiungiGiocatore("Benjamin Pavard", 5);
        inter.aggiungiGiocatore("Stefan de Vrij", 6);
        inter.aggiungiGiocatore("Francesco Acerbi", 15);
        inter.aggiungiGiocatore("Nicolò Barella", 23);
        inter.aggiungiGiocatore("Hakan Çalhanoğlu", 20);
        inter.aggiungiGiocatore("Henrikh Mkhitaryan", 22);
        inter.aggiungiGiocatore("Lautaro Martínez", 10);
        inter.aggiungiGiocatore("Marcus Thuram", 9);
        inter.aggiungiGiocatore("Luis Henrique", 11);
        inter.aggiungiGiocatore("Raffaele Di Gennaro", 12);
        inter.aggiungiGiocatore("Josep Martínez", 13);
        inter.aggiungiGiocatore("Ange-Yoan Bonny", 14);
        inter.aggiungiGiocatore("Davide Frattesi", 16);
        inter.aggiungiGiocatore("Andy Diouf", 17);
        inter.aggiungiGiocatore("Manuel Akanji", 25);
        inter.aggiungiGiocatore("Carlos Augusto", 30);
        inter.aggiungiGiocatore("Yann Aurel Bisseck", 31);
        inter.aggiungiGiocatore("Federico Dimarco", 32);
        inter.aggiungiGiocatore("Matteo Darmian", 36);
        inter.aggiungiGiocatore("Tomás Palacios", 42);
        inter.aggiungiGiocatore("Alessandro Bastoni", 95);
        inter.aggiungiGiocatore("Mehdi Taremi", 99);
        squadre[3] = inter;

        // 5) Lazio (solo 3 giocatori di esempio)
        Squadra lazio = new Squadra("Lazio", "Claudio Lotito");
        lazio.aggiungiGiocatore("Ivan Provedel", 1);
        lazio.aggiungiGiocatore("Luiz Felipe", 3);
        lazio.aggiungiGiocatore("Sergej Milinković-Savić", 21);
        squadre[4] = lazio;

        // 6) Napoli (solo 3 giocatori di esempio)
        Squadra napoli = new Squadra("Napoli", "Aurelio De Laurentiis");
        napoli.aggiungiGiocatore("Keylor Navas", 1);
        napoli.aggiungiGiocatore("Giovanni Di Lorenzo", 22);
        napoli.aggiungiGiocatore("Victor Osimhen", 9);
        squadre[5] = napoli;

        // 7) Roma (solo 3 giocatori di esempio)
        Squadra roma = new Squadra("Roma", "Dan Friedkin");
        roma.aggiungiGiocatore("Rui Patrício", 1);
        roma.aggiungiGiocatore("Gianluca Mancini", 23);
        roma.aggiungiGiocatore("Paulo Dybala", 10);
        squadre[6] = roma;

        // 8) Torino (solo 3 giocatori di esempio)
        Squadra torino = new Squadra("Torino", "Urbano Cairo");
        torino.aggiungiGiocatore("Vanja Milinković-Savić", 95);
        torino.aggiungiGiocatore("Samuele Ricci", 4);
        torino.aggiungiGiocatore("Antonio Sanabria", 37);
        squadre[7] = torino;


    }

    public Squadra[] getSquadre() {
        return squadre;
    }
}
