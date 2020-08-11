$(document).ready(function() {
	$("#date").change(aggiornaIncassi);
	var date=new Date();
	var string_date=date.getFullYear()+'-';
	if(date.getMonth()<10){
		string_date+='0';
	}
	string_date+=date.getMonth()+'-';
	if(date.getDate()<10){
		string_date+='0';
	}
	string_date+=date.getDate();
	$("#date").val(string_date);
	aggiornaIncassi();
});

function aggiornaIncassi(){
	$.ajax({
		url: "StoricoIncassi",
		method: "post",
		data: {data: $("#date").val()},
		success: function(data) {
			mostraStoricoIncassi(data);
			},
		error: function() {
			$("#modalErr").modal();
		}
	});
}

function mostraStoricoIncassi(data){
	
	data[0].giorno != undefined ? $("#giorno").html(parseFloat(data[0].giorno).toFixed(2)+"&euro;") : $("#giorno").html("0.00&euro;");
	data[1].settimana != undefined ? $("#settimana").html(parseFloat(data[1].settimana).toFixed(2)+"&euro;"): $("#settimana").html("0.00&euro;");
	data[2].mese != undefined ? $("#mese").html(parseFloat(data[2].mese).toFixed(2)+"&euro;"): $("#mese").html("0.00&euro;");
	data[3].anno != undefined ? $("#anno").html(parseFloat(data[3].anno).toFixed(2)+"&euro;"): $("#anno").html("0.00&euro;");
}
