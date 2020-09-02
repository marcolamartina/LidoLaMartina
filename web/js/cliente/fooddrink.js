$(document).ready(function() {

	creaProdotti();
	
	
	/** 
	 * Al cambio della categoria nel menu a tendina  mostra solo la categoria selezionata.
	 */
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
    
});



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
		url: "AggiungiProdotto",
		method: "post",
		data: {idprodotto: i,
				quantity : $("#sel"+i).val(),
				note : $("#note"+i).val()},
		success: function() {
			$("#modalSucc").modal();
			updateChart();
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

/**
 * Aggiorna il badge del carrello
 */
function updateChart(){
	$.ajax({
		url: "Carrello",
		method: "post",
		data: {dimension: "true",},
		success: function(data) {
			$("#counter").text(data);
		},
		error: function() {
			$("#modalErr").modal();
		}
	});
}
