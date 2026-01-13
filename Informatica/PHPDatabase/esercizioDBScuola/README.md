# Istituto Scolastico – Import CSV con PHP e PDO

## Obiettivo del progetto
Questo progetto mostra come:
- collegarsi a MySQL usando **PHP PDO**
- creare un database e delle tabelle
- importare dati da un file **CSV**
- evitare duplicati tramite query di controllo
- visualizzare i dati con una **JOIN**

Il tutto è fatto in **un solo file PHP** per semplicità didattica.

---

## Struttura del CSV

Il file `istitutoScolastico.csv` deve avere questo formato:

```
nome_studente,cognome_studente,corso,docente,anno
Mario,Rossi,Informatica,Bianchi,2024
Luisa,Verdi,Matematica,Rossi,2024
Marco,Bianchi,Informatica,Bianchi,2024
Anna,Neri,Storia,Verdi,2023
```

La prima riga è l'intestazione e viene ignorata dallo script.

---

## Database

Nome database:
```
istitutoScolastico
```

### Tabelle create

**docenti**
- id (PK)
- nome

**corsi**
- id (PK)
- nome
- anno
- docente_id (FK)

**studenti**
- id (PK)
- nome
- cognome
- corso_id (FK)

Le tabelle sono collegate tramite **chiavi esterne** per evitare duplicazioni.

---

## Funzionamento dello script

1. Connessione a MySQL con PDO
2. Creazione del database se non esiste
3. Creazione delle tabelle
4. Apertura del file CSV
5. Lettura riga per riga con `fgetcsv`
6. Inserimento:
   - docente (se non esiste)
   - corso (se non esiste)
   - studente
7. Visualizzazione finale tramite `JOIN`

---

## JOIN finale

La query JOIN unisce:
- studenti
- corsi
- docenti

per ottenere una vista completa:

```
Studente - Corso (Anno) - Docente
```

---

## Requisiti

- PHP 7+
- MySQL / MariaDB
- Server locale (XAMPP, MAMP, WAMP)
- Estensione PDO attiva

---

## Avvio

1. Metti il file PHP e il CSV nella stessa cartella
2. Avvia Apache e MySQL
3. Apri il file PHP dal browser
4. Il database viene creato automaticamente

---

## Scopo didattico

Questo progetto è pensato per:
- studio
- verifiche
- interrogazioni
- comprensione di CSV, database e JOIN

Nessun framework, solo PHP puro.
