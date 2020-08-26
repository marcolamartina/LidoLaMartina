$(document).ready(function() {
	richiediPostazioni();
	
	$("#search").on("keyup", function() {
    	var value = $(this).val().toLowerCase();
        $("#accordionClients .card-header").filter(function() {
          $(this).parent().toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
        
      });
	
	
});

/**
 * Richiede al server, mediante una chiamata ajax, i conti aperti
 */
function richiediPostazioni() {
	$.ajax({
		url: "LiberaPostazione",
		method: "post",
		data: {request: true},
		success: function(data) {
			mostraPostazioni(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});

}




/**
 * Costruisce l'accordion contenente tutte le postazioni occupate per ogni utente
 */
function mostraPostazioni(data) {
	// Se non ci sono postazioni da liberare mostra il relativo messaggio.
	if(data.length == 0 ) {
		$("#accordionClients").hide();
		$("#search").hide();
		$("#postazioniVuote").show();
		return;
	}
	$("#postazioni").append('<div id="accordionClients"></div>');
	
	for(var i=0; i<data.length; i++){
		
		var idutente = data[i].idutente;
		var nome = data[i].nome;
		var cognome = data[i].cognome;
		var idprenotazione = data[i].idprenotazione;
		var postazione_numero = data[i].postazione_numero;
		
		if($("#cardClient"+idutente).length==0){
			
			var accordionClients='<div id="cardClient'+idutente+'" class="card">'
			    + '<div class="card-header">'
			    + '<a class="collapsed card-link" data-toggle="collapse" href="#collapseClient'+idutente+'">'
			    + '<h5>'+nome+' '+cognome+'</h5>'
			    + '</a>'
			    + '</div>'
			    + '<div id="collapseClient'+idutente+'" class="collapse" data-parent="#accordionClients">'
			    + '<div class="card-body">'
			    + '<div id="contenutoClient'+idutente+'">'
			    + '<table class="table table-sm table-borderless" id="tabellaSpiaggia'+idutente+'">'
	    		+ '<tbody id="bodySpiaggia'+idutente+'">'
	    		+ '</tbody>'
				+ '</div>'
			    + '</div>'
			    + '</div>';
			
			$("#accordionClients").append(accordionClients);
		}

		var postazioni = '<tr id="row'+idprenotazione+'"> <td class="col-sm-6">Postazione n°'+postazione_numero+'</td><td><button type="submit" class="btn btn-primary" id="button'+idprenotazione+'" onclick="libera('+idprenotazione+','+ idutente+')" name="commit">Libera</button></td></tr>';  
		
		$("#bodySpiaggia"+idutente).append(postazioni);
		

	}	
	//$("#accordionClients .collapse").first().collapse("show");
	//$(".accordionClients .collapse").first().collapse("show");

}




function libera(idprenotazione, idutente){
	$.ajax({
		url: "LiberaPostazione",
		method: "post",
		data: {idprenotazione: idprenotazione},
		success: function() {
			$("#modalSucc").modal();
			$("#row"+idprenotazione).remove();
			if($("#bodySpiaggia"+idutente).html().length == 0){
				$("#cardClient"+idutente).empty();
				$("#cardClient"+idutente).remove();
			}
			$("#accordionClients:empty").remove();
			if($("#accordionClients").length == 0){
				$("#search").hide();
				$("#postazioniVuote").show();
			}	
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}