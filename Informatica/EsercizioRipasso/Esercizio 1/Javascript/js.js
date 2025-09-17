
// Per richiamare la funzione in html scrivere
// oninput = "contaCarratteri"
function contaCaratteri() {
    //Qua prendiamo il campo del form
    input = document.getElementById("testo")

    //Prendo il testo che mi fa vedere quanti car rimangono
    counter = document.getElementById("counter")

    //Prendo il valore massimo di caratteri dal maxLength
    // (inserito nel html)
    max = input.maxLength

    //Prendo i caratteri scritti attualmente nel campo 
    usati = input.value.length

    //Calcolo quanti caratteri rimangono
    restanti = max - usati

    counter.textContent = restanti + " caratteri rimasti"
}