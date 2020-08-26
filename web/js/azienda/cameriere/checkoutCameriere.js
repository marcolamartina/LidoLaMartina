$(document).ready(function() {
	richiediComande();
});

/**
 * Richiede al server, mediante una chiamata ajax, i prodotti inseriti dal cameriere tra le comande
 */
function richiediComande() {
	$.ajax({
		url: "Comande",
		method: "post",
		data: {all: "true"},
		success: function(data) {
			mostraComande(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

/**
 * Nasconde la tabella delle comande e mostra il messaggio di errore delle comande vuote 
 */
function comandeVuote() {
	$("#contenutoComande").hide();
	$("#comandeVuote").show();
}

/**
 * Costruisce la tabella contenente tutti i prodotti inseriti tra le comande
 * dal cameriere e permette di rimuoverli 
 */
function mostraComande(data) {
	// Se il cameriere non ha ancora inserito comande mostra il relativo messaggio.
	if(data.length == 0) {
		comandeVuote();
		return;
	}
	// Se c'è almeno una comanda riempie la tabella.
	$("#comandeVuote").hide();
	$("#comande").append('<div id="accordionClients"></div>');
	var options='<option value="0">Da portare via</option>';
	for (var j=1; j<=25; j++){
		options+='<option>'+j+'</option>';
	}
	for(var i=0; i<data.length; i++){
		var utente=data[i].utente;
		var carrello=data[i].carrello; 
		var idutente=utente.idutente;
		var accordionClients='<div id="cardClient'+idutente+'" class="card">'
		    + '<div class="card-header">'
		    + '<a class="collapsed card-link" data-toggle="collapse" href="#collapseClient'+idutente+'">'
		    + '<h5>'+utente.nome+' '+utente.cognome+'</h5>'
		    + '</a>'
		    + '</div>'
		    + '<div id="collapseClient'+idutente+'" class="collapse" data-parent="#accordionClients">'
		    + '<div class="card-body">'
		    + '<div id="contenutoClient'+idutente+'">'
			+ '</div>'
		    + '</div>'
		    + '</div>';
		
		$("#accordionClients").append(accordionClients);
		
		
		
		var contenuto=   '<div class="accordionClients" id="contenutoCarrello'+idutente+'">'
			           + '  	<table class="table table-sm table-borderless" id="tabella'+idutente+'">'
			           + ' 		<tbody id="tabellaOrdini'+idutente+'">'
			           + ' 		</tbody>'
			           + ' 	</table>'
			           + ' 	<h2 id="totaleCarrello'+idutente+'"></h2>'
			           + ' 	<div class="form-group">'
			           + '		<label for="tavolo">Numero del tavolo:</label>'
			           + ' 		<select class="form-control form-control-sm" style="width:auto;" id="tavolo'+idutente+'">'
			           + options
			           + '		</select>'
			           + '	</div>	'
			           + ' 	<button class="btn btn-primary" onclick="conferma('+idutente+')">Conferma</button>'
			           + ' </div>';
	
		$("#contenutoClient"+idutente).append(contenuto);
		
		var totale = 0, content = "";
		$("#tabellaOrdini"+idutente).find("tr:gt(0)").remove();
		for(i=0; i<carrello.length; i++){
			var textnote="";
			if(carrello[i].note!=null && carrello[i].note!=undefined){
				textnote=carrello[i].note;
			}
			var button_trash='<td class="col-sm-1"><button class="btn btn-primary btn-sm" onclick="rimuoviDalCarrello('+carrello[i].idprodotto+','+idutente+')"><i class="fa fa-trash"></i></button></td>';
			var button_edit='<td class="col-sm-1"><button class="btn btn-primary btn-sm" onclick="showNote('+carrello[i].idprodotto+','+idutente+')"><i class="fa fa-sticky-note"></i></button></td>';
			var note='<td colspan="6"><textarea  class="form-control" id="note'+carrello[i].idprodotto+'" placeholder="Note" maxlength="100">'+textnote+'</textarea></td>';
			var quantitySelector='<td class="col-sm-1"><select class="form-control form-control-sm sel'+carrello[i].idprodotto+'" style="width:auto;" onchange="changeQuantity('+carrello[i].idprodotto+','+idutente+')">';
			
			for(j=1; j<=20; j++){
				if(j==carrello[i].quantita){
					quantitySelector+='<option selected>'+j+'</option>';
				}else{
					quantitySelector+='<option>'+j+'</option>';
				}
				
			}
			quantitySelector+='</select></td>';
			
			var costo = parseFloat(parseFloat(carrello[i].prezzo)* parseFloat(carrello[i].quantita)).toFixed(2);
			content += '<tr class="row'+ carrello[i].idprodotto +'"> <td class="col-sm-6">' + carrello[i].nome + '<br /><small>('+ carrello[i].categoria +')</small></td> <td class="costo'+carrello[i].idprodotto+'" class="col-sm-1">' 
					+ costo + '&euro;</td>' + quantitySelector + button_edit + button_trash+'</tr><tr class="rownote'+ carrello[i].idprodotto + '" style="display:none">'+note+'</tr><tr class="hr'+ carrello[i].idprodotto +'"><td colspan="6"><hr /></td></tr>';
			totale = parseFloat(parseFloat(costo) + parseFloat(totale)).toFixed(2);
		}
		$("#contenutoCarrello"+idutente).show();
		$("#tabellaOrdini"+idutente).append(content).show();
		$("#totaleCarrello"+idutente).append("Totale: " + totale + "&euro;");
		
	}
	$(".card .collapse").first().collapse("show");

}

function showNote(i, idutente){
	$("#contenutoCarrello"+idutente+" .rownote"+i).toggle();
}


/**
 * Gestisce il cambiamento della quntità di un prodotto nel carrello e comunica tale 
 * cambiamento al server mediante una chiamata ajax.
 */
function changeQuantity(i, idutente){
	$.ajax({
		url: "Comande",
		method: "post",
		data: {idprodotto: i,
				quantity : $("#contenutoCarrello"+idutente+" .sel"+i).first().val(),
				idutente: idutente},
		success: function(data) {
			aggiornaTotale(idutente);
			var costo = parseFloat(parseFloat(data)* parseFloat($("#contenutoCarrello"+idutente+" .sel"+i).first().val())).toFixed(2);
			$("#contenutoCarrello"+idutente+" .costo"+i).html(costo);
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

/**
 * Aggiorna il totale
 */
function aggiornaTotale(idutente){
	$.ajax({
		url: "Comande",
		method: "post",
		data: {totale: "true",
				idutente: idutente},
		success: function(data) {
			$("#totaleCarrello"+idutente).html("Totale: " + parseFloat(data).toFixed(2) + "&euro;");;
			},
		error: function(xhr) {
			$("#modalErr").modal();
		}
	});
	
	
}



/**
 * Gestisce l'eliminazione di un prodotto nel carrello dalla tabella e comunica tale 
 * eliminazione al server mediante una chiamata ajax.
 */
function rimuoviDalCarrello(i, idutente) {
		
		$.ajax({
			url: "Comande",
			method: "post",
			data: {idprodotto: i,
					idutente: idutente,
					rimuovi: "true"},
			success: function() {
				
				$("#contenutoCarrello"+idutente+" .row"+i).remove();
				$("#contenutoCarrello"+idutente+" .rownote"+i).remove();
				$("#contenutoCarrello"+idutente+" .hr"+i).remove();
				
				
				if(checkCarrelloVuoto(idutente)==false){
					aggiornaTotale(idutente);
				};
				checkComandeVuote();
				
			},
			error: function() {
				$("#modalErr").modal();
			}
		});
}

/**
 * Gestisce la conferma dell'ordinazione e la comunica al server mediante una chiamata ajax.
 */
function conferma(idutente){
	$.ajax({
		url: "CheckoutCameriere",
		method: "post",
		data: {tavolo: $("#tavolo".idutente).val(),
				idutente: idutente},
		success: function() {
			$("#modalSucc").modal();
			$("#cardClient"+idutente).empty();
			$("#cardClient"+idutente).remove();
			checkComandeVuote();
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

/**
 * Ritorna true se le comande sono vuote, false altrimenti
 * @returns
 */
function checkComandeVuote(){
	if($("#accordionClients .card-header").length==0){
		comandeVuote();
		return true;
	}else{
		return false;
	}
}

/**
 * Ritorna true se le comande sono vuote, false altrimenti
 * @returns
 */
function checkCarrelloVuoto(idutente){
	if($("#tabellaOrdini"+idutente+" tr").length==0){
		$("#cardClient"+idutente).empty();
		$("#cardClient"+idutente).remove();
		return true;
	}else{
		return false;
	}
}


