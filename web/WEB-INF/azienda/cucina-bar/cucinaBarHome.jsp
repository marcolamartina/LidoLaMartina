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

<script src="js/azienda/cucina-bar/cucinaBarHome.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	<div class="jumbotron">
		<h1>Ciao ${account.utente.nome}!</h1>
		<p>Da questa sezione potrai vedere gli ordini dei clienti, ordinare per loro, togliere dal menu i prodotti di cui non ci sono gli ingredienti in cucina</p>
	</div>
	
	
	<!-- Pannello "Buttons" -->
       <div>
       		<a href="CucinaBarHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
       		<a href="OrdineCucinaBar" ><button type="submit" class="btn btn-primary" name="commit">Ordina</button></a>
       		<a href="CheckoutCucinaBar" ><button type="submit" class="btn btn-primary" name="commit">Check-out</button></a>
       		<a href="Prodotti" ><button type="submit" class="btn btn-primary" name="commit">Prodotti</button></a>
            <br />
            <br />
		</div>
	
	<!-- Pannello "Ordini" -->
       <div>
       		<h2 id="ordiniVuoti" style="display:none">Non ci sono ordinazioni</h2>
            <br />
            <input class="form-control" id="search" type="text" placeholder="Cerca..">
            <br />
            <div id="ordini">
            </div>
		</div>
	
	
  	
 		
	
</div>
	
</body>