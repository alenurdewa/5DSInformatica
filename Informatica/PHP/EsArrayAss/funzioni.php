<?php

function somma($arr_numeri){
    $somma = 0;
    for($i= 0;$i<count($arr_numeri);$i++){
        $somma += $arr_numeri[$i];
    }
    return $somma;
};

function minimo($arr_numeri){
    $min = $arr_numeri[0];
    for($i= 0;$i<count($arr_numeri); $i++){
        if($arr_numeri[$i] < $min){
            $min = $arr_numeri[$i];
        }
    }
    return $min;
};

function massimo($arr_numeri){
    $max = $arr_numeri[0];
    for($i= 0;$i<count($arr_numeri);$i++){
        if($arr_numeri[$i] > $max){
            $max = $arr_numeri[$i];
        }
    }
    return $max;
};

function media($arr_numeri){
   $media = somma($arr_numeri)/count($arr_numeri);
   return $media;
      
};

?>