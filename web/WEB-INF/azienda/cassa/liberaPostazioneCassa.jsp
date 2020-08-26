<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Libera Postazione</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/azienda/cassa/liberaPostazione.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	<div class="jumbotron">
		<h1>Libera postazione</h1>
		<p>Da questa sezione potrai eseguire il checkout dei clienti in modo da rendere di nuovo disponibile la loro postazione</p>
	</div>
	
	<!-- Pannello "Buttons" -->
       <div>
       		<a href="CassaHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
       		<a href="StoricoIncassi" ><button type="submit" class="btn btn-primary" name="commit">Storico incassi</button></a>
       		<a href="LiberaPostazioneCassa" ><button type="submit" class="btn btn-primary" name="commit">Libera postazione</button></a>
            <br />
            <br />
		</div>
	
	<!-- Pannello "Postazioni" -->
       <div id="postazioni">
       		<h2 id="postazioniVuote" style="display:none">Non ci sono postazioni da liberare</h2>
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
        				Postazione liberata correttamente
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