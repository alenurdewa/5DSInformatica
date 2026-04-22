<?php
include "connessioneDB.php";

$pdo->exec("CREATE DATABASE IF NOT EXISTS OpenData");
$pdo->exec("USE OpenData ");

$pdo->exec("CREATE TABLE IF NOT EXISTS STRADA(
CodiceVia INT,
NomeVia VARCHAR(50) NOT NULL,
IsSensoUnico BOOL NOT NULL,
NomeStrada VARCHAR(50),
PRIMARY KEY(CodiceVia)

)");

$pdo->exec("CREATE TABLE IF NOT EXISTS EDIFICIO(
NumCivico INT,
Strada INT,
Cap INT NOT NULL,
Sobborgo VARCHAR(25),
FOREIGN KEY (Strada) REFERENCES STRADA(CodiceVia) ON DELETE CASCADE,
PRIMARY KEY(NumCivico, Strada)

)");

$pdo->exec("CREATE TABLE IF NOT EXISTS RASTRELLIERA(
Id INT,
Tipologia VARCHAR(50) NOT NULL,
NPosti INT NOT NULL,
Anno YEAR NOT NULL,

TotBici INT NOT NULL,
CodiceVia INT,
Civico INT,
Tipo_Edificio VARCHAR(50),
FOREIGN KEY (Civico, CodiceVia) REFERENCES EDIFICIO(NumCivico, Strada),
PRIMARY KEY(Id)

)");