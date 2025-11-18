<?php


setcookie('utente', 'Mario Rossi', time()+3600, '/', '', true, true);

if(!isset($_COOKIE['nVisits'])){
    setcookie('nVisits', 1, time()+3600, '/', '', true, true);
}else{
    $total = $_COOKIE['nVisits'] +1;
    setcookie('nVisits', $total, time()+3600, '/', '', true, true);
};


foreach($_COOKIE as $name => $value){
    echo "$name = $value <br>";
};


?>