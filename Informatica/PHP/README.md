# 📘 Appunti PHP – Basi

## 🔹 Stampa a schermo
```php
echo "Ciao";
echo "<h1>Questo è un titolo</h1>";
```
- `echo` serve a stampare testo o HTML.  
- Puoi mischiare codice **PHP** e **HTML puro** nello stesso file.  

---

## 🔹 Variabili e valori booleani
```php
$var1 = 0;

if ($var1) {
    echo "è un valore true";
} else {
    echo "è un valore false";
}
```
- In PHP **0, null, stringa vuota `""`, array vuoto `[]` → false** nelle condizioni.  
- Ogni altro valore → true.  

---

## 🔹 Debug delle variabili
```php
var_dump($var1);
```
- Mostra **tipo e valore** della variabile.  
- Utile per capire se una variabile è `int`, `string`, `bool`, ecc.  

---

## 🔹 Condizioni e confronti
```php
$var2 = '2';
$var3 = 2;

if ($var2 == $var3) { ... }
```
- `==` → confronto solo sul **valore** (coercizione automatica dei tipi).  
- `===` → confronto su **valore e tipo**.  

Esempio:  
- `'2' == 2` → true  
- `'2' === 2` → false  

---

## 🔹 Numeri in diversi formati
```php
$dec = 10;     // decimale
$bin = 0b1010; // binario
$oct = 012;    // ottale
$hex = 0xA;    // esadecimale

$float = 10.5;   // float
$float1 = 1.5e3; // notazione scientifica → 1500
$float2 = 1.5e-3;// notazione scientifica → 0.0015
```

---

## 🔹 Stringhe
```php
$nome1 = "Alessandro";
$nome2 = "Ester";

echo "Il mio nome è $nome1";
echo "La mia tipa è $nome2";
```
- Con le **doppie virgolette `" "`** puoi inserire variabili direttamente (`$nome1`).  
- Con le **singole virgolette `' '`** il contenuto resta letterale.  

### Concatenazione
```php
$nome1 .= " Berti"; // aggiunge testo
echo "Io sono $nome1 e sono con " . $nome2;
```
- Operatore `.` → concatena stringhe.  
- Operatore `.=` → aggiunge alla stringa esistente.  

---

## 🔹 Array
```php
$nomi = ['Mario', 'Maria', 'Giovanni'];
echo $nomi[0]; // Mario
```
- Gli array possono essere **indicizzati** (posizione numerica).  

---

## 🔹 Array associativi
```php
$studente = array(
    "nome" => "Margherita",
    "eta" => 22,
    "corso" => "Informatica"
);

echo $studente["nome"]; // Margherita
```
- Gli array possono avere **chiavi personalizzate** (tipo “oggetto semplificato”).  

### 🔸 Ciclo `foreach`
```php
foreach ($studente as $chiave => $valore) {
    echo "$chiave: $valore<br>";
}
```
- `$chiave` → la chiave dell’array, `$valore` → il valore corrispondente.  

### 🔸 Aggiungere, modificare e rimuovere elementi
```php
$studente["anno"] = 3;   // aggiunge
$studente["eta"] = 23;   // modifica
unset($studente["corso"]); // rimuove
```

---

## 🔹 Mischiare HTML e PHP
```html
<h2>Scritto in html</h2>
<?php
echo "Riaperto PHP";
?>
```

---

## 🔹 Funzioni fondamentali per le stringhe
```php
strlen($str);        // lunghezza
trim($str);          // rimuove spazi inizio/fine
strtoupper($str);    // maiuscolo
strtolower($str);    // minuscolo
ucfirst($str);       // prima lettera maiuscola
ucwords($str);       // prima lettera di ogni parola maiuscola
strpos($str, "cerca"); // posizione della sottostringa
str_replace("da","a",$str); // sostituisce tutte le occorrenze
substr($str, $start, $len); // estrazione
explode(" ", $str);  // stringa → array
implode("-", $array); // array → stringa
nl2br($str);         // \n → <br> in HTML
```

---

## 🔹 Include e require per funzioni esterne
```php
// funzioni.php
function somma(array $numeri) {
    return array_sum($numeri);
}

// index.php
require_once 'funzioni.php';
$numeri = [1,2,3,4];
echo somma($numeri);
```
- `include` o `require` → include il file specificato.  
- `_once` → evita doppi include (utile per non ridefinire funzioni).  
- Serve per organizzare meglio il codice, separando funzioni dal file principale.

---

## 🔹 Array associativi avanzato
```php
$utenti = [
    "1" => "Alessandro",
    "2" => "Antonio",
    "3" => "Davide"
];

foreach ($utenti as $matricola => $nome) {
    echo "Matricola: $matricola - Nome: $nome<br>";
}

$utenti["4"] = "Ester"; // aggiunta
unset($utenti["2"]);     // rimozione
```
- Utilizzo di chiavi personalizzate, aggiunta, modifica e cancellazione di elementi.
- Ciclo `foreach` per scorrere array associativi.  

---

# 📘 Appunti PHP -- Destrutturazione, Merge e Array Unici

## 🔹 Destrutturazione di un array indicizzato

``` php
$nomi = ["Mario", "Maria", "Germano", "Genoveffa"];

// Destrutturazione classica
list($a, $b, $c, $d) = $nomi;

// Destrutturazione moderna (PHP 7.1+)
[$e, $f, $g, $h] = $nomi;

$a = "Pippo";
echo $a;
echo "<br>";
echo $e;
echo "<br>";
print_r($nomi);
```

-   `list()` e `[]` permettono di **assegnare più variabili
    contemporaneamente** dagli elementi di un array.
-   Modificare `$a` non cambia l'array originale, perché i valori sono
    **copiati** (non per riferimento).

------------------------------------------------------------------------

## 🔹 Destrutturazione di un array associativo

``` php
$dati = [
    "nome" => "Mario",
    "cognome" => "Rossi",
    "indirizzo" => "Via Roma",
];

["nome" => $n, "cognome" => $c, "indirizzo" => $i] = $dati;

echo $n; // Mario
echo $c; // Rossi
echo $i; // Via Roma
```

-   È possibile **estrarre chiavi specifiche** in variabili.\
-   Funziona solo con chiavi esatte corrispondenti.

------------------------------------------------------------------------

## 🔹 Merge di array associativi

``` php
$nome = [
    "nome"=> "Alessandro",
    "cognome"=> "Berti",
];

$indirizzo = [
    "via"=> "via Roma",
    "citta"=> "Trento",
];

$dati = array_merge($nome, $indirizzo);
$dati3 = [...$nome, ...$indirizzo]; // sintassi moderna (PHP 7.4+)
$dati4 = [...$nome, ...$indirizzo, "avvocato"];
```

-   `array_merge()` unisce due o più array.\
-   L'operatore **spread (`...`)** copia tutti gli elementi di un array
    dentro un nuovo array.\
-   In `$dati4`, `"avvocato"` viene aggiunto come valore con **indice
    numerico** (non associativo).

------------------------------------------------------------------------

## 🔹 Merge e rimozione dei duplicati (array numerici)

``` php
$arr_numerico1 = [1,2,3,4];
$arr_numerico2 = [1,2,3,4,5,6];

$datiNumerici = array_unique([...$arr_numerico1, ...$arr_numerico2]);
echo "<br>";
print_r($datiNumerici);
```

-   `array_unique()` rimuove automaticamente i **valori duplicati**.\
-   Lo spread `...` unisce più array in uno solo prima del filtraggio.

Risultato:

    Array ( [0] => 1 [1] => 2 [2] => 3 [3] => 4 [7] => 5 [8] => 6 )

------------------------------------------------------------------------

## 🔹 Funzioni utili per gli array

``` php
print_r($array); // mostra contenuto leggibile
var_dump($array); // mostra tipo + valore dettagliato
```

-   `print_r()` → debug veloce.\
-   `var_dump()` → tipi di dato e struttura completa.

------------------------------------------------------------------------

📘 Appunti PHP – Superglobali e Form
🔹 Superglobali principali
- `$_SERVER` → info sul server e la richiesta (metodo, IP, browser, ecc.)  
- `$_GET`, `$_POST` → dati inviati da form  
- `$_REQUEST` → unisce GET e POST  
- `$_SESSION` → salva dati temporanei dell’utente  
- `$_COOKIE` → dati salvati nel browser  
- `$GLOBALS` → contiene tutte le variabili globali

🔹 Esempio pratico con form e sessioni
```php
session_start();

$nome = $_POST['nome'] ?? 'Non dato';
$eta = isset($_POST['eta']) ? (int)$_POST['eta'] : 'NULL';

$mail = filter_input(INPUT_POST, "mail", FILTER_VALIDATE_EMAIL);

echo "<br>Registrazione effettuata da $nome, con età $eta e email $mail";

$metodo = $_SERVER['REQUEST_METHOD'];
$user_a = $_SERVER['HTTP_USER_AGENT'];
$ip = $_SERVER['REMOTE_ADDR'];

echo "Il metodo è: $metodo, ip $ip, $user_a";

$_SESSION["visite"] = 1;
$_SESSION["nomeUtente"] = $nome;

var_dump($_SESSION);
session_destroy();
```
🔸 Spiegazione semplice:
- `$_POST['nome'] ?? 'Non dato'` → se non c’è il nome, usa “Non dato”  
- `isset($_POST['eta']) ? (int)$_POST['eta'] : 'NULL'` → controlla se il valore esiste, altrimenti imposta NULL  
- `filter_input(..., FILTER_VALIDATE_EMAIL)` → controlla che la mail sia valida  
- `$_SERVER` contiene info sulla richiesta HTTP  
- `$_SESSION` serve per salvare variabili da una pagina all’altra  
- `session_destroy()` chiude la sessione corrente

🔸 Form HTML collegato
```html
<form action="index.php" method="post">
    <input type="text" name="nome"><br>
    <input type="text" name="eta"><br>
    <input type="text" name="mail"><br>
    <input type="submit" name="invio">
</form>
```
------------------------------------------------------------------------

# 📘 Appunti PHP – Basi + Cookie & Login/Carrello

## 🔹 Stampa a schermo
```php
echo "Ciao";
echo "<h1>Questo è un titolo</h1>";
```
- `echo` serve a stampare testo o HTML.  
- Puoi mischiare codice **PHP** e **HTML puro** nello stesso file.

---

## 🔹 Variabili e valori booleani
```php
$var1 = 0;

if ($var1) {
    echo "è un valore true";
} else {
    echo "è un valore false";
}
```
- In PHP **0, null, stringa vuota `""`, array vuoto `[]` → false** nelle condizioni.  
- Ogni altro valore → true.

---

## 🔹 Debug delle variabili
```php
var_dump($var1);
```
- Mostra **tipo e valore** della variabile.

---

## 🔹 Condizioni e confronti
```php
$var2 = '2';
$var3 = 2;

if ($var2 == $var3) { ... }
```
- `==` → confronto solo sul **valore**.  
- `===` → confronto su **valore e tipo**.
- `'2' == 2` → true, `'2' === 2` → false

---

## 🔹 Array associativi avanzato, sessioni e cookie
### Sessioni
```php
session_start();
$_SESSION['user'] = 'Alessandro';
```
- Permette di salvare dati temporanei per l'utente.
- Dura fino alla chiusura del browser o a `session_destroy()`.

### Cookie
```php
setcookie('user', 'Alessandro', time()+3600); // 1 ora
echo $_COOKIE['user'];
setcookie('user', '', time()-3600); // cancella cookie
```
- I cookie rimangono anche dopo aver chiuso il browser.
- Possono salvare preferenze, login persistenti, carrelli.

---

### Esempio mini-login SESSION + COOKIE
```php
// login.php
session_start();
$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';
$ricordami = isset($_POST['ricordami']);

if($username === 'admin' && $password === '1234') {
    $_SESSION['user'] = $username;
    if($ricordami){ setcookie('user', $username, time()+7*24*60*60); }
    header('Location: area_riservata.php');
} else {
    echo 'Credenziali sbagliate';
}
```
```php
// area_riservata.php
session_start();
if(!isset($_SESSION['user']) && isset($_COOKIE['user'])){
    $_SESSION['user'] = $_COOKIE['user'];
}
```
```php
// logout.php
session_start();
session_unset();
session_destroy();
setcookie('user', '', time()-3600);
```

### Esempio mini-carrello con COOKIE
```php
// aggiungi al carrello
$carrello = isset($_COOKIE['carrello']) ? json_decode($_COOKIE['carrello'], true) : [];
$prodotto = $_POST['prodotto'] ?? '';
if($prodotto){
    $carrello[$prodotto] = ($carrello[$prodotto] ?? 0) + 1;
    setcookie('carrello', json_encode($carrello), time()+7*24*60*60);
}
```
```php
// svuota carrello
setcookie('carrello', '', time()-3600);
```

---

## 🔹 Superglobali principali
- `$_SERVER` → info sul server e richiesta HTTP
- `$_GET`, `$_POST` → dati da form
- `$_REQUEST` → unisce GET e POST
- `$_SESSION` → dati temporanei lato server
- `$_COOKIE` → dati salvati lato client
- `$GLOBALS` → tutte le variabili globali

------------------------------------------------------------------------

📌 **In sintesi:**  
Basi principali:  
- `echo` e output HTML  
- variabili e valori booleani  
- `if`, `==`, `===`  
- numeri (dec, bin, oct, hex, float)  
- stringhe + concatenazione  
- array semplici e associativi  
- debug con `var_dump`  
- funzioni base per manipolare stringhe  
- include/require per organizzare il codice
- destrutturazione e merge di array indicizzati e associativi
- superglobali ($_POST, $_SERVER, $_SESSION, ecc.) e gestione form con validazione email

