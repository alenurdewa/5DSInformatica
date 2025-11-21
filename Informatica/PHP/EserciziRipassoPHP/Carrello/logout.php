<?php
session_start();

// cancello tutte le variabili di sessione
session_unset();

// distruggo la sessione
session_destroy();

// cancello il cookie "user" se esiste
if(isset($_COOKIE['user'])) {
    setcookie('user', '', time() - 3600); // scade subito
}

echo "Logout effettuato :)<br>";
echo '<a href="index.php">Torna al login</a>';
