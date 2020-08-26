var sdraioMax;

$(document).ready(function() {
    $("#prezzi").remove();
    richiediPostazioni();
    sdraioMax=parseInt($("#sdraioBattigia").text());
    $("#date").change(aggiornaMappa);
    var date=new Date();
    var string_date=date.getFullYear()+'-';
    if(date.getMonth()<9){
        string_date+='0';
    }
    string_date+=date.getMonth()+1+'-';
    if(date.getDate()<10){
        string_date+='0';
    }
    string_date+=date.getDate();
    $("#date").val(string_date);
    $('[type="date"]').prop('min', string_date);

    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase().replace(" ","");
        $("#spiaggiaTableBody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });

    });

    aggiornaMappa();


});

function aggiornaMappa(){
    $.ajax({
        url: "BagninoHome",
        method: "post",

        data: {data: $("#date").val()},
        success: function(data) {
            setPostazioni(data);
        },
        error: function() {
            $("#modalErr").modal();
        }
    });

    // Aggiorna la mappa ogni minuto e mezzo.
    setInterval(function(){
        aggiornaMappa();
    }, 90000);
}

function resetMappa(){
    $(".postazione").css("background-color", "white");
    $(".postazione").removeAttr("data-original-title");
    $("#spiaggiaTableBody").empty();
}

function setPostazioni(data){
    resetMappa();
    var sdraio=0;
    var colore;
    if(data.length===0){
        $("#search").hide();
        $("#spiaggiaTable").hide();
    }else{
        $("#search").show();
        $("#spiaggiaTable").show();
    }
    for(var i=0; i<data.length; i++){
        sdraio+=parseInt(data[i].sdraio);
        if(data[i].numero!==0){
            if(data[i].occupata===true){
                colore="red";
            }else{
                colore="yellow";
            }
            $("#postazione"+data[i].numero).css("background-color", colore);
            $("#postazione"+data[i].numero).attr("data-toggle","tooltip");
            $("#postazione"+data[i].numero).attr("data-original-title",data[i].nome+" "+data[i].cognome);

        }

        var numero=data[i].numero === undefined ? "" : data[i].numero;
        var num_sdraio=data[i].sdraio === 0 ? "" : data[i].sdraio;
        if($("#rowSpiaggia"+data[i].idutente).length===0){
            var riga='<tr id="rowSpiaggia'+data[i].idutente+'"><td><button id="info'+data[i].idutente+'" class="btn btn-link" onclick=\'getInfo("'+data[i].idutente+'","'+data[i].nome+'","'+data[i].cognome+'","'+data[i].email+'","'+data[i].cellulare+'")\'>'+data[i].nome+' '+data[i].cognome+'</td><td class="sdraioCol">'+num_sdraio+'</td><td class="postazioneCol">'+numero+'</td></tr>';
            $("#spiaggiaTableBody").append(riga);
        } else {
            if(numero!=="")$("#rowSpiaggia"+data[i].idutente+" .postazioneCol").append(" "+numero);
            if(num_sdraio!==""){
                var oldSdraio=$("#rowSpiaggia"+data[i].idutente+" .sdraioCol").text() === "" ? 0 : $("#rowSpiaggia"+data[i].idutente+" .sdraioCol").text();
                var newSdraio=parseInt(oldSdraio)+parseInt(num_sdraio);

                $("#rowSpiaggia"+data[i].idutente+" .sdraioCol").text(newSdraio);
            }
        }
    }
    $("#sdraioBattigia").text(sdraioMax-sdraio);
    $('[data-toggle="tooltip"]').tooltip();



}

/**
 * Richiede al server, mediante una chiamata ajax, i dati relativi alle postazioni
 */
function richiediPostazioni() {
    $.ajax({
        url: "SpiaggiaMappa",
        method: "post",
        data: {postazioni: "true"},

        success: function(data) {
            mostraMappa(data);
        },
        error: function() {
            $("#modalErr").modal();
        }
    });
}

/**
 * Costruisce la mappa del lido
 */
function mostraMappa(data) {
    var max_x=1;
    var max_y=1;

    for(i=0; i<data.length; i++){
        var numero=data[i].numero;
        var x=parseInt(data[i].x);
        var y=parseInt(data[i].y);
        if(x>max_x)max_x=x;
        if(y>max_y)max_y=y;
        x+=2;
        var content='<div class="postazione" id="postazione'+numero+'" style="grid-row: '+x+'; grid-column: '+y+';"><small>'+numero+'</small></div>';
        $("#mappaLido").append(content);
    }
    var mare='<div style=" background-color: lightskyblue; grid-row: 1; grid-column: 1 / span '+max_y+';"><small>MARE</small></div>';
    var battigia='<div style=" background-color: #F2D16B; grid-row: 2; grid-column: 1 / span '+max_y+';"><small>BATTIGIA</small></div>';
    var cabine='<div style="background-color: #B07840; grid-row: '+(max_x+3)+'; grid-column: 1 / span 8;"><small>CABINE</small></div>';
    var wc='<div style="background-color: #B07840; grid-row: '+(max_x+3)+'; grid-column: 9 / span 2;"><small>WC</small></div>';
    var bar='<div style="background-color: #B07840; grid-row: '+(max_x+3)+'; grid-column: 11 / span 9; "><small>BAR</small></div>';
    var pedana='<div style="background-color: #B07840; grid-row: '+(max_x)+'/ span 3; grid-column: 11 / span 9; "><small>PEDANA</small></div>';
    var ingresso='<div style="background-color: #B07840; grid-row: '+(max_x+3)+'; grid-column: 20 / span 8; "><small>INGRESSO</small></div>';
    $("#mappaLido").append(mare);
    $("#mappaLido").append(battigia);
    $("#mappaLido").append(cabine);
    $("#mappaLido").append(wc);
    $("#mappaLido").append(bar);
    $("#mappaLido").append(pedana);
    $("#mappaLido").append(ingresso);
}

/**
 * Mostra le informazione dell'utente
 * @param id
 * @param nome
 * @param cognome
 * @param email
 * @param cellulare
 */
function getInfo(id, nome, cognome, email, cellulare){
    var modal='<div class="modal" id="modal'+id+'"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h4 class="modal-title">Info</h4><button type="button" class="close" data-dismiss="modal">&times;</button></div><div class="modal-body"><p>'+nome+' '+cognome+'</p><p>'+email+'</p><p>'+cellulare+'</p></div><div class="modal-footer"><button type="button" class="btn btn-danger" data-dismiss="modal">Chiudi</button></div></div></div></div>';
    $("#modals").append(modal);
    $("#modal"+id).modal();
}