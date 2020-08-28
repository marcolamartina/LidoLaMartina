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
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

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
		color: white;
		padding: 10px;
		text-align: center;
	}

	@media only screen and (min-width: 768px) {
		/* For desktop: */
		.imageContainer:hover .overlay {
			opacity: 1;
		}
		.overlay {
			opacity:0;
		}

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
			<a href="Spiaggia"><div class="overlay img-thumbnail">Spiaggia</div></a>
		</div>

		<div class="imageContainer" style="float: right; width: 52.2%">
			<a href="FoodDrink"><img class="img-fluid img-thumbnail image" src="img/food.svg" alt="food_drink" title="Food&Drink"/></a>
			<a href="FoodDrink"><div class="overlay img-thumbnail">Food & Drink</div></a>
		</div>

	</div>

</div>

<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>