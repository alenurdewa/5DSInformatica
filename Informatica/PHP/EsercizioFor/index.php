<?php

echo "<h1>Stampa dei primi 100 numeri pari</h1>";
for($i=0; $i<=100; $i=$i+2){
    echo "$i ";
}

$palindromo = true;
$counter = 0;
$stringa = "Anna";
$stringa = strtolower($stringa);

$lung = strlen($stringa);


while ($palindromo == true and $counter < strlen($stringa)/2){
    if($stringa[$counter] == $stringa[strlen($stringa)-$counter-1]){
        $counter+=1;
    }else{
        $palindromo = false;
    }
    
}
echo "<br><br>";

if ($palindromo == false){
    echo "Il coso non è palindromo ";
}else{
    echo "La stringa $stringa è palindroma ";
}



?>