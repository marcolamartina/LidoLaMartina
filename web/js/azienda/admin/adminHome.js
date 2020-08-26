$(document).ready(function() {
	richiediAccountAziendali();
});

/**
* Richiede al server, mediante una chiamata ajax gli account aziendali
*/
function richiediAccountAziendali() {
	$.ajax({
		url: "AdminHome",
		method: "post",
		success: function(data) {
			mostraAccountAziendali(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}


/**
 * Costruisce la tabella contenente tutti i dati degli account aziendali
 */
function mostraAccountAziendali(data){
	if(data.length == 0) {
		$("#nessunAccount").show();
		$("#containerTabella").hide();
		return;
	}
	$("#nessunAccount").hide();
	$("#containerTabella").show();
	for(var i=0; i<data.length; i++){
		var utente=data[i];
		if($("#info" + utente.idutente).length === 0){
			var checkbox_bagnino='<td><div class="form-check"><input type="checkbox" class="form-check-input" id="checkBagnino'+utente.idutente+'" name="Bagnino" value="'+utente.idutente+'"></div></td>';
			var checkbox_cassa='<td><div class="form-check"><input type="checkbox" class="form-check-input" id="checkCassa'+utente.idutente+'" name="Cassa" value="'+utente.idutente+'"></div></td>';
			var checkbox_cucina='<td><div class="form-check"><input type="checkbox" class="form-check-input" id="checkCucina'+utente.idutente+'" name="Cucina" value="'+utente.idutente+'"></div></td>';
			var checkbox_bar='<td><div class="form-check"><input type="checkbox" class="form-check-input" id="checkBar'+utente.idutente+'" name="Bar" value="'+utente.idutente+'"></div></td>';
			var checkbox_reception='<td><div class="form-check"><input type="checkbox" class="form-check-input" id="checkReception'+utente.idutente+'" name="Reception" value="'+utente.idutente+'"></div></td>';
			var checkbox_cameriere='<td><div class="form-check"><input type="checkbox" class="form-check-input" id="checkCameriere'+utente.idutente+'" name="Cameriere" value="'+utente.idutente+'"></div></td>';
			var content='<tr id="row'+utente.idutente+'"><td><button id="info'+utente.idutente+'" class="btn btn-link" onclick=\'getInfo("'+utente.idutente+'","'+utente.nome+'","'+utente.cognome+'","'+utente.email+'","'+utente.cellulare+'")\'>'+utente.nome+' '+utente.cognome+'</td>'+checkbox_bagnino+checkbox_cassa+checkbox_cucina+checkbox_bar+checkbox_reception+checkbox_cameriere+'</tr>';
			$("#tablebody").append(content);
		}
		$('#check'+utente.ruolo+utente.idutente).prop('checked', true);
	}
	$("#tabella td, th").css("text-align","center");
	$("input[type='checkbox']").change(function() {
	    if(this.checked) {
	    	aggiungiRuolo(this.value, this.name, this.id);
	    }else{
	    	rimuoviRuolo(this.value, this.name, this.id);
	    }
	});
}

/**
 * Mostra le informazione dell'utente
 * @param id
 * @param nome
 * @param cognome
 * @param email
 * @param cellulare
 * @returns
 */
function getInfo(id, nome, cognome, email, cellulare){
	var modal='<div class="modal" id="modal'+id+'"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h4 class="modal-title">Info</h4><button type="button" class="close" data-dismiss="modal">&times;</button></div><div class="modal-body"><p>'+nome+' '+cognome+'</p><p>'+email+'</p><p>'+cellulare+'</p></div><div class="modal-footer"><button type="button" class="btn btn-danger" data-dismiss="modal">Chiudi</button></div></div></div></div>';
	$("#containerTabella").append(modal);
	$("#modal"+id).modal();
}

/**
 * Effettua una chiamata ajax per aggiungere un ruolo
 * @param id
 * @param ruolo
 * @param id_checkbox
 * @returns
 */
function aggiungiRuolo(id, ruolo, id_checkbox){ 
	$.ajax({
		url: "AggiungiRuolo",
		method: "post",
		data: {id: id,
				ruolo: ruolo},
		error: function() {
			$("#modalErr").modal();
			$("#"+id_checkbox).prop('checked', false);
		}
	});

}


/**
 * Effettua una chiamata ajax per rimuovere un ruolo
 * @param id
 * @param ruolo
 * @param id_checkbox
 * @returns
 */
function rimuoviRuolo(id, ruolo, id_checkbox){ 
	$.ajax({
		url: "RimuoviRuolo",
		method: "post",
		data: {id: id,
			ruolo: ruolo},
		success: function() {
			checkRiga(id);
			},
		error: function() {
			$("#modalErr").modal();
			$(id_checkbox).prop('checked', true);
		}
	});
}	

/**
 * Controlla se la riga non ha nessun checkbox attivo e in tal caso la elimina
 * @param id
 * @returns
 */
function checkRiga(id){
	if(!$("#checkBagnino"+id).is(':checked') && 
			!$("#checkCassa"+id).is(':checked') && 
			!$("#checkCucina"+id).is(':checked') &&
			!$("#checkBar"+id).is(':checked') &&
			!$("#checkReception"+id).is(':checked') &&
			!$("#checkCameriere"+id).is(':checked')){
		$("#row"+id).remove();
		if($('#tablebody tr').length==0){
			$("#nessunAccount").show();
			$("#containerTabella").hide();
		}
	}
}

