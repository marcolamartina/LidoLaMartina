<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Conferma ordinazione</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

<!-- JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<script src="js/azienda/cucina/prodottiCucina.js"></script>

<style>
	.line-through { text-decoration:line-through; }
</style>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Ingredienti</h1>
		<p>Puoi rimuovere e ripristinare dal menu i prodotti non presenti in cucina o al bar o di cui non sono disponibili tutti gli ingredienti</p>
	</div>

    <!-- Pannello "Buttons" -->
    <div>
        <a href="CucinaHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
        <a href="ProdottiCucina" ><button type="submit" class="btn btn-primary" name="commit">Prodotti</button></a>
        <br />
        <br />
    </div>
	
	<!-- Pannello "ProdottiBar" -->
       <div>
            <label for="selCategoria">Categoria: </label>
            <select class="form-control" id="selCategoria" name="categoria"></select>
            <br />
            <input class="form-control" id="search" type="text" placeholder="Cerca..">
        </div>
        <br />
        <br />
        <div id="menu">
        	
        </div>  
            
            
     
	
</div>
	
</body>