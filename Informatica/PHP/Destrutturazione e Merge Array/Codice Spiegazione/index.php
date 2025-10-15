<?php
$nomi = ["Mario", "Maria", "Germano", "Genoveffa"];

//Destrutturazione di un array

list($a, $b, $c, $d) = $nomi;

[$e, $f, $g, $h] = $nomi;

$a = "Pippo";
echo $a;
echo "<br>";
echo $e;
echo "<br>";
print_r($nomi);

$dati = [
    "nome" => "Mario",
    "cognome" => "Rossi",
    "indirizzo" => "Via Roma",
    
];

//destrutturazione array associativo
["nome" => $n, "cognome" => $c, "indirizzo" => $i] = $dati;
echo "<br>";
echo $n;

echo "<br>";
echo $c;

echo "<br>";
echo $i;

print_r($dati);


$nome = [
    "nome"=> "Alessandro",
    "cognome"=> "Berti",
];

$indirizzo = [
    "via"=> "via Roma",
    "citta"=> "Trento",
];

$dati = array_merge($nome, $indirizzo);
//oppure 
$dati3 = [... $nome , ... $indirizzo];

$dati4 = [... $nome , ... $indirizzo, "avvocato"];

echo "<br>";
print_r($dati);

echo "<br>";
print_r($dati3);

echo "<br>";
print_r($dati4);

$arr_numerico1 = [1,2,3,4];
$arr_numerico2 = [1,2,3,4,5,6];

$datiNumerici = array_unique([...$arr_numerico1, ...$arr_numerico2]);
echo "<br>";
print_r($datiNumerici);


?>