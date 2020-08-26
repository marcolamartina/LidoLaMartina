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

<script src="js/azienda/cassa/cassaHome.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	<div class="jumbotron">
		<h1>Ciao ${account.utente.nome}!</h1>
		<p>Da questa sezione potrai far pagare ai clienti i loro conti</p>
	</div>
	
	
	<!-- Pannello "Buttons" -->
       <div>
       		<a href="CassaHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
       		<a href="StoricoIncassi" ><button type="submit" class="btn btn-primary" name="commit">Storico incassi</button></a>
       		<a href="LiberaPostazioneCassa" ><button type="submit" class="btn btn-primary" name="commit">Libera postazione</button></a>
            <br />
            <br />
		</div>
	
	<!-- Pannello "Conti" -->
       <div id="conti">
       		<h2 id="contiVuoti" style="display:none">Non ci sono conti aperti</h2>
            <br />
            <input class="form-control" id="search" type="text" placeholder="Cerca..">
            <br />
		</div>
	
  	
  	<div class="modal" id="modalSucc">
  			<div class="modal-dialog">
    			<div class="modal-content">

      				<!-- Modal Header -->
      				<div class="modal-header">
        				<h4 class="modal-title">Operazione completata</h4>
        				<button type="button" class="close" data-dismiss="modal">&times;</button>
      				</div>

      				<!-- Modal body -->
      				<div class="modal-body">
        				Pagamento effettuato correttamente
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