var sdraioMax;

$(document).ready(function() {
    $("#conferma").hide();
    $("#sdraio").change(checkButtonVisibility);
    $("#legenda").append('<i class="fa fa-square" aria-hidden="true" style="color: green; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Selezionato</small><br />');
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
    $(".postazione").click(select);
    aggiornaMappa();
});

function aggiornaMappa(){

    $.ajax({
        url: "SpiaggiaMappa",
        method: "post",
        async:false,
        data: {data: $("#date").val()},
        success: function(data) {
            setPostazioni(data);
        },
        error: function() {
            $("#modalErr").modal();
        }
    });
}



function resetMappa(){
    $(".postazione").css("background-color", "white");
    $(".postazione").addClass("free");
    $(".postazione").removeClass("selected");
    $(".postazione").css("cursor", "");
    $("#conferma").hide();
}

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
        async:false,
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
    var battigia='<div style=" background-color: sandybrown; grid-row: 2; grid-column: 1 / span '+max_y+';"><small>BATTIGIA</small></div>';
    var cabine='<div style="background-color: palegreen; grid-row: '+(max_x+3)+'; grid-column: 1 / span 8;"><small>CABINE</small></div>';
    var wc='<div style="background-color: palegreen; grid-row: '+(max_x+3)+'; grid-column: 9 / span 2;"><small>WC</small></div>';
    var bar='<div style="background-color: palegreen; grid-row: '+(max_x+3)+'; grid-column: 11 / span 9; "><small>BAR</small></div>';
    var pedana='<div style="background-color: palegreen; grid-row: '+(max_x)+'/ span 3; grid-column: 11 / span 9; "><small>PEDANA</small></div>';
    var ingresso='<div style="background-color: palegreen; grid-row: '+(max_x+3)+'; grid-column: 20 / span 8; "><small>INGRESSO</small></div>';
    $("#mappaLido").append(mare);
    $("#mappaLido").append(battigia);
    $("#mappaLido").append(cabine);
    $("#mappaLido").append(wc);
    $("#mappaLido").append(bar);
    $("#mappaLido").append(pedana);
    $("#mappaLido").append(ingresso);
}

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

function checkButtonVisibility(){
    if($(".selected").length===0 && $("#sdraio").val()==="0"){
        $("#conferma").hide();
    }else{
        $("#conferma").show();
    }
}
