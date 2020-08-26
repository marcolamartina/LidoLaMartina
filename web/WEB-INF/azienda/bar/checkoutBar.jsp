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
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/azienda/cameriere/checkoutCameriere.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Conferma ordinazione</h1>
		<p>Puoi modificare le ordinazioni effettuate per i clienti, inserire il numero del tavolo in cui vuoi che sia consegnato l'ordine e confermare</p>
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
	
	<!-- Pannello "Carrelli" -->
       <div id="comande">
       		<h2 id="comandeVuote" style="display:none">
            Non ci sono ordini da confermare.<br>
            Seleziona "Ordina" per effettuare nuove ordinazioni.
            </h2>
     
        </div>    
            
            <!-- The Modal -->
			<div class="modal" id="modalSucc">
	  			<div class="modal-dialog">
	    			<div class="modal-content">
	
	      				<!-- Modal Header -->
	      				<div class="modal-header">
	        				<h4 class="modal-title">Ordine effettuato</h4>
	        				<button type="button" class="close" data-dismiss="modal">&times;</button>
	      				</div>
	
	      				<!-- Modal body -->
	      				<div class="modal-body">
	        				L'ordinazione è stata effettuata con successo e sarà consegnata al più presto
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