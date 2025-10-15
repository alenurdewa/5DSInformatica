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

📌 **In sintesi:**\
- `list()` e `[]` → destrutturazione di array semplici\
- `["chiave" => $var]` → destrutturazione associativa\
- `array_merge()` e `...` → fusione di array\
- `array_unique()` → elimina duplicati\
- `print_r()` / `var_dump()` → debug dei dati
