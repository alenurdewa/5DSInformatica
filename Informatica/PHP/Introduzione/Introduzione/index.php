<?php

echo "Ciao";
echo "<h1>Questo è un titolo</h1>";

//dichiarazione una variabile

//se inizializzo una variabile a null o 0 lo legge come false (nelle condizioni)
$var1 = 0;

if($var1){
    echo "è un valore true<br><br>";
    
}else{
    
    echo "\nè un valore false<br><br>";
}


//var_dump() funzione che stampa il tipo della variabile
var_dump($var1);



//condizioni
// == confronto sul valore
// === confonto su valore e tipo

$var2 = '2';
$var3 = 2;

if($var2 == $var3){
    echo "I valori sono uguali <br>";
    
}else{
    
    echo "I valori non sono uguali";
}



//numeri con diversi tipi numerici9
$dec = 10;
$bin = 0b1010;
$oct = 012;
$hex = 0xA;

$float = 10.5;
$float1 = 1.5e3;
$float2 = 1.5e-3;

// Stringhe 
$nome1 = "Alessandro";
$nome2 = "Ester";

echo "<br>Il mio nome è $nome1";
echo "<br>La mia tipa è $nome2";
?>

<h2>Scritto in html</h2>

<?php

echo "Riaperto PHP";


//.= per la concatenzaione delle variabili
$nome1 .= " Berti";


// nella stringa o uso $nomevariabile o concateno con .
echo "<br>Il mio nome è $nome1 e sono insieme a $nome2" . " e sono insieme a $nome2";


//metodi delle stringhe (mi studiero quelle fondamentali e basta)


//Array
$nomi = ['Mario', 'Maria', 'Giovanni'];
echo "<br><br><br> <h1>Array in PHP</h1>";
echo "$nomi[0]";


$studente = array(
    "nome" => "Margherita",
    "eta" => 22,
    "corso" => "Informatica",
);



echo "<br><br>Oggetto studente[nome]:" . $studente["nome"];

for

?>