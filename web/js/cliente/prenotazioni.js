$(document).ready(function() {
    richiediPrenotazioni();
});

/**
 * Richiede al server, mediante una chiamata ajax, le prenotazioni
 */
function richiediPrenotazioni() {
    $.ajax({
        url: "Prenotazioni",
        method: "post",
        data: {all: "true"},
        success: function(data) {
            mostraPrenotazioni(data);
        },
        error: function() {
            $("#modalErr").modal();
        }
    });

}




/**
 * Costruisce l'accordion contenente tutti le prenotazioni
 */
function mostraPrenotazioni(data) {
    // Se non ci sono prenotazioni mostra il relativo messaggio.
    if(data.length === 0 ) {
        $("#accordion").hide();
        $("#prenotazioniVuote").show();
        return;
    }
    $("#prenotazioni").append('<div id="accordion"></div>');
    for(var i=0; i<data.length; i++){
       var idconto=data[i].idconto;
       var idprenotazione=data[i].idprenotazione;
        if($("#contenuto"+idconto).length===0){
            var init='<div class="card" id="card'+idconto+'">'
                + '<div class="card-header">'
                + '<a class="collapsed card-link" data-toggle="collapse" href="#collapse'+idconto+'">Prenotazioni del '+data[i].data+'</a>'
                + '</div>'
                + '<div id="collapse'+idconto+'" class="collapse" data-parent="#accordion">'
                + '<div class="card-body">'
                + '<div id="contenuto'+idconto+'">'
                + '<table class="table table-sm table-borderless" id="tabellaPrenotazioni'+idconto+'">'
                + '<tbody id="body'+idconto+'">'
                + '</tbody>'
                + '</table>'
                + '</div>'
                + '</div>'
                + '</div>'
                + '</div>';

            $("#accordion").append(init);

        }

        var costo="", content="";
        var button_trash='<td class="col-sm-1"><button id="button'+idprenotazione+'" name="button'+idconto+'" class="btn btn-danger btn-sm""><i class="fa fa-trash"></i></button></td>';
        if(data[i].sdraio>0){
            costo = parseFloat(parseFloat(data[i].prezzo)* parseFloat(data[i].sdraio)).toFixed(2);
            var quantity='<td class="col-sm-1">x'+data[i].sdraio+'</td>';
            content += '<tr id="row'+idprenotazione+'"> <td class="col-sm-6">Sdraio</td>' + quantity +'<td class="col-sm-1">'
                + costo + '&euro;</td>'+button_trash+'</tr><tr id="hr'+idprenotazione+'"><td colspan="4"><hr /></td></tr>';

        }
        if(data[i].numero>0){
            costo = parseFloat(data[i].prezzo).toFixed(2);
            content += '<tr id="row'+idprenotazione+'"> <td class="col-sm-6">Postazione n°'+data[i].numero+'</td><td></td><td class="col-sm-1">'
                + costo + '&euro;</td>'+button_trash+'</tr><tr id="hr'+idprenotazione+'"><td colspan="4"><hr /></td></tr>';
        }

        $("#body"+idconto).append(content);
        $("#button"+idprenotazione).click(rimuoviPrenotazione);
    }
    $("#accordion .collapse").first().collapse("show");
}

/**
 * Gestisce l'eliminazione di una prenotazione e comunica tale
 * eliminazione al server mediante una chiamata ajax.
 */
function rimuoviPrenotazione() {
    var idprenotazione=$(this).attr("id").substr(6);
    var idconto=$(this).attr("name").substr(6);
    $.ajax({
        url: "Prenotazioni",
        method: "post",
        data: {idprenotazione: idprenotazione,
            idconto: idconto},
        success: function() {
            $("#modalSucc").modal();
            $("#row"+idprenotazione).remove();
            $("#hr"+idprenotazione).remove();
            if($("#body"+idconto+" tr").length===0){
                $("#card"+idconto).empty();
                $("#card"+idconto).remove();
            }
            $("#accordion:empty").remove();
            if($("#accordion").length == 0){
                $("#prenotazioniVuote").show();
            }

        },
        error: function() {
            $("#modalErr").modal();
        }
    });
}

