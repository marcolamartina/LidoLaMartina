$(document).ready(function() {
	richiediCarrello();
});


/**
 * Richiede al server, mediante una chiamata ajax, i prodotti inseriti dal cliente all'interno
 * del carrello (il carrello è un oggetto di sessione, in modo che possa mantenere i dati del
 * cliente all'interno della stessa sessione).
 */
function richiediCarrello() {
	$.ajax({
		url: "Carrello",
		method: "post",
		data: {all: "true"},
		success: function(data) {
			mostraCarrello(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}


/**
 * Nasconde la tabella dei prodotti e mostra il messaggio di errore del carrello
 */
function carrelloVuoto() {
	$("#contenutoCarrello").hide();
	$("#ordinaButton").show();
	$("#carrelloVuoto").show();
	$("#counter").text("0");
}

/**
 * Costruisce la tabella contenente tutti i prodotti inseriti nel carrello
 * dal cliente e permette di rimuoverli
 */
function mostraCarrello(data) {
	// Se il cliente non ha ancora inserito prodotti nel carrello mostra il relativo messaggio.
	if(data.length == 0) {
		carrelloVuoto();
		return;
	}
	// Se c'è almeno un prodotto nel carrello riempie la tabella.
	$("#carrelloVuoto").hide();
	$("#ordinaButton").hide();
	$("#totaleCarrello").empty();


	var totale = 0, content = "";
		$("#tabellaOrdini").find("tr:gt(0)").remove();
		for(i=0; i<data.length; i++){
			var textnote="";
			if(data[i].note!=null && data[i].note!=undefined){
				textnote=data[i].note;
			}
			var button_trash='<td class="col-sm-1"><button class="btn btn-danger btn-sm" onclick="rimuoviDalCarrello('+data[i].idprodotto+')"><i class="fa fa-trash"></i></button></td>';
			var button_edit='<td class="col-sm-1"><button class="btn btn-primary btn-sm" onclick="showNote('+data[i].idprodotto+')"><i class="fa fa-sticky-note"></i></button></td>';
			var note='<td colspan="6"><textarea  class="form-control" id="note'+data[i].idprodotto+'" placeholder="Note" maxlength="100">'+textnote+'</textarea></td>';
			var quantitySelector='<td class="col-sm-1"><select class="form-control form-control-sm" style="width:auto;" id="sel'+data[i].idprodotto+'" onchange="changeQuantity('+data[i].idprodotto+')">';

			for(j=1; j<=20; j++){
				if(j==data[i].quantita){
					quantitySelector+='<option selected>'+j+'</option>';
				}else{
					quantitySelector+='<option>'+j+'</option>';
				}

			}
			quantitySelector+='</select></td>';

			var costo = parseFloat(parseFloat(data[i].prezzo)* parseFloat(data[i].quantita)).toFixed(2);
			content += '<tr id="row'+ data[i].idprodotto +'"> <td class="col-sm-6">' + data[i].nome + '<br /><small>('+ data[i].categoria +')</small></td> <td id="costo'+data[i].idprodotto+'" class="col-sm-1">'
					+ costo + '&euro;</td>' + quantitySelector + button_edit + button_trash+'</tr><tr id="rownote'+ data[i].idprodotto + '" style="display:none">'+note+'</tr><tr id="hr'+ data[i].idprodotto +'"><td colspan="6"><hr /></td></tr>';
			totale = parseFloat(parseFloat(costo) + parseFloat(totale)).toFixed(2);
		}
		$("#contenutoCarrello").show();
		$("#tabellaOrdini").append(content).show();
		$("#totaleCarrello").append("Totale: " + totale + "&euro;");
}

function showNote(i){
	$("#rownote"+i).toggle();
}


/**
 * Gestisce il cambiamento della quntità di un prodotto nel carrello e comunica tale
 * cambiamento al server mediante una chiamata ajax.
 */
function changeQuantity(i){
	$.ajax({
		url: "Carrello",
		method: "post",
		data: {idprodotto: i,
				quantity : $("#sel"+i).val()},
		success: function(data) {
			aggiornaTotale();
			var costo = parseFloat(parseFloat(data)* parseFloat($("#sel"+i).val())).toFixed(2);
			$("#costo"+i).html(costo);
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

/**
 * Aggiorna il totale
 */
function aggiornaTotale(){
	$.ajax({
		url: "Carrello",
		method: "post",
		data: {totale: "true"},
		success: function(data) {
			$("#totaleCarrello").html("Totale: " + parseFloat(data).toFixed(2) + "&euro;");;
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
function rimuoviDalCarrello(i) {

		$.ajax({
			url: "RimuoviProdotto",
			method: "post",
			data: {idprodotto: i},
			success: function() {

				$("#row"+i).remove();
				$("#rownote"+i).remove();
				$("#hr"+i).remove();

				$.ajax({
					url: "Carrello",
					method: "post",
					data: {dimension: "true",},
					success: function(data) {
						$("#counter").text(data);
						if(data == 0)
							carrelloVuoto();
						else {
							aggiornaTotale();
						}
					},
					error: function() {
						$("#modalErr").modal();
					}
				});

			},
			error: function() {
				$("#modalErr").modal();
			}
		});
}

function conferma(){
	$.ajax({
		url: "Ordina",
		method: "post",
		data: {tavolo: $("#tavolo").val()},
		success: function(data) {
			$("#modalSucc").modal();
			carrelloVuoto();
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}


