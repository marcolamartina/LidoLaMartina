$(document).ready(function() {
	richiediOrdini();
	
	$("#search").on("keyup", function() {
    	var value = $(this).val().toLowerCase();
        $("#accordionTables .card-header").filter(function() {
          $(this).parent().toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
        
      });
	
	
});

/**
 * Richiede al server, mediante una chiamata ajax, le ordinazioni
 */
function richiediOrdini() {
	$.ajax({
		url: "RichiediOrdini",
		data: {ruolo: "bar"},
		method: "post",
		success: function(data) {
			mostraOrdini(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});

}




/**
 * Costruisce l'accordion contenente tutte le ordinazioni
 */
function mostraOrdini(data) {
	// Se non ci sono ordinazioni mostra il relativo messaggio.
	if(data.length == 0 ) {
		$("#accordionTables").hide();
		$("#search").hide();
		$("#ordiniVuoti").show();
		return;
	}
	$("#ordini").empty();
	$("#ordini").append('<div id="accordionTables"></div>');
	for(var i=0; i<data.length; i++){
		
		var idutente = data[i].idutente;
		var nome = data[i].utente_nome;
		var cognome = data[i].cognome;
		var prodotto = data[i].nome;
		var idordinazione = data[i].idordinazione;
		var quantita = data[i].quantita;
		var idconto = data[i].idconto;
		var tavolo = data[i].tavolo;
		var note = data[i].note;
		var ingredienti = data[i].ingredienti;
		var categoria = data[i].categoria;
		var takeAway = tavolo===0 ? true : false;
		
		if($("#cardTable"+tavolo).length==0){
			
			var accordionTables='<div id="cardTable'+tavolo+'" class="card">'
			    + '<div class="card-header">'
			    + '<a id="cardTitle'+tavolo+'" class="collapsed card-link" data-toggle="collapse" href="#collapseTable'+tavolo+'">'
			    + '<span style="float:left">Tavolo n°'+tavolo+'</span>'
			    + '</a>'
			    + '</div>'
			    + '<div id="collapseTable'+tavolo+'" class="collapse" data-parent="#accordionTables">'
			    + '<div class="card-body">'
			    + '<div id="contenutoTable'+tavolo+'">'
			    + '<table class="table table-sm table-borderless" id="tabellaTavolo'+tavolo+'">'
				+ '<tbody id="bodyTable'+tavolo+'">'
				+ '</tbody>'
				+ '</table>'
				+ '</div>'
			    + '</div>'
			    + '</div>';
			
			$("#accordionTables").append(accordionTables);
			if(takeAway){
				$("#cardTitle"+tavolo+' span').first().text('Da portare via');
				$("#contenutoTable"+tavolo).empty();
				$("#contenutoTable"+tavolo).append('<div class="accordionClients" id="accordionTakeAway"></div>');
			}	
		}
		
		if(takeAway && $("#card"+idutente).length==0){
			
			var accordionConto='<div id="card'+idutente+'" class="card">'
			    + '<div class="card-header">'
			    + '<a id="title'+idutente+'" class="collapsed card-link" data-toggle="collapse" href="#collapse'+idutente+'">'
			    + '<h5>'+nome+' '+cognome+'</h5>'
			    + '</a>'
			    + '</div>'
			    + '<div id="collapse'+idutente+'" class="collapse" data-parent="#accordionTakeAway">'
			    + '<div class="card-body">'
			    + '<div id="contenutoConto'+idutente+'">'
				+ '<table class="table table-sm table-borderless" id="tabellaTakeAway'+idutente+'">'
				+ '<tbody id="bodyTakeAway'+idutente+'">'
				+ '</tbody>'
				+ '</table>'
				+ '</div>'
			    + '</div>'
			    + '</div>';
		    
		    
			$("#accordionTakeAway").append(accordionConto);
		}
		
		if($("#cardTitle"+tavolo+" #span"+idutente).length==0){
			$("#cardTitle"+tavolo).append('<span id="span'+idutente+'" style="float:right"><small> - '+nome+' '+cognome+' </small></span>');
		}	
		printTable(data[i]);
		
		
	}
	
	$("#accordionTables .collapse").first().collapse("show");
	$(".accordionClients .collapse").first().collapse("show");
	
	
	// Richiede la lista delle ordinazioni ogni minuto e mezzo.
    setInterval(function(){
    	richiediOrdini();
    }, 90000);

}


/**
 * Costruisce la tabella di un conto
 */
function printTable(data){
	var idutente = data.idutente;
	var nome = data.utente_nome;
	var cognome = data.cognome;
	var prodotto = data.nome;
	var idordinazione = data.idordinazione;
	var quantita = data.quantita;
	var idconto = data.idconto;
	var tavolo = data.tavolo;
	var note = data.note;
	var ingredienti = data.ingredienti;
	var categoria = data.categoria;
	var takeAway = tavolo===0 ? true : false;


	var quantity='<td class="col-sm-1">x'+quantita+'</td>';

	var content = '<tr class="row'+idordinazione+' user'+idutente+'"> <td class="col-sm-9">' + prodotto + ' <small>('+ categoria +')';
	if(ingredienti!=null)content+=('<br />'+ingredienti);
	content+='</small></td> ' + quantity+ '</tr>';
	if(note!=null)content+='<tr class="row'+idordinazione+'"><small>'+note+'</small></tr>';
	if(!takeAway)content+='<tr class="row'+idordinazione+'"><td class="col-sm-1"><small>'+nome+' '+cognome+'</small></td></tr>';
	
	content	+= '<tr class="row'+idordinazione+'"><td class="col-sm-5"><button type="submit" class="btn btn-primary" id="button'+idordinazione+'" onclick="consegnato('+idordinazione+','+tavolo+','+idutente+')" name="commit">Consegnato</button></td></tr><tr class="row'+idordinazione+'"><td colspan="2"><hr /></td></tr>';
	
	if(takeAway){
		$("#bodyTakeAway"+idutente).append(content);
	}else{
		$("#bodyTable"+tavolo).append(content);
	}
	
	
}



function consegnato(idordinazione, tavolo, idutente){
	$.ajax({
		url: "CameriereHome",
		method: "post",
		data: {idordinazione: idordinazione},
		success: function(data) {
			$("#modalSucc").modal();

			

			$(".row"+idordinazione).empty();
			$(".row"+idordinazione).remove();
			if($("#cardTable"+tavolo+" .user"+idutente).length==0)$("#cardTitle"+tavolo+" #span"+idutente).remove();
			if(tavolo==0){
				if($("#bodyTakeAway"+idutente+' tr').length == 0){
					$("#card"+idutente).empty();
					$("#card"+idutente).remove();
				}
			}
			if($("#cardTable"+tavolo+' tr').length == 0){
				$("#cardTable"+tavolo).empty();
				$("#cardTable"+tavolo).remove();
			}
	
			$("#accordionTables:empty").remove();
			if($("#accordionTables").length == 0){
				$("#search").hide();
				$("#ordiniVuoti").show();
			}	
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}