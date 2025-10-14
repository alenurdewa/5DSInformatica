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

📌 **In sintesi:**  
Hai toccato le basi principali:  
- `echo` e output HTML  
- variabili e valori booleani  
- `if`, `==`, `===`  
- numeri (dec, bin, oct, hex, float)  
- stringhe + concatenazione  
- array semplici e associativi  
- debug con `var_dump`  
- funzioni base per manipolare stringhe  
- include/require per organizzare il codice

