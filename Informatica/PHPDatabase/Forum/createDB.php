<?php
require "config.php";


$sql = "CREATE DATABASE IF NOT EXISTS Social; USE SOCIAL";
$pdo->exec($sql);


//Tabella utente
function createUtente(){
    global $pdo;
    $sql = "CREATE TABLE IF NOT EXISTS UTENTE(
        username VARCHAR(30),
        pwd VARCHAR(30) NOT NULL,
        nome VARCHAR(30) NOT NULL,
        cognome VARCHAR(30) NOT NULL,
        PRIMARY KEY(username) 
    )";

    $pdo->exec($sql);

};

//Tabella argomento
function createArgomento(){
    global $pdo;
    $sql = "CREATE TABLE IF NOT EXISTS ARGOMENTO(
        id INT AUTO_INCREMENT,
        nome VARCHAR(30) NOT NULL,
        PRIMARY KEY(id) 
    )";
    $pdo->exec($sql);

};


//Tabella post
function createPost(){
    global $pdo;
    $sql = "CREATE TABLE IF NOT EXISTS POST(
        id INT AUTO_INCREMENT,
        argomento INT NOT NULL,
        testo VARCHAR(512) NOT NULL,
        utente VARCHAR(30) NOT NULL,
        numLike int DEFAULT 0 NOT NULL,
        data DATETIME NOT NULL,
        PRIMARY KEY(id),
        FOREIGN KEY (utente) REFERENCES UTENTE(username),
        FOREIGN KEY (argomento) REFERENCES ARGOMENTO(id)
    )";
    $pdo->exec($sql);

};

createUtente();
createArgomento();
createPost();


