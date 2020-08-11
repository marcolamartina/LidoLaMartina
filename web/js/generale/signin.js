/**
 * Verifica la correttezza dei campi nel form di registrazione, in modo che il cliente inserisca 
 * dati che rispettino il formato richiesto prima di inviarli al server.
 */
function validatePassword() {
	var regex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})");
	var messaggio = "La password deve contenere almeno 8 caratteri, di cui un carattere minuscolo, un carattere maiuscolo e un numero";

	
    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var password= document.getElementById("password");


    // Controlli sui pattern richiesti per tutti i campi.
    if( regex.test(password.value)==false) {
    	password.setCustomValidity(messaggio);
    }
    else {
    	password.setCustomValidity('');
    }

}

function validateCel() {
	var regex = new RegExp("^([0|\+][0-9]{1,5})?([3][0-9]{9})$");
	var messaggio = "Inserire un numero di telefono corretto";

	
    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var cellulare= document.getElementById("cellulare");


    // Controlli sui pattern richiesti per tutti i campi.
    if( regex.test(cellulare.value)==false) {
    	cellulare.setCustomValidity(messaggio);
    }
    else {
    	cellulare.setCustomValidity('');
    }

}

function validateEmail() {
	var regex = new RegExp("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.][a-zA-Z0-9-.]+$");
	var messaggio = "Inserire un indirizzo email corretto";

	
    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var email= document.getElementById("email");


    // Controlli sui pattern richiesti per tutti i campi.
    if( regex.test(email.value)==false) {
    	email.setCustomValidity(messaggio);
    }
    else {
    	email.setCustomValidity('');
    }

}

function validateName() {
	var regex = new RegExp("^[A-Za-zèùàòé][a-zA-Z'èùàòé ]*$");
	var messaggio = "Inserire un nome corretto";

	
    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var nome= document.getElementById("nome");


    // Controlli sui pattern richiesti per tutti i campi.
    if( regex.test(nome.value)==false) {
    	nome.setCustomValidity(messaggio);
    }
    else {
    	nome.setCustomValidity('');
    }

}


function validateSurname() {
	var regex = new RegExp("^[A-Za-zèùàòé][a-zA-Z'èùàòé ]*$");
	var messaggio = "Inserire un cognome corretto";

	
    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var cognome= document.getElementById("cognome");


    // Controlli sui pattern richiesti per tutti i campi.
    if( regex.test(nome.value)==false) {
    	cognome.setCustomValidity(messaggio);
    }
    else {
    	cognome.setCustomValidity('');
    }

}




function validatePasswordConf() {
	var messaggio = "Le password non corrispondono";
	
    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var password= document.getElementById("password");
    var password_conf = document.getElementById("password_conf");

    // Controlli sui pattern richiesti per tutti i campi.
    if( password.value!=password_conf.value) {
    	password_conf.setCustomValidity(messaggio);
    	return false;
    }
    else {
    	password_conf.setCustomValidity('');
    	return true;
    }

}

