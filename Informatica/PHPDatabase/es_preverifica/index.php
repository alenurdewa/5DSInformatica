<?php
require("db.php");
require("config.php");
require("inserimentoDati.php");

$tipoDiInvio = "";
$nomePartecipante = "";
$cognomePartecipante = "";


$titoloEvento ="";
$dataEvento = "";

if(isset($_POST["nomePartecipante"]) and isset($_POST["cognomePartecipante"] )){
    $tipoDiInvio = "InserisciPartecipante";
    $nomePartecipante = $_POST["nomePartecipante"];
    $cognomePartecipante = $_POST["cognomePartecipante"];
    
};

if(isset($_POST["titoloEvento"]) and isset($_POST["dataEvento"] )){
    $tipoDiInvio = "InserisciEvento";
    $titoloEvento = $_POST["titoloEvento"];
    $dataEvento = $_POST["dataEvento"];
    
};



if(isset($_POST["selezionaData"]) and isset($_POST["selezionaUser"] )){
    $tipoDiInvio = "InserisciPrenotazione";
    $codPartecipante =$_POST["selezionaUser"];
    $codEvento = $_POST["selezionaData"];
};




?>


    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sistema informativo</title>
    </head>
    <body>
        <h1>Sistema informativo</h1>
        <a href="paginaEliminazione.php">Schiacciare per eliminare i dati</a>
        <br>
        <a href="paginaModifica.php">Schiacciare per updatare i dati</a>
        <br>
        <a href="visualizzaDati.php">Schiacciare per visualizzare i dati</a>

        <br>

        <h2>Inserire nuovo partecipante</h2>
        <form action="index.php" method="POST" id="formPartecipante">
        
            <label for="nomePartecipante">Nome</label>
            <input type="text" name="nomePartecipante" id="nomePartecipante" value=<?php echo $nomePartecipante?>>
            <br>

            <label for="CognomePartecipante">Cognome</label>
            <input type="text" name="cognomePartecipante" id="cognomePartecipante" value=<?php echo $cognomePartecipante?>>
            <br>

            <input type="submit" name="invio" id="invio">

        </form>


        <h2>Inserire nuovo evento</h2>
        <form action="index.php" method="POST" id="formEvento">
        
            <label for="dataEvento">Data</label>
            <input type="date" name="dataEvento" id="dataEvento">
            <br>

            <label for="titoloEvento">Titolo</label>
            <input type="text" name="titoloEvento" id="titoloEvento">
            <br>

            <input type="submit" name="invio" id="invio">

        </form>

        <br>

        <h2>Inserire nuova prenotazione</h2>
        <form action="index.php" method="POST" >
            <select name="selezionaUser" id="selectUser">
                <?php
                    $sql = "SELECT * FROM PARTECIPANTE";
                    $partecipanti = $pdo->query($sql)->fetchAll();
                ?>
                <?php foreach($partecipanti as $partecipante): ?>
                <option value=<?php echo $partecipante["CodPartecipante"]?>><?php echo $partecipante["Nome"]." ID: ".$partecipante["CodPartecipante"] ?></option>
                <?php endforeach?>
            </select>

            <select name="selezionaData" id="selectDate">
                <?php
                    $sql = "SELECT * FROM EVENTO";
                    $eventi = $pdo->query($sql)->fetchAll();
                ?>
                <?php foreach($eventi as $evento): ?>
                <option value=<?php echo $evento["CodEvento"]?>><?php echo $evento["Titolo"]." ID: ".$evento["CodEvento"] ?></option>
                <?php endforeach?>
            </select>


            <input type="submit">   
        </form>

        
        <?php
            if($tipoDiInvio === "InserisciPartecipante"){
                inserireNuovoPartecipante($nomePartecipante, $cognomePartecipante, $pdo);
                echo "Partecipante $nomePartecipante $cognomePartecipante inserito correttamente nel database";
            }else if($tipoDiInvio === "InserisciEvento"){
                inserireNuovoEvento($titoloEvento, $dataEvento, $pdo);
                echo "Evento $titoloEvento  alla data $dataEvento è stato inserito correttamente nel database";
            }else if($tipoDiInvio === "InserisciPrenotazione"){
                $sql = "SELECT * FROM PRENOTAZIONE WHERE CodPartecipante = :codPart AND CodEvento = :codEv;";
                $stmt = $pdo->prepare($sql);
                $stmt->bindParam(':codPart', $codPartecipante);
                $stmt->bindParam(':codEv', $codEvento);
                $stmt->execute();
                $prenotazione = $stmt->fetch();
                if($prenotazione === false){
                    $sql2 = 'SELECT DataEvento FROM Evento WHERE CodEvento = :codEvento';
                    $stmt2 = $pdo->prepare($sql2);
                    $stmt2->bindParam(':codEvento', $codEvento);
                    $stmt2->execute();
                    $dataEvento = $stmt2->fetchColumn();
                    inserimentoNuovaPrenotazione($codEvento,$codPartecipante,$dataEvento,$pdo);
                    echo 'Prenotazione effettuata correttamente. Dati inseriti: ' .$codEvento .' '.$codPartecipante.' ' .$dataEvento;
                }else{
                    echo "Prenotazione gia esistente";
                }
                
            
            
            }

        ?>

    <script>
    // --- CONTROLLO PARTECIPANTE ---
    const formPart = document.getElementById('formPartecipante'); // Assicurati di aver messo id="formPartecipante" al form
    
    // Se il form esiste nella pagina (per evitare errori se sei in un'altra pagina)
    if(formPart) {
        formPart.addEventListener('submit', function(event) {
            
            // Prendo i valori
            let nome = document.getElementById('nomePartecipante').value.trim();
            let cognome = document.getElementById('cognomePartecipante').value.trim();
            let errori = [];

            // Controllo Nome
            if (nome === "") {
                errori.push("Il nome è obbligatorio.");
            } else if (nome.length > 20) {
                errori.push("Il nome non può superare i 20 caratteri.");
            }

            // Controllo Cognome
            if (cognome === "") {
                errori.push("Il cognome è obbligatorio.");
            } else if (cognome.length > 20) {
                errori.push("Il cognome non può superare i 20 caratteri.");
            }

            // SE CI SONO ERRORI:
            if (errori.length > 0) {
                event.preventDefault(); // BLOCCA L'INVIO AL PHP
                alert("Attenzione:\n" + errori.join("\n")); // Mostra l'errore
            }
        });
    }


    // --- CONTROLLO EVENTO ---
    const formEv = document.getElementById('formEvento'); // Assicurati di aver messo id="formEvento" al form HTML
    
    if(formEv) {
        formEv.addEventListener('submit', function(event) {
            
            let titolo = document.getElementById('titoloEvento').value.trim();
            let dataInput = document.getElementById('dataEvento').value;
            let errori = [];

            // Controllo Titolo
            if (titolo === "") {
                errori.push("Il titolo è obbligatorio.");
            } else if (titolo.length > 20) {
                errori.push("Il titolo è troppo lungo (max 20 char).");
            }

            // Controllo Data (Non deve essere nel passato?)
            if (dataInput === "") {
                errori.push("Devi inserire una data.");
            } else {
                let dataScelta = new Date(dataInput);
                let oggi = new Date();
                
                // Resetto l'orario di 'oggi' a 00:00:00 per fare un confronto equo solo sulla data
                oggi.setHours(0,0,0,0);

                if (dataScelta < oggi) {
                    // Nota: Se vuoi permettere eventi passati, togli questo if.
                    // Di solito si impedisce di creare eventi nel passato.
                    errori.push("La data dell'evento non può essere nel passato!");
                }
            }

            // SE CI SONO ERRORI:
            if (errori.length > 0) {
                event.preventDefault(); // BLOCCA TUTTO
                alert("Errore Evento:\n" + errori.join("\n"));
            }
        });
    }
    </script>

    </body>
    </html>

