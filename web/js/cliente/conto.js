$(document).ready(function() {
	richiediConti();
});

/**
 * Richiede al server, mediante una chiamata ajax, i conti aperti
 */
function richiediConti() {
	$.ajax({
		url: "Conto",
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
 * Costruisce l'accordion contenente tutti i prodotti del conto di oggi
 */
function mostraConti(data) {
	// Se non c'è un conto aperto mostra il relativo messaggio.
	if(data.length ==0 || (data[0].ordinazioni.length == 0 && data[0].prenotazioni.length == 0)) {
		$("#accordion").hide();
		$("#contoVuoto").show();
		return;
	}
	$("#conto").append('<div id="accordion"></div>');
		for(var i=0; i<data.length; i++){
			var bar=data[i].ordinazioni;
			var spiaggia=data[i].prenotazioni;
			var dataConto;
			var id;
			if(bar[0]!=undefined){
				dataConto=bar[0].data;
				id=bar[0].idconto;
			}else{
				dataConto=spiaggia[0].data;
				id=spiaggia[0].idconto;
			}
			
			
			var init='<div class="card">'
			    + '<div class="card-header">'
			    + '<a id="title'+id+'" class="collapsed card-link" data-toggle="collapse" href="#collapse'+id+'">'
			    + '<span style="float:left">Conto del '+dataConto+'</span>'
			    + '</a>'
			    + '</div>'
			    + '<div id="collapse'+id+'" class="collapse" data-parent="#accordion">'
			    + '<div class="card-body">'
			    + '<div id="contenutoConto'+id+'">'
				+ '<table class="table table-sm table-borderless" id="tabellaBar'+id+'">'
				+ '<tbody id="bodyBar'+id+'">'
				+ '</tbody>'
				+ '</table>'
				+ '<table class="table table-sm table-borderless" id="tabellaSpiaggia'+id+'">'
        		+ '<tbody id="bodySpiaggia'+id+'">'
        		+ '</tbody>'
        		+ '</table>'
        		+ '<h2 id="totaleConto'+id+'" style="float:right"></h2>'
        		+ '<br /></div>'
			    + '</div>'
			    + '</div>';
			    
			    
			$("#accordion").append(init);
			printTable(id, bar, spiaggia);
		}
		$("#accordion .collapse").first().collapse("show");
}


/**
 * Costruisce la tabella di un conto
 */
function printTable(id, bar, spiaggia){
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

	$("#bodyBar"+id).append(content_bar);
	$("#bodySpiaggia"+id).append(content_spiaggia);
	$("#totaleConto"+id).append("Totale: " + totale + "&euro;");
	$("#title"+id).append('<span style="float:right">'+totale + '&euro;'+'</span>');
}

