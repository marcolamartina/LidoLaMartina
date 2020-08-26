<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Gorgo Beach</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

<!-- JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<style>

	.imageContainer {
		position: relative;

	}

	.overlay {
		position: absolute;
		bottom: 0;
		background: rgba(83,109,254, 0.5);
		width: 100%;
		transition: .5s ease;
		opacity:0;
		color: white;
		padding: 10px;
		text-align: center;
	}

	.imageContainer:hover .overlay {
		opacity: 1;
	}

</style>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">



	<div class="jumbotron">
		<h1>Ciao ${account.utente.nome}!</h1>
		<p>Puoi prenotare in comodità sdraio e ombrelloni ma anche cibo e bevande.</p>
		<p>Clicca sull'immagine di destra per la sezione riservata al bar e su quella di sinistra per la spiaggia.</p>
	</div>
	
	<div style="display: inline-block">
		<div class="imageContainer" style="float: left; width: 47.8%">
			<a href="Spiaggia"><img class="img-fluid img-thumbnail image" src="img/spiaggia.svg" alt="spiaggia" title="Spiaggia"/></a>
			<div class="overlay">Spiaggia</div>
		</div>

		<div class="imageContainer" style="float: right; width: 52.2%">
			<a href="FoodDrink"><img class="img-fluid img-thumbnail image" src="img/food.svg" alt="food_drink" title="Food&Drink"/></a>
			<div class="overlay">Food & Drink</div>
		</div>

	</div>

</div>

<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>