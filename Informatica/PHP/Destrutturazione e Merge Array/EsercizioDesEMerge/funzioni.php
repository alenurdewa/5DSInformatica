<?php

function trasforma($arrayAssociativo){

    ["nome" => $n, "cognome" =>$c, "eta" => $e] = $arrayAssociativo;

    $isMaggiorenne = true;
    if($e<18){
        $isMaggiorenne = false;
    }
    $arrayIndicizzato = ["$n $c", "ha $e anni", $isMaggiorenne];

    return $arrayIndicizzato;


}


?>