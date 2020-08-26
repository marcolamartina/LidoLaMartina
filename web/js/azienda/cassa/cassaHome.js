$(document).ready(function() {
	richiediConti();
	
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
function richiediConti() {
	$.ajax({
		url: "RichiediConti",
		method: "post",
		success: function(data) {
			mostraConti(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});

}




/**
 * Costruisce l'accordion contenente tutti i conti di oggi
 */
function mostraConti(data) {
	// Se non c'è un conto aperto mostra il relativo messaggio.
	if(data.length === 0 ) {
		$("#accordionClients").hide();
		$("#search").hide();
		$("#contiVuoti").show();
		return;
	}
	$("#conti").append('<div id="accordionClients"></div>');
	for(var i=0; i<data.length; i++){
		var idutente, nome, cognome; 
		if(data[i].conto[0].ordinazioni[0]!=undefined){
			idutente = data[i].conto[0].ordinazioni[0].idutente;
			nome = data[i].conto[0].ordinazioni[0].utente_nome;
			cognome = data[i].conto[0].ordinazioni[0].cognome;
		}else{
			idutente = data[i].conto[0].prenotazioni[0].idutente;
			nome = data[i].conto[0].prenotazioni[0].utente_nome;
			cognome = data[i].conto[0].prenotazioni[0].cognome;
		}
		
		var accordionClients='<div id="cardClient'+idutente+'" class="card">'
		    + '<div class="card-header">'
		    + '<a class="collapsed card-link" data-toggle="collapse" href="#collapseClient'+idutente+'">'
		    + '<h5>'+nome+' '+cognome+'</h5>'
		    + '</a>'
		    + '</div>'
		    + '<div id="collapseClient'+idutente+'" class="collapse" data-parent="#accordionClients">'
		    + '<div class="card-body">'
		    + '<div id="contenutoClient'+idutente+'">'
			+ '</div>'
		    + '</div>'
		    + '</div>';
		
		$("#accordionClients").append(accordionClients);
		$("#contenutoClient"+idutente).append('<div class="accordionClients" id="accordionConto'+idutente+'"></div>');
		
		for(var j=0; j<data[i].conto.length; j++){	
			var bar=data[i].conto[j].ordinazioni;
			var spiaggia=data[i].conto[j].prenotazioni;
			var dataConto;
			var id;
			if(bar[0]!=undefined){
				dataConto=bar[0].data;
				id=bar[0].idconto;
			}else{
				dataConto=spiaggia[0].data;
				id=spiaggia[0].idconto;
			}
			
			
			var accordionConto='<div id="card'+id+'" class="card">'
			    + '<div class="card-header">'
			    + '<a id="title'+id+'" class="collapsed card-link" data-toggle="collapse" href="#collapse'+id+'">'
			    + '<span style="float:left">Conto del '+dataConto+'</span>'
			    + '</a>'
			    + '</div>'
			    + '<div id="collapse'+id+'" class="collapse" data-parent="#accordionConto'+idutente+'">'
			    + '<div class="card-body">'
			    + '<div id="contenutoConto'+id+'">'
				+ '<table class="table table-sm table-borderless" id="tabellaBar'+id+'">'
				+ '<tbody id="bodyBar'+id+'">'
				+ '</tbody>'
				+ '</table>'
				+ '<table class="table table-sm table-borderless" id="tabellaSpiaggia'+id+'">'
        		+ '<tbody id="bodySpiaggia'+id+'">'
        		+ '</tbody>'
        		+ '<table class="table table-sm table-borderless" id="tabellaSconto'+id+'">'
        		+ '<tbody id="bodySconto'+id+'">'
        		+ '</tbody>'
        		+ '</table>'
        		+ '<table class="table table-sm table-borderless" id="tabellaSupplemento'+id+'">'
        		+ '<tbody id="bodySupplemento'+id+'">'
        		+ '</tbody>'
        		+ '</table>'
        		+ '<h2 id="totaleConto'+id+'" style="float:right"></h2>'
        		+ '<button type="submit" class="btn btn-primary" id="button'+id+'" name="commit">Paga</button>'
        		+ '<br /></div>'
			    + '</div>'
			    + '</div>';

			$("#accordionConto"+idutente).append(accordionConto);
			printTable(id, bar, spiaggia, idutente);
		}
	}	
	//$("#accordionClients .collapse").first().collapse("show");
	//$(".accordionClients .collapse").first().collapse("show");

}


/**
 * Costruisce la tabella di un conto
 */
function printTable(id, bar, spiaggia, idutente){
	var totale = 0, content_bar = "", content_spiaggia = "";
	for(i=0; i<bar.length; i++){
		var quantity='<td class="col-sm-1">x'+bar[i].quantita+'</td>';
		var costo = parseFloat(parseFloat(bar[i].prezzo)* parseFloat(bar[i].quantita)).toFixed(2);
		content_bar += '<tr> <td class="col-sm-6">' + bar[i].nome + '<br /><small>('+ bar[i].categoria +')</small></td> ' + quantity +'<td class="col-sm-1">' 
				+ costo + '&euro;</td><tr><td colspan="3"><hr /></td></tr>';
		totale = parseFloat(parseFloat(costo) + parseFloat(totale)).toFixed(2);
	}
	
	for(i=0; i<spiaggia.length; i++){
		
		if(spiaggia[i].sdraio>0){
			var costo = parseFloat(parseFloat(spiaggia[i].prezzo)* parseFloat(spiaggia[i].sdraio)).toFixed(2);
			var quantity='<td class="col-sm-1">x'+spiaggia[i].sdraio+'</td>';
			content_spiaggia += '<tr> <td class="col-sm-6">Sdraio</td>' + quantity +'<td class="col-sm-1">' 
			+ costo + '&euro;</td></tr><tr><td colspan="3"><hr /></td></tr>';
			totale = parseFloat(parseFloat(costo) + parseFloat(totale)).toFixed(2);
		}
		if(spiaggia[i].postazione_numero>0){
			var costo = parseFloat(spiaggia[i].prezzo).toFixed(2);
			content_spiaggia += '<tr> <td class="col-sm-6">Postazione n°'+spiaggia[i].postazione_numero+'</td><td></td><td class="col-sm-1">' 
			+ costo + '&euro;</td></tr><tr><td colspan="3"><hr /></td></tr>';
			totale = parseFloat(parseFloat(costo)+ parseFloat(totale)).toFixed(2);
		}
	}
	
	$("#contenutoConto"+id).show();
	$("#bodyBar"+id).append(content_bar).show();
	$("#bodySpiaggia"+id).append(content_spiaggia).show();
	$("#bodySconto"+id).append('<tr><td class="col-sm-6">Sconto</td><td>-</td><td class="col-sm-11"><form method="post" action="javascript:void(0);" class="form-inline" style="width:max-content"><input type="number" onkeyup="aggiornaTotale('+id+','+totale+')"  oninput="aggiornaTotale('+id+','+totale+')" class="form-control form-control-sm" id="sconto'+id+'" min="0" max="'+totale+'" step="0.01" value="0.00"><td>&euro;<i class="fa fa-times" id="x_errore" aria-hidden="true" style="display:none"></i></td></form></td></tr>');
	$("#bodySupplemento"+id).append('<tr><td class="col-sm-6">Supplemento</td><td>+</td><td class="col-sm-11"><form method="post" action="javascript:void(0);" class="form-inline" style="width:max-content"><input type="number" onkeyup="aggiornaTotale('+id+','+totale+')"  oninput="aggiornaTotale('+id+','+totale+')" class="form-control form-control-sm" id="supplemento'+id+'" min="0" max="'+totale+'" step="0.01" value="0.00"><td>&euro;<i class="fa fa-times" id="x_errore_suppl" aria-hidden="true" style="display:none"></i></td></form></td></tr>');
	$("#totaleConto"+id).html("Totale: " + totale + "&euro;");
	$("#title"+id).append('<span id="totaleSpan'+id+'" style="float:right">'+totale + '&euro;</span>');
	$("#button"+id).click(function(){
	    paga(id, idutente, totale);
	});
}

/**
 * Aggiorna il totale
 * @param id
 * @param old_tot
 */
function aggiornaTotale(id, old_tot){
	var totale, sconto, suppl;

	if(Number.isNaN(parseFloat($("#sconto"+id).val()))){
		$("#x_errore").show();
		return;
	}else if(Number.isNaN(parseFloat($("#supplemento"+id).val()))){
		$("#x_errore_suppl").show();
		return;
	}else{
		$("#x_errore").hide();
		$("#x_errore_suppl").hide();
		sconto=parseFloat($("#sconto"+id).val());
		suppl=parseFloat($("#supplemento"+id).val());
		totale = parseFloat(parseFloat(old_tot) - parseFloat(sconto) + parseFloat(suppl)).toFixed(2);
		$("#totaleConto"+id).html("Totale: " + totale + "&euro;");
		$("#totaleSpan"+id).html(totale + '&euro;');
		
	}
	
}


/**
 * Paga il conto mediante una richiesta ajax
 * @param id
 * @param idutente
 * @param totale
 */
function paga(id, idutente, totale){
	$.ajax({
		url: "CassaHome",
		method: "post",
		data: {id: id,
				sconto: $("#sconto"+id).val(),
				supplemento: $("#supplemento"+id).val(),
				totale: totale},
		success: function(data) {
			$("#modalSucc").modal();
			$("#card"+id).empty();
			$("#card"+id).remove();
			if($("#cardClient"+idutente+' .card').length == 0){
				$("#cardClient"+idutente).empty();
				$("#cardClient"+idutente).remove();
			}
			$("#accordionClients:empty").remove();
			if($("#accordionClients").length == 0){
				$("#search").hide();
				$("#contiVuoti").show();
			}	
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

