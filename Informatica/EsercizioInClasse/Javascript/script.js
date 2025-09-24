const form = document.getElementById("form");

const nome = document.getElementById("nome")
const cognome = document.getElementById("cognome")
const eta = document.getElementById("eta")
const datanascita = document.getElementById("datanascita")
const password = document.getElementById("password")
const confpassword = document.getElementById("confPassword")


function checkNome(){
    document.getElementById("nomeP").innerHTML = "Nome inserito = " + nome.value
}

function checkPassword(){
    if(password.value !== confpassword.value){
        alert("Le due password non coincidono")
    }   
    
}

function chiamaFunzioni(event){
    checkPassword()
    checkNome()
    event.preventDefault()
}

form.addEventListener("submit", function(event){chiamaFunzioni(event)})








