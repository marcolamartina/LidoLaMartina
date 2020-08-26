

function validateName() {
    var regex = new RegExp("^[A-Za-zèùàòé][a-zA-Z'èùàòé ]*$");
    var messaggio = "Inserire un nome corretto";


    // Variabili che contengono i dati inseriti dall'utente nel form di registrazione.
    var nome= document.getElementById("nome");


    // Controlli sui pattern richiesti per tutti i campi.
    if( regex.test(nome.value)==false) {
        nome.setCustomValidity(messaggio);
        return false;
    }
    else {
        nome.setCustomValidity('');
        return true;
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
        return false;
    }
    else {
        cognome.setCustomValidity('');
        return true;
    }

}

function searchAccount(){
    if(validateSurname() && validateName()){
        $.ajax({
            url: "NuovaPrenotazione",
            method: "post",
            data: {nome: $("#nome").val(),
                cognome: $("#cognome").val()},
            success: function(data) {
                mostraAccount(data);
            },
            error: function() {
                $("#modalErr").modal();
            }
        });
    }
}

/**
 * Costruisce la tabella contenente tutti i dati degli utenti
 */
function mostraAccount(data){
    if(data.length == 0) {
        $("#nessunAccount").show();
        $("#containerRisultati").hide();
        $("#containerSpiaggia").hide();
        return;
    }
    $("#nessunAccount").hide();
    $("#containerRisultati").empty();
    $("#containerRisultati").show();
    $("#containerSpiaggia").show();

    for(var i=0; i<data.length; i++){
        var utente=data[i];
        var radio='<div class="form-check">'
            + '<input name="opt" value="'+utente.idutente+'" type="radio" id="radio'+utente.idutente+'">'
            + '<label for="radio'+utente.idutente+'"> '+utente.nome+' '+utente.cognome+' <small>('+utente.email+')</small></label>'
            + '</div>';
        $("#containerRisultati").append(radio);
    }
    $("#containerRisultati input[type='radio']").change(setID);
    $("#containerRisultati input").first().prop('checked', true);
    $("#idutente").val($("#containerRisultati input").first().attr("value"));



}

function setID() {
    console.log($("#containerRisultati input[type='radio']:checked").attr("value"));
    $("#idutente").val($("#containerRisultati input[type='radio']:checked").attr("value"));
}
