
var ruolo;
function init(r) {
	ruolo=r;
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
        
        $("#menu .form-check").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
          });
      });
    
}



/**
 * Effettua una chiamata ajax per richiedere i prodotti
 */
function creaProdotti(){
	
	$.ajax({
		url: "Prodotti"+ruolo,
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
	$("#selCategoria").html('<option value="Tutto">Tutto</option>');
	for(i = 0; i < arr.length; i++) {
		var categoria=arr[i].categoria;
		var categoriaId=categoria.replace(" ","").replace("'","");
		var content='<div class="form-check">'
			  		+ '<label class="form-check-label" id="label'+arr[i].idprodotto+'">'
			  		+ '<input id="checkbox'+arr[i].idprodotto+'" type="checkbox" class="form-check-input" value="'+arr[i].idprodotto+'">'+arr[i].nome;
		
		if(arr[i].ingredienti!=null){
			content+='<br /><small>'+arr[i].ingredienti+'</small>';
		}
		content+='</label><hr /></div>';

		if($("#"+categoriaId).length===0){
			var out = '<option value="' + categoria + '">' + categoria + '</option>';
			var content2='<div id="'+categoriaId+'Container"><h3>'+categoria+'</h3><div id="'+categoriaId+'"></div>'
			$("#menu").append(content2);
			$("#selCategoria").append(out);
		}

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
		url: "Prodotti"+ruolo,
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



