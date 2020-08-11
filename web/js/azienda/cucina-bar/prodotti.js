$(document).ready(function() {
	
	creaSelector();
	
	
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
        
        $("#menu .form-check").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
          });
      });
    
});

/**
 * Creazione del menu a tendina con le categorie e gli headers delle categorie 
 */
function creaSelector(){
	$.ajax({
		url: "Prodotti",
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
		url: "Prodotti",
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
	var out = '<option value="Tutto">Tutto</option>';
	var i;
	
	for(i = 0; i < arr.length; i++) {
		var categoria=arr[i].categoria;
		var categoriaId;
		if (categoria!=null && categoria!=undefined){
			categoriaId=categoria.replace(" ","").replace("'","");
		}	
		out += '<option value="' + categoria + '">' + categoria + '</option>';
		var content='<div id="'+categoriaId+'Container"><h3>'+categoria+'</h3><div id="'+categoriaId+'"></div>'
		$("#menu").append(content);
	}
	$("#selCategoria").html(out); 
}


function parsingProdotti(arr) { 
	var i;
	var j;
	for(i = 0; i < arr.length; i++) {
		var categoria=arr[i].categoria;
		var prezzo=parseFloat(arr[i].prezzo).toFixed(2);
		var categoriaId=categoria.replace(" ","").replace("'","");
		var content='<div class="form-check">'
			  		+ '<label class="form-check-label" id="label'+arr[i].idprodotto+'">'
			  		+ '<input id="checkbox'+arr[i].idprodotto+'" type="checkbox" class="form-check-input" value="'+arr[i].idprodotto+'">'+arr[i].nome;
		
		if(arr[i].ingredienti!=null){
			content+='<br /><small>'+arr[i].ingredienti+'</small>';
		}
		content+='</label><hr /></div>';
		$("#"+categoriaId).append(content);
		checking(arr[i].idprodotto,arr[i].disponibile);
		
	}
	$("input[type='checkbox']").change(function() {
		checking(this.value, this.checked);
	    setDisponibile(this.value, this.checked);
	});
}

/**
 * Effettua una chiamata ajax per settare la disponibilità di un prodotto
 * flag è il boolean che rappresenta la disponibilità del prodotto
 * @param id
 * @param flag
 * @returns
 */
function setDisponibile(id, flag){ 
	$.ajax({
		url: "Prodotti",
		method: "post",
		data: {id: id,
				flag: flag},
		error: function() {
			$("#modalErr").modal();
			checking(id,!flag);
		}
	});

}


/**
 * Barra il checkbox e taglia il testo se non selezionato
 * @param id
 * @param flag
 * @returns
 */
function checking(id, flag){
	$("#checkbox"+id).prop('checked', flag);
	flag === false ? $("#label"+id).addClass("line-through") : $("#label"+id).removeClass("line-through");
}



