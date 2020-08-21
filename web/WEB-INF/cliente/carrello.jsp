<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Carrello</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

<!-- JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<script src="js/cliente/carrello.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Carrello ordini</h1>
		<p>Ecco il tuo carrello. Puoi modificare la tua ordinazione, inserire il numero del tavolo in cui vuoi consegnato l'ordine e confermare</p>
	</div>
	
	<!-- Pannello "Carrello" -->
       <div>
       		<h2 id="carrelloVuoto" style="display:none">
            Non ci sono ordini nel tuo carrello.<br>
            Seleziona "Ordina" per aggiungere i prodotti che desideri acquistare.
            </h2>
            <a href="FoodDrink" id="ordinaButton"><button class="btn btn-primary" id="ordinaButton">Ordina</button></a> 
            <br />
            <div id="contenutoCarrello">
             	<table class="table table-sm table-borderless" id="tabella">
            		<tbody id="tabellaOrdini">
            
            
            		</tbody>
            	</table>
            	
            	
            	<h2 id="totaleCarrello"></h2>
            	
            	
            	<div class="form-group">
  					<label for="tavolo">Numero del tavolo:</label>
            		<select class="form-control form-control-sm" style="width:auto;" id="tavolo">
            		<option value="0">Da portare via</option>
					<%for(int j=1; j<=25; j++){ %>
						<option><%=j%></option>
					<% } %>
					</select>
				</div>
				<p id="notaCarrello"> Attenzione! <i class="fa fa-exclamation-triangle"></i><br>
            		Ti ricordiamo che se scegli il numero del tavolo, quello che hai ordinato ti sarà recapitato direttamente 
            		al tavolo. Se si sceglie l'opzione "Da portare via" sarai chiamato per nome dal bancone del bar appena l'ordinazione sarà pronta</p>	
            	<button class="btn btn-primary" onclick="conferma()">Conferma</button>
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
	        				<button type="button" class="btn btn-danger" data-dismiss="modal">Chiudi</button>
	      				</div>
	
	    			</div>
	  			</div>
			</div>
            
           
	</div>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>