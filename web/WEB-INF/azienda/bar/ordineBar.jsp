<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ordina</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/azienda/cameriere/ordineCameriere.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	<div class="jumbotron">
		<h1>Prendi le ordinazioni</h1>
		<p>Da questa sezione potrai prendere le ordinazioni per i clienti</p>
	</div>

	<!-- Pannello "Buttons" -->
	<div>
		<a href="BarHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
		<a href="OrdineBar" ><button type="submit" class="btn btn-primary" name="commit">Ordina</button></a>
		<a href="CheckoutBar" ><button type="submit" class="btn btn-primary" name="commit">Check-out</button></a>
		<a href="ProdottiBar" ><button type="submit" class="btn btn-primary" name="commit">Prodotti</button></a>
		<br />
		<br />
	</div>
	
	<!-- Contenitore del form di ricerca account -->
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
	  	
	  	
	 <!-- Contenitore dei risultati della ricerca -->
		<div id="risultatiRicerca">
			<h2 id="nessunAccount" style="display:none">Non esiste nessun utente che corrisponde ai dati che hai inserito</h2>
			<div id="containerRisultati" >
			</div>
			<br />
       		<br />
			<div id="containerMenu" style="display:none">
            	<label for="selCategoria">Categoria: </label>
            	<select class="form-control" id="selCategoria" name="categoria"></select>
            	<br />
            	<input class="form-control" id="search" type="text" placeholder="Cerca..">
        		<br />
       			<br />
       			<div id="menu"></div>
			</div>
		</div>	
	
	
  	<!-- The Modal -->
		<div class="modal" id="modalSucc">
  			<div class="modal-dialog">
    			<div class="modal-content">

      				<!-- Modal Header -->
      				<div class="modal-header">
        				<h4 class="modal-title">Aggiunto al carrello</h4>
        				<button type="button" class="close" data-dismiss="modal">&times;</button>
      				</div>

      				<!-- Modal body -->
      				<div class="modal-body">
        				Il prodotto è stato aggiunto al carrello dell'utente
      				</div>

      				<!-- Modal footer -->
      				<div class="modal-footer">
        				<button type="button" class="btn btn-success" data-dismiss="modal">Chiudi</button>
      				</div>

    			</div>
  			</div>
		</div>


</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>