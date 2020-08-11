$(document).ready(function() {
	richiediStorico();

});

/**
 * Richiede al server, mediante una chiamata ajax, lo storico dei pagamenti
 */
function richiediStorico() {
	$.ajax({
		url: "StoricoPagamenti",
		method: "post",
		success: function(data) {
			mostraStorico(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});

}



/**
 * Costruisce l'accordion contenente tutti i pagamenti effettuati
 */
function mostraStorico(data) {
	// Se non ci sono pagamenti effettuati mostra il relativo messaggio.
	if(data.length == 0 ) {
		$("#accordion").hide();
		$("#storicoVuoto").show();
		return;
	}
	
	$("#storicoPagamenti").append('<div id="accordion"></div>');
		for(var i=0; i<data.length; i++){
			var bar=data[i].ordinazioni;
			var spiaggia=data[i].prenotazioni;
			var dataPagamento;
			var id;
			var totalePagato;
			if(bar[0]!=undefined){
				dataPagamento=bar[0].data;
				id=bar[0].idconto;
				
			}else{
				dataConto=spiaggia[0].data;
				id=spiaggia[0].idconto;

			}
			
			var init='<div class="card">'
			    + '<div class="card-header">'
			    + '<a id="title'+id+'" class="collapsed card-link" data-toggle="collapse" href="#collapse'+id+'">'
			    + '<span style="float:left">'+dataPagamento+'</span>'
			    + '</a>'
			    + '</div>'
			    + '<div id="collapse'+id+'" class="collapse" data-parent="#accordion">'
			    + '<div class="card-body">'
			    + '<div id="contenutoPagamento'+id+'">'
				+ '<table class="table table-sm table-borderless" id="tabellaBar'+id+'">'
				+ '<tbody id="bodyBar'+id+'">'
				+ '</tbody>'
				+ '</table>'
				+ '<table class="table table-sm table-borderless" id="tabellaSpiaggia'+id+'">'
        		+ '<tbody id="bodySpiaggia'+id+'">'
        		+ '</tbody>'
        		+ '</table>'
        		+ '<table class="table table-sm table-borderless" id="tabellaSconto'+id+'">'
        		+ '<tbody id="bodySconto'+id+'">'
        		+ '</tbody>'
        		+ '</table>'
        		+ '<h2 id="totaleConto'+id+'" style="float:right"></h2><br /><br />'
        		+ '<small id="totaleIVA'+id+'" style="float:right"></small>'
        		+ '<br /></div>'
			    + '</div>'
			    + '</div>';

				
			$("#accordion").append(init);
			printTable(id, bar, spiaggia);
		}
		$("#accordion .collapse").first().collapse("show");

}


/**
 * Costruisce la tabella dei dettagli del pagamento
 */
function printTable(id, bar, spiaggia){
	var totale = 0, content_bar = "", content_spiaggia = "" ,totalePagato=0, totaleIVA=0;
	var mySet = new Set();
	for(i=0; i<bar.length; i++){
		if(!mySet.has(bar[i].idconto)){
			mySet.add(bar[i].idconto);
			totalePagato=parseFloat(parseFloat(bar[i].totale)+parseFloat(totalePagato)).toFixed(2);
		}
		
		var quantity='<td class="col-sm-1">x'+bar[i].quantita+'</td>';
		var costo = parseFloat(parseFloat(bar[i].prezzo)* parseFloat(bar[i].quantita)).toFixed(2);
		totaleIVA=parseFloat(parseFloat(costo/10)+parseFloat(totaleIVA)).toFixed(2);
		content_bar += '<tr> <td class="col-sm-6">' + bar[i].nome + '<br /><small>('+ bar[i].categoria +')</small></td> ' + quantity +'<td class="col-sm-1">' 
				+ costo + '&euro;</td><tr><td colspan="3"><hr /></td></tr>';
		totale = parseFloat(parseFloat(costo) + parseFloat(totale)).toFixed(2);
	}
	
	for(i=0; i<spiaggia.length; i++){
		
		
		
		if(spiaggia[i].sdraio>0){
			var costo = parseFloat(parseFloat(spiaggia[i].prezzo)* parseFloat(spiaggia[i].sdraio)).toFixed(2);
			var quantity='<td class="col-sm-1">x'+spiaggia[i].sdraio+'</td>';
			content_spiaggia += '<tr> <td class="col-sm-6">Sdraio</td>' + quantity +'<td class="col-sm-1">' 
			+ costo + '&euro;</td></tr><tr><td colspan="4"><hr /></td></tr>';
			totale = parseFloat(parseFloat(costo) + parseFloat(totale)).toFixed(2);
		}
		if(spiaggia[i].postazione_numero>0){
			var costo = parseFloat(spiaggia[i].prezzo).toFixed(2);
			content_spiaggia += '<tr> <td class="col-sm-6">Postazione n°'+spiaggia[i].postazione_numero+'</td><td></td><td class="col-sm-1">' 
			+ costo + '&euro;</td></tr><tr><td colspan="3"><hr /></td></tr>';
			totale = parseFloat(parseFloat(costo)+ parseFloat(totale)).toFixed(2);
		}
		totaleIVA=parseFloat(parseFloat(costo*22/100)+parseFloat(totaleIVA)).toFixed(2);
	}
	
	
	$("#bodyBar"+id).append(content_bar);
	$("#bodySpiaggia"+id).append(content_spiaggia);
	if(totalePagato!=totale){
		var sconto=parseFloat(totalePagato-parseFloat(totale)).toFixed(2);
		totaleIVA=parseFloat(parseFloat(sconto*22/100)+parseFloat(totaleIVA)).toFixed(2);
		if(sconto<0){
			$("#bodySconto"+id).append('<tr><td class="col-sm-6">Sconto</td><td class="col-sm-1">'+sconto+'&euro;</td></tr>');
		}else{
			$("#bodySconto"+id).append('<tr><td class="col-sm-6">Supplemento</td><td class="col-sm-1">'+sconto+'&euro;</td></tr>');
		}
		
	}
	$("#totaleConto"+id).append("Totale: " + totalePagato + "&euro;");
	$("#totaleIVA"+id).append("IVA: " + totaleIVA + "&euro;");
	$("#title"+id).append('<span style="float:right">'+totalePagato + '&euro;'+'</span>');
}

