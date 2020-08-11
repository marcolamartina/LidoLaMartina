<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Modifica dati</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
<!-- JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<script src="js/generale/signin.js"></script>

</head>
<body>
	<%@ include file="/WEB-INF/utils/header.jsp"%>
	<div class="container">
		
		<div class="jumbotron">
			<h1>Modifica i tuoi dati personali</h1>
		</div>
	
	<!-- Contenitore del form di modifca dei dati -->
		<div>
			<form class="form-horizontal" action="ModificaDati" method="post">
				<div class="form-group">
	      			<label class="control-label col-sm-2" for="nome">Nome:</label>
	      			<div class="col-sm-10">
	        			<input type="text" class="form-control" id="nome" placeholder="Inserisci il tuo nome" name="nome" oninput="validateName()" required="required">
	      			</div>
	    		</div>
	    		<div class="form-group">
	      			<label class="control-label col-sm-2" for="cognome">Cognome:</label>
	      			<div class="col-sm-10">
	        			<input type="text" class="form-control" id="cognome" placeholder="Inserisci il tuo cognome" name="cognome" oninput="validateSurname()" required="required">
	      			</div>
	    		</div>
	    		<div class="form-group">
	      			<label class="control-label col-sm-2" for="email">Email:</label>
	      			<div class="col-sm-10">
	        			<input type="email" class="form-control" id="email" placeholder="Inserisci l'indirizzo email" name="email" oninput="validateEmail()" required="required">
	      			</div>
	    		</div>
	    		<div class="form-group">
	      			<label class="control-label col-sm-2" for="cellulare">Cellulare:</label>
	      			<div class="col-sm-10">
	        			<input type="text" class="form-control" id="cellulare" placeholder="Inserisci il tuo numero di cellulare" name="cellulare" oninput="validateCel()" required="required">
	      			</div>
	    		</div>
	    		<div class="form-group">        
	      			<div class="col-sm-offset-2 col-sm-10">
	        			<button type="submit" class="btn btn-primary" name="commit">Submit</button>
	      			</div>
	    		</div>
	  		</form>
	  	</div>	
	</div>
	
	
	
	<% if(session.getAttribute("account")!=null){ 
		Account account=(Account)session.getAttribute("account"); %>
		<script>
			$("#nome").val("<%= account.getUtente().getNome()%>");
			$("#cognome").val("<%= account.getUtente().getCognome()%>");
			$("#email").val("<%= account.getUtente().getEmail()%>");
			$("#cellulare").val("<%= account.getUtente().getCellulare()%>");
		</script>
		<% } %>

</body>
</html>