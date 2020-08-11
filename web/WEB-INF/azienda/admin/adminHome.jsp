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

<script src="js/azienda/admin/adminHome.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	

	
	<div class="jumbotron">
		<h1>Ciao ${account.utente.nome}!</h1>
		<p>Da questa sezione potrai inserire, modificare ed eliminare gli account aziendali.</p>
	</div>
	
	<div>
		<h2 id="nessunAccount" style="display:none">Non hai ancora registrato nessun account aziendale</h2>
        <br />
        <div id="containerTabella" >
        	<div style="width:50%">
        		<h4>Legenda</h4>
	  			<table id="legenda" class="table table-borderless w-auto table-sm">
	      			<tbody>
	      				<tr><td>Bag</td><td>=</td><td>Bagnino</td></tr>
	      				<tr><td>Cas</td><td>=</td><td>Cassa</td></tr>
	      				<tr><td>CB</td><td>=</td><td>Cucina-Bar</td></tr>
	      				<tr><td>Rec</td><td>=</td><td>Reception</td></tr>
	      				<tr><td>Cam</td><td>=</td><td>Cameriere</td></tr>
	      				
	      			</tbody>
	  			</table>
			</div>
			
        	<div >
        		<label for="plusButton">Aggiungi un nuovo account aziendale: </label>
        		<a href="AggiungiRuolo"><button id="plusButton" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></button></a>
        		
        	</div>
        	<br />
	        <div class="table-responsive-sm ">
	  			<table id="tabella" class="table table-hover table-sm">
	  				<thead>
	        			<tr>
		          			<th>Nome</th>
		          			<th>Bag</th>
		          			<th>Cas</th>
		          			<th>CB</th>
		          			<th>Rec</th>
		          			<th>Cam</th>
	        			</tr>
	      			</thead>
	      			<tbody id="tablebody">
	      			</tbody>
	  			</table>
			</div>
		</div>	
	</div>
	
	
</div>
	
</body>