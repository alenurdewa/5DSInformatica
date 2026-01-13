<?php
require 'funzioni.php';

// Capisco cosa vuole vedere l'utente
$view = $_GET['view'] ?? 'join'; 
?>

<!DOCTYPE html>
<html>
<head><title>Scuola</title></head>
<body>

    <nav>
        <a href="?view=join">Vista Completa</a> | 
        <a href="?view=studenti">Studenti</a> | 
        <a href="?view=corsi">Corsi</a> | 
        <a href="?view=docenti">Docenti</a>
    </nav>
    <br>

    <form method="GET">
        <input type="hidden" name="view" value="cerca">
        Cerca Cognome: <input type="text" name="q">
        <button>Cerca</button>
    </form>
    
    <hr>

    <table border="1">
    
    <?php
    
    if ($view == 'studenti') {
        $righe = getStudenti($pdo);
        echo "<tr><th>Nome</th><th>Cognome</th></tr>";
        
        foreach($righe as $r) {
            echo "<tr><td>{$r['nome']}</td><td>{$r['cognome']}</td></tr>";
        }

    } elseif ($view == 'corsi') {
        $righe = getCorsi($pdo);
        echo "<tr><th>Corso</th><th>Anno</th></tr>";

        foreach($righe as $r) {
            echo "<tr><td>{$r['nome']}</td><td>{$r['anno']}</td></tr>";
        }

    } elseif ($view == 'docenti') {
        $righe = getDocenti($pdo);
        echo "<tr><th>Nome Docente</th></tr>";

        foreach($righe as $r) {
            echo "<tr><td>{$r['nome']}</td></tr>";
        }

    } else {
        if ($view == 'cerca') {
            $q = $_GET['q'] ?? '';
            $righe = cercaStudente($pdo, $q);
            echo "<caption>Risultati ricerca per: $q</caption>";
        } else {
            $righe = getTutto($pdo);
        }

        echo "<tr><th>Studente</th><th>Corso</th><th>Docente</th></tr>";

        foreach($righe as $r) {
            echo "<tr>
                    <td>{$r['nome']} {$r['cognome']}</td>
                    <td>{$r['corso']}</td>
                    <td>{$r['docente']}</td>
                  </tr>";
        }
    }
    ?>
    
    </table>

</body>
</html>