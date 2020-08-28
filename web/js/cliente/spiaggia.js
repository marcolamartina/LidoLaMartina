
var sdraioMax;

$(document).ready(init);

function init(){

    $("#conferma").hide();
    $("#sdraio").change(checkButtonVisibility);
    $("#legenda").append('<i class="fa fa-square" aria-hidden="true" style="color: green; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Selezionato</small><br />');
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
    $("#date").val(string_date);
    $('[type="date"]').prop('min', string_date);


}

/**
 * Aggiorna la mappa delle postazioni
 */
function aggiornaMappa(){

    $.ajax({
        url: "SpiaggiaMappa",
        method: "post",

        data: {data: $("#date").val()},
        success: function(data) {
            setPostazioni(data);
        },
        error: function() {
            $("#modalErr").modal();
        }
    });
}


/**
 * Resetta la mappa delle postazioni
 */
function resetMappa(){
    $(".postazione").css("background-color", "white");
    $(".postazione").addClass("free");
    $(".postazione").removeClass("selected");
    $(".postazione").css("cursor", "");
    $("#conferma").hide();
    $("#totale").hide();
}

/**
 * Setta le postazioni prenotate o occupate
 * @param data
 */
function setPostazioni(data){
    resetMappa();
    var sdraio=0;
    var colore;
    for(var i=0; i<data.length; i++){
        sdraio+=parseInt(data[i].sdraio);
        if(data[i].numero!==0){
            if(data[i].occupata===0){
                colore="red";
            }else{
                colore="yellow";
            }
            $("#postazione"+data[i].numero).css("cursor", "not-allowed");
            $("#postazione"+data[i].numero).css("background-color", colore);
            $("#postazione"+data[i].numero).removeClass("free");
        }
    }
    $("#sdraio").empty();
    $("#sdraio").append('<option>0</option>');
    for(var j=1; j<=sdraioMax-sdraio;j++){
        $("#sdraio").append('<option>'+j+'</option>');
    }


    $("#sdraioBattigia").text(sdraioMax-sdraio);

    // Aggiorna la mappa ogni 10 minuti.
    setInterval(function(){
        aggiornaMappa();
    }, 600000);
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
            $(".postazione").click(select);
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
 * Imposta una postazione come "selezionata"
 */
function select(){
    if($(this).hasClass("free")){
        if($(this).hasClass("selected")){
            $(this).removeClass("selected");
            $(this).css("background-color", "white");
        }else{
            $(this).addClass("selected");
            $(this).css("background-color","green");
        }
        var postazioni="";
        $(".selected").each(function(){
            postazioni+=("-"+$(this).attr("id").substr(10));
        });
        $("#posti").val(postazioni.substr(1));
        checkButtonVisibility();
    }

};

/**
 * Controlla se il tasto "conferma" va visualizzato
 */
function checkButtonVisibility(){
    if($(".selected").length===0 && $("#sdraio").val()==="0"){
        $("#conferma").hide();
        $("#totale").hide();
    }else{
        $("#conferma").show();
        $("#totale").show();
    }
    aggiornaTotale();
}

/**
 * Aggiorna il totale
 */
function aggiornaTotale(){
    var totale=parseFloat(($(".selected").length*getPrezzo())+7*parseInt($("#sdraio").val())).toFixed(2);
    $("#totale").html("Totale: "+totale+"&euro;")
}

/**
 * Ritorna il prezzo di una postazione
 * @returns {number}
 */
function getPrezzo(){
    switch($("#date").val().substr(5,2)){
        case "06":
            return 14.00;
            break;
        case "07":
            return 16.00;
            break;
        case "08":
            return 18.00;
            break;
        case "09":
            return 14.00;
            break;
        default:
            return 14.00;
            break;
    }
}
