<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Account aziendale</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/azienda/admin/aggiungiRuolo.js"></script>

</head>
<body>

<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Aggiungi account aziendale</h1>
		<p>Potrai aggiungere un nuovo account aziendale compilando il form sottostante</p>
	</div>

	<!-- Pannello "Buttons" -->
	<div>
		<a href="AdminHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
		<a href="AggiungiRuolo" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-plus"></i> Nuovo account aziendale</button></a>
		<br />
		<br />
	</div>


	<!-- Contenitore del form di inserimento account -->
		<div>
			<form class="form-horizontal" action="javascript:searchAccount();">
				<div class="form-group">
	      			<label class="control-label col-sm-2" for="nome">Nome:</label>
	      			<div class="col-sm-10">
	        			<input type="text" class="form-control" id="nome" placeholder="Inserisci il nome" name="nome" oninput="validateName()" required="required">
	      			</div>
	    		</div>
	    		<div class="form-group">
	      			<label class="control-label col-sm-2" for="cognome">Cognome:</label>
	      			<div class="col-sm-10">
	        			<input type="text" class="form-control" id="cognome" placeholder="Inserisci il cognome" name="cognome" oninput="validateSurname()" required="required">
	      			</div>
	    		</div>
	    		<div class="form-group">        
	      			<div class="col-sm-offset-2 col-sm-10">
	        			<button type="submit" class="btn btn-primary" name="commit">Cerca</button>
	      			</div>
	    		</div>
			</form>
	  	</div>	

		
		
	<!-- Contenitore della tabella degli account trovati -->	
		<h2 id="nessunAccount" style="display:none">Non esiste nessun cliente che corrisponde ai dati che hai inserito</h2>
        <br />
		<div id="containerTabella" >
        	<div style="width:50%">
        		<h4>Legenda</h4>
	  			<table id="legenda" class="table table-borderless w-auto table-sm">
	      			<tbody>
	      				<tr><td>Bag</td><td>=</td><td>Bagnino</td></tr>
	      				<tr><td>Cas</td><td>=</td><td>Cassa</td></tr>
	      				<tr><td>Cuc</td><td>=</td><td>Cucina</td></tr>
						<tr><td>Bar</td><td>=</td><td>Bar</td></tr>
	      				<tr><td>Rec</td><td>=</td><td>Reception</td></tr>
	      				<tr><td>Cam</td><td>=</td><td>Cameriere</td></tr>
	      				
	      			</tbody>
	  			</table>
			</div>
			
        	
	        <div class="table-responsive-sm ">
	  			<table id="tabella" class="table table-hover table-sm">
	  				<thead>
	        			<tr>
		          			<th>Nome</th>
		          			<th>Bag</th>
		          			<th>Cas</th>
		          			<th>Cuc</th>
							<th>Bar</th>
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
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>