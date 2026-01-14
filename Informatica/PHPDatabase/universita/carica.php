<?php

require "db.php";

$pdo->exec("CREATE TABLE IF NOT EXISTS studente(
    matricola INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL,
    dataNascita DATE NOT NULL
    )
");

$pdo->exec("CREATE TABLE IF NOT EXISTS docente(
    matricola INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL
    )
");

$pdo->exec("CREATE TABLE IF NOT EXISTS corso(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    anno INT NOT NULL
    )
");

$pdo->exec("CREATE TABLE IF NOT EXISTS insegna(
    id_corso INT NOT NULL,
    matricola_docente INT NOT NULL,
    PRIMARY KEY (id_corso, matricola_docente),
    foreign key (id_corso) references corso(id),
    foreign key (matricola_docente) references docente(matricola)
    )
");

$pdo->exec("CREATE TABLE IF NOT EXISTS frequenta(
    id_corso INT NOT NULL,
    matricola_studente INT NOT NULL,
    PRIMARY KEY (id_corso, matricola_studente),
    foreign key (id_corso) references corso(id),
    foreign key (matricola_studente) references studente(matricola)
    )
");





$filename = "dati.csv";

if(count($id_corso = $pdo->query("SELECT * FROM studente")->fetchAll())==0){
    //popola tabelle real
    if (($handle = fopen($filename, "r")) !== false) {
        // Legge l'intestazione 
        $header_arr = fgetcsv($handle);
        $header_str_stud = "($header_arr[0], $header_arr[1])";
        $header_str_doc = "(cognome, nome)";
        $header_str_corso = "(nome, $header_arr[4])";

        while (($data = fgetcsv($handle)) !== false) {

            
            $pdo->exec("INSERT INTO studente $header_str_stud VALUES ('$data[0]', '$data[1]')");
            if(count($pdo->query("SELECT cognome FROM docente WHERE cognome='$data[3]'")->fetchAll())==0){
                $pdo->exec("INSERT INTO docente $header_str_doc VALUES ('$data[3]', 'corn')");
            }
            if(count($pdo->query("SELECT nome FROM corso WHERE nome='$data[2]'")->fetchAll())==0){
                $pdo->exec("INSERT INTO corso $header_str_corso VALUES ('$data[2]', '$data[4]')");
            }
            
        }

        fclose($handle);
    } else {
        echo "Impossibile aprire il file.";
    }

    //popola tabelle fake
    if (($handle = fopen($filename, "r")) !== false) {
        // Legge l'intestazione 
        $header_arr = fgetcsv($handle);
        $header_str_stud = "($header_arr[0], $header_arr[1])";
        $header_str_doc = "(cognome, nome)";
        $header_str_corso = "(nome, $header_arr[4])";

        while (($data = fgetcsv($handle)) !== false) {

            $id_corso = $pdo->query("SELECT id FROM corso WHERE nome='$data[2]'")->fetchAll()[0]["id"];
            $matricola_studente = $pdo->query("SELECT matricola FROM studente WHERE nome='$data[0]' AND cognome='$data[1]'")->fetchAll()[0]["matricola"];
            $pdo->exec("INSERT INTO frequenta (id_corso, matricola_studente) VALUES ($id_corso, $matricola_studente)");

            try{
                $id_corso = $pdo->query("SELECT id FROM corso WHERE nome='$data[2]'")->fetchAll()[0]["id"];
                $matricola_doc = $pdo->query("SELECT matricola FROM studente WHERE cognome='$data[3]'")->fetchAll()[0]["matricola"];
                $pdo->exec("INSERT INTO insegna (id_corso, matricola_docente) VALUES ($id_corso, $matricola_doc)");
            }catch (Exception $e) {
                
            }
            
        }

        fclose($handle);
    } else {
        echo "Impossibile aprire il file.";
    }
}

//$pdo->exec("drop database universita");

?>