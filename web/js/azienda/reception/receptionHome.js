var sdraioMax;
var oggi;


$(document).ready(function() {
    $("#prezzi").remove();
    sdraioMax=parseInt($("#sdraioBattigia").text());
    richiediPostazioni();
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
    oggi=string_date;
    $("#date").val(string_date);
    $('[type="date"]').prop('min', string_date);

    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase().replace(" ","");
        $("#spiaggiaTableBody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });

    });


});

function aggiornaMappa(){
    $.ajax({
        url: "ReceptionHome",
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
    $(".postazione").removeClass("occupata");
    $(".postazione").removeClass("prenotata");
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
        var button;
        if(data[i].numero!==0){
            if(data[i].occupata===true){
                colore="red";
                $("#postazione"+data[i].numero).addClass("occupata");
                button='<button id="button'+data[i].numero+'" name="'+data[i].idprenotazione+'" class="btn btn-occupata btn-sm">';
            }else{
                colore="yellow";
                $("#postazione"+data[i].numero).addClass("prenotata");
                button='<button id="button'+data[i].numero+'" name="'+data[i].idprenotazione+'" class="btn btn-prenotata btn-sm">';
            }
            $("#postazione"+data[i].numero).css("background-color", colore);
            $("#postazione"+data[i].numero).attr("data-toggle","tooltip");
            $("#postazione"+data[i].numero).attr("data-original-title",data[i].nome+" "+data[i].cognome);

        }

        var numero=data[i].numero === undefined ? "" : data[i].numero;
        var num_sdraio=data[i].sdraio === 0 ? "" : data[i].sdraio;
        var button_postazione="";
        if(data[i].numero !== undefined){
            button_postazione=button+numero+'</button>';
        }
        if($("#rowSpiaggia"+data[i].idutente).length===0){
            var riga='<tr id="rowSpiaggia'+data[i].idutente+'"><td><button style="padding: 0" id="info'+data[i].idutente+'" class="btn btn-link btn-sm" onclick=\'getInfo("'+data[i].idutente+'","'+data[i].nome+'","'+data[i].cognome+'","'+data[i].email+'","'+data[i].cellulare+'")\'>'+data[i].nome+' '+data[i].cognome+'</td><td class="sdraioCol">'+num_sdraio+'</td><td class="postazioneCol">'+button_postazione+'</td></tr>';
            $("#spiaggiaTableBody").append(riga);
        } else {
            if(numero!=="")$("#rowSpiaggia"+data[i].idutente+" .postazioneCol").append(" "+button_postazione);
            if(num_sdraio!==""){
                var oldSdraio=$("#rowSpiaggia"+data[i].idutente+" .sdraioCol").text() === "" ? 0 : $("#rowSpiaggia"+data[i].idutente+" .sdraioCol").text();
                var newSdraio=parseInt(oldSdraio)+parseInt(num_sdraio);

                $("#rowSpiaggia"+data[i].idutente+" .sdraioCol").html(newSdraio);
            }
        }
    }

    if($("#date").val()===oggi){
        $(".btn-occupata, .btn-prenotata, .occupata, .prenotata").click(setOccupata);
    }else{
        $(".btn-occupata, .btn-prenotata").attr("disabled","disabled");
    }

    $("#sdraioBattigia").text(sdraioMax-sdraio);
    $('[data-toggle="tooltip"]').tooltip();

}

/**
 * Imposta una postazione come occupata e lo comunica al server tramite una chiamata ajax
 */
function setOccupata(){
    var occupata; // true se si vuole rendere occupata una postazione o false se la si vuole fare ritornare prenotata
    if($(this).hasClass("btn-prenotata") || $(this).hasClass("prenotata")){
        occupata=true;
    }else if($(this).hasClass("btn-occupata") || $(this).hasClass("occupata")){
        occupata=false;
    }
    var button=$("#button"+$(this).text());
    var postazione=$("#postazione"+$(this).text());


    var color;
    $.ajax({
        url: "ReceptionHome",
        method: "post",
        data: {prenotazione: button.attr("name"),
                occupata: occupata},
        success: function() {
            if(occupata==true){
                button.removeClass("btn-prenotata");
                button.addClass("btn-occupata");
                postazione.removeClass("prenotata");
                postazione.addClass("occupata");
                if(button.parent().hasClass("postazioneCol")){
                   color="red";
                }

            }else{
                button.addClass("btn-prenotata");
                button.removeClass("btn-occupata");
                postazione.addClass("prenotata");
                postazione.removeClass("occupata");
                if(button.parent().hasClass("postazioneCol")){
                    color="yellow";
                }
            }
            postazione.css("background-color", color);
        },
        error: function() {
            $("#modalErr").modal();
        }
    });


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
            aggiornaMappa();
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

    for(var i=0; i<data.length; i++){
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
    var corridoio='<div style="background-color: #B07840; grid-row: 4 / span 3; grid-column: 15 ; "><i class="fas fa-long-arrow-alt-up"></i></div>';
    var torretta='<div style="background-color: firebrick; grid-row: 3 ; grid-column: 15 ; "><i class="far fa-life-ring"></i></div>';
    $("#mappaLido").append(mare);
    $("#mappaLido").append(battigia);
    $("#mappaLido").append(cabine);
    $("#mappaLido").append(wc);
    $("#mappaLido").append(bar);
    $("#mappaLido").append(pedana);
    $("#mappaLido").append(ingresso);
    $("#mappaLido").append(corridoio);
    $("#mappaLido").append(torretta);
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
