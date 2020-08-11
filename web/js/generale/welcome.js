$(document).ready(function() {
	creaSelector();
});


/**
 * Creazione del menu a tendina con le categorie e gli headers delle categorie 
 */
function creaSelector(){
	$.ajax({
		url: "VisualizzaProdotti",
		method: "post",
		data: {categorie: "true"},
		success: function(data) {
			parsingCategorie(data);
			creaProdotti();
			},
		error: function(xhr) {
			$("#modalErr").modal();
		}
	});
	
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
		error: function(xhr) {
			$("#modalErr").modal();
		}
	});
	
}



function parsingCategorie(arr) { 
	
	for(var i = 0; i < arr.length; i++) {
		var categoria=arr[i].categoria;
		var categoriaId;
		if (categoria!=null && categoria!=undefined){
			categoriaId=categoria.replace(" ","").replace("'","");
		}	
		var content='<div id="'+categoriaId+'Container"><h3>'+categoria+'</h3><table class="table table-sm table-borderless"><tbody id="'+categoriaId+'"></tbody></table></div>'
		$("#menuWelcome").append(content);
	} 
}


function parsingProdotti(arr) { 
	var i;
	var j;
	for(i = 0; i < arr.length; i++) {
		var categoria=arr[i].categoria;
		var prezzo=parseFloat(arr[i].prezzo).toFixed(2);
		
		var categoriaId=categoria.replace(" ","").replace("'","");
		var content='<tbody><tr><td class="col-sm-10">'+arr[i].nome;
		
		if(arr[i].ingredienti!=null){
			content+='<br /><small>'+arr[i].ingredienti+'</small>';
		}
		content+='</td><td class="col-sm-2">'+prezzo+'€</td></tr><tr><td colspan="12"><hr /></td></tr></tbody>';
		$("#"+categoriaId).append(content);
	}
}


