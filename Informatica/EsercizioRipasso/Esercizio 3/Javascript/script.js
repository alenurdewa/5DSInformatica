const form = document.getElementById("prenotazioneForm")
const nome = document.getElementById("nome")
const telefono = document.getElementById("telefono")
const persone = document.getElementById("persone")
const data = document.getElementById("data")
const orario = document.getElementById("orario")
const errore = document.getElementById("errore")

form.addEventListener("submit", function(e){
  e.preventDefault()
  errore.textContent = ""

  // Controllo nome
  if(nome.value.trim() === ""){
    errore.textContent = "Inserisci il nome e cognome"
    return
  }

  // Controllo telefono
  if(telefono.value.trim() === ""){
    errore.textContent = "Inserisci il numero di telefono"
    return
  }
  if(!/^\d+$/.test(telefono.value.trim())){
    errore.textContent = "Il numero di telefono deve contenere solo cifre"
    return
  }

  // Controllo data
  const oggi = new Date()
  const prenData = new Date(data.value)
  oggi.setHours(0,0,0,0)
  if(prenData < oggi){
    errore.textContent = "La data deve essere oggi o successiva"
    return
  }

  // Controllo orario
  if(orario.value){
    const [h, m] = orario.value.split(":").map(Number)
    if(h < 12 || h > 23){
      errore.textContent = "L'orario deve essere tra le 12:00 e le 23:00"
      return
    }
  }

  // Se tutti i controlli passano
  alert("Prenotazione effettuata con successo!")
  form.reset()
})
