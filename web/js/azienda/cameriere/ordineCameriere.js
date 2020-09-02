/**
 * Valida un nome
 * @returns {boolean}
 */
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

/**
 * Valida un cognome
 * @returns {boolean}
 */
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

/**
 * Effettua una chiamata ajax per cercare un account
 */
function searchAccount(){
	if(validateSurname() && validateName()){
		$.ajax({
			url: "OrdineCameriere",
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
		$("#containerMenu").hide();
		return;
	}
	$("#nessunAccount").hide();
	$("#containerRisultati").empty();
	$("#containerRisultati").show();
	$("#containerMenu").show();
	
	for(var i=0; i<data.length; i++){
		var utente=data[i];
		var radio='<div class="form-check">'
				+ '<input name="opt" value="'+utente.idutente+'" type="radio" id="radio'+utente.idutente+'">'
				+ '<label for="radio'+utente.idutente+'"> '+utente.nome+' '+utente.cognome+' <small>('+utente.email+')</small></label>'
				+ '</div>';
		$("#containerRisultati").append(radio);
	}
	$("#containerRisultati input").first().prop('checked', true);
	
	creaMenu();
	

}

/**
 * Crea il menu
 */
function creaMenu(){
	if($("#menu tr").length==0){
		creaProdotti();
		
	    $("#selCategoria").change(function(){
	        var selected=$("#selCategoria option:selected").val();
	        if(selected=="Tutto"){
	        	$("#menu > div").show();
	        } else{
	        	$("#menu > div").hide();
	        	$("#"+selected.replace(" ","").replace("'","")+"Container").show();
	        }
	       
	        
		});
	    
	    
	    $("#search").on("keyup", function() {
	    	$("#selCategoria").val("Tutto");
	        var value = $(this).val().toLowerCase();
	        $("#menu > div").filter(function() {
	          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	        });
	        
	        $("#menu tbody").filter(function() {
	            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	          });
	      });
		
	}
	
}


/**
 * Inserimento dei prodotti
 */
function creaProdotti(){
	
	$.ajax({
		url: "VisualizzaProdotti",
		method: "post",
		data: {prodotti: "true"},
		success: function(data) {
			parsingProdotti(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
	
}

/**
 * Crea l'elenco dei prodotti
 * @param arr
 */
function parsingProdotti(arr) { 
	var i;
	var j;
	var s="";
	for(j=1; j<=20; j++){
		s+='<option>'+j+'</option>';
	}
	s+='</select></td>';
	$("#selCategoria").html('<option value="Tutto">Tutto</option>');
	for(i = 0; i < arr.length; i++) {
		var categoria=arr[i].categoria;
		var quantitySelector='<td class="col-sm-1"><select class="form-control form-control-sm" style="width:auto;" id="sel'+arr[i].idprodotto+'">'+s;
		var prezzo=parseFloat(arr[i].prezzo).toFixed(2);
		
		var note='<td colspan="5"><textarea class="form-control" id="note'+arr[i].idprodotto+'" placeholder="Note" maxlength="100"></textarea></td>';
		var button='<td class="col-sm-1"><button class="btn btn-primary btn-sm" onclick="addToChart('+arr[i].idprodotto+')"><i class="fa fa-plus"></i></button></td>';
		var button_edit='<td class="col-sm-1"><button class="btn btn-primary btn-sm" onclick="showNote('+arr[i].idprodotto+')"><i class="fa fa-sticky-note"></i></button></td>';
		var categoriaId=categoria.replace(" ","").replace("'","");
		var content='<tbody><tr><td class="col-sm-8">'+arr[i].nome+'</td><td class="col-sm-1">'+prezzo+'€</td>' + quantitySelector + button_edit + button + '</tr>';
		
		if(arr[i].ingredienti!=null){
			content+='<tr><td colspan="5"><small>'+arr[i].ingredienti+'</small></td></tr>';
		}
		content+='<tr id="rownote'+arr[i].idprodotto+'" style="display:none">'+note+'</tr><tr><td colspan="12"><hr /></td></tr></tbody>';

		if($("#"+categoriaId).length===0){
			var out = '<option value="' + categoria + '">' + categoria + '</option>';
			var content2='<div id="'+categoriaId+'Container"><h3>'+categoria+'</h3><table class="table table-sm table-borderless"><tbody id="'+categoriaId+'"></tbody></table></div>'
			$("#menu").append(content2);
			$("#selCategoria").append(out);
		}

		$("#"+categoriaId).append(content);
	}
}

/**
 * Mostra il campo delle note
 * @param i
 */
function showNote(i){
	$("#rownote"+i).toggle();
}

/**
 * Aggiunge il prodotto al carrello mediante una chiamata ajax
 * @param i
 */
function addToChart(i){
	$.ajax({
		url: "OrdineCameriere",
		method: "post",
		data: {idprodotto: i,
				quantity : $("#sel"+i).val(),
				note : $("#note"+i).val(),
				idutente: $("#containerRisultati input[type='radio']:checked").attr("value")},
		success: function() {
			$("#modalSucc").modal();
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}


